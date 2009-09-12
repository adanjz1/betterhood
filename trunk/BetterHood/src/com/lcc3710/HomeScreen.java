package com.lcc3710;



import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.lcc3710.Event.AttributeList;

public class HomeScreen extends MapActivity {
	private Button buttonWant;
	private Button buttonSettings;
	private Button buttonEventList;
	Bundle b;
	GeoPoint geopoint = null;
	
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
	String elist;
	private Intent intent;
	
	private String sessionID;
	private String username;
	
	private Bundle extras;
	
	private static final int EVENT_LIST_DIALOG_ID = 0;
	
	private Dialog eventListDialog;
	
	public static final int Menu1 = Menu.FIRST + 1;
	public static final int Menu2 = Menu.FIRST + 2;
	public static final int Menu3 = Menu.FIRST + 3;
	public static final int Menu4 = Menu.FIRST + 4;
	private static final int want = 1;
	private static final int have = 2;
	private static final int list = 3;
	

	
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
    					
    					elist = szWebResponse;
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
    							if(name[0].length() == 0){
    								
    								newEvent.setAttribute(AttributeList.EVENT_DESCRIPTION, "Description not provided");
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
    						
    						// if we've reached the end of this event, commit the event and create an empty one
    						if(itemsCount >= 10){  
    							Log.i(BetterHood.TAG_EVENT_LIST, "Adding event #" + Integer.toString(eventArrayList.size()+1) + ": " + newEvent.getAttribute(AttributeList.EVENT_NAME));
    							eventArrayList.add(newEvent);
    							itemsCount = 0;
    							newEvent = new Event();
    						}
    						
    					}
    					// update eventList with the results
    					eventList.populate(eventArrayList);
    					mapView.getController().setCenter(getMapLocations().get(0).getPoint());
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
        b = savedInstanceState;
        
       // LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
   //     Location location = locationManager.getCurrentLocation("gps");
        
       
        initMap();
        initLocationManager();
        overlay = new EventOverlay(this);
		mapView.getOverlays().add(overlay);
    	mapView.getController().setZoom(14);
        
        intent = getIntent();
        
       
        
        if ((extras = intent.getExtras()) != null) {
        	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
        	username = extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
        } else {
        	//big fat error
        }
        
        eventList = new EventList(sessionID);
        eventList.queryDatabase(this);     
        
       
      
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
	
	// when we want to create a dialog, figure out which kind it is and return it
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case EVENT_LIST_DIALOG_ID:
        	
            return buildEventListDialog();
        }
        return null;
    }
    
    private Dialog buildEventListDialog() {    	
    	eventListDialog = new Dialog(this);
    	eventListDialog.setContentView(R.layout.event_list_screen);
    	eventListDialog.getWindow().setLayout(320, 450);
    	eventListDialog.setTitle("Current Events");
    	
    	Button buttonForward;
    	buttonForward = (Button) eventListDialog.findViewById(R.id.buttonForward);
    	
    	ListView listEventView;
        listEventView = (ListView) eventListDialog.findViewById(R.id.listEvents);
    	
    	String[] aszCurrentEvents;
    	ArrayAdapter<String> adapter;
    	
    	ArrayList<Event> alEvents = eventList.getEvents();
        aszCurrentEvents = new String[alEvents.size()];
        
        for (int i = 0; i < alEvents.size(); i++) {
        	aszCurrentEvents[i] = alEvents.get(i).getAttribute(AttributeList.EVENT_NAME);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, aszCurrentEvents);
        listEventView.setAdapter(adapter);
        
        for (int i = 0; i < listEventView.getCount(); i++) {
        	String eventType = alEvents.get(i).getAttribute(AttributeList.EVENT_TYPE);

        	int color = 0;
        	if (eventType.equals("Missing Child")) {
        		color = Color.RED;
        	}
        	if (eventType.equals("Pool Party")) {
        		color = Color.BLUE;
        	}
        	if (eventType.equals("Potluck")) {
        		color = Color.rgb(255, 128, 0);
        	}
        	if (eventType.equals("Carpool")) {
        		color = Color.GREEN;
        	}
        	
        	if (color != 0) {
        		//child.setBackgroundColor(color);
        	}
        }
        
        buttonForward.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				eventListDialog.dismiss();
			}
        });
    	
     
    	return eventListDialog;
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
	/*
	private void initMyLocation() {
		myLocOverlay = new MyLocationOverlay(this, mapView);
		myLocOverlay.enableMyLocation();
		mapView.getOverlays().add(myLocOverlay);
 
	}
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
		
		}
	public List<MapLocation> getMapLocations() {
		mapLocations = new ArrayList<MapLocation>();	
		ArrayList<Event> events;
		Event e;
		if ((events = eventList.getEvents()) != null) {
			for (int i = 0; i < events.size(); i++) {
				e = events.get(i);
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
		  
		  MenuItem item = menu.add("Desire");
		 // item.setIcon(R.drawable.w);
		    
		  item = menu.add("Share");
		  //item.setIcon(R.drawable.have);
		 
		  item = menu.add("Event List");
		 // item.setIcon(R.drawable.settings);
		  
		  item = menu.add("Similarities");
		  
		  item = menu.add("responses");
		  
		  
		  
		  return true;
		}

		/** when menu button option selected */
		public boolean onOptionsItemSelected(MenuItem item) {
			String id = (String) item.getTitle();
			Log.i("whats item id=", id);
			
			
			
			
			if(id == "Desire"){
				Intent inWant = new Intent(this.getBaseContext(), CreateEventScreen1.class);
				inWant.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
				inWant.putExtra(BetterHood.EXTRAS_CURRENT_LOCATION, lastKnownLocation);
				inWant.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, username);
				startActivityForResult(inWant, BetterHood.REQ_CREATE_EVENT);
			}
				
			else if(id == "Share"){
				if (sessionID != null) {
					Intent inSettings = new Intent(this.getBaseContext(), SettingsScreen.class);
					inSettings.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
					startActivityForResult(inSettings, BetterHood.REQ_SETTINGS_SCREEN);
				}
			}
			else if(id == "Event List"){
			
				if (sessionID != null) {
					Intent inSettings = new Intent(this.getBaseContext(), EventListScreen.class);
					inSettings.putExtra(BetterHood.EXTRAS_EVENT_LIST, elist);
					startActivityForResult(inSettings, BetterHood.REQ_EVENT_LIST_SCREEN);
				    //Intent intent = new Intent();;
					//startActivityForResult(intent, eventList);
				}
				
			}
			
			else if(id == "Similarities"){
				if (sessionID != null) {
					Intent inSettings = new Intent(this.getBaseContext(), SimilarScreen.class);
					inSettings.putExtra(BetterHood.EXTRAS_EVENT_LIST, elist);
					startActivityForResult(inSettings, BetterHood.REQ_EVENT_LIST_SCREEN);
				    //Intent intent = new Intent();;
					//startActivityForResult(intent, eventList);
				}
				
			}
				
			
				     
				     // Consume the selection event.
				 return true;
				   }
		
		
		
}


