package com.lcc3710;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SettingsScreen extends Activity {
	private Button buttonBack;
	private Button buttonForward;
	
	private ListView listIHave;
	private AutoCompleteTextView editListText;
	Intent iHaveIntent;
	Activity intentHolder = this;
    	
	private Intent intent;
	private Bundle extras;
	private String sessionID;
	
	private Template[] templates;
	private String[] aszIHave;
	private String[] checkedTemplates;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO response handling
	}
	
	private void populateChecked() {
		String query = "&sid=" + sessionID;
		SQLQuery q = new SQLQuery(BetterHood.PHP_FILE_GET_HAVE, query);
		String result = q.submit();
		checkedTemplates = result.split("\\|");
		
		ListAdapter adapter = listIHave.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			String val = (String) adapter.getItem(i);
			for (int j = 0; j < checkedTemplates.length; j++) {
				if (val.equals(checkedTemplates[j])) {
					listIHave.setItemChecked(i, true);
				}
			}
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button)findViewById(R.id.buttonForward);
        
        listIHave = (ListView) findViewById(R.id.listIHave);
        listIHave.setChoiceMode(2);
        
        editListText = (AutoCompleteTextView) findViewById(R.id.editListText);        
		
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
        
        // get templates
		TemplateFactory tf = new TemplateFactory(intent.getExtras().getString(BetterHood.EXTRAS_SESSION_ID));
		templates = tf.getTemplates(TemplateFactory.POPULATE_TEMPLATES);
		
		// make an array of template titles
		ArrayList<String> sl = new ArrayList<String>();
		for (int i = 0; i < templates.length; i++) {
			sl.add(templates[i].title);
		}
		
		aszIHave = sl.toArray(new String[0]);
        
        editListText.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, aszIHave));
        listIHave.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, aszIHave));                
        
        // get checked
        populateChecked();
        
        buttonBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED, intent);
				finish();
			}        	
        });
        
        buttonForward.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				// commit changes
				String query;
				
				for (int i = 0; i < aszIHave.length; i++){	
					boolean checked = listIHave.isItemChecked(i);
					String title = aszIHave[i].toString();
					SQLQuery q;
					query = "";
					query += "&title=" + title;
					query += "&sid=" + sessionID;
					boolean found = false;
					for (int j = 0; j < checkedTemplates.length; j++) {
						if (title.equals(checkedTemplates[j])) {
							found = true;
							break;
						}
					}
					if (checked && !found){	
						q = new SQLQuery(BetterHood.PHP_FILE_ADD_HAVE, query);
						q.submit();
					} else if (!checked && found) {
						q = new SQLQuery(BetterHood.PHP_FILE_REMOVE_HAVE, query);
						q.submit();
					}
				}
				
				if (editListText.getText().toString() != null && editListText.getText().toString().length() > 0){
				}
				
				setResult(RESULT_OK, intent);
				finish();
			}	        	
        });
	}
}
