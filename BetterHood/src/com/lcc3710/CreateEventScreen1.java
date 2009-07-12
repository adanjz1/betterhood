package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class CreateEventScreen1 extends Activity {
    /** Called when the activity is first created. */
	private Button buttonBack;
	private Button buttonForward;
	
	private AutoCompleteTextView editEventTemplate;
	
	private ArrayAdapter<String> adapter;
	
	private Intent intent;
	
	static final String[] aszAvailableEvents = {
    	"Carpool", "Kids Play-date", "Lawnmowing", "Missing child", "Pool Party", "Potluck"
    };
	

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
    	case BetterHood.REQ_CREATE_EVENT:
    		
    		if (resultCode == RESULT_OK) {
    			// event was created, success!
    			setResult(RESULT_OK, data);
    			finish();
    		}
    		
    		if (resultCode == RESULT_CANCELED) {
    			//stay here, the user clicked back
    		}
    		
    		break;
    	}
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_1);
        
        intent = getIntent();
        
		buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonForward = (Button) findViewById(R.id.buttonForward);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, aszAvailableEvents);
		
		editEventTemplate = (AutoCompleteTextView) findViewById(R.id.editEventTemplate);
		editEventTemplate.setAdapter(adapter);
		editEventTemplate.setThreshold(0);
		
		if (!editEventTemplate.isPopupShowing()) {
			editEventTemplate.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_P));
			//editEventTemplate.showDropDown();
		}
		
		buttonBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
		
		buttonForward.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				String tempEventTemplate = editEventTemplate.getText().toString();
				boolean userInputIsValid = false;
				int idx = 0;
				int count = adapter.getCount();
				
				// search thru templates and see if input is one of them
				while ((!userInputIsValid) && (idx < count)) {
					if (tempEventTemplate.equalsIgnoreCase(adapter.getItem(idx))) {
						tempEventTemplate = adapter.getItem(idx);
						userInputIsValid = true;
					}
					idx++;
				}
				
				if (userInputIsValid) {					
					intent.setClass(view.getContext(), CreateEventScreen2.class);
					intent.putExtra(BetterHood.EXTRAS_EVENT_TEMPLATE_NAME, tempEventTemplate);
					startActivityForResult(intent, BetterHood.REQ_CREATE_EVENT);
				} else {
					// fail
					Toast.makeText(view.getContext(), "The event template you entered is not in your community's database! Please try again.", BetterHood.TOAST_TIME).show();
				}
			}
		});
    }

}