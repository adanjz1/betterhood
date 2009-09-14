package com.lcc3710;

import java.util.ArrayList;

import com.lcc3710.Event.AttributeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SimilarScreen extends Activity{
	
	private Button buttonCancel;
	private ListView lv;
	private Intent intent;
	private Bundle extras;
	private Button buttonRespond;
	private String sessionID;
	String[] itemsName;
	private Activity a = this;
	String delims = "\\^";
	String[] partyTokens;
	String[] items;
	
	
	private static final String[] aszIHave = new String[] {
    	"John", "Taylor", "Freddy Kruger", "Alex", "Tiffany", "Scott",
    	"Garden Club"
    };
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO response handling
		intent = getIntent();
        extras = intent.getExtras();
		switch (requestCode) {
    	case BetterHood.REQ_SIMILAR_SCREEN:
    		
    		if (resultCode == RESULT_OK) {
    			Log.i("gettin dat info=", extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR));
    			// event was created, success!
    			if (extras != null) {
    				String szWebResponse;
    				if ((szWebResponse = extras.getString(BetterHood.EXTRAS_WEB_RESPONSE)) != null) {
    					Log.i(BetterHood.TAG_HOME_SCREEN, "pop sim returned response: " + szWebResponse);
    				}
    		}
    		}
    		
    		if (resultCode == RESULT_CANCELED) {
    			//something went wrong in creating the event
    		}
    		break;
    	case BetterHood.REQ_HOME_SCREEN:
		}
    			
    		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.similar_screen);
        intent = getIntent();
        extras = intent.getExtras();
       
		
       // Log.i("gettin dat info=", extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR));
      

        
        if (extras != null) {
        	// get sessionID
        	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);        	
        } else {
        	intent.putExtra(BetterHood.EXTRAS_ERROR_MESSAGE, "Session ID not found");
        	setResult(RESULT_CANCELED, intent);
        	finish();
        }
        populateSimilar();
        
        

}
	private void populateSimilar(){
		buttonCancel = (Button) findViewById(R.id.buttonReturn);
		buttonRespond = (Button) findViewById(R.id.buttonRespond);
		lv = (ListView) findViewById(R.id.similarView);
		lv.setChoiceMode(1);
		
		partyTokens = extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR).split(delims);
		Log.i("poart=", partyTokens.toString());
		int num = partyTokens.length;
	
		 items = new String[num/2];
		itemsName = new String[num/2];
		int itemsNameCount = 0;
		
		
		String[] name = partyTokens[0].split("\\|");
		int itemsCount = 0;
		
		
		Event newEvent = new Event();
		
		for (int i = 0; i < partyTokens.length; i++) {
			
			// EVENT_NAME
			
			if(partyTokens[i].startsWith("|")){
				
				name = partyTokens[i].split("\\|");
				
					//Log.i(TAG, "name = " + name[1]);
					items[itemsCount] = name[1];
					itemsCount++;
					//items[i+1] = "no";
					
				
				
			}
			if(partyTokens[i].startsWith(">")){
				
				name = partyTokens[i].split("\\>");
				
					//Log.i(TAG, "name = " + name[1]);
					itemsName[itemsNameCount] = name[1];
					itemsNameCount++;
					//items[i+1] = "no";
					
				
				
			}
			
			
				
				
			
				
			
		
		//lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, aszIHave));
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemsName));
		buttonCancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent home = new Intent(a, HomeScreen.class);
			    	home.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
			    	home.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
			    	startActivityForResult(home, BetterHood.REQ_HOME_SCREEN);
				}        	
	        });
		buttonRespond.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String name;
				String comment;
				for(int i =0; i< lv.getCount(); i++){
					if(lv.isItemChecked(i)){
						name = itemsName[i].toString();
						comment = items[i].toString();
						Log.i("nammmmme", name);
						Log.i("whattisit", comment);
						Intent home = new Intent(a, SimilarSub.class);
				    	home.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
				    	home.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
				    	home.putExtra(BetterHood.EXTRAS_EVENT_NAME, comment);
				    	home.putExtra(BetterHood.EXTRAS_EVENT_HOST, name);
				    	
				    	startActivityForResult(home, BetterHood.REQ_HOME_SCREEN);
						
						
						
					}
				}
			
			}        	
        });
	}
	}}
