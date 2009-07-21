package com.lcc3710;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class EventList {
	private String sessionID;
	
	private ArrayList<Event> events;
	
	public EventList(String sessionID) {
		this.sessionID = sessionID;
	}
	
	public void queryDatabase(Activity a) {
		if (a != null) {
			Intent inPopulate = new Intent(a.getBaseContext(), ConnectionResource.class);
	    	
	    	String tempQuery = "sid=" + sessionID;
	    	
	    	inPopulate.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
			inPopulate.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_POPULATE_EVENT_LIST);
		
			// Launch ConnectionResource with the query and request code in the extras
			a.startActivityForResult(inPopulate, BetterHood.REQ_POPULATE_EVENT_LIST);
		} else {
			Log.i(BetterHood.TAG_EVENT_LIST, "Recieved a null activity, cancelling database query");
		}		
	}
	
	public void populate(ArrayList<Event> in) {
		events = in;
		Log.i(BetterHood.TAG_EVENT_LIST, "EventList updated, " + Integer.toString(events.size()) + " events returned.");
	}

	public ArrayList<Event> getEvents() {
		return events;
	}
}
