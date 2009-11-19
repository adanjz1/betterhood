package com.gregbugaj.tabwidget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Tab {
	private static final String TAG = Tab.class.getSimpleName();
	private int resourceIcon;
	private int resourceIconSelected=0;

	private Activity context;
	private Intent intent;

	private ImageButton btn;
	private String tabTag;

	public int preferedHeight=-1;
	private boolean isSelected;
	private int transparentResourceID;
	private Dialog dialog;
	private int requestCode=-1;
	
	private View targetView;

	public Tab(Activity context, String tabTag) {
		if(context==null){
			throw new IllegalStateException("Context can't be null");
		}
		this.tabTag=tabTag;
		this.context=context;	
	}

	public void setIcon(int resourceIcon) {
		this.resourceIcon=resourceIcon;

	}

	public void setIconSelected(int resourceIcon) {
		this.resourceIconSelected=resourceIcon;
	}
	
	public void setTargetView(View v) {
		this.targetView = v;
	}
	
	public View getTargetView() {
		return targetView;
	}

	public View getView() {
		if(btn==null){
			createView();
		}
		return btn;
	}

	public void createView() {
		btn = new ImageButton(context);
		btn.setTag(TAG);
		btn.setMaxHeight(preferedHeight);
		btn.setMinimumHeight(preferedHeight);
		btn.setPadding(0, 0, 0, 0);
		int iconId=resourceIcon;

		if(isSelected && resourceIconSelected!=0){
			iconId=resourceIconSelected;
		}
		Bitmap icon=BitmapFactory.decodeResource(context.getResources(), iconId);

		btn.setBackgroundResource(transparentResourceID);
		btn.setImageBitmap(icon);
		btn.setTag(tabTag);
	}

	public void bindListeners(OnClickListener c, OnTouchListener t) {
		btn.setOnClickListener(c);
		btn.setOnTouchListener(t);
	}

	public String getTag() {
		return tabTag;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		int iconId=resourceIcon;

		if(isSelected && resourceIconSelected!=0){
			iconId=resourceIconSelected;
		}
		btn.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), iconId));
	}

	public void setTransparentResourceID(int transparentResourceID) {
		this.transparentResourceID = transparentResourceID;
	}

	public void setDialog(Dialog dialog) {
		this.dialog=dialog;		
	}


}
