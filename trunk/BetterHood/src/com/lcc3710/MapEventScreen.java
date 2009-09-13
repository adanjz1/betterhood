package com.lcc3710;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
		buttonAddComment = (Button) this.findViewById(R.id.buttonAddComment);
		
		textEventType = (TextView) this.findViewById(R.id.textEventType);
		textEventDate = (TextView) this.findViewById(R.id.textEventDate);
		textEventAddress = (TextView) this.findViewById(R.id.textEventAddress);
		textEventHost = (TextView) this.findViewById(R.id.textEventHost);
		textEventDescription = (TextView) this.findViewById(R.id.textEventDescription);
		
		editEventComment = (EditText) this.findViewById(R.id.editEventComment);
		
		buttonDialogForward.setEnabled(true);
		
		textEventType.setText(extras.getString(BetterHood.EXTRAS_EVENT_TEMPLATE_NAME));
		textEventDate.setText(extras.getString(BetterHood.EXTRAS_EVENT_START_DATE));
		textEventAddress.setText(extras.getString(BetterHood.EXTRAS_EVENT_LOCATION_ADDRESS));
		textEventHost.setText(extras.getString(BetterHood.EXTRAS_EVENT_HOST));
		textEventDescription.setText(extras.getString(BetterHood.EXTRAS_EVENT_MESSAGE));
		
		this.setTitle(extras.getString(BetterHood.EXTRAS_EVENT_NAME));
		
		editEventComment.setMaxLines(5);
		
		  

		View.OnClickListener buttonListener = new View.OnClickListener() {
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.buttonBack:
					setResult(RESULT_CANCELED, intent);
					finish();
					break;
				case R.id.buttonForward:
					if (v.isEnabled()) {
						setResult(RESULT_CANCELED, intent);
						finish();
					}
					break;
				case R.id.buttonAddComment:
					//posting comments
					if(editEventComment.getText() != null){
						Log.i("what'smy Query=" , editEventComment.getText().toString());
						HandleEventComment commentHandler = new HandleEventComment(ac);
						commentHandler.postComment(extras.getString(BetterHood.EXTRAS_EVENT_HOST),editEventComment.getText().toString(), BetterHood.EXTRAS_ACCOUNT_FIRST_NAME + BetterHood.EXTRAS_ACCOUNT_LAST_NAME);
						
					}
					break;
				}
			}
		};

		buttonDialogBack.setOnClickListener(buttonListener);
		buttonDialogForward.setOnClickListener(buttonListener);
		buttonAddComment.setOnClickListener(buttonListener);
	}
	}

