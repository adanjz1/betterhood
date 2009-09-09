package com.lcc3710;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class HandleHavingItem {
	private String sessionID;
	Activity myActivity;
	HandleHavingItem(Activity a){
		myActivity = a;
		sessionID = myActivity.getIntent().getExtras().getString(BetterHood.EXTRAS_SESSION_ID);
		
	
	}
	void postItem(String id, String c, String n){
		//do intent stuff
		
		

		
		Intent commentIntent = new Intent(myActivity.getBaseContext(), ConnectionResource.class);
		//commentIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
		commentIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_EVENT_COMMENT);
		myActivity.startActivityForResult(commentIntent, BetterHood.REQ_EVENT_COMMENT);
		//do the php stuff to send to mysql
		
		
	}
	
	ArrayList<String> recieveItem(int id){
		int eventID = id;
		
		//todo php stuff to request comments from mysql for an event
		return null;
		
	}
	
	

}

