package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsScreen extends Activity {
	private Button buttonBack;
	private Button buttonForward;
	
	private ListView listIHave;
	private TextView selection;
	private EditText editListText;
	Intent iHaveIntent;
	Activity intentHolder = this;
	
	
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
        buttonForward = (Button)findViewById(R.id.buttonForward);
        listIHave = (ListView) findViewById(R.id.listIHave);
        editListText = (EditText) findViewById(R.id.editListText);
        listIHave.setChoiceMode(2);
		
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
				String query = "";
				query += "&" + BetterHood.EXTRAS_ACCOUNT_FIRST_NAME.toString();
				//Log.i("name====", extras.getString(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME.toString()));
				//Log.i("name====", BetterHood.EXTRAS_ACCOUNT_USERNAME.toString());
				for(int i = 0; i < aszIHave.length; i++){
					
					if(listIHave.isItemChecked(i) == true){
						query += "&item_name=" + aszIHave[i].toString();
						query += "&item_owner_name=" +  extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
						//Log.i("sodm", query += "&item_owner=" +  extras.getString(BetterHood.EXTRAS_EVENT_HOST));
						
					}
					
				}
				
				if(editListText.getText().toString() != null && editListText.getText().toString().length() > 0){
					query += "&item_name=" + editListText.getText().toString();
					query += "&item_owner_name=" +  extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
				}
				    query += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
					Log.i("what do i gots =", query);
					iHaveIntent = new Intent(intentHolder, ConnectionResource.class);
					iHaveIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
					iHaveIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_SETTINGS_SCREEN);
					startActivityForResult(iHaveIntent, BetterHood.REQ_SETTINGS_SCREEN);
				
				setResult(RESULT_OK, intent);
				finish();
			}	        	
        });
	}
	
	
}
