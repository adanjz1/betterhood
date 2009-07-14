package com.lcc3710;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateEventScreen2 extends Activity {
    /** Called when the activity is first created. */
	private Button buttonBack;
	private Button buttonForward;
	private Button buttonPickLocation;
	private Button buttonPickDate;
	
	private EditText editEventName;
	private EditText editEventMessage;
	
	private Intent intent;
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	AlertDialog.Builder createEventAlert = new AlertDialog.Builder(this)
        .setTitle("Create an event")
        .setIcon(R.drawable.icon);  
    	
    	Bundle extras = data.getExtras();
    	
    	if (resultCode == RESULT_OK) {
    		String szWebResponse;
    		
    		if ((szWebResponse = extras.getString(BetterHood.EXTRAS_WEB_RESPONSE)) != null) {
				Log.i(BetterHood.TAG_CREATE_EVENT_SCREEN_2, "Create Event response: " + szWebResponse);
				
				// valid?
				if (!(szWebResponse.length() > 0)) {
					createEventAlert.setMessage("Your event, '" + data.getExtras().getString(BetterHood.EXTRAS_EVENT_NAME) + "', was successfully created!");
		    		createEventAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int whichButton) {
		                	setResult(RESULT_OK, intent);
		            		finish();
		                }
					});
				} else {
					createEventAlert.setMessage("Creation of event, '" + data.getExtras().getString(BetterHood.EXTRAS_EVENT_NAME) + "', failed! Try again later.");
		    		createEventAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int whichButton) {
		                	setResult(RESULT_CANCELED, intent);
		            		//finish();
		                }
					});
				}
    		}
    		
    		createEventAlert.show();
    	}
    	if (resultCode == RESULT_CANCELED) {
    		// account creation failed, lets see why
			String szErrorMessage;
			
			if ((szErrorMessage = extras.getString(BetterHood.EXTRAS_ERROR_MESSAGE)) != null) {
				//we have an error message
				Log.i(BetterHood.TAG_CREATE_ACCOUNT_SCREEN_2, BetterHood.ERROR_PREFIX + szErrorMessage);
			}
    	}
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        intent = getIntent();
        Bundle extras = intent.getExtras();
        
        String szTemplateName;
        int iLayoutFile = 0;
            
        if ((szTemplateName = extras.getString(BetterHood.EXTRAS_EVENT_TEMPLATE_NAME)) != null) {
        	if (szTemplateName.equals(BetterHood.TEMPLATE_CARPOOL)) {
        		iLayoutFile = R.layout.template_carpool;
        	} else if (szTemplateName.equals(BetterHood.TEMPLATE_MISSING_CHILD)) {
        		iLayoutFile = R.layout.template_missing_child;
        	} else if (szTemplateName.equals(BetterHood.TEMPLATE_POOL_PARTY)) {
        		iLayoutFile = R.layout.template_pool_party;
        	} else if (szTemplateName.equals(BetterHood.TEMPLATE_POTLUCK)) {
        		iLayoutFile = R.layout.template_potluck;
        	}
        	setContentView(iLayoutFile);
        } else {
        	setContentView(R.layout.create_event_2);
        }
        
        
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button) findViewById(R.id.buttonForward);
        buttonPickLocation = (Button) findViewById(R.id.buttonPickLocation);
        buttonPickDate = (Button) findViewById(R.id.buttonPickDate);
		
        editEventName = (EditText) findViewById(R.id.editEventName);
        editEventMessage = (EditText) findViewById(R.id.editEventMessage);
        
		buttonBack.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
		
		buttonForward.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {				
				String tempEventName;
				
				tempEventName = editEventName.getText().toString();
				
				if (tempEventName.length() == 0) {
					Toast.makeText(view.getContext(), "Error: " + "'Event Name' cannot be left blank.", BetterHood.TOAST_TIME).show();
				} else {					
					intent.putExtra(BetterHood.EXTRAS_EVENT_NAME, tempEventName);
					doEventCreation();
				}
				
			}
		});
    }
    
    private void doEventCreation() {
    	Bundle extras = intent.getExtras();
    	
    	String tempSessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
    	
    	String tempQuery = "Event_Name=" + extras.getString(BetterHood.EXTRAS_EVENT_NAME)
    		+ "&Event_Cost=" + "1234" 
    		+ "&Event_Location=" + "12345"
    		+ "&sid=" + tempSessionID;
    	
    	intent.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
		intent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_CREATE_EVENT);
	
		// Launch ConnectionResource with the query and request code in the extras
		intent.setClass(this, ConnectionResource.class);
		
		startActivityForResult(intent, BetterHood.REQ_CREATE_EVENT);
    }
}