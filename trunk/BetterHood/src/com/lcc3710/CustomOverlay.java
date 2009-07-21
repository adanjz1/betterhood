package com.lcc3710;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
 
public class CustomOverlay extends Overlay {
 
	
	
	public PopupWindow window;
	private TextView text;
	private static final int CIRCLERADIUS = 2;
 
	private GeoPoint geopoint;
 
	public CustomOverlay(GeoPoint point) {
		geopoint = point;
	}
 
 
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// Transfrom geoposition to Point on canvas
		Projection projection = mapView.getProjection();
		Point point = new Point();
		projection.toPixels(geopoint, point);
 
		// background
		Paint background = new Paint();
		background.setColor(Color.WHITE);
		RectF rect = new RectF();
		rect.set(point.x + 2 * CIRCLERADIUS, point.y - 4 * CIRCLERADIUS,
				point.x + 90, point.y + 12);
 
		// text "My Location"
		Paint text = new Paint();
		text.setAntiAlias(true);
		text.setColor(Color.BLUE);
		text.setTextSize(12);
		text.setTypeface(Typeface.MONOSPACE);
 
		// the circle to mark the spot
		Paint circle = new Paint();
		circle.setColor(Color.BLUE);
		circle.setAntiAlias(true);
 
		canvas.drawRoundRect(rect, 2, 2, background);
		canvas.drawCircle(point.x, point.y, CIRCLERADIUS, circle);
		canvas.drawText("My Location", point.x + 3 * CIRCLERADIUS, point.y + 3
				* CIRCLERADIUS, text);
	//	text = new TextView(getBaseContext());
	}
    @Override

	public boolean onTouchEvent(MotionEvent event, MapView mapView) 
    {   
        //---when user lifts his finger---
    	//if (event.getAction() == 1) {        
    	//	PopUp popup = new PopUp("This is your current location"); 
    		//popup.makeWindow();
    	//}
    	//window = new PopupWindow(this.getViewInflate().inflate(R.layout.main1,null,null),0,0); 
    	
        return false;
    }        
    
    public String type(){
    	return "myLocation";
    }


	
    }