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
	//	this.getWindow().setLayout(300, 400);

		buttonDialogBack = (Button) this.findViewById(R.id.buttonBack);
		buttonDialogForward = (Button) this.findViewById(R.id.buttonForward);
	//	buttonAddComment = (Button) this.findViewById(R.id.buttonAddComment);
		buttonComments = (Button) this.findViewById(R.id.buttonComments);
		
		textEventType = (TextView) this.findViewById(R.id.textEventType);
		textEventDate = (TextView) this.findViewById(R.id.textEventDate);
		textEventAddress = (TextView) this.findViewById(R.id.textEventAddress);
		textEventHost = (TextView) this.findViewById(R.id.textEventHost);
		textEventDescription = (TextView) this.findViewById(R.id.textEventDescription);
		
		//editEventComment = (EditText) this.findViewById(R.id.editEventComment);
		
		buttonDialogForward.setEnabled(true);
		
		textEventType.setText(extras.getString(BetterHood.EXTRAS_EVENT_TEMPLATE_NAME));
		textEventDate.setText(extras.getString(BetterHood.EXTRAS_EVENT_START_DATE));
		textEventAddress.setText(extras.getString(BetterHood.EXTRAS_EVENT_LOCATION_ADDRESS));
		//textEventHost.setText("John Smith");
		//textEventDescription.setText("My son is loose somewhere in the neighborhood! Please " +
		//		"help me find him!");
		textEventHost.setText(extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
		textEventDescription.setText(extras.getString(BetterHood.EXTRAS_EVENT_MESSAGE));
		Log.i("do i have an id yo", extras.getString(BetterHood.EXTRAS_EVENT_ID));
		this.setTitle( extras.getString(BetterHood.EXTRAS_EVENT_NAME));
		
		//editEventComment.setMaxLines(5);
		
		  

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
				/*case R.id.buttonAddComment:
					//posting comments
					if(editEventComment.getText() != null){
						Log.i("what'smy Query=" , editEventComment.getText().toString());
						HandleEventComment commentHandler = new HandleEventComment(ac);
						commentHandler.postComment(extras.getString(BetterHood.EXTRAS_EVENT_HOST),editEventComment.getText().toString(), BetterHood.EXTRAS_ACCOUNT_FIRST_NAME + BetterHood.EXTRAS_ACCOUNT_LAST_NAME);
						
					}
					break;*/
				}
			}
		};

		buttonDialogBack.setOnClickListener(buttonListener);
		buttonDialogForward.setOnClickListener(buttonListener);
		//buttonAddComment.setOnClickListener(buttonListener);
	}
	}

