package com.lcc3710;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.gregbugaj.tabwidget.Tab;
import com.gregbugaj.tabwidget.TabHost;
import com.gregbugaj.tabwidget.TabHostProvider;

public class HomeScreen extends MapActivity {

	private Location lastKnownLocation;
	private GeoPoint curLocation = null;
	
	private LocationManager locManager;
	private LocationListener locListener;

	private EventOverlay overlay;
	String[] partyTokens;
	
	private Template[] eventList;

    //  Known latitude/longitude coordinates that we'll be using.
    private List<MapLocation> mapLocations;
	
	private MapView mapView;
	String elist;
	private Intent intent;
	
	private String sessionID;
	private String username;
	
	private Bundle extras;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bundle extras = data.getExtras();

		if (extras == null) {
			Log.i(BetterHood.TAG_HOME_SCREEN, BetterHood.ERROR_PREFIX
					+ "onActivityResult recieved a null extras bundle!");
		}
		
		switch (requestCode) {
			// processing of activity result data
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        intent = getIntent();
        extras = intent.getExtras();
        // extract session id and username
    	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
    	username = extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
    	
    	// setup tabs
        TabHostProvider tabProvider = new BHTabProvider(this);
        TabHost tabHost = tabProvider.getTabHost(sessionID);
        
        tabHost.setCurrentView(R.layout.home_screen);
        Tab t = tabHost.getTab("Map");
        t.setSelected(true);
        
        //setContentView(R.layout.home_screen); 
        setContentView(tabHost.render());
        
        initMap();
        initLocationManager();
    	
    	// create map view
    	//overlay = new EventOverlay(this, sessionID);
		mapView.getOverlays().add(overlay);
    	mapView.getController().setZoom(16);  
	}
	
	// called when HomeScreen is shown to user after being paused
	protected void onResume() {
		super.onResume();
		
		// populate event list
		TemplateFactory tf = new TemplateFactory(sessionID);
		eventList = tf.getTemplates(TemplateFactory.POPULATE_EVENTS);
	}
	
	// when we want to create a dialog, figure out which kind it is and return it
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        }
        return null;
    }
	
	@SuppressWarnings("deprecation")
	private void initMap() {
		//mapView = new MapView(this, "0hL9tSrc2xdo-IfbiOtIcX6kBMhJAWcVJv8Y97w");
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
	private void initLocationManager() {
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
 
		locListener = new LocationListener() {
 
			public void onLocationChanged(Location newLocation) {
				lastKnownLocation = newLocation;
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
		List<Overlay> overlays = mapView.getOverlays();
 
		// first remove old overlay
		if (overlays.size() > 0) {
			for (Iterator<Overlay> iterator = overlays.iterator(); iterator
					.hasNext();) {
				iterator.next();
				iterator.remove();
			}
		}
 
		// transform the location to a geopoint
		curLocation = new GeoPoint(
				(int) (newLocation.getLatitude() * 1E6), (int) (newLocation
						.getLongitude() * 1E6));
 
		// Create new Overlay
		CustomOverlay overlay = new CustomOverlay(curLocation);
 
		mapView.getOverlays().add(overlay);
		//EventOverlay myEvents = new EventOverlay(this, extras.getString(BetterHood.EXTRAS_SESSION_ID));
		//mapView.getOverlays().add(myEvents);
 
		// move to location
		mapView.getController().animateTo(curLocation);
 
		// redraw map
		mapView.postInvalidate();
	}
	
	public List<MapLocation> getMapLocations() {
		mapLocations = new ArrayList<MapLocation>();
		Template e;
		if (eventList != null) {
			for (int i = 0; i < eventList.length; i++) {
				e = eventList[i];
				mapLocations.add(new MapLocation(e));
			}
		}
		return mapLocations;
	}
	
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	/** create the menu items */

	  @Override 
	  public boolean onCreateOptionsMenu(Menu menu) {
		  
		  super.onCreateOptionsMenu(menu);
		  MenuItem item = menu.add("I Want");
		  item.setIcon(android.R.drawable.ic_menu_add);
		    
		  item = menu.add("I Have");
		  item.setIcon(android.R.drawable.ic_menu_info_details);
		 
		  //item = menu.add("Event List");
		  //item.setIcon(R.drawable.settings);
		  
		  //item = menu.add("Similarities");
		  
		  //item = menu.add("Messages");

		  return true;
		}

	  /** when menu button option selected */
	  public boolean onOptionsItemSelected(MenuItem item) {
		  String id = (String) item.getTitle();
		  Log.i("whats item id=", id);

		  if(id == "I Want"){
			  Intent inWant = new Intent(this.getBaseContext(), CreateEventScreen1.class);
			  inWant.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
			  inWant.putExtra(BetterHood.EXTRAS_CURRENT_LOCATION, lastKnownLocation);
			  inWant.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, username);
			  startActivityForResult(inWant, BetterHood.REQ_CREATE_EVENT);
		  }

		  else if(id == "I Have"){
			  if (sessionID != null) {
				  Intent inSettings = new Intent(this.getBaseContext(), SettingsScreen.class);
				  inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
				  inSettings.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
				  inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME));
				  startActivityForResult(inSettings, BetterHood.REQ_SETTINGS_SCREEN);
			  }
		  }
		  else if(id == "Event List"){

			  if (sessionID != null) {
				  Intent inSettings = new Intent(this.getBaseContext(), EventListScreen.class);
				  inSettings.putExtra(BetterHood.EXTRAS_EVENT_LIST, elist);
				  inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
				  inSettings.putExtra(BetterHood.EXTRAS_SESSION_ID, extras.getString(BetterHood.EXTRAS_SESSION_ID));
				  inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME));
				  startActivityForResult(inSettings, BetterHood.REQ_EVENT_LIST_SCREEN);
				  //Intent intent = new Intent();;
				  //startActivityForResult(intent, eventList);
			  }

		  }
		  else if(id == "Messages"){

			  if (sessionID != null) {
				  Intent inSettings = new Intent(this.getBaseContext(), ConnectionResource.class);
				  inSettings.putExtra(BetterHood.EXTRAS_EVENT_LIST, elist);
				  String tempQuery = "";
				  tempQuery += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
				  inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
				  inSettings.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
				  inSettings.putExtra(BetterHood.EXTRAS_SESSION_ID, extras.getString(BetterHood.EXTRAS_SESSION_ID));
				  inSettings.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_SIMILAR_RESPONSE);
				  startActivityForResult(inSettings, BetterHood.REQ_SIMILAR_RESPONSE);
				  //Intent intent = new Intent();;
				  //startActivityForResult(intent, eventList);
			  }

		  }

		  else if(id == "Similarities"){
			  if (sessionID != null) {
				  Intent inSettings = new Intent(this.getBaseContext(), ConnectionResource.class);
				  inSettings.putExtra(BetterHood.EXTRAS_EVENT_LIST, elist);
				  String tempQuery = "";
				  tempQuery += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
				  inSettings.putExtra(BetterHood.EXTRAS_SESSION_ID, extras.getString(BetterHood.EXTRAS_SESSION_ID));
				  inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
				  inSettings.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
				  inSettings.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_SIMILAR_SCREEN);
				  startActivityForResult(inSettings, BetterHood.REQ_SIMILAR_SCREEN);
				  //Intent intent = new Intent();;
				  //startActivityForResult(intent, eventList);
			  }

		  }
		  // Consume the selection event.
		  return true;
	  }
	  public void startSimilarScreen(String sessionID, String username){

		  Intent home = new Intent(getBaseContext(), SimilarScreen.class);
		  home.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
		  home.putExtra(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME));
		  home.putExtra(BetterHood.EXTRAS_SESSION_ID, extras.getString(BetterHood.EXTRAS_SESSION_ID));
		  home.putExtra(BetterHood.EXTRAS_EVENT_SIMILAR, sessionID);
		  startActivityForResult(home, BetterHood.REQ_SIMILAR_SCREEN);
	  }

	  public void startResponseScreen(String sessionID, String username){

		  Intent home = new Intent(getBaseContext(), ResponseScreen.class);
		  home.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
		  home.putExtra(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME));
		  home.putExtra(BetterHood.EXTRAS_SESSION_ID, extras.getString(BetterHood.EXTRAS_SESSION_ID));
		  home.putExtra(BetterHood.EXTRAS_EVENT_SIMILAR, sessionID);
		  startActivityForResult(home, BetterHood.REQ_SIMILAR_RESPONSE);
	  }
}