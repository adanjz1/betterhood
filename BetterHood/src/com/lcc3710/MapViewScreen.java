package com.lcc3710;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class MapViewScreen extends MapActivity {
	private Intent intent;
	LinearLayout linearLayout;
	MapView mapView;
	//ZoomControls mZoom;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.map_screen);
	        intent = getIntent();
	
	//linearLayout = (LinearLayout) findViewById(R.id.zoomview);
	mapView = (MapView) findViewById(R.id.mapview);
	//mZoom = (ZoomControls) mapView.getZoomControls();
	//linearLayout.addView(mZoom);
	 }
	
	protected boolean isRouteDisplayed() {
	    return false;
	}

}
