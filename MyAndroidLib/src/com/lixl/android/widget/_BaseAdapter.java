package com.lixl.android.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.lixl.R;
import com.lixl.bean.ChatRecordItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * BaseAdapter  extends Object	
 * 				implements ListAdapter, SpinnerAdapter
 */
public class _BaseAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater = null;
	private ArrayList<ChatRecordItem> mListItems;
	private List<Map<String, Object>> mListMaps;
	//自定义一个 构造函数
	public _BaseAdapter(Context context, ArrayList<ChatRecordItem> listItems, List<Map<String, Object>> listMaps) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mListItems = listItems;
		mListMaps = listMaps;
	}
	
	@Override
	public int getCount() {
		//此方法表示  数据源一共有几条数据
		return mListItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * getView 表示适配器每一条 返回的视图
	 * 			可以在此动态添加 任意View，也可以返回指定的 Layout
	 * 【参数】
	 * position就是位置从0开始
	 * convertView 就是每一项要显示的view。通常return的view就是convertView
	 * parent	就是父窗体，即 那个 a.setAdapter(mAdapter)中的a。
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//1.动态添加view	缺点是数据量大时，需要每次都创建一个view，效率不高
		
//		TextView mTextView = new TextView(mContext);
//        mTextView.setText("BaseAdapterDemo");
//        mTextView.setTextColor(android.graphics.Color.RED);
//        return mTextView;
		
        //2. 在此指定Layout	只创建一次，其余都利用了缓存中的view，提升了性能
		
//        if(convertView == null)
//        	convertView = mInflater.inflate(R.layout.layout_chat_record_item, null);
//	    TextView mTextView = (TextView)convertView.findViewById(R.id.chat_record_txt_name);
//	    mTextView.setText("BaseAdapterDemo" + position);
//	    mTextView.setTextColor(android.graphics.Color.RED);
//	    return convertView;
	    
	    //3. 利用 convertView + ViewHolder 实现。 ViewHolder是个静态类，关键好处是可以缓存显示数据的视图，加快了 UI的响应速度。
	    ViewHolder holder = null;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_chat_record_item, null);
            holder.avatar = (ImageView)convertView.findViewById(R.id.chat_record_img_avatar);
            //holder.name = (TextView)convertView.findViewById(R.id.chat_record_txt_name);
            holder.timeago = (TextView)convertView.findViewById(R.id.chat_record_txt_timeago);
            holder.content = (TextView)convertView.findViewById(R.id.chat_record_txt_content);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
//            holder.avatar.setImageResource(R.drawable.ic_launcher);
//            holder.name.setText("小黑");
//            holder.timeago.setText("昨天 晚上");
//            holder.content.setText("尼滚....");
        ChatRecordItem item = mListItems.get(position);
//            holder.avatar.setImageBitmap(bitmap);
//            holder.avatar.setImageDrawable(drawable);
//            holder.avatar.setImageResource(resId);
        holder.avatar.setImageURI(item.getAvatar());	//setImageURI(Uri)也是令人头疼，同一个Uri,但图像内容变了，有时居然还是显示前一个的。
        holder.name.setText(item.getName());
        holder.timeago.setText(item.getTimeAgo());
        holder.content.setText(item.getContent());
            
        Map<String, Object> map = mListMaps.get(position);
        holder.name.setText((String)map.get("name"));
        
        return convertView;
	}
	
	static class ViewHolder
    {
        public ImageView avatar;
        public TextView name;
        public TextView timeago;
        public TextView content;
    }

}
