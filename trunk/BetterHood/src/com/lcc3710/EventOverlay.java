package com.lcc3710;

import java.net.URL;
import java.util.Iterator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class EventOverlay extends Overlay {

	private Bitmap bubbleIcon;
	
	private int iconWidth;

	private HomeScreen homeScreen;
	
	public boolean locationHit;
	boolean dialogIsHit = false;
	AlertDialog.Builder alert2;
	Dialog eventDialog;

	private Paint	innerPaint, borderPaint, textPaint;

	//  The currently selected Map Location...if any is selected.  This tracks whether an information  
	//  window should be displayed & where...i.e. whether a user 'clicked' on a known map location
	private MapLocation selectedMapLocation;  

	public EventOverlay(HomeScreen	map, String name, String ID) {
		this.homeScreen = map;
	}

	@Override
	public boolean onTap(GeoPoint p, MapView	mapView)  {

		//  Store whether prior popup was displayed so we can call invalidate() & remove it if necessary.
		boolean isRemovePriorPopup = selectedMapLocation != null;  

		//  Next test whether a new popup should be displayed
		selectedMapLocation = getHitMapLocation(mapView,p);
		if ( isRemovePriorPopup || selectedMapLocation != null) {
			//mapView.invalidate();
		}		
		if(selectedMapLocation != null){
			dialogIsHit = true;
			if(dialogIsHit){	
				
			}
		}

		//  Lastly return true if we handled this onTap()
		return selectedMapLocation != null;
	}

	public void draw(Canvas canvas, MapView	mapView, boolean shadow) {

		drawMapLocations(canvas, mapView, shadow);
		drawInfoWindow(canvas, mapView, shadow);

	}

	/**
	 * Test whether an information balloon should be displayed or a prior balloon hidden.
	 */
	private MapLocation getHitMapLocation(MapView	mapView, GeoPoint tapPoint) {

		//  Track which MapLocation was hit...if any
		MapLocation hitMapLocation = null;

		RectF hitTestRecr = new RectF();
		Point screenCoords = new Point();
		Iterator<MapLocation> iterator = homeScreen.getMapLocations().iterator();

		while(iterator.hasNext()) {
			MapLocation testLocation = iterator.next();

			//  Translate the MapLocation's lat/long coordinates to screen coordinates
			mapView.getProjection().toPixels(testLocation.getPoint(), screenCoords);

			// Create a 'hit' testing Rectangle w/size and coordinates of our icon
			// Set the 'hit' testing Rectangle with the size and coordinates of our on screen icon
			hitTestRecr.set(-bubbleIcon.getWidth()/2,-bubbleIcon.getHeight(),bubbleIcon.getWidth()/2,0);
			hitTestRecr.offset(screenCoords.x,screenCoords.y);

			//  Finally test for a match between our 'hit' Rectangle and the location clicked by the user
			mapView.getProjection().toPixels(tapPoint, screenCoords);
			if (hitTestRecr.contains(screenCoords.x,screenCoords.y)) {
				hitMapLocation = testLocation;
				break;
			}
		}

		//  Lastly clear the newMouseSelection as it has now been processed
		tapPoint = null;

		return hitMapLocation; 
	}

	private void drawMapLocations(Canvas canvas, MapView mapView, boolean shadow) {

		Iterator<MapLocation> iterator = homeScreen.getMapLocations().iterator();
		Point screenCoords = new Point();
		while(iterator.hasNext()) {	   
			MapLocation location = iterator.next();
			mapView.getProjection().toPixels(location.getPoint(), screenCoords);

			if (shadow) {
				// shadow?
			} else {
				// download the event icon
				try {
					URL iconURL = new URL(BetterHood.URL_BASE + "icons/" + location.getTemplate().icon);
					bubbleIcon = BitmapFactory.decodeStream(iconURL.openStream());
					Log.i("EventOverlay", iconURL.toExternalForm() + " downloaded successfully.");
				} catch (Exception e) {
					// use the default balloon icon
					Log.i("EventOverlay", "Icon download failed: " + e.getMessage());
					bubbleIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.balloon);
				}
				int zoomLevel = mapView.getZoomLevel();
				//resize based on zoom level				
				iconWidth = (zoomLevel * 9) - 88;
				if (iconWidth < 15) {
					iconWidth = 15;
				} 
				else if (iconWidth > 70) {
					iconWidth = 70;
				}
				//Log.i(BetterHood.TAG_HOME_SCREEN, "iconWidth: " + Integer.toString(iconWidth));
				int tempHeight = (int)(((float)iconWidth / (float)bubbleIcon.getWidth()) * (float)bubbleIcon.getHeight());
				bubbleIcon = Bitmap.createScaledBitmap(bubbleIcon, iconWidth, tempHeight, true);
				canvas.drawBitmap(bubbleIcon, screenCoords.x - bubbleIcon.getWidth()/2, screenCoords.y - bubbleIcon.getHeight(),null);
			}
		}
	}

	private void drawInfoWindow(Canvas canvas, MapView	mapView, boolean shadow) {

		if ( selectedMapLocation != null) {
			Point selDestinationOffset = new Point();
			mapView.getProjection().toPixels(selectedMapLocation.getPoint(), selDestinationOffset);
		}
	}

	public Paint getInnerPaint() {
		if ( innerPaint == null) {
			innerPaint = new Paint();
			innerPaint.setARGB(225, 75, 75, 75); //gray
			innerPaint.setAntiAlias(true);
		}
		return innerPaint;
	}

	public Paint getBorderPaint() {
		if ( borderPaint == null) {
			borderPaint = new Paint();
			borderPaint.setARGB(255, 255, 255, 255);
			borderPaint.setAntiAlias(true);
			borderPaint.setStyle(Style.STROKE);
			borderPaint.setStrokeWidth(2);
		}
		return borderPaint;
	}

	public Paint getTextPaint() {
		if ( textPaint == null) {
			textPaint = new Paint();
			textPaint.setARGB(255, 255, 255, 255);
			textPaint.setAntiAlias(true);
		}
		return textPaint;
	}

	public String type(){
		return "eventOverlay";
	}
}
