package com.lcc3710;



import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
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
import android.widget.EditText;
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
	private ListView lv;
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
	private String[] stringOfNeeds;
	
	private static final int EVENT_LIST_DIALOG_ID = 0;
	
	private Dialog eventListDialog, eventNoticeDialog;
	
	public static final int Menu1 = Menu.FIRST + 1;
	public static final int Menu2 = Menu.FIRST + 2;
	public static final int Menu3 = Menu.FIRST + 3;
	public static final int Menu4 = Menu.FIRST + 4;
	private static final int want = 1;
	private static final int have = 2;
	private static final int list = 3;
	String[] itemsName2, itemsName3;
	private Activity a = this;
	
	String[] items, items2, items3;
	

	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bundle extras;
		lastRequestCode = requestCode;
		
		//if ((extras = data.getExtras()) == null) {
			//Log.i(BetterHood.TAG_HOME_SCREEN, BetterHood.ERROR_PREFIX + "onActivityResult recieved a null extras bundle!");
//		}
		
    	switch (requestCode) {
    	
    	case BetterHood.REQ_SIMILAR_SCREEN:
    		try{
    		extras = data.getExtras();
    		final String response = data.getExtras().getString(BetterHood.EXTRAS_WEB_RESPONSE);
    		Log.i("WTF IS HAPPENING", response);
    		
    		if (resultCode == RESULT_OK) {
    			// event was created, success!
    			Log.i("WTF IS HAPPENING", response);
    			String tempUsername = extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
    			
    			startSimilarScreen(response, tempUsername);

    		}
    		
    		if (resultCode == RESULT_CANCELED) {
    			//something went wrong in creating the event
    		}
    		}
    		catch(Exception e){
    			Log.i("error", "error");
    		}
    		
    		break;
    	case BetterHood.REQ_SIMILAR_RESPONSE:
    		
    		try{
    			final String responseSimilar = data.getExtras().getString(BetterHood.EXTRAS_WEB_RESPONSE);
        		Log.i("WTF IS  similar HAPPENING", responseSimilar);
    		if (resultCode == RESULT_OK) {
    			extras = data.getExtras();
    			// event was created, success!
    			Log.i("WTF IS HAPPENING", responseSimilar);
    			String tempUsername = extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
    			
    			startResponseScreen(responseSimilar, tempUsername);

    		}
    		}
    		catch(Exception e){
    			Log.i("erro", "there was a back error");
    		}
    		
    		if (resultCode == RESULT_CANCELED) {
    			//something went wrong in creating the event
    		}
    		break;
    	case BetterHood.REQ_SETTINGS_SCREEN:
    		try{
    		extras = data.getExtras();
    		if (resultCode == RESULT_OK) {
    			//successfully updated settings
    		}
    		if (resultCode == RESULT_CANCELED) {
    			// somethign went wrong in settings
    		}
    		}
    		catch(Exception e){
    			Log.i("error", "error");
    		}
    		break;
    	 case BetterHood.REQ_SIMILAR_SCREEN_EVENTS_HAVE:
    		 try{
    		 extras = data.getExtras();
 	    	Log.i("you ok,", data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE));
 		if (resultCode == RESULT_OK) {
 			
 			String[] interest = data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE).split(delims);
 			//partyTokens.add(2,interest) ;
 			//Log.i("poart=", partyTokens.toString());
 			int num = interest.length;
 		
 			 items3 = new String[num/2];
 			itemsName3 = new String[num/2];
 			int itemsNameCount = 0;
 			
 			
 			
 			String [] partyTokensReal = interest;
 			String[] name = interest[0].split("\\|");
 			String[] eventID = new String[num/2];
 			int itemsCount = 0;
 			
 			
 			Event newEvent = new Event();
 			
 			for (int i = 0; i < interest.length; i++) {
 				
 				// EVENT_NAME
 				
 				if(interest[i].startsWith("|")){
 					
 					name = interest[i].split("\\|");
 					
 						//Log.i(TAG, "name = " + name[1]);
 						items3[itemsCount] = name[1];
 						itemsCount++;
 						//items[i+1] = "no";
 						
 					
 					
 				}
 				if(interest[i].startsWith(">")){
 					
 					name = interest[i].split("\\>");
 					
 						//Log.i(TAG, "name = " + name[1]);
 						itemsName3[itemsNameCount] = name[1];
 						itemsNameCount++;
 						//items[i+1] = "no";
 						 
 		
 						
 					
 					
 		}
 				if(interest[i].startsWith("?")){
 					
 					name = interest[i].split("\\?");
 					
 						//Log.i(TAG, "name = " + name[1]);
 						eventID[itemsNameCount] = name[1];
 						itemsNameCount++;
 						//items[i+1] = "no";
 						 
 		
 						
 					
 					
 		}
 			}
 			//partyTokens.set(2, itemsName2);
 		
 			String[]  adapt = new String[items3.length];
 			System.out.println(adapt.length);
 			//System.out.println(itemsName2.length);
 			//System.out.println(itemsName.length);
 			//String[] item = partyTokens.get(0);
 			//String[] item2 = partyTokens.get(1);
 			
 			
 			//aAdapter.clear();
 			for(int i =0; i < (items3.length); i++){
 				
 				
 				if(i < itemsName3.length){
 					//System.out.println(itemsName2[0]);
 					adapt[i] = "Someone Wants: " + itemsName3[i];
 					
 				}
 				
 				
 				
 				
 			}
 			
 			String[]  adaptName = new String[items3.length];
 			int count3=0;
 			int count4=0;
 			for(int i =0; i < (items3.length); i++){
 				
 				
 				if(i < items3.length ){
 					//System.out.println(itemsName2[0]);
 					adaptName[i] = items3[i];
 					count4++;
 				}

 			}
 			stringOfNeeds = adapt;
 			//final String[] adapter = adapt;
 			//final String[] adapterName = adaptName;
 			Log.i("what is it", intent.getStringExtra(BetterHood.EXTRAS_USER_LOGGED_IN).toString());
 			if(data.getStringExtra(BetterHood.EXTRAS_USER_LOGGED_IN) == null && !intent.getStringExtra(BetterHood.EXTRAS_USER_LOGGED_IN).toString().equals("yes")){
 			buildNoticeDialog().show();
 			}
 			
 		}
    		 }
    		 catch(Exception e){
    			 
    		 }
    		 break;
    	case BetterHood.REQ_POPULATE_EVENT_LIST:
    		try{
    		extras = data.getExtras();
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
    							if(name[1].length() < 1){
    								
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
    						else if (partyTokens[i].startsWith("#")) {
    							name = partyTokens[i].split("\\#");
    							if (name[0] != null) {
    								//Log.i(TAG, "contact email = " + name[1]);
    								newEvent.setAttribute(AttributeList.EVENT_ID, name[1]);
    							}
    						}
    						else if(partyTokens[i].startsWith("&")){
    							
    						}
    					    //Log.i(BetterHood.TAG_HOME_SCREEN, i + " string " + partyTokens[i]);
    					
    						itemsCount++;
    						
    						// if we've reached the end of this event, commit the event and create an empty one
    						if(itemsCount >= 11){  
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
    		}
    		catch(Exception e){
    			
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
        
        
        intent = getIntent();
        
        
       
        
        if ((extras = intent.getExtras()) != null) {
        	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
        	username = extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
        	overlay = new EventOverlay(this,username, sessionID);
    		mapView.getOverlays().add(overlay);
        	mapView.getController().setZoom(16);
        	String tempQuery="";
        	eventList = new EventList(sessionID);
		     eventList.queryDatabase(this);  
			Intent in = new Intent(this, ConnectionResource.class);
		//	in.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, adapt);
			tempQuery += "&sid=" + sessionID;
			//in.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
			in.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
			in.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
			in.putExtra(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME));
			in.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_SIMILAR_SCREEN_EVENTS_HAVE);
			
			startActivityForResult(in, BetterHood.REQ_SIMILAR_SCREEN_EVENTS_HAVE);
		
        	
			    
			
		    
        } else {
        	//big fat error
        }
        
       
        
       
      
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
    
    
    private Dialog buildNoticeDialog() {
    	eventNoticeDialog = new Dialog(this);
    	eventNoticeDialog.setContentView(R.layout.event_notice_dialog);
    	eventNoticeDialog.getWindow().setLayout(320, 400);
    	eventNoticeDialog.setTitle("Common Events");
    	
    	  Button buttonBack = (Button) eventNoticeDialog.findViewById(R.id.buttonBack);
          Button buttonForward = (Button)eventNoticeDialog.findViewById(R.id.buttonForward);
          lv = (ListView) eventNoticeDialog.findViewById(R.id.listNeeds);
          
          lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, stringOfNeeds));
          lv.setChoiceMode(1);
          
          buttonBack.setOnClickListener(new OnClickListener() {
  			public void onClick(View view) {
  				eventNoticeDialog.dismiss();
  			}
          });
          
          
          
          return eventNoticeDialog;
    	
    
    }
    private Dialog buildEventListDialog() {    	
    	eventListDialog = new Dialog(this);
    	eventListDialog.setContentView(R.layout.event_list_screen);
    	eventListDialog.getWindow().setLayout(280, 400);
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
		EventOverlay myEvents = new EventOverlay(this,extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME), extras.getString(BetterHood.EXTRAS_SESSION_ID));
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
		  MenuItem item = menu.add("I Want");

		 // item.setIcon(R.drawable.w);
		    
		  item = menu.add("I Have");
		  //item.setIcon(R.drawable.have);
		 
		  item = menu.add("Event List");
		 // item.setIcon(R.drawable.settings);
		  
		  item = menu.add("Similarities");
		  
		  item = menu.add("Messages");
		  
		  
		  
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


