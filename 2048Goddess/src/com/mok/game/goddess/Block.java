package com.mok.game.goddess;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
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
		//params.setMargins(5, 5, 5, 5);
		params.gravity = Gravity.CENTER;
		TextView txtBg = new TextView(mContext);
		txtBg.setBackgroundColor(this.getResources().getColor(android.R.color.white));
		this.addView(txtBg, params);
		
		blockWidth -= 10;
		params = new LayoutParams(blockWidth, blockWidth);
		params.gravity = Gravity.CENTER;
		mImgStar = new ImageView(mContext);
		mImgStar.setBackgroundColor(this.getResources().getColor(android.R.color.darker_gray));
		this.addView(mImgStar, params);
		
		blockWidth -= 10;
		params = new LayoutParams(blockWidth, blockWidth);
//		params.gravity = Gravity.CENTER;
		mTxtStar = new TextView(mContext);
		//mTxtStar.setBackgroundColor(0xFF0000);
		mTxtStar.setTextColor(this.getResources().getColor(android.R.color.black));
//		mTxtStar.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
		mTxtStar.setGravity(Gravity.CENTER);
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
			mImgStar.setBackgroundResource(starPic);
//			setText(starName);
			//mImgStar.setImageResource(starPic);
			//mImgStar.setScaleType(ScaleType.FIT_XY);
			System.out.println("value=" + value);
		}
		else{
			mImgStar.setBackgroundColor(this.getResources().getColor(android.R.color.darker_gray));
			mImgStar.setImageResource(0);
			mTxtStar.setText("");
		}
		
		//setBackground();
	}
	
	public void setText(String value){
		mTxtStar.setText(value);
	}
	
	//String[] starNames = {"韩雪", "高圆圆", "张庭", "林熙蕾", "蒋勤勤", "刘亦菲", "周慧敏", "白百合", "黄圣依", "杨幂", "王祖贤", "林心如", "林志玲", "杨颖"};
	int[] starImages = {R.drawable.hx, R.drawable.zt, R.drawable.gyy, R.drawable.lxl, R.drawable.jqq, R.drawable.lyf, R.drawable.sara, R.drawable.bbh, R.drawable.hsy, R.drawable.ym, R.drawable.ynj, R.drawable.lxr, R.drawable.lzl, R.drawable.yy};
	String[] starNames = this.getResources().getStringArray(R.array.starNames);

	
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
