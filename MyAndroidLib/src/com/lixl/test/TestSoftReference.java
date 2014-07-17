package com.lixl.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * 内存缓存
 * 相册图片预取缓存策略是内存缓存（硬引用LruCache、软引用SoftReference<Bitmap>）、外部文件缓存（context.getCachedDir()）
 * @author Administrator
 * 
 * 只是理论上测试，并未实际运行
 *
 */
public class TestSoftReference extends Activity {
	private static final String TAG = TestSoftReference.class.getSimpleName();

	//开辟8M硬缓存空间
	private final int hardCachedSize = 8*1024*1024;
	//hard cache
	private final LruCache<String, Bitmap> sHardBitmapCache = new LruCache<String, Bitmap>(hardCachedSize){
		@Override
		public int sizeOf(String key, Bitmap value){
			return value.getRowBytes() * value.getHeight();
		}
		@Override
		protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue){
			Log.v(TAG, "hard cache is full, push to soft cache");
			//硬引用缓冲区清除：将一个最不常用的oldValue推入到软引用缓存区
			sSoftBitmapCache.put(key, new SoftReference<Bitmap>(oldValue));
		}
	};
	//软引用
	private static final int SOFT_CACHE_CAPACITY = 40;
	private final static LinkedHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache 
		= new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_CAPACITY, 0.75f, true){
		@Override
		public SoftReference<Bitmap> put(String key, SoftReference<Bitmap> value){
			return super.put(key, value);
		}
		@Override
		protected boolean removeEldestEntry(LinkedHashMap.Entry<String, SoftReference<Bitmap>> eldest){
			if(size() > SOFT_CACHE_CAPACITY){
				Log.v(TAG, "Soft Reference limit, purge one");
				return true;
			}
			return false;
		}
	};
	//缓存bitmap
	public boolean putBitmap(String key, Bitmap bitmap){
		if(bitmap != null){
			synchronized (sHardBitmapCache) {
				sHardBitmapCache.put(key, bitmap);
			}
			return true;
		}
		return false;
	};
	//从缓存中获取Bitmap
	public Bitmap getBitmap(String key) {
		synchronized (sHardBitmapCache) {
			final Bitmap bitmap = sHardBitmapCache.get(key);
			if(bitmap != null)
				return bitmap;
		}
		//硬引用缓存区间中，读取失败，从软引用缓冲区间读取
		synchronized (sSoftBitmapCache) {
			SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(key);
			if(bitmapReference != null){
				final Bitmap bitmap2 = bitmapReference.get();
				if(bitmap2 != null){
					return bitmap2;
				}else {
					Log.v(TAG, "soft reference 已经被回收");
					sSoftBitmapCache.remove(key);
				}
			}
		}
		return null;
	}
	//2. 外部文件保存
	//private File mCacheDir = context.getCacheDir();
	private File mCacheDir = this.getCacheDir();
	private static final int MAX_CACHE_SIZE = 20 * 1024 * 1024; // 20M
	private final LruCache<String, Long> sFileCache = new LruCache<String, Long>(MAX_CACHE_SIZE){
		@Override
		public int sizeOf(String key, Long value){
			return value.intValue();
		}
		@Override
		protected void entryRemoved(boolean evicted, String key, Long oldValue, Long newValue){
			File file = null;
			try {
				file = getFile(key);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(file != null)
				file.delete();
		}
	};
	
	private File getFile(String fileName) throws FileNotFoundException {
		File file = new File(mCacheDir, fileName);
		if(!file.exists() || !file.isFile())
			throw new FileNotFoundException("文件不存在或有同名文件夹");
		return file;
	}
	//缓存bitmap到外部存储
	public boolean putBitmap2(String key, Bitmap bitmap) throws IOException{
		File file = getFile(key);
		if(file != null){
			Log.v(TAG, "文件已存在");
			return true;
		}
		FileOutputStream fos = getOutputStream(key);
		boolean saved = bitmap.compress(CompressFormat.JPEG, 100, fos);
		fos.flush();
		fos.close();
		if(saved){
			synchronized (sFileCache) {
				sFileCache.put(key, getFile(key).length());
			}
			return true;
		}
		return false;
	}
	//根据key获取OutputStream
	private FileOutputStream getOutputStream(String key) throws FileNotFoundException{
		if(mCacheDir == null)
			return null;
		FileOutputStream fos = new FileOutputStream(mCacheDir.getAbsoluteFile() + File.separator + key);
		return fos;
	}
	//获取bitmap
	private static BitmapFactory.Options sBitmapOptions;
	static {
		sBitmapOptions = new BitmapFactory.Options();
		sBitmapOptions.inPurgeable = true;	//bitmap can be purged to disk
	}
	public Bitmap getBitmap2(String key) throws FileNotFoundException{
		File bitmapFile = getFile(key);
		if(bitmapFile != null){
			Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(bitmapFile), null, sBitmapOptions);
			if(bitmap != null){
				//重新将其缓存至硬引用中
				//...
			}
		}
		return null;
	}
	//3.从服务器下载图片
	private static String generateKey(String fileId, int width, int height){
		String ret = fileId + "_" + Integer.toString(width) + "x" + Integer.toString(height);
		return ret;
	}
	
	/*
 String key = generateKey(...)即可生成唯一的key值
下载成功后调用1内存缓存的putBitmap()函数,缓存图片。
在外部文件缓存中也写入一份，调用2的putBitmap()函数.
4.预览图片的流程
1) 如果预览的图片在内存缓存区中，直接调用1的getBitmap()函数，获取bitmap数据(先在硬引用缓存区查找匹配，若硬引用区匹配失败，再去软引用区匹配)
2) 如果从内存缓存区读取失败，再从外部文件缓存中读取，调用2的getBitmap()函数
3) 如果从外部文件缓存中读取失败，则从服务端下载该图片，过程3.
5.生成key值*/
}
