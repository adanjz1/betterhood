package com.lcc3710;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class HandleEventComment {
	private String sessionID;
	Activity myActivity;
	HandleEventComment(Activity a){
		myActivity = a;
		sessionID = myActivity.getIntent().getExtras().getString(BetterHood.EXTRAS_SESSION_ID);
		
	
	}
	void postComment(String id, String c, String n){
		//do intent stuff
		
		
		String eventID = id;
		String name = n;
		String comment = c;
		String query = "sid=" + sessionID;
		query += "&eventID=" + eventID;
		query += "&user_id=" + name;
		query += "&comment=" + comment;
		
		Log.i("query junk= ", query);
		
		Intent commentIntent = new Intent(myActivity.getBaseContext(), ConnectionResource.class);
		commentIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
		commentIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_EVENT_COMMENT);
		myActivity.startActivityForResult(commentIntent, BetterHood.REQ_EVENT_COMMENT);
		//do the php stuff to send to mysql
		
		
	}
	
	ArrayList<String> recieveComments(int id){
		int eventID = id;
		
		//todo php stuff to request comments from mysql for an event
		return null;
		
	}
	
	

}
