package com.lcc3710;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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
	
	private int iDay;
	private int iMonth;
	private int iYear;
	private int iMinute;
	private int iHour;
	
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	static final int LOCATION_DIALOG_ID = 2;
	
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
        
        final Calendar c = Calendar.getInstance();
		
        iYear = c.get(Calendar.YEAR);
        iMonth = c.get(Calendar.MONTH);
        iDay = c.get(Calendar.DAY_OF_MONTH);
        iHour = c.get(Calendar.HOUR_OF_DAY);
        iMinute = c.get(Calendar.MINUTE);
        
        buttonPickDate.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		showDialog(DATE_DIALOG_ID);
        	}
        });
        
        buttonPickLocation.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		showDialog(LOCATION_DIALOG_ID);
        	}
        });
        
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
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        iYear, iMonth, iDay);
        case TIME_DIALOG_ID:
        	return new TimePickerDialog(this,
        				mTimeSetListener,
        				iHour,
        				iMinute,
        				false);
        case LOCATION_DIALOG_ID:
        	// TODO location picker dialog
        	return new DatePickerDialog(this,
                    mDateSetListener,
                    iYear, iMonth, iDay);
        }
        return null;
    }
    
    private DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, 
                                  int monthOfYear, int dayOfMonth) {
                iYear = year;
                iMonth = monthOfYear;
                iDay = dayOfMonth;
                
                showDialog(TIME_DIALOG_ID);
            }
        };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = 
    	new TimePickerDialog.OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				iMinute = minute;
				iHour = hourOfDay;
				int tempHour = iHour;
				
				String tempMinute = Integer.toString(iMinute);
				if (iMinute < 10) { 
					tempMinute = "0" + tempMinute;
				}
				
				String ampm = "AM";
				
				if (iHour > 12) {
					tempHour = 24 - iHour;
					ampm = "PM";
				}
				
				buttonPickDate.setText("On " 
						+ Integer.toString(iMonth) + "/" 
						+ Integer.toString(iDay) + "/" 
						+ Integer.toString(iYear) + " at " 
						+ Integer.toString(tempHour) + ":" 
						+ tempMinute + " "
						+ ampm);
			}
    	};
    
    private void doEventCreation() {
    	Bundle extras = intent.getExtras();
    	
    	String tempSessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
    	
    	String tempQuery = "Event_Name=" + extras.getString(BetterHood.EXTRAS_EVENT_NAME)
    		+ "&Event_Cost=" + "10" 
    		+ "&Event_Location=" + "123456"
    		+ "&sid=" + tempSessionID;
    	
    	intent.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
		intent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_CREATE_EVENT);
	
		// Launch ConnectionResource with the query and request code in the extras
		intent.setClass(this, ConnectionResource.class);
		
		startActivityForResult(intent, BetterHood.REQ_CREATE_EVENT);
    }
}