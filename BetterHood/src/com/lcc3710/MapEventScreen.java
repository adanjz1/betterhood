package com.lcc3710;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MapEventScreen extends Activity {
	private Intent intent;
	private Bundle extras;
	private String sessionID;
	private Button buttonDialogBack;
	private Button buttonDialogForward;
	private Button buttonAddComment;
	private Button buttonComments;
	private TextView textEventType;
	private TextView textEventDate;
	private TextView textEventAddress;
	private TextView textEventHost;
	private TextView textEventDescription;
	
	private TextView textEventName;
	
	private EditText editEventComment;
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO response handling
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.event_screen);
        
        intent = getIntent();
        extras = intent.getExtras();
        makeScreen(this);

}
	
	public void makeScreen(Activity a){
		
		final Activity ac = a;

		this.setContentView(R.layout.event_screen);

		buttonDialogBack = (Button) this.findViewById(R.id.buttonBack);
		buttonDialogForward = (Button) this.findViewById(R.id.buttonForward);
		buttonComments = (Button) this.findViewById(R.id.buttonComments);
		
		textEventName = (TextView) this.findViewById(R.id.textEventName);
		textEventType = (TextView) this.findViewById(R.id.textEventType);
		textEventDate = (TextView) this.findViewById(R.id.textEventDate);
		textEventAddress = (TextView) this.findViewById(R.id.textEventAddress);
		textEventHost = (TextView) this.findViewById(R.id.textEventHost);
		textEventDescription = (TextView) this.findViewById(R.id.textEventDescription);
		
		buttonDialogForward.setEnabled(true);
		buttonComments.setEnabled(true);
		
		textEventName.setText(extras.getString(BetterHood.EXTRAS_EVENT_NAME));
		textEventType.setText(extras.getString(BetterHood.EXTRAS_EVENT_TEMPLATE_NAME));
		textEventDate.setText(extras.getString(BetterHood.EXTRAS_EVENT_START_DATE));
		textEventAddress.setText(extras.getString(BetterHood.EXTRAS_EVENT_LOCATION_ADDRESS));
		textEventHost.setText(extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
		//textEventHost.setText("John Smith");
		textEventDescription.setText(extras.getString(BetterHood.EXTRAS_EVENT_MESSAGE));
		//textEventDescription.setText("My son is loose somewhere in the neighborhood! Please " +
		//		"help me find him!");
		
		//Log.i(BetterHood.TAG_EVENT_OVERLAY, extras.getString(BetterHood.EXTRAS_EVENT_ID));

		View.OnClickListener buttonListener = new View.OnClickListener() {
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.buttonBack:
					setResult(RESULT_CANCELED, intent);
					finish();
					break;
				case R.id.buttonForward:
					if (v.isEnabled()) {
						String query ="";
						query += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
						query += "&user_name=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
						query += "&event_id=" + extras.getString(BetterHood.EXTRAS_EVENT_ID);
						
						Log.i("what do i gots =", query);
						Intent iHaveIntent = new Intent(ac, ConnectionResource.class);
						iHaveIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
						iHaveIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_JOIN_EVENT);
						startActivityForResult(iHaveIntent, BetterHood.REQ_JOIN_EVENT);
						setResult(RESULT_OK, intent);
						finish();
					}
					break;
				case R.id.buttonComments:
					//posting comments
					
						String query = "";
						query += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
						//query += "&user_name=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
						query += "&event_id=" + extras.getString(BetterHood.EXTRAS_EVENT_ID);
						//query += "&comment_text=" + commentTXT.getText().toString();
						
						
						Intent iHaveIntent = new Intent(ac, EventCommentPage.class);
						iHaveIntent.putExtra(BetterHood.EXTRAS_SESSION_ID, extras.getString(BetterHood.EXTRAS_SESSION_ID));
						iHaveIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
						iHaveIntent.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
						iHaveIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_COMMENT_POPULATE);
						iHaveIntent.putExtra(BetterHood.EXTRAS_EVENT_ID,extras.getString(BetterHood.EXTRAS_EVENT_ID));
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

