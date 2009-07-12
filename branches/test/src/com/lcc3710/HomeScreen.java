package com.lcc3710;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class HomeScreen extends MapActivity {
	private Button buttonWant;
	private Button buttonSettings;
	private Button buttonMap;
	
	private MapView mapView;
	
	private Intent intent;
	
	private String sessionID;
	private String username;
	
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
        
        mapView = (MapView) findViewById(R.id.mapview);
        
        if ((extras = intent.getExtras()) != null) {
        	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
        	username = extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
        }
     
        
        buttonWant.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent inWant = new Intent(view.getContext(), CreateEventScreen1.class);
				
				inWant.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
				inWant.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, username);
				
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
        
	}
	
	protected boolean isRouteDisplayed() {
	    return false;
	}

}
