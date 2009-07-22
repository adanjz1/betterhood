package com.lcc3710;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.lcc3710.Event.AttributeList;

public class HomeScreen extends MapActivity {
	private Button buttonWant;
	private Button buttonSettings;
	GeoPoint geopoint = null;

	private static final String TAG = BetterHood.TAG_HOME_SCREEN; 
	
	private MyLocationOverlay myLocOverlay;
	private LocationManager locManager;
	private LocationListener locListener;
	ArrayList<Event> eventArrayList = null;

	private EventOverlay overlay;
	String delims = "\\^";
	String[] partyTokens;

	
	private EventList eventList;
	
	private Location lastKnownLocation;
	
	
    //  Known latitude/longitude coordinates that we'll be using.
    private List<MapLocation> mapLocations;

    private int lastRequestCode;
	
	private MapView mapView;
	
	private Intent intent;
	
	private String sessionID;
	private String username;
	
	private Bundle extras;
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bundle extras;
		lastRequestCode = requestCode;
		
		if ((extras = data.getExtras()) == null) {
			Log.i(BetterHood.TAG_HOME_SCREEN, BetterHood.ERROR_PREFIX + "onActivityResult recieved a null extras bundle!");
		}
		
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
    	case BetterHood.REQ_SETTINGS_SCREEN:
    		if (resultCode == RESULT_OK) {
    			//successfully updated settings
    		}
    		if (resultCode == RESULT_CANCELED) {
    			// somethign went wrong in settings
    		}
    		break;
    	case BetterHood.REQ_POPULATE_EVENT_LIST:
    		if (resultCode == RESULT_OK) {
    			// event list populated, we better find out whats in the extras
    			if (extras != null) {
    				String szWebResponse;
    				if ((szWebResponse = extras.getString(BetterHood.EXTRAS_WEB_RESPONSE)) != null) {
    					Log.i(BetterHood.TAG_HOME_SCREEN, "EventList.populate() returned response: " + szWebResponse);
    					
    					eventArrayList = new ArrayList<Event>();
    					partyTokens = szWebResponse.split(delims);

    					int itemsCount = 0;
    					String[] name;
    					
    					Event newEvent = new Event();
    					
    					for (int i = 0; i < partyTokens.length; i++) {
    						
    						// EVENT_NAME
    						if(partyTokens[i].startsWith("|")){
    							name = partyTokens[i].split("\\|");
    							if(name[0] != null){
    								//Log.i(TAG, "name = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_NAME, name[1]);
    							}
    							
    						}
    						// EVENT_TYPE
    						else if(partyTokens[i].startsWith("-")){
    							name = partyTokens[i].split("-");
    							if(name[0] != null){
    								//Log.i(TAG, "type = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_TYPE, name[1]);
    							}
    							
    						}
    						// EVENT_HOST
    						else if(partyTokens[i].startsWith("~")){
    							name = partyTokens[i].split("~");
    								if(name[1] != null){
    								//Log.i(TAG, "host = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_HOST, name[1]);
    							}
    							
    						}
    						// EVENT_DESCRIPTION
    						else if(partyTokens[i].startsWith("+")){
    							name = partyTokens[i].split("\\+");
    							if(name[0] != null){
    								//Log.i(TAG, "description = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_DESCRIPTION, name[1]);
    							}
    							
    						}
    						// EVENT_ADDRESS
    						else if(partyTokens[i].startsWith(")")){
    							name = partyTokens[i].split("\\)") ;
    							if(name[0] != null){
    								//Log.i(TAG, "address = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_LOCATION_ADDRESS, name[1]);
    							}
    							
    						}
    						// EVENT_LATITUDE
    						else if(partyTokens[i].startsWith("(")){
    							name = partyTokens[i].split("\\(");
    							if(name[0] != null){
    								//Log.i(TAG, "latitude = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_LOCATION_LATITUDE, name[1]);
    							}
    						}
    						// EVENT_LONGITUDE
    						else if(partyTokens[i].startsWith("*")){
    							name = partyTokens[i].split("\\*");
    							if(name[0]!= null){
    								//Log.i(TAG, "longitude = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_LOCATION_LONGITUDE, name[1]);
    							}
    							
    						}
    						// EVENT_START_DATE
    						else if (partyTokens[i].startsWith("_")) {
    							name = partyTokens[i].split("_");
    							if (name[0] != null) {
    								//Log.i(TAG, "start date = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_START_DATE, name[1]);
    							}
    						}
    						// EVENT_PHONE_NUMBER
    						else if (partyTokens[i].startsWith("&")) {
    							name = partyTokens[i].split("&");
    							if (name[0] != null) {
    								//Log.i(TAG, "phone number = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_PHONE_NUMBER, name[1]);
    							}
    						}
    						// EVENT_CONTACT_EMAIL
    						else if (partyTokens[i].startsWith("%")) {
    							name = partyTokens[i].split("%");
    							if (name[0] != null) {
    								//Log.i(TAG, "contact email = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_CONTACT_EMAIL, name[1]);
    							}
    						}
    						else if(partyTokens[i].startsWith("&")){
    							
    						}
    					    //Log.i(BetterHood.TAG_HOME_SCREEN, i + " string " + partyTokens[i]);
    					
    						itemsCount++;
    						if(itemsCount >= 10){  
    							Log.i(BetterHood.TAG_EVENT_LIST, "Adding event #" + Integer.toString(eventArrayList.size()+1) + ": " + newEvent.getAttribute(AttributeList.EVENT_NAME));
    							eventArrayList.add(newEvent);
    							itemsCount = 0;
    							newEvent = new Event();
    						}
    						
    					}
    					// update eventList with the results
    					eventList.populate(eventArrayList);
    				} else {
    					if ((szWebResponse = extras.getString(BetterHood.EXTRAS_ERROR_MESSAGE)) != null) {
    						Log.i(BetterHood.TAG_HOME_SCREEN, BetterHood.ERROR_PREFIX + "EventList.populate() returned no response!");
    					}
    				}
    			}
    		}
    		
    		if (resultCode == RESULT_CANCELED) {
    			//something went wrong in populating the event list
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
        } else {
        	//big fat error
        }
        
        eventList = new EventList(sessionID);
        eventList.queryDatabase(this);
     
        
        buttonWant.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {					
				Intent inWant = new Intent(view.getContext(), CreateEventScreen1.class);
				
				inWant.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
				inWant.putExtra(BetterHood.EXTRAS_CURRENT_LOCATION, lastKnownLocation);
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
	
	// called when HomeScreen is shown to user after being paused
	protected void onResume() {
		super.onResume();
		//intent = getIntent();
		if (lastRequestCode > 2) {
			if (lastRequestCode != BetterHood.REQ_POPULATE_EVENT_LIST) {
				eventList = new EventList(sessionID);
				eventList.queryDatabase(this);
			}
		}
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
		geopoint = new GeoPoint(
				(int) (newLocation.getLatitude() * 1E6), (int) (newLocation
						.getLongitude() * 1E6));
 
		// Create new Overlay
		CustomOverlay overlay = new CustomOverlay(geopoint);
		
		
 
		mapView.getOverlays().add(overlay);
		EventOverlay myEvents = new EventOverlay(this);
		mapView.getOverlays().add(myEvents);
 
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
		//mapLocations = new ArrayList<MapLocation>();
		if(geopoint != null){
			Log.i(TAG, "the Geo Points" + geopoint.toString());
			
			if(geopoint.getLatitudeE6() >= (33.769710*1E6) && geopoint.getLatitudeE6() <= (33.786333*1E6)
					&& geopoint.getLongitudeE6() <= (-84.392037*1E6) && geopoint.getLongitudeE6() >= (-84.407143*1E6) ){
				Log.i(TAG, "I KNOW IM HERE DAMNIT" );
				mapLocations = new ArrayList<MapLocation>();
				mapLocations.add(new MapLocation("Yard Sale",33.782105,-84.402443, "services", "We are having a yard sale"," saturday 11:30"));
				mapLocations.add(new MapLocation("House for Rent",33.764706,-84.392652, "rent","I need to rent my house out", "until may 4th"));
				mapLocations.add(new MapLocation("Party",33.778179,-84.398848, "party" , "we are having a party for steves birthday", "7:20 saturday"));
				mapLocations.add(new MapLocation("Lawn Service", 33.765206, -84.396927,  "services", " I need my yard mowed and will pay 20$","today"));
			  
			}
		
		     if(geopoint.getLatitudeE6() >= (33.771076*1E6) && geopoint.getLatitudeE6() <= (33.792663*1E6)
				&& geopoint.getLongitudeE6() <= (-84.364808*1E6) && geopoint.getLongitudeE6() >= (-84.390171*1E6) ){
			    Log.i(TAG, "I KNOW IM HERE DAMNIT" );
			    mapLocations = new ArrayList<MapLocation>();
			    mapLocations.add(new MapLocation("Summerfest fundraiser",33.776631,-84.379506,"charity", "we are having a block party fundraiser for johnny sue", "saturday"));
			    mapLocations.add(new MapLocation("Midtown Safety Meeting",33.779128,-84.3856, "warning","Meeting to raise awareness about safety", "friday"));
			    mapLocations.add(new MapLocation("Shooting Alert",33.773135,-84.375815, "alert" , "A shooting has occured at 6:34 p.m.", "Today"));
			    
		  
		}
		}
			else if(geopoint == null){
				mapLocations = new ArrayList<MapLocation>();
				mapLocations.add(new MapLocation("Yard Sale",33.782105,-84.402443, "doo doo party ", "We are having a doo doo party"," saturday 11:30"));
			
			
			
		Geocoder gc = new Geocoder(this);
		
		if(eventArrayList != null){
		
		//iterate through the array list
		for(int i = 0; i < eventArrayList.size(); i ++){
			
			//creating an even object and giving it the value of the index of eventListArray value i
			
			Event e = new Event();
			e = eventArrayList.get(i);
			
			//here we try and give the geocoder the address we have, it returns a list 
			//because it can find multiple answers
			
			try{
			List<Address> address = gc.getFromLocationName(e.getAttribute(AttributeList.EVENT_LOCATION_ADDRESS), 1);
			
			// try and see if we get any results
			
			Address x = address.get(0); 
			Log.d(TAG, "where is this event suppose to occur" + "lat is" + x.getLatitude() + "long is" + x.getLongitude() );
			
			//here we create an even with the attributes of the event object if there is at
			// least any addresses 
			
			if(address.size()>=0){
			mapLocations.add(new MapLocation(e.getAttribute(AttributeList.EVENT_NAME),
					x.getLatitude(),x.getLongitude(),
					e.getAttribute(AttributeList.EVENT_TYPE), 
					e.getAttribute(AttributeList.EVENT_DESCRIPTION), 
					e.getAttribute(AttributeList.EVENT_START_DATE)));
			}
			}
			catch(Exception e1){
				//todo
			}
			
		 }
		}
		}
			
		

		return mapLocations;
	}
	



	
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	
}


