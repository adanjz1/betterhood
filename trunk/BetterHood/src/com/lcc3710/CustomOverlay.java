package com.lcc3710;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.widget.PopupWindow;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
 
public class CustomOverlay extends Overlay {
	
	public PopupWindow window;
 
	private GeoPoint geopoint;
 
	public CustomOverlay(GeoPoint point) {
		geopoint = point;
	}
	
	public void setGeoPoint(GeoPoint p) {
		this.geopoint = p;
	}
 
 
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// Transfrom geoposition to Point on canvas
		Projection projection = mapView.getProjection();
		Point point = new Point();
		projection.toPixels(geopoint, point);
 
		// little dude icon
		Bitmap icon = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.location);
		float top = point.y - icon.getHeight()/2;
		float left = point.x - icon.getWidth()/2;
		canvas.drawBitmap(icon, left, top, null);
	}
    @Override

	public boolean onTouchEvent(MotionEvent event, MapView mapView) 
    {    
        return false;
    }        
    
    public String type(){
    	return "myLocation";
    }	
}
