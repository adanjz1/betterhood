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
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class SettingsScreen extends Activity {
	private Button buttonBack;
	private Button buttonForward;
	private Button buttonBack2;
	private Button buttonForward2;
	
	private ListView listIHave;
	private TextView selection;
	private ListView listIHave2;
	private TextView selection2;
	private EditText editListText;
	private EditText editListText2;
	Intent iHaveIntent;
	Activity intentHolder = this;
	private TabHost myTabHost;
    
	
	
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
        
        buttonBack2 = (Button) findViewById(R.id.buttonBack2);
        buttonForward2 = (Button)findViewById(R.id.buttonForward2);
        listIHave2 = (ListView) findViewById(R.id.listIHave2);
        editListText2 = (EditText) findViewById(R.id.editListText2);
        listIHave2.setChoiceMode(2);
		
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
        
        this.myTabHost = (TabHost)this.findViewById(R.id.th_set_menu_tabhost);
        this.myTabHost.setup();   
        
        
        final TabSpec ts = myTabHost.newTabSpec("TAB_TAG_1");
       // myTabHost.setCurrentTab(index)
        
        ts.setIndicator("Items");                
        
        ts.setContent(new TabHost.TabContentFactory(){
            public View createTabContent(String tag)
            {                           
         	   	
         	   
                 return findViewById(R.id.linlayoutBase);
                 
            }          
                 
            });  
        
        myTabHost.addTab(ts);
        
        TabSpec ts1 = myTabHost.newTabSpec("TAB_TAG_2");
        
       
        ts1.setContent(new TabHost.TabContentFactory(){
             public View createTabContent(String tag)
             {            
          	   
          	   /* Intent inSettings = new Intent(a, ConnectionResource.class);
					
					String tempQuery = "";
					tempQuery += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
					//inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
					inSettings.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
					inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
	    			inSettings.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_OUT_RESPONSE);
					startActivityForResult(inSettings, BetterHood.REQ_OUT_RESPONSE);	
          	    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(a,android.R.layout.simple_list_item_multiple_choice,new String[]{"item4","item5","item6"});
                  //listSent.setAdapter(adapter2); */
                  return findViewById(R.id.linlayoutBase2);
                  
             }          
                  
             });  
        
        
        
        ts1.setIndicator("Interest");
                  
        myTabHost.addTab(ts1);
        
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
        buttonBack2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED, intent);
				finish();
			}        	
        });
        
        buttonForward2.setOnClickListener(new OnClickListener() {
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
				
				if(editListText2.getText().toString() != null && editListText2.getText().toString().length() > 0){
					query += "&item_name=" + editListText2.getText().toString();
					query += "&item_owner_name=" +  extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
				}
				    query += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
					Log.i("what do i gots =", query);
					iHaveIntent = new Intent(intentHolder, ConnectionResource.class);
					iHaveIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
					iHaveIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_SETTINGS_SCREEN_INTEREST);
					startActivityForResult(iHaveIntent, BetterHood.REQ_SETTINGS_SCREEN_INTEREST);
				
				setResult(RESULT_OK, intent);
				finish();
			}	        	
        });
      // this.myTabHost.setOnTabChangedListener((OnTabChangeListener) this);       
       this.myTabHost.setCurrentTab(1);
	}
	
	
}
