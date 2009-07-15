package com.lcc3710;

import android.app.Activity;
import android.content.Intent;

public class EventList {
	String sessionID;
	
	Event[] events;
	
	public EventList(String sessionID) {
		this.sessionID = sessionID;
	}
	
	public void queryDatabase(Activity a) {
		Intent inPopulate = new Intent(a.getBaseContext(), ConnectionResource.class);
    	
    	String tempQuery = "sid=" + sessionID;
    	
    	inPopulate.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
		inPopulate.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_POPULATE_EVENT_LIST);
	
		// Launch ConnectionResource with the query and request code in the extras
		a.startActivityForResult(inPopulate, BetterHood.REQ_POPULATE_EVENT_LIST);
	}
	
	public void populate() {
		// TODO what does this actually take in?
	}
}
