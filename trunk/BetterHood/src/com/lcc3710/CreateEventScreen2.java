package com.lcc3710;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
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
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class CreateEventScreen2 extends Activity {
    /** Called when the activity is first created. */
	private Button buttonBack;
	private Button buttonForward;
	
	private Button buttonPickLocation;
	private Button buttonPickDate;
	
	Activity thisActivity = this;
	
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
	
	private HashMap<String,Date> dates;
	private HashMap<String,Time> times;
	
	private String szEventAddress;
	private Location lEventLocation;
	
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	static final int LOCATION_DIALOG_ID = 2;
	
	private Template template;
	
	private Bundle extras;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        intent = getIntent();
        extras = intent.getExtras();
        
        // get templates
		TemplateFactory tf = new TemplateFactory(extras.getString(BetterHood.EXTRAS_SESSION_ID));
		Template[] templates = tf.getTemplates(TemplateFactory.POPULATE_TEMPLATES);
        
		// set the base layout
        setContentView(R.layout.create_event_2);
        
        // create the layout dynamically
        if (templates != null) {
        	// get our template
        	String tName = extras.getString(BetterHood.EXTRAS_EVENT_TEMPLATE_NAME);
        	for (int i = 0; i < templates.length; i++) {
        		if (tName.equals(templates[i].title)) {
        			template = templates[i];
        		}
        	}
        	// populate the form
        	populateForm();
        }
        
        // current location if we have a location picker
        Location t;
        if ((t = (Location) extras.get(BetterHood.EXTRAS_CURRENT_LOCATION)) != null) {
        	lEventLocation = t;
        } else {
        	lEventLocation = null;
        }
        
        // date & time for if we have a date picker
        final Calendar c = Calendar.getInstance();
		
        iYear = c.get(Calendar.YEAR);
        iMonth = c.get(Calendar.MONTH);
        iDay = c.get(Calendar.DAY_OF_MONTH);
        iHour = c.get(Calendar.HOUR_OF_DAY);
        iMinute = c.get(Calendar.MINUTE);
        
        // set back and forward button behavior
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button) findViewById(R.id.buttonForward);
        
		buttonBack.setOnClickListener(new View.OnClickListener() {
			// back button
			public void onClick(View view) {
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
		
		buttonForward.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// get our user id
				SQLQuery grabUserId = new SQLQuery(BetterHood.PHP_FILE_GET_USERID, "sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID));
				String uid = grabUserId.submit();
				template.creator = uid;
				
				// update widget values
				TemplateWidget[] widgets = template.widgets;
				for (int i = 0; i < widgets.length; i++) {
					TemplateWidget w = widgets[i];
					String v = null;
					if (w.type.equals("EditText")) {
						v = ((EditText)findViewById(w.id)).getText().toString();
					} else if ((w.type.equals("Location")) | (w.type.contains("Date"))) {
						v = ((Button)findViewById(w.id)).getText().toString();
					}
					
					if (v != null) {
						template.widgets[i].value = v;
					}
				}
				//end value updating
				//pack up the xml
				String xml = "";
				Boolean xmlFail = false;
				xml += "<Template";
				xml += " title=\"" + template.title + "\"";
				xml += " icon=\"" + template.icon + "\"";
				xml += " creator=\"" + template.creator + "\"";
				xml += ">";
				//pack the widgets
				widgets = template.widgets;
				for (int i = 0; i < widgets.length; i++) {
					TemplateWidget w = widgets[i];
					if ((w.value != null) && (!w.value.equals("")) && (!w.value.equals(w.label))) {
						xml += "<Widget";
						xml += " type=\"" + w.type + "\"";
						xml += " label=\"" + w.label + "\"";
						xml += ">";
						if (w.type.equals("Location")) {
							// location w/ GPS attributes
							DissectAddress da = new DissectAddress(w.value, view.getContext());
                            String lat = Double.toString(da.getLatitude());
                            String lon = Double.toString(da.getLongitude());

							xml += "<value " + "latitude=\"" + lat + "\" ";
							xml += "longitude=\"" + lon + "\">";
							xml += w.value + "</value>";
						} else if (w.type.contains("Date")) {
							Date date = dates.get(w.label);
							Time time = times.get(w.label);
							xml += "<value " + "date=\"" + date.toString() + "\" ";
							xml += "time=\"" + time.toString() + "\">";
							xml += w.value + "</value>";
						} else {
							xml += "<value>" + w.value + "</value>";
						}
						xml += "</Widget>";
					} else {
						if (w.required) {
							// a required field wasn't filled out
							Toast.makeText(view.getContext(), w.label + " is a required field.", 6).show();
							xmlFail = true;
							break;
						}
					}
				}
				xml += "</Template>";
				//finished packing xml
				//send it off to the server
				if (!xmlFail) {
					String q = "event_xml=" + xml + "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
					SQLQuery query = new SQLQuery(BetterHood.PHP_FILE_CREATE_EVENT_XML, q);
					String result = query.submit();
					String tag = "Event Creation";
					Log.i(tag, "Sent query: " + q);
					Log.i(tag, "Event Creation Result: " + result);
					
					AlertDialog.Builder createEventAlert = new AlertDialog.Builder(view.getContext())
			        .setTitle("Create an event")
			        .setIcon(R.drawable.icon); 
					
					if (result.equals("true")) {
						// event creation success, finish the activity
						createEventAlert.setMessage("Your event was successfully created!");
                        createEventAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                                setResult(RESULT_OK, intent);
                                finish();
                        	}
                        });                    
					} else {
						// event creation failed, notify
						createEventAlert.setMessage("Creation of event failed! Try again later.");
                        createEventAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                                setResult(RESULT_CANCELED, intent);
                                //finish();
                        	}
                        });

					}
					
					createEventAlert.show();
				}	
			}
		});
    }
    
    // here's where we turn our template into a form
    private void populateForm() {
    	RelativeLayout layout = (RelativeLayout) findViewById(R.id.formLayout);
    	
    	TextView labelTemplateTitle = (TextView) findViewById(R.id.labelTemplateTitle);
     	
     	// set template title
     	if (labelTemplateTitle != null) {
     		String temp = labelTemplateTitle.getText().toString();
     		labelTemplateTitle.setText(template.title + temp);
     	}
     	
     	TemplateWidget[] widgets = template.widgets;
     	
     	// default layout params
     	LayoutParams params;
     	
     	// go through each template widget and add it to the form
     	String tag = "Form Population";
     	Log.i(tag, "-------\nGet ready to populate our form\n-------");
     	
     	TemplateWidget w = null;
     	View previousView = findViewById(R.id.viewDivider);
     	
     	int curId = 0;
     	for (int i = 0; i < layout.getChildCount(); i++) {
     		int j = layout.getChildAt(i).getId();
     		if (j > curId)
     			curId = j;
     	}
     	curId++;
     	
     	for (int i = 0; i < widgets.length; i++) {
     		w = widgets[i];
     		Log.i(tag, w.toString());
     		
     		if (w.type.equals("EditText")) {
     			TextView label = new TextView(this);
     			EditText edit = new EditText(this);
     			
     			// setup edit box
     			edit.setInputType(inputTypeFromString(w.inputType));
     			edit.setHint(w.hint);
     			// set id
     			edit.setId(curId);
     			template.widgets[i].id = curId;
     			curId++;
     			// setup layout params
     			params = new LayoutParams(225, LayoutParams.WRAP_CONTENT);
     			if (w.size.equals("Large")) {
     				params.height = 100;
     			}
     			params.addRule(RelativeLayout.BELOW, previousView.getId());
     			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
     			params.rightMargin = 6;
     			edit.setLayoutParams(params);
     			
     			
     			// setup label
     			label.setText(w.label + ":");
     			// set id
     			label.setId(curId);
     			curId++;
     			// setup layout params
     			params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
     			params.addRule(RelativeLayout.BELOW, previousView.getId());
     			params.addRule(RelativeLayout.LEFT_OF, edit.getId());
     			params.rightMargin = 6;
     			label.setLayoutParams(params);
     			
     			// add to the layout
     			layout.addView(edit);
     			layout.addView(label);
     			
     			// set the edit as the previous view
     			previousView = edit;
     			
     		} else if (w.type.equals("Location")) {
     			Button loc = new Button(this);
     			loc.setText(w.label);
     			// set id
     			loc.setId(curId);
     			template.widgets[i].id = curId;
     			curId++;
     			// setup layout params
     			params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
     			params.addRule(RelativeLayout.BELOW, previousView.getId());
     			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
     			loc.setLayoutParams(params);
     			
     			loc.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						buttonPickLocation = (Button)v;
						showDialog(LOCATION_DIALOG_ID);
					}
     			});
     			
     			// ad to the layout
     			layout.addView(loc);
     			
     			// set previous view
     			previousView = loc;
     			
     		} else if (w.type.contains("Date")) {
     			Button date = new Button(this);
     			date.setTag(w.label);
     			date.setText(w.label);
     			// set id
     			date.setId(curId);
     			template.widgets[i].id = curId;
     			curId++;
     			// setup layout params
     			params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
     			params.addRule(RelativeLayout.BELOW, previousView.getId());
     			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
     			date.setLayoutParams(params);
     			
     			date.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						buttonPickDate = (Button)v;
						showDialog(DATE_DIALOG_ID);
					}
     			});
     			
     			// ad to the layout
     			layout.addView(date);
     			
     			// set previous view
     			previousView = date;
     			
     		}
     	}
     	Log.i(tag, "-------\nForm population finished\n-------");
    }
    
    private int inputTypeFromString(String type) {
    	int result = 0;
    	if (type.equals("text")) {
    		result = InputType.TYPE_CLASS_TEXT;
    	} else if (type.equals("textCapWords")) {
    		result = InputType.TYPE_TEXT_FLAG_CAP_WORDS;
    	} else if (type.equals("textCapSentences")) {
    		result = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
    	} else if (type.equals("textAutoCorrect")) {
    		result = InputType.TYPE_TEXT_FLAG_AUTO_CORRECT;
    	} else if (type.equals("textAutoComplete")) {
    		result = InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;
    	} else if (type.equals("textEmailAddress")) {
    		result = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
    	} else if (type.equals("textPersonName")) {
    		result = InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
    	} else if (type.equals("textPostalAddress")) {
    		result = InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS;
    	} else if (type.equals("number")) {
    		result = InputType.TYPE_CLASS_NUMBER;
    	} else if (type.equals("phone")) {
    		result = InputType.TYPE_CLASS_PHONE;
    	} else {
    		// default case
    		result = InputType.TYPE_CLASS_TEXT;
    	}
    	
    	return result;
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
        				0,
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
					if (buttonView.isEnabled()) {
						if (isChecked) {
							checkBoxAddressLocation.setChecked(false);
							buttonDialogForward.setEnabled(true);
						}  else if (!checkBoxCurrentLocation.isChecked()) {
							buttonDialogForward.setEnabled(false);
						}
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
    		szEventAddress = locationToAddress(lEventLocation);
    		textCurrentLocation.setText("Current Location:\n" + szEventAddress);
    		checkBoxCurrentLocation.setEnabled(true);
    		checkBoxCurrentLocation.setChecked(true);
    	} else {
    		textCurrentLocation.setText("Could not get current location from GPS, please enter an address above.");
    		checkBoxCurrentLocation.setEnabled(false);
    		checkBoxAddressLocation.setChecked(true);
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
            	if (dates == null) {
            		dates = new HashMap<String,Date>();
            	}
            	
            	String tag = (String) buttonPickDate.getTag();
            	Date d = dates.get(tag);
            	
            	if (d != null) {
            		dates.remove(tag);
            	}
            	d = Date.valueOf(year + "-" + monthOfYear + "-" + dayOfMonth);
            	dates.put(tag, d);
                
                buttonPickDate.setText("On " 
						+ Integer.toString(monthOfYear) + "/" 
						+ Integer.toString(dayOfMonth) + "/" 
						+ Integer.toString(year));
                
                showDialog(TIME_DIALOG_ID);
            }
        };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = 
    	new TimePickerDialog.OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if (times == null) {
					times = new HashMap<String,Time>();
				}
				
				Log.i("time set", Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
				
				String tag = (String) buttonPickDate.getTag();
				Time t = times.get(tag);
				if (t != null) {
					times.remove(tag);
				}
				t = new Time(hourOfDay, minute, 0);
				times.put(tag, t);
				
				int tempHour = hourOfDay;
				
				String tempMinute = Integer.toString(minute);
				if (minute < 10) { 
					tempMinute = "0" + tempMinute;
				}
				
				String ampm = "AM";
				
				if (hourOfDay > 12) {
					tempHour = hourOfDay - 12;
					ampm = "PM";
				}
				
				buttonPickDate.setText(buttonPickDate.getText()
						+ " at " 
						+ Integer.toString(tempHour) + ":" 
						+ tempMinute + " "
						+ ampm);
			}
    	};
}