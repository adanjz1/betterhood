package com.lcc3710;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.gregbugaj.tabwidget.Tab;


public class MainScreen extends MapActivity {
	
	private Intent intent;
	private Bundle extras;
	public String sessionID;
	
	private TableLayout mainLayout;
	private TableRow contentRow;
	
	// global stuff
	private Template[] eventList;
	private ArrayList<MapLocation> mapLocations;
	private Location curLocation;
	
	// map view stuff
	private MapView mapView;
	private EventOverlay eventOverlay;
	private CustomOverlay locationOverlay;
	
	// event list stuff
	private ListView eventListView;
	
	// share view stuff
	private TextView shareView;
	
	// tab stuff
	final private String[] tabTags = {"SHARE", "MAP", "LIST"};
	Tab[] tabs;
	
	public void onCreate(Bundle savedInstanceState) {	
        super.onCreate(savedInstanceState);
        intent = getIntent();
        extras = intent.getExtras();
        // extract session id and username
    	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
    	
    	populateEvents();
    	initLocationManager();
        
        initMapView();
        initShareView();
        initListView();
    	
    	// create table layout
    	FrameLayout.LayoutParams pTable = new FrameLayout.LayoutParams(
    			TableRow.LayoutParams.FILL_PARENT, 
    			TableRow.LayoutParams.FILL_PARENT);
    	mainLayout = new TableLayout(this);
    	mainLayout.setLayoutParams(pTable);
    	
    	TableRow.LayoutParams pRowTabs=new TableRow.LayoutParams();
		pRowTabs.height = TableRow.LayoutParams.WRAP_CONTENT;
		pRowTabs.weight = 1;
		
		// create content view
		contentRow = new TableRow(this);
		contentRow.addView(shareView);
		
		// add views
		mainLayout.addView(getTabRow(), pRowTabs);
		mainLayout.addView(contentRow, pRowTabs);
    	
    	setContentView(mainLayout);
	}
	
	private void populateEvents() {
		// populate event list
		TemplateFactory tf = new TemplateFactory(sessionID);
		eventList = tf.getTemplates(TemplateFactory.POPULATE_EVENTS);
	}
	
	// called when HomeScreen is shown to user after being paused
	protected void onResume() {
		super.onResume();
		populateEvents();
	}
	
	private void initMapView() {
		mapView = new MapView(this, "0hL9tSrc2xdog8AQjqyiyWuJxzORhAy3s74GaGA");
		mapView.setEnabled(true);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
    	
    	addOverlays();
    	
    	mapView.getController().setZoom(16);
	}
	
	private void addOverlays() {
		List<Overlay> o = mapView.getOverlays();
		for (int i = 0; i < o.size(); i++) {
			o.remove(i);
		}
		
		eventOverlay = new EventOverlay(this, sessionID);
		o.add(eventOverlay);
		
		if (curLocation != null) {
			GeoPoint p = new GeoPoint(
    				(int)(curLocation.getLatitude()* 1e6), 
    				(int)(curLocation.getLongitude()* 1e6));
    		locationOverlay = new CustomOverlay(p);
    		
    		o.add(locationOverlay);
    		
    		mapView.getController().animateTo(p);
    	} else {
    		List<MapLocation> ml = getMapLocations();
    		if (ml.size() > 0) {
    			GeoPoint p = null;
    			for (int i = 0; i < ml.size(); i++) {
    				p = ml.get(i).getPoint();
    				if (p != null) {
    					mapView.getController().animateTo(p);
    					break;
    				}
    			}
    		}
    	}
	}
	
	private void initShareView() {
		Display defDisplay = getWindowManager().getDefaultDisplay();
		int tabHeight = BitmapFactory.decodeResource(this.getResources(), R.drawable.tab_bg).getHeight();
    	int displayWidth = defDisplay.getWidth();
    	int displayHeight = defDisplay.getHeight();
		
		shareView = new TextView(this);
		shareView.setText("poop");
		shareView.setBackgroundColor(Color.WHITE);
		shareView.setTextColor(Color.BLACK);
		
		//shareView.setMinimumWidth(displayWidth);
		shareView.setMaxWidth(displayWidth);
		shareView.setHeight(displayHeight-tabHeight);
		
		TableRow.LayoutParams params = new TableRow.LayoutParams(
				TableRow.LayoutParams.FILL_PARENT,
				TableRow.LayoutParams.FILL_PARENT);
		params.span = tabTags.length + 2;
		shareView.setLayoutParams(params);
	}
	
	private void initListView() {
		Display defDisplay = getWindowManager().getDefaultDisplay();
		int tabHeight = BitmapFactory.decodeResource(this.getResources(), R.drawable.tab_bg).getHeight();
    	int displayWidth = defDisplay.getWidth();
    	int displayHeight = defDisplay.getHeight();
    	
    	TableRow.LayoutParams params = new TableRow.LayoutParams(
				TableRow.LayoutParams.FILL_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		params.span = tabTags.length + 2;
		params.height = displayHeight - tabHeight;
		params.width = displayWidth;
		params.weight = 1;
		
		eventListView = new ListView(this);
		eventListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		eventListView.setMinimumHeight(params.height);
		eventListView.setLayoutParams(params);
		
		EventListAdapter adapter = new EventListAdapter(this, eventListView, eventList, sessionID);
		adapter.areAllItemsEnabled();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);
		MenuItem item = menu.add("I Want");
		item.setIcon(android.R.drawable.ic_menu_add);

		item = menu.add("I Have");
		item.setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}
	
	/**
	 * Initialises the MyLocationOverlay and adds it to the overlays of the map
	 */
	private void initLocationManager() {
		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener locListener = new LocationListener() {
	
			public void onLocationChanged(Location newLocation) {
				curLocation = newLocation;
				addOverlays();
			}
	
			public void onProviderDisabled(String provider) {
				showToast("GPS provider is disabled, please enable it.", 5);
			}
	
			public void onProviderEnabled(String provider) {
			}
	
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
		};
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 100,
				locListener);
	
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		  String id = (String) item.getTitle();

		  if(id == "I Want"){
			  Intent inWant = new Intent(this.getBaseContext(), CreateEventScreen1.class);
			  inWant.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
			  inWant.putExtra(BetterHood.EXTRAS_CURRENT_LOCATION, curLocation);
			  startActivityForResult(inWant, BetterHood.REQ_CREATE_EVENT);
		  }

		  else if(id == "I Have"){
			  if (sessionID != null) {
				  Intent inSettings = new Intent(this.getBaseContext(), SettingsScreen.class);
				  inSettings.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
				  startActivityForResult(inSettings, BetterHood.REQ_SETTINGS_SCREEN);
			  }
		  }
		  return true;
	}
 
	private void showToast(String message, int length) {
		Toast.makeText(this, message, length).show();
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
	
	private TableRow getTabRow() {
		// calculate tab sizes
    	int numTabs = tabTags.length;
    	Display defDisplay = getWindowManager().getDefaultDisplay();
    	int displayWidth = defDisplay.getWidth();
    	Bitmap tab = BitmapFactory.decodeResource(this.getResources(), R.drawable.tab_left);
    	Bitmap tab_bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.tab_bg);
    	Bitmap tab_div = BitmapFactory.decodeResource(this.getResources(), R.drawable.tab_divider);
    	int tabWidth = tab.getWidth();
    	int tab_divWidth = tab_div.getWidth();
    	int tabSpace = displayWidth - (numTabs * tabWidth) 
    								- ((numTabs - 1) * tab_divWidth);
    	
    	// setup tabs
    	tabs = new Tab[numTabs];
    	tabs[0] = new Tab(this, tabTags[0]);
    	tabs[0].setIcon(R.drawable.tab_left);
    	tabs[0].setIconSelected(R.drawable.tab_left_selected);
    	tabs[0].createView();
    	tabs[0].setSelected(true);
    	
    	tabs[1] = new Tab(this, tabTags[1]);
    	tabs[1].setIcon(R.drawable.tab_center);
    	tabs[1].setIconSelected(R.drawable.tab_center_selected);
    	tabs[1].createView();
    	
    	tabs[2] = new Tab(this, tabTags[2]);
    	tabs[2].setIcon(R.drawable.tab_right);
    	tabs[2].setIconSelected(R.drawable.tab_right_selected);
    	tabs[2].createView();
    	
    	// setup tab listener
    	OnClickListener clickListener = new OnClickListener() {
			public void onClick(View v) {
				String tag = (String)v.getTag();
				Log.i("Tabs", tag + " pressed.");
				View newView = null;
				contentRow.removeAllViews();
				if (tag.equals(tabTags[0])) {
					tabs[0].setSelected(true);
					tabs[1].setSelected(false);
					tabs[2].setSelected(false);
					newView = shareView;
				} else if (tag.equals(tabTags[1])) {
					tabs[0].setSelected(false);
					tabs[1].setSelected(true);
					tabs[2].setSelected(false);
					newView = mapView;
				} else if (tag.equals(tabTags[2])) {
					initListView();
					
					tabs[0].setSelected(false);
					tabs[1].setSelected(false);
					tabs[2].setSelected(true);
					newView = eventListView;
				}
				
				TableRow.LayoutParams pRowContent=new TableRow.LayoutParams();
				pRowContent.span=tabTags.length;
				pRowContent.width=TableRow.LayoutParams.FILL_PARENT;
				pRowContent.height=TableRow.LayoutParams.FILL_PARENT;
				pRowContent.weight=1;
				
				if (newView != null) {
					contentRow.addView(newView, pRowContent);
				}
			}
    	};
    	OnTouchListener touchListener = new OnTouchListener(){
			public boolean onTouch(View view, MotionEvent e) {
				if(e.getAction()==MotionEvent.ACTION_DOWN){
					//view.setBackgroundColor(0x200000FF);
				}else if(e.getAction()==MotionEvent.ACTION_UP){		
					//Set back to transparent color 00==Full transparencty 255 == none
					//view.setBackgroundColor(0x00FFFFFF);
				}				  
				return false;
			}
		};
    	tabs[0].bindListeners(clickListener, touchListener);
    	tabs[1].bindListeners(clickListener, touchListener);
    	tabs[2].bindListeners(clickListener, touchListener);
    	
    	// setup tab view
    	TableRow tabRow = new TableRow(this);
    	tabRow.setBackgroundDrawable(new BitmapDrawable(tab_bg));
		
		int j=0;
		for(int i=0;i<tabTags.length;i++){
			Tab t = tabs[i];
			View view = t.getView();
			if (i == 0) {
				view.setPadding(tabSpace/2, 0, 0, 0);
			}
			if (i == numTabs-1) {
				view.setPadding(0, 0, tabSpace/2, 0);
			}
			TableRow.LayoutParams pCol=new TableRow.LayoutParams();
			pCol.weight=0;
			tabRow.addView(view, pCol);
			
			//Handle the separators between tabs
			if(j%2==0 && i < numTabs-1 ){
				TableRow.LayoutParams pSpanCol=new TableRow.LayoutParams();
				ImageButton sep = new ImageButton(this);
				sep.setImageBitmap(tab_div);
				sep.setMaxHeight(tab_div.getHeight());
				sep.setMinimumHeight(tab_div.getHeight());
				sep.setPadding(0, 0, 0, 0);
				sep.setBackgroundColor(0x00);
				sep.setMaxWidth(tab_div.getWidth());
				tabRow.addView(sep, pSpanCol);
				j++;
			}
			j++;
		}
		
		return tabRow;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
