package com.lixl.android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

@SuppressLint("ResourceAsColor")
public class _ImageView extends ImageView {

	public _ImageView(Context context){
		super(context);
		init(context);
	}
	
	public _ImageView(Context context, AttributeSet attrs){
		super(context, attrs);
		init(context);
	}
	
	public _ImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private static final int CIRCLE_DEGREE = 360;
	private int mThisWidth, mThisHeight;
	private float mStart = -90;
	private float mSweep;
	

	private void init(Context context){
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mThisWidth = getMeasuredWidth();
		mThisHeight = getMeasuredHeight();
		
		drawArc(canvas);
	}
	
	@SuppressLint("ResourceAsColor")
	private void drawArc(Canvas canvas){
		int radius = mThisWidth / 2 - 12;
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setStyle(Paint.Style.FILL);
		p.setColor(0x882f90ba);
		RectF rectF = new RectF(12, 12, radius * 2 + 12, radius * 2 + 12);
		canvas.drawArc(rectF, mStart, mSweep, true, p);
	}
	
	public void update(float mProgressValue){
		mSweep = (int) (CIRCLE_DEGREE * mProgressValue);
		if(mSweep >= CIRCLE_DEGREE)
			mSweep = CIRCLE_DEGREE;
		else
			invalidate();
	}
	
	private float getDenstiyValue(float value){
		return getDensity(getContext()) * value;
	}
	
	private float getDensity(Context context){
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}
	
}
