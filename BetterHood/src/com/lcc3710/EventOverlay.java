package com.lcc3710;

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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class EventOverlay extends Overlay {

	private Bitmap bubbleIcon, shadowIcon;

	private HomeScreen homeScreen;

	private Button buttonDialogBack;
	private Button buttonDialogForward;
	private Button buttonAddComment;
	
	private TextView textEventType;
	private TextView textEventDate;
	private TextView textEventAddress;
	private TextView textEventHost;
	private TextView textEventDescription;
	
	private EditText editEventComment;
	
	public boolean locationHit;
	boolean dialogIsHit = false;
	AlertDialog.Builder alert2;
	Dialog eventDialog;

	private Paint	innerPaint, borderPaint, textPaint;

	//  The currently selected Map Location...if any is selected.  This tracks whether an information  
	//  window should be displayed & where...i.e. whether a user 'clicked' on a known map location
	private MapLocation selectedMapLocation;  

	public EventOverlay(HomeScreen	map) {
		this.homeScreen = map;
		shadowIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.shadow);
		bubbleIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.party);
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
				Dialog myEvent = buildEventDisplayDialog();
				myEvent.show();
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
				//  Only offset the shadow in the y-axis as the shadow is angled so the base is at x=0; 
				canvas.drawBitmap(shadowIcon, screenCoords.x, screenCoords.y - shadowIcon.getHeight(),null);
			} else {
				if(location.getType().equals("Carpool")){
					bubbleIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.services);
					shadowIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.shadow);
				}
				if(location.getType().equals("Missing Child")){
					bubbleIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.alert);
					shadowIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.shadow);
				}
				if(location.getType().equals("Pool Party")) {
					bubbleIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.party);
					shadowIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.shadow);
				}
				if(location.getType().equals("Potluck")){
					bubbleIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.charity);
					shadowIcon = BitmapFactory.decodeResource(this.homeScreen.getResources(),R.drawable.shadow);
				}
				canvas.drawBitmap(bubbleIcon, screenCoords.x - bubbleIcon.getWidth()/2, screenCoords.y - bubbleIcon.getHeight(),null);
			}
		}
	}

		
	private Dialog buildEventDisplayDialog() {
		eventDialog = new Dialog(this.homeScreen);

		eventDialog.setContentView(R.layout.event_screen);
		eventDialog.getWindow().setLayout(300, 400);

		buttonDialogBack = (Button) eventDialog.findViewById(R.id.buttonBack);
		buttonDialogForward = (Button) eventDialog.findViewById(R.id.buttonForward);
		buttonAddComment = (Button) eventDialog.findViewById(R.id.buttonAddComment);
		
		textEventType = (TextView) eventDialog.findViewById(R.id.textEventType);
		textEventDate = (TextView) eventDialog.findViewById(R.id.textEventDate);
		textEventAddress = (TextView) eventDialog.findViewById(R.id.textEventAddress);
		textEventHost = (TextView) eventDialog.findViewById(R.id.textEventHost);
		textEventDescription = (TextView) eventDialog.findViewById(R.id.textEventDescription);
		
		editEventComment = (EditText) eventDialog.findViewById(R.id.editEventComment);
		
		buttonDialogForward.setEnabled(true);
		
		textEventType.setText(selectedMapLocation.getType());
		textEventDate.setText(selectedMapLocation.getTime());
		textEventAddress.setText(selectedMapLocation.getAddress());
		textEventHost.setText(selectedMapLocation.getHost());
		textEventDescription.setText(selectedMapLocation.getDescription());
		
		eventDialog.setTitle(selectedMapLocation.getName());
		
		editEventComment.setMaxLines(5);
		
		  

		View.OnClickListener buttonListener = new View.OnClickListener() {
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.buttonBack:
					eventDialog.dismiss();
					break;
				case R.id.buttonForward:
					if (v.isEnabled()) {
					eventDialog.dismiss();
					}
					break;
				case R.id.buttonAddComment:
					//posting comments
					if(editEventComment.getText() != null){
						Log.i("what'smy Query=" , editEventComment.getText().toString());
						HandleEventComment commentHandler = new HandleEventComment(homeScreen);
						commentHandler.postComment(selectedMapLocation.getHost(),editEventComment.getText().toString(), BetterHood.EXTRAS_ACCOUNT_FIRST_NAME + BetterHood.EXTRAS_ACCOUNT_LAST_NAME);
						
					}
					break;
				}
			}
		};

		buttonDialogBack.setOnClickListener(buttonListener);
		buttonDialogForward.setOnClickListener(buttonListener);
		buttonAddComment.setOnClickListener(buttonListener);

		return eventDialog;
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
