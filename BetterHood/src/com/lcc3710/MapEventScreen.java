package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MapEventScreen extends Activity {
	private Intent intent;
	private Bundle extras;
	private String sessionID;
	private Button buttonDialogBack;
	private Button buttonDialogForward;
	private Button buttonComments;
	private TextView textEventType;
	private TextView textEventHost;
	private TextView textEventName;
	private LinearLayout eventLayout;
	
	private Template[] eventList;
	private Template event;
	private int eventID;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO response handling
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_screen);

		intent = getIntent();
		extras = intent.getExtras();
		sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
		eventID = extras.getInt(BetterHood.EXTRAS_EVENT_ID);
		
		makeScreen();

	}

	public void makeScreen() {
		this.setContentView(R.layout.event_screen);
		
		eventLayout = (LinearLayout) this.findViewById(R.id.eventLayout);

		buttonDialogBack = (Button) this.findViewById(R.id.buttonBack);
		buttonDialogForward = (Button) this.findViewById(R.id.buttonForward);
		buttonComments = (Button) this.findViewById(R.id.buttonComments);

		textEventName = (TextView) this.findViewById(R.id.textEventName);
		textEventHost = (TextView) this.findViewById(R.id.textEventHost);
		textEventType = (TextView) this.findViewById(R.id.textEventType);

		buttonDialogForward.setEnabled(true);
		buttonComments.setEnabled(true);
		
		// populate fields
		TemplateFactory tf = new TemplateFactory(sessionID);
		eventList = tf.getTemplates(TemplateFactory.POPULATE_EVENTS);
		
		for (int i = 0; i < eventList.length; i++) {
			Template t = eventList[i];
			if (t.id == eventID) {
				event = t;
				break;
			}
		}
		
		// set basic info
		textEventType.setText(event.title);
		textEventHost.setText(event.creatorName);
		
		// go through each template widget and add it to the form
     	String tag = "Form Population";
     	Log.i(tag, "-------\nGet ready to populate our form\n-------");
     	
     	TemplateWidget w = null;
     	View previousView = findViewById(R.id.textEventHost);
     	
     	int curId = 0;
     	for (int i = 0; i < eventLayout.getChildCount(); i++) {
     		int j = eventLayout.getChildAt(i).getId();
     		Log.i("Form Population", "found id: " + Integer.toString(j));
     		if (j > curId)
     			curId = j;
     	}
		
		TemplateWidget[] tw = event.widgets;
		for (int i = 0; i < tw.length; i++) {
			w = tw[i];
			String type = w.type;
			String label = w.label;
			
			Log.i("Form Population", "previousView id: " + Integer.toString(previousView.getId()));
			
			RelativeLayout rlRow = new RelativeLayout(this);
			
			TextView tvLabel = new TextView(this);
			tvLabel.setTextColor(Color.parseColor("#FF227A81"));
			tvLabel.setId(++curId);
			TextView tvValue = new TextView(this);
			tvValue.setId(++curId);
			
			RelativeLayout.LayoutParams pLabel = new RelativeLayout.LayoutParams(
					100, 
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			pLabel.setMargins(0, 5, 0, 0);
			RelativeLayout.LayoutParams pValue = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT, 
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			pValue.addRule(RelativeLayout.RIGHT_OF, tvLabel.getId());
			pValue.setMargins(0, 5, 0, 0);
			
			previousView = tvValue;
			
			if (type.equals("EditText")) {
				if (label.equals("Title")) {
					textEventName.setText(w.value);
				} else {
					tvLabel.setText(w.label + ":");
					tvValue.setText(w.value);
					
					// add the views
					rlRow.addView(tvLabel, pLabel);
					rlRow.addView(tvValue, pValue);
				}
			} else if (type.equals("Location")) {
				tvLabel.setText(w.label + ":");
				tvValue.setText(w.value);
				
				// add the views
				rlRow.addView(tvLabel, pLabel);
				rlRow.addView(tvValue, pValue);
			} else if (type.contains("Date")) {
				tvLabel.setText(w.label + ":");
				tvValue.setText(w.value);
				
				// add the views
				rlRow.addView(tvLabel, pLabel);
				rlRow.addView(tvValue, pValue);
			}
			
			eventLayout.addView(rlRow);
		}

		View.OnClickListener buttonListener = new View.OnClickListener() {
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.buttonBack:
					setResult(RESULT_CANCELED, intent);
					finish();
					break;
				case R.id.buttonForward:
					if (v.isEnabled()) {
						String query = "";
						query += "&sid=" + sessionID;
						query += "&event_id=" + extras.getString(BetterHood.EXTRAS_EVENT_ID);

						Intent iHaveIntent = new Intent(v.getContext(), ConnectionResource.class);
						iHaveIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
						iHaveIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_JOIN_EVENT);
						startActivityForResult(iHaveIntent, BetterHood.REQ_JOIN_EVENT);
						setResult(RESULT_OK, intent);
						finish();
					}
					break;
				case R.id.buttonComments:
					// posting comments
					String query = "";
					query += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
					query += "&event_id=" + event.id;
					Log.i("event id", Integer.toString(event.id));

					Intent iHaveIntent = new Intent(v.getContext(), EventCommentPage.class);
					iHaveIntent.putExtra(BetterHood.EXTRAS_SESSION_ID, extras.getString(BetterHood.EXTRAS_SESSION_ID));
					iHaveIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
					iHaveIntent.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
					iHaveIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_COMMENT_POPULATE);
					iHaveIntent.putExtra(BetterHood.EXTRAS_EVENT_ID, event.id);
					startActivityForResult(iHaveIntent, BetterHood.REQ_COMMENT_POPULATE);
					break;
				}
			}
		};

		buttonDialogBack.setOnClickListener(buttonListener);
		buttonDialogForward.setOnClickListener(buttonListener);
		buttonComments.setOnClickListener(buttonListener);
	}
}
