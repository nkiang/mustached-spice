package com.mok.game.goddess;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class Block extends FrameLayout {

	private Context mContext;
	//private FrameLayout mFrameLayoutBlock;
	private TextView mTxtStar;
	private ImageView mImgStar;
	
	private int value;
	
	public Block(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initInstance(context);
	}
	
	public Block(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initInstance(context);
	}

	public Block(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initInstance(context);
	}
	
//	@Override
//	protected void onFinishInflate(){
//		super.onFinishInflate();
//
//		findView();
//	}
	
	private void findView(){
		//mFrameLayoutBlock = (FrameLayout)findViewById(R.id.block_frame);
	}

	protected void initInstance(Context context) {
		mContext = context;

		value = 0;
		
//		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
//		mFrameLayoutBlock = (FrameLayout)layoutInflater.inflate(R.layout.block, null);
//		mImgStar = (ImageView)mFrameLayoutBlock.findViewById(R.id.block_img_bg);
//		mTxtStar = (TextView)mFrameLayoutBlock.findViewById(R.id.block_txt_name);
		
		WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		int widthScreen = wm.getDefaultDisplay().getWidth();
		int blockWidth = Math.round(widthScreen / 5);
		
		//FrameLayout.LayoutParams params = new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		FrameLayout.LayoutParams params = new LayoutParams(blockWidth, blockWidth);
		mImgStar = new ImageView(mContext);
		mImgStar.setBackgroundColor(0x0E0E0E);
		this.addView(mImgStar, params);
		
		params = new LayoutParams(blockWidth, blockWidth);
		mTxtStar = new TextView(mContext);
		mTxtStar.setBackgroundColor(0xFF0000);
		mTxtStar.setTextColor(000000);
		mTxtStar.setTextSize(R.dimen.block_text_size);
		this.addView(mTxtStar, params);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		//String text = String.valueOf(value);
		int indexStar = 0;
		for(int i = 1; i < 20; i++){
			if((int)Math.pow(2, i) == value){
				indexStar = i;
				break;      
			}
		}
		String starName = starNames[indexStar];
		int starPic = starImages[indexStar];
		if (value != 0){
			setText(starName);
			//mImgStar.setBackgroundResource(starPic);
			mImgStar.setImageResource(starPic);
			mImgStar.setScaleType(ScaleType.FIT_XY);
			System.out.println("value=" + value);
		}
		else
			mTxtStar.setText("");
		
		//setBackground();
	}
	
	public void setText(String value){
		mTxtStar.setText(value);
	}
	
	String[] starNames = {"韩雪", "高圆圆", "张庭", "林熙蕾", "蒋勤勤", "刘亦菲", "周慧敏", "白百合", "黄圣依", "杨幂", "王祖贤", "林心如", "林志玲", "杨颖"};
	int[] starImages = {R.drawable.hx, R.drawable.gyy, R.drawable.zt, R.drawable.lxl, R.drawable.jqq, R.drawable.lyf, R.drawable.zhm, R.drawable.bbh, R.drawable.hsy, R.drawable.ym, R.drawable.wzx, R.drawable.lxr, R.drawable.lzl, R.drawable.yy};

	
//	@Override
//	protected void onDraw(Canvas canvas) {
//		// TODO Auto-generated method stub
//		super.onDraw(canvas);
//		mWidth = getWidth();
//		mHeight = getHeight();
//		if (mSlideBitmap == null) {
//			return;
//		}
//		drawSlideDrawable(canvas);
//	}
//
//	private void drawSlideDrawable(Canvas canvas) {
//		Paint paint = new Paint();
//		Rect rect = new Rect();
//		rect.left = (mWidth - mSlideW) / 2;
//		rect.top = 3;
//		rect.right = rect.left + mSlideW;
//		rect.bottom = rect.top + mSlideH;
//		canvas.drawBitmap(mSlideBitmap, null, rect, paint);
//	}
}
