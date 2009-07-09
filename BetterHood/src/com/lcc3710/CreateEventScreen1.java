package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class CreateEventScreen1 extends Activity {
    /** Called when the activity is first created. */
	private Button buttonBack;
	private Button buttonForward;
	
	private AutoCompleteTextView editEventTemplate;
	
	private Intent intent;
	
	static final String[] aszAvailableEvents = {
    	"Carpool", "Kids Play-date", "Lawnmowing", "Missing child", "Pool Party", "Potluck"
    };
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
    	case BetterHood.REQ_CREATE_EVENT:
    		
    		if (resultCode == RESULT_OK) {
    			// event was created, success!
    			setResult(RESULT_OK, intent);
    			finish();
    		}
    		
    		if (resultCode == RESULT_CANCELED) {
    			//stay here, the user clicked back
    		}
    		
    		break;
    	}
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_1);
        
        intent = getIntent();
        
		buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonForward = (Button) findViewById(R.id.buttonForward);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, aszAvailableEvents);
		
		editEventTemplate = (AutoCompleteTextView) findViewById(R.id.editEventTemplate);
		editEventTemplate.setAdapter(adapter);
		editEventTemplate.setText("p");
		editEventTemplate.setThreshold(0);
		
		if (!editEventTemplate.isPopupShowing()) {
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
				// TODO check for errors and pack up extras
				String tempEventTemplate = editEventTemplate.getText().toString();
				//if (adapter.)
				Intent myIntent = new Intent(view.getContext(), CreateEventScreen2.class);
				startActivityForResult(myIntent, BetterHood.REQ_CREATE_EVENT);
			}
		});
    }

}