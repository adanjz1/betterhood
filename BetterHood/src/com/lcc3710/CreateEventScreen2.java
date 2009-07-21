package com.lcc3710;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateEventScreen2 extends Activity {
    /** Called when the activity is first created. */
	private Button buttonBack;
	private Button buttonForward;
	private Button buttonPickLocation;
	private Button buttonPickDate;
	
	private EditText editEventName;
	private EditText editEventCost;
	private EditText editEventMessage;
	
	// location dialog picker members
	private Dialog dialogLocationPicker;
	
	private EditText editAddress;
	private TextView textCurrentLocation;
	
	private CheckBox checkBoxAddressLocation;
	private CheckBox checkBoxCurrentLocation;
	
	private Button buttonDialogBack;
	private Button buttonDialogForward;
	
	private Intent intent;
	
	private int iDay;
	private int iMonth;
	private int iYear;
	private int iMinute;
	private int iHour;
	
	private String szEventAddress;
	private Location lEventLocation;
	
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
        
        Location t;
        if ((t = (Location) extras.get(BetterHood.EXTRAS_CURRENT_LOCATION)) != null) {
        	lEventLocation = t;
        } else {
        	lEventLocation = null;
        }
        
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button) findViewById(R.id.buttonForward);
        buttonPickLocation = (Button) findViewById(R.id.buttonPickLocation);
        buttonPickDate = (Button) findViewById(R.id.buttonPickDate);
		
        editEventName = (EditText) findViewById(R.id.editEventName);
        editEventCost = (EditText) findViewById(R.id.editEventCost);
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
				String tempEventName, tempEventAddressStreet, tempEventAddressCity, tempEventContactEmail, tempEventCost,
				tempEventDate, tempEventLocationName, tempEventLatitude, tempEventLongitude, tempEventMessage, tempEventPhoneNumber, 
				tempEventStartTime, tempEventEndTime;
				
				tempEventName = editEventName.getText().toString();
				
				if (szEventAddress != null) {
					szEventAddress = szEventAddress.replace(" ", "");
					int commaIdx = szEventAddress.indexOf(",");
					int commaIdx2 = szEventAddress.indexOf(",", commaIdx+1);
					tempEventAddressStreet = szEventAddress.substring(0, commaIdx);
					tempEventAddressCity = szEventAddress.substring(commaIdx+1, commaIdx2);
				} else {
					tempEventAddressStreet = null;
					tempEventAddressCity = null;
				}
				tempEventCost = editEventCost.getText().toString();
				
				String tempMonth, tempDay;
				if (iMonth < 10) {
					tempMonth = "0" + Integer.toString(iMonth);
				} else {
					tempMonth = Integer.toString(iMonth);
				}
				
				if (iDay < 10) {
					tempDay = "0" + Integer.toString(iDay);
				} else {
					tempDay = Integer.toString(iDay);
				}
				
				tempEventDate = tempMonth + tempDay + Integer.toString(iYear);
				
				if (lEventLocation != null) {
					tempEventLatitude = Double.toString(lEventLocation.getLatitude());
					tempEventLongitude = Double.toString(lEventLocation.getLongitude());
				} else {
					tempEventLatitude = null;
					tempEventLongitude = null;
				}
				tempEventLocationName = "";
				tempEventMessage = editEventMessage.getText().toString();
				tempEventStartTime = Integer.toString(iHour) + "_" + Integer.toString(iMinute);
				
				// TEMPORARY CRAP
				tempEventEndTime = "0";
				tempEventPhoneNumber = "0";
				tempEventContactEmail = "0";
				
				if (tempEventName.length() == 0) {
					Toast.makeText(view.getContext(), "Error: " + "'Event Name' cannot be left blank.", BetterHood.TOAST_TIME).show();
				} else {					
					intent.putExtra(BetterHood.EXTRAS_EVENT_NAME, tempEventName);
					intent.putExtra(BetterHood.EXTRAS_EVENT_ADDRESS_STREET, tempEventAddressStreet);
					intent.putExtra(BetterHood.EXTRAS_EVENT_ADDRESS_CITY, tempEventAddressCity);
					intent.putExtra(BetterHood.EXTRAS_EVENT_CONTACT_EMAIL, tempEventContactEmail);
					intent.putExtra(BetterHood.EXTRAS_EVENT_COST, tempEventCost);
					intent.putExtra(BetterHood.EXTRAS_EVENT_DATE, tempEventDate);
					intent.putExtra(BetterHood.EXTRAS_EVENT_LOCATION_LATITUDE, tempEventLatitude);
					intent.putExtra(BetterHood.EXTRAS_EVENT_LOCATION_LONGITUDE, tempEventLongitude);
					intent.putExtra(BetterHood.EXTRAS_EVENT_LOCATION_NAME, tempEventLocationName);
					intent.putExtra(BetterHood.EXTRAS_EVENT_PHONE_NUMBER, tempEventPhoneNumber);
					intent.putExtra(BetterHood.EXTRAS_EVENT_MESSAGE, tempEventMessage);
					intent.putExtra(BetterHood.EXTRAS_EVENT_START_TIME, tempEventStartTime);
					intent.putExtra(BetterHood.EXTRAS_EVENT_END_TIME, tempEventEndTime);
					doEventCreation();
				}
				
			}
		});
		
		if (BetterHood.DEBUG) {
			editEventName.setText("Event1234");
			editEventMessage.setText("helloworld");
			editEventCost.setText("1");
		}
    }
    
    // when we want to create a dialog, figure out which kind it is and return it
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
        	return buildLocationPickerDialog();
        }
        return null;
    }
    
    // LOCATION PICKER DIALOG
    private Dialog buildLocationPickerDialog() {
    	dialogLocationPicker = new Dialog(this);
    	
    	dialogLocationPicker.setContentView(R.layout.location_picker);
    	dialogLocationPicker.getWindow().setLayout(300, 300);
    	
    	buttonDialogBack = (Button) dialogLocationPicker.findViewById(R.id.buttonBack);
    	buttonDialogForward = (Button) dialogLocationPicker.findViewById(R.id.buttonForward);
    	
    	editAddress = (EditText) dialogLocationPicker.findViewById(R.id.editAddress);
    	textCurrentLocation = (TextView) dialogLocationPicker.findViewById(R.id.textCurrentLocation);
    	
    	checkBoxAddressLocation = (CheckBox) dialogLocationPicker.findViewById(R.id.checkBoxAddressLocation);
    	checkBoxCurrentLocation = (CheckBox) dialogLocationPicker.findViewById(R.id.checkBoxCurrentLocation);
    	
    	checkBoxCurrentLocation.setChecked(true);
    	
    	CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				switch (buttonView.getId()) {
				case R.id.checkBoxAddressLocation:
					if (isChecked) {
						checkBoxCurrentLocation.setChecked(false);
						buttonDialogForward.setEnabled(true);
					} else if (!checkBoxCurrentLocation.isChecked()) {
						buttonDialogForward.setEnabled(false);
					}
					break;
				case R.id.checkBoxCurrentLocation:
					if (isChecked) {
						checkBoxAddressLocation.setChecked(false);
						buttonDialogForward.setEnabled(true);
					}  else if (!checkBoxCurrentLocation.isChecked()) {
						buttonDialogForward.setEnabled(false);
					}
					break;
				}
			}
    	};
    	
    	checkBoxAddressLocation.setOnCheckedChangeListener(checkBoxListener);
    	checkBoxCurrentLocation.setOnCheckedChangeListener(checkBoxListener);
    	
    	View.OnClickListener buttonListener = new View.OnClickListener() {
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.buttonBack:
					dialogLocationPicker.dismiss();
					break;
				case R.id.buttonForward:
					if (v.isEnabled()) {
						if (checkBoxAddressLocation.isChecked()) {
							szEventAddress = editAddress.getText().toString();
							buttonPickLocation.setText(szEventAddress);
						}
						if (checkBoxCurrentLocation.isChecked()) {
							if (lEventLocation != null) {
								//buttonPickLocation.setText(geoPointToAddress());
								buttonPickLocation.setText(locationToAddress(lEventLocation));
							}
						}
					}
					dialogLocationPicker.dismiss();
					break;
				}
			}
    	};
    	
    	buttonDialogBack.setOnClickListener(buttonListener);
    	buttonDialogForward.setOnClickListener(buttonListener);
    	
    	
    	if (lEventLocation != null) {
    		//textCurrentLocation.setText("Current location:\n" + geoPointToAddress(mEventLocation.getPoint()));
    		//textCurrentLocation.setText("Current Location:\n" + Double.toString(lEventLocation.getLatitude()) + ",\n" + Double.toString(lEventLocation.getLongitude()));
    		textCurrentLocation.setText("Current Location:\n" + locationToAddress(lEventLocation));
    	}
    	
    	if (BetterHood.DEBUG) {
    		editAddress.setText("451 Heartcircle Pl, Atlanta, GA 30303");
    	}
    	
    	return dialogLocationPicker;
    }
    
    private String locationToAddress(Location l) {
    	Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    	try {
			List<Address> a = geocoder.getFromLocation(l.getLatitude(), l.getLongitude(), 3);
			if (a.size() > 0) {
				String szAddress = a.get(0).getAddressLine(0) + "\n" + a.get(0).getAddressLine(1);
				return szAddress;
			}
		} catch (IOException e) {
			e.printStackTrace();
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
					tempHour = iHour - 12;
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
    	
    	String tempQuery = "";
    	String token;
    		
    	if ((token = extras.getString(BetterHood.EXTRAS_EVENT_NAME)) != null) {
    		tempQuery += "Event_Name=" + token;
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_TEMPLATE_NAME)) != null) {
    			tempQuery += "&Event_Type=" + token.replace(" ", "");
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_ADDRESS_STREET)) != null) {
    			tempQuery += "&Event_Location_Address_Street=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_ADDRESS_CITY)) != null) {
    			tempQuery += "&Event_Location_Address_City=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_CONTACT_EMAIL)) != null) {
    			tempQuery += "&Event_Contact_Email=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_COST)) != null) {
    			tempQuery += "&Event_Cost=" + token.replace(".", "_");
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_DATE)) != null) {
    			tempQuery += "&Event_Start_Date=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_LOCATION_LATITUDE)) != null) {
    			tempQuery += "&Event_Location_Latitude=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_LOCATION_LONGITUDE)) != null) {
    			tempQuery += "&Event_Location_Longitude=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_LOCATION_NAME)) != null) {
    			tempQuery += "&Event_Location_Name=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_MESSAGE)) != null) {
    			tempQuery += "&Event_Description=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_PHONE_NUMBER)) != null) {
    			tempQuery += "&Event_Phone_Number=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_START_TIME)) != null) {
    			tempQuery += "&Event_Start_Time=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_EVENT_END_TIME)) != null) {
    			tempQuery += "&Event_End_Time=" + token;
    		}
    		if ((token = extras.getString(BetterHood.EXTRAS_SESSION_ID)) != null) {
    			tempQuery += "&sid=" + token;
    			
    			intent.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
    			intent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_CREATE_EVENT);
    		
    			// Launch ConnectionResource with the query and request code in the extras
    			intent.setClass(this, ConnectionResource.class);
    			
    			startActivityForResult(intent, BetterHood.REQ_CREATE_EVENT);
    		} else {
    			Log.i(BetterHood.TAG_CREATE_EVENT_SCREEN_2, BetterHood.ERROR_PREFIX + "Session ID not found.");
    		}
    	}
    	
    	
    }
}