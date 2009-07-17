package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SettingsScreen extends Activity {
	private Button buttonBack;
	private Button buttonForward;
	
	private ListView listIHave;
	
	private Intent intent;
	private Bundle extras;
	private String sessionID;
	
	private static final String[] aszIHave = new String[] {
    	"a pool", "a car", "a lawnmower", "a pickup truck", "kids", "an electric generator",
    	"a medical degree"
    };
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO response handling
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button) findViewById(R.id.buttonForward);
        listIHave = (ListView) findViewById(R.id.listIHave);
		
        intent = getIntent();
        extras = intent.getExtras();
        
        if (extras != null) {
        	// get sessionID
        	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);        	
        } else {
        	intent.putExtra(BetterHood.EXTRAS_ERROR_MESSAGE, "Session ID not found");
        	setResult(RESULT_CANCELED, intent);
        	finish();
        }
        
        listIHave.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, aszIHave));
        
        
        
        buttonBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED, intent);
				finish();
			}        	
        });
        
        buttonForward.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				// commit changes
				setResult(RESULT_OK, intent);
				finish();
			}	        	
        });
	}
}
