package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CreateAccountScreen2 extends Activity {
	
	private Intent inCreateAccount2;
	
	private Button buttonBack;
	private Button buttonForward;
	
	private Bundle previousForm;
	
    /** Called when the activity is first created. */	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_2);
        
        inCreateAccount2 = getIntent();
        previousForm = inCreateAccount2.getExtras();
        
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button) findViewById(R.id.buttonForward);
        
        ListView listIHave = (ListView) findViewById(R.id.listIHave);
        listIHave.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, aszIHave));
        
        listIHave.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO figure out why the checkboxes dont change when you click them
				Log.i(BetterHood.TAG_CREATE_ACCOUNT_SCREEN_2, "item " + Integer.toString(position) + " was clicked");
			}        	
        });
        
        buttonBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED, inCreateAccount2);
				finish();
			}        	
        });
        
        buttonForward.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO process checked list
				// TODO check for errors
				
				// TODO create account in database
				// but lets pretend we already succeeded
				setResult(RESULT_OK, inCreateAccount2);
				finish();
			}        	
        });
    }
    
    static final String[] aszIHave = new String[] {
    	"a pool", "a car", "a lawnmower", "a pickup truck", "kids", "an electric generator",
    	"a medical degree"
    };
}