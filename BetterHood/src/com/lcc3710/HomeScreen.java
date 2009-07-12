package com.lcc3710;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class HomeScreen extends MapActivity {
	private Button buttonWant;
	private Button buttonSettings;
	
	private MyLocationOverlay myLocOverlay;
	private LocationManager locManager;
	private LocationListener locListener;
	private EventOverlay overlay;
	
	
    //  Known latitude/longitude coordinates that we'll be using.
    private List<MapLocation> mapLocations;

	
	private MapView mapView;
	
	private Intent intent;
	
	private String sessionID;
	private String username;
	
	private Bundle extras;
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
    	case BetterHood.REQ_CREATE_EVENT:
    		
    		if (resultCode == RESULT_OK) {
    			// event was created, success!
    			Toast.makeText(this.getWindow().getContext(), "Event created successfully!", BetterHood.TOAST_TIME);
    		}
    		
    		if (resultCode == RESULT_CANCELED) {
    			//something went wrong in creating the event
    		}
    		
    		break;
    	}
    }
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        
       // LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
   //     Location location = locationManager.getCurrentLocation("gps");
        
       
        initMap();
        initLocationManager();
        overlay = new EventOverlay(this);
		mapView.getOverlays().add(overlay);

    	mapView.getController().setZoom(14);
    	mapView.getController().setCenter(getMapLocations().get(0).getPoint());

        
        intent = getIntent();
        
        buttonWant = (Button) findViewById(R.id.buttonWant);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        
        
        if ((extras = intent.getExtras()) != null) {
        	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
        	username = extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
        }
     
        
        buttonWant.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent inWant = new Intent(view.getContext(), CreateEventScreen1.class);
				
				inWant.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
				inWant.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, username);
				
				startActivityForResult(inWant, BetterHood.REQ_CREATE_EVENT);
			}
        });
        
        buttonSettings.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (sessionID != null) {
					Intent inSettings = new Intent(view.getContext(), SettingsScreen.class);
					inSettings.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
					startActivityForResult(inSettings, BetterHood.REQ_SETTINGS_SCREEN);
				}
			}
        });
      
	}
	
	private void initMap() {
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setEnabled(true);
		mapView.setClickable(true);
 
		View zoomView = mapView.getZoomControls();
		
		LinearLayout myzoom = (LinearLayout) findViewById(R.id.myzoom);
		myzoom.addView(zoomView);
		mapView.displayZoomControls(true);
 
	}
 
	/**
	 * Initialises the MyLocationOverlay and adds it to the overlays of the map
	 */
	private void initMyLocation() {
		myLocOverlay = new MyLocationOverlay(this, mapView);
		myLocOverlay.enableMyLocation();
		mapView.getOverlays().add(myLocOverlay);
 
	}
	
	private void initLocationManager() {
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
 
		locListener = new LocationListener() {
 
			public void onLocationChanged(Location newLocation) {
				createAndShowCustomOverlay(newLocation);
			}
 
			public void onProviderDisabled(String arg0) {
			}
 
			public void onProviderEnabled(String arg0) {
			}
 
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			}
		};
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locListener);
 
	}
 
	protected void createAndShowCustomOverlay(Location newLocation) {
		List overlays = mapView.getOverlays();
 
		// first remove old overlay
		if (overlays.size() > 0) {
			for (Iterator iterator = overlays.iterator(); iterator
					.hasNext();) {
				iterator.next();
				iterator.remove();
			}
		}
 
		// transform the location to a geopoint
		GeoPoint geopoint = new GeoPoint(
				(int) (newLocation.getLatitude() * 1E6), (int) (newLocation
						.getLongitude() * 1E6));
 
		// Create new Overlay
		CustomOverlay overlay = new CustomOverlay(geopoint);
		
		
 
		mapView.getOverlays().add(overlay);
 
		// move to location
		mapView.getController().animateTo(geopoint);
 
		// redraw map
		mapView.postInvalidate();
		
		//if(overlay.tapped == true){
			//PopUp popup = new PopUp("This is your current location"); 
    		//popup.makeWindow();
        //} 
		
		}
	public List<MapLocation> getMapLocations() {
		if (mapLocations == null) {
			mapLocations = new ArrayList<MapLocation>();
			mapLocations.add(new MapLocation("Yard Sale",33.782105,-84.402443));
			mapLocations.add(new MapLocation("Party",33.764706,-84.392652));
			mapLocations.add(new MapLocation("Party",33.778179,-84.398848 ));
			mapLocations.add(new MapLocation("Lawn Service", 33.765206, -84.396927));
		}
		return mapLocations;
	}
	



	
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	
}


