package com.lixl.app;

import com.lixl.R;
import com.lixl.android.widget._ImageView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class _Dialog extends Dialog implements DialogInterface {

	private View mView;
	private TextView mValueView;
	private _ImageView mBgImageView;
	private ImageView mFgImageView;
	
	
	public _Dialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initDialog();
	}
	
	public _Dialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		initDialog();
	}

	public _Dialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
		initDialog();
	}
	
	private void initDialog(){
		mView = getLayoutInflater().inflate(R.layout.dialog, null);
		mValueView = (TextView) mView.findViewById(R.id.valueText);
		mFgImageView = (ImageView) mView.findViewById(R.id.roateArrowImageView);
		mBgImageView = (_ImageView) mView.findViewById(R.id.bgImageView);
	}
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		super.onCreate(savedInstanceState);
//
//		setContentView(mView);
//
//		setRotateAnimation();
//	}
//
//	private void setRotateAnimation() {
//		RotateAnimation animation = new RotateAnimation(0, 360,
//				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
//				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//		animation.setDuration(1500);
//		animation.setRepeatCount(Animation.INFINITE);
//		animation.setInterpolator(new LinearInterpolator());
//		animation.setRepeatMode(Animation.INFINITE);
//		mFgImageView.startAnimation(animation);
//
//	}
//
//	public void updateProgress(float radian) {
//		mBgImageView.update(radian);
//		mValueView.setText(((int) (radian * 100)) + "%");
//	}

}
