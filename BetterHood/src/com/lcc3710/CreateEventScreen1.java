package com.lcc3710;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CreateEventScreen1 extends Activity {
    /** Called when the activity is first created. */
	private Button buttonBack;
	private Button buttonForward;
	
	private AutoCompleteTextView editEventTemplate;
	private ListView listEventTemplate;
	
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> listAdapter;
	private Template[] templates;
	
	private Intent intent;
	

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
		
		// get templates
		TemplateFactory tf = new TemplateFactory(intent.getExtras().getString(BetterHood.EXTRAS_SESSION_ID));
		templates = tf.getTemplates(TemplateFactory.POPULATE_TEMPLATES);
		
		// make an array of template titles
		ArrayList<String> sl = new ArrayList<String>();
		for (int i = 0; i < templates.length; i++) {
			sl.add(templates[i].title);
		}
		
		/*
		 // LIST DEBUGGING NONSENSE
		if (sl.size() < 10) {
			for (int i = sl.size(); i < 10; i++) {
				sl.add("testing " + Integer.toString(i));
			}
		 }
		 */
		
		String[] availableEvents = sl.toArray(new String[0]);
		
		// set our list of templates for the dropdown edittext thing
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, availableEvents);
		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, availableEvents);
		
		editEventTemplate = (AutoCompleteTextView) findViewById(R.id.editEventTemplate);
		editEventTemplate.setAdapter(adapter);
		editEventTemplate.setThreshold(0);
		
		// set up list view
		listEventTemplate = (ListView) findViewById(R.id.listEventTemplate);
		listEventTemplate.setAdapter(listAdapter);
		
		listEventTemplate.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {
				String template = (String) adapter.getItemAtPosition(position);
				editEventTemplate.setText(template);
			}
		});
		
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