package com.lcc3710;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class EventListScreen extends Activity {
	private Intent intent;
	private Bundle extras;
	private String sessionID;
	String delims = "\\^";
	String[] partyTokens;
	String eList;
    private OnCheckedChangeListener BasicCheckListener;
	final Activity eActivity = this;
	
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//todo
    }
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       
    	setContentView(R.layout.event_list_screen);
    	
    	
    	this.setTitle("Current Events");
    	
    	intent = getIntent();
        extras = intent.getExtras();
    }
	
	public void makeList(){
		
		Button buttonForward;
    	buttonForward = (Button) this.findViewById(R.id.buttonForward);
    	Button buttonCancel;
		buttonCancel = (Button) this.findViewById(R.id.buttonCancel);
    	
    	final ListView listEventView;
    	
        listEventView = (ListView) this.findViewById(R.id.listEvents);
        listEventView.setChoiceMode(1);
    	
    	String[] aszCurrentEvents;
    	ArrayAdapter<String> adapter;
    	
        listEventView.getCheckedItemPosition();
        buttonForward.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				for (int i = 0; i < listEventView.getCount(); i++) {
					if (listEventView.isItemChecked(i)) {
					}
				}
			}
		});
        
        
        buttonCancel.setOnClickListener(new OnClickListener() {
        	
			public void onClick(View view) {
				setResult(RESULT_CANCELED, intent);
	        	finish();
			}
        });
        
        
        

        
	}
	}


