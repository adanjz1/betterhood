package com.lcc3710;

import java.util.ArrayList;

import com.lcc3710.Event.AttributeList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class EventListScreen extends Activity {
	private Intent intent;
	private Bundle extras;
	private String sessionID;
	String delims = "\\^";
	String[] partyTokens;
	ArrayList<Event> theEvent;
	String eList;
	
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//todo
    }
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       
    	setContentView(R.layout.event_list_screen);
    	
    	this.setTitle("Current Events");
    	//Log.i("eventlist =",extras.getString(BetterHood.EXTRAS_EVENT_LIST));
    	
    	intent = getIntent();
        extras = intent.getExtras();
        
        makeList(parseList(extras.getString(BetterHood.EXTRAS_EVENT_LIST)));
        
    	
        
       
    	
     
    	
    }
	
	public void makeList(ArrayList<Event> eventL){
		
		Button buttonForward;
    	buttonForward = (Button) this.findViewById(R.id.buttonForward);
    	
    	ListView listEventView;
        listEventView = (ListView) this.findViewById(R.id.listEvents);
    	
    	String[] aszCurrentEvents;
    	ArrayAdapter<String> adapter;
    	
    	ArrayList<Event> alEvents = eventL;
        aszCurrentEvents = new String[alEvents.size()];
        
        for (int i = 0; i < alEvents.size(); i++) {
        	aszCurrentEvents[i] = alEvents.get(i).getAttribute(AttributeList.EVENT_NAME);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aszCurrentEvents);
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
				setResult(RESULT_CANCELED, intent);
				finish();
			}
        });
        
	}
	
	private ArrayList<Event> parseList(String s){
		ArrayList<Event> eventArrayList;
		eventArrayList = new ArrayList<Event>();
		partyTokens = s.split(delims);
		

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
		return eventArrayList;
	
	}
	}


