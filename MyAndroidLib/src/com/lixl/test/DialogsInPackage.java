package com.lixl.test;

import android.app.Dialog;

public class DialogsInPackage {
	
	private static DialogsInPackage mUIUtils;
	private static final int TOAST_DURATION = 2000;
	
	public static DialogsInPackage getInstance(){
		if(mUIUtils == null)
			mUIUtils = new DialogsInPackage();
		return mUIUtils;
	}

	private Dialog mDialog;
}
