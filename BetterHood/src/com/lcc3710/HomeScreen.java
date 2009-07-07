package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreen extends Activity {
	private Button buttonWant;
	private Button buttonSettings;
	private Button buttonMap;
	
	private Intent intent;
	
	private String sessionID;
	private Bundle extras;
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    	}
    }
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        
        intent = getIntent();
        
        buttonWant = (Button) findViewById(R.id.buttonWant);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        buttonMap = (Button) findViewById(R.id.buttonMap);
        
        if ((extras = intent.getExtras()) != null) {
        	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
        }
        
        buttonWant.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent inWant = new Intent(view.getContext(), CreateEventScreen1.class);
				startActivityForResult(inWant, BetterHood.REQ_CREATE_EVENT);
			}
        });
        
        buttonSettings.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				// TODO settings button doesn't do anything yet
				// lets pretend its a logout button for now
				setResult(RESULT_CANCELED, intent);
				finish();
				
			}
        });
        
        buttonMap.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				// TODO settings button doesn't do anything yet
				// lets pretend its a logout button for now
				Intent inMap = new Intent(view.getContext(), MapViewScreen.class);
				startActivityForResult(inMap, BetterHood.REQ_CREATE_EVENT);
			}
        });
	}

}
