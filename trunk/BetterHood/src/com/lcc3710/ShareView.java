package com.lcc3710;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

public class ShareView extends View {
	private int screenWidth;
	private int screenHeight;
	
	private Bitmap homeIcon;
	
	public ShareView(Context context) {
		super(context);
		init();
	}

	public ShareView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ShareView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		this.setBackgroundColor(Color.WHITE);
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
	}

	/* (non-Javadoc)
	 * @see android.view.View#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		
		if (homeIcon == null) {
			homeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon);
			homeIcon = Bitmap.createScaledBitmap(homeIcon, 35, 35, true);
		}
		if ((screenWidth == 0) | (screenHeight == 0)) {
			screenWidth = this.getWidth();
			screenHeight = this.getHeight();
		}
		
		canvas.drawBitmap(homeIcon, 
				screenWidth/2 - homeIcon.getWidth()/2, 
				screenHeight/2 - homeIcon.getHeight()/2, 
				null);
	}

}
