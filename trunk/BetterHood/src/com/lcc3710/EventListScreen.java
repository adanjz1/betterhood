package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class EventListScreen extends Activity {
	
	Intent intent;
	Bundle extras;
	
	EventList eventList;
	String sessionID;
	
	ListView listEventView;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        intent = getIntent();
        extras = intent.getExtras();
        
        if (extras != null) {
        	sessionID = (String) extras.getString(BetterHood.EXTRAS_SESSION_ID);
        	eventList = (EventList) extras.get(BetterHood.EXTRAS_EVENT_LIST);
        }
	}
}
