package com.lcc3710;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CreateAccountScreen2 extends Activity {
	
	private Intent intent;
	
	private Button buttonBack;
	private Button buttonForward;
	
	private ListView listIHave;
	
	private Bundle extras;
	
    /** Called when the activity is first created. */	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK) {
    		setResult(RESULT_OK, intent);
    		finish();
    	}
    	if (resultCode == RESULT_CANCELED) {
    		// account creation failed, lets see why
			Bundle extras = data.getExtras();
			String szErrorMessage;
			
			if ((szErrorMessage = extras.getString(BetterHood.EXTRAS_ERROR_MESSAGE)) != null) {
				//we have an error message
				Toast.makeText(this, BetterHood.ERROR_PREFIX + szErrorMessage, BetterHood.TOAST_TIME).show();
			}
    	}
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_2);
        
        intent = getIntent();
        extras = intent.getExtras();
        
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button) findViewById(R.id.buttonForward);
        
        listIHave = (ListView) findViewById(R.id.listIHave);
        
        listIHave.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, aszIHave));
        
//        listIHave.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				// TODO figure out why the checkboxes dont change when you click them
//				Log.i(BetterHood.TAG_CREATE_ACCOUNT_SCREEN_2, "item " + Integer.toString(position) + " was clicked");
//				/*
//				if (view.isPressed()) {
//					view.setPressed(false);
//				} else {
//					view.setPressed(true);
//				}
//				*/
//			}        	
//        });
        
        buttonBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED, intent);
				finish();
			}        	
        });
        
        buttonForward.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				
				SparseBooleanArray checkedItemPositions;
								
				if ((checkedItemPositions = listIHave.getCheckedItemPositions()) != null) {
					
					String iHaveQuery = "";
					boolean playsWellWithOthers = false;
					
					if (checkedItemPositions.size() > 0) {
						
						for (int i = 0; i < listIHave.getCount(); i++) {
							// if there's a check, make it a 1 and if not, a 0
							// this is our bitmask for the I HAVE part of the account
							if (checkedItemPositions.get(i)) {
								iHaveQuery += "1";							
								playsWellWithOthers = true;
							} else {
								iHaveQuery += "0";
							}							
						}
						
						intent.putExtra(BetterHood.EXTRAS_ACCOUNT_IHAVE, iHaveQuery);
						
						if (playsWellWithOthers) {							
							doAccountCreation();
						} else {
							// share something, please
							confirmEmptyList();
						}
					}
				} else {
					String iHaveQuery = "";
					
					for (int i = 0; i < listIHave.getCount(); i++) {
						iHaveQuery += "0";							
					}
					intent.putExtra(BetterHood.EXTRAS_ACCOUNT_IHAVE, iHaveQuery);
					confirmEmptyList();
				}
			}        	
        });
    }
    
    private void confirmEmptyList() {
    	AlertDialog.Builder emptyListAlert = new AlertDialog.Builder(this)
        .setTitle("Create an account")
        .setIcon(R.drawable.icon)
        .setMessage("You didn't select anything on the list. Continue anyway?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
					doAccountCreation();
                }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	extras.remove(BetterHood.EXTRAS_ACCOUNT_IHAVE);
                }
        });
        
    	emptyListAlert.show();
    }
    
    private void doAccountCreation() {
    	extras = intent.getExtras();
    	
    	String tempQuery = "Name=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME) 
    		+ "&Password=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_PASSWORD) 
    		+ "&Email=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_EMAIL) 
    		+ "&CommunityCode=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_COMMUNITY_CODE) 
    		+ "&Skills=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_IHAVE)
    		+ "&FirstName=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME)
    		+ "&LastName=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_LAST_NAME)
    		+ "&StreetAddress=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_ADDRESS)
    		+ "&ZipCode=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_ZIPCODE);
	
		intent.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
		intent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_CREATE_ACCOUNT);
	
		// Launch ConnectionResource with the query and request code in the extras
		intent.setClass(this, ConnectionResource.class);
		startActivityForResult(intent, BetterHood.REQ_CREATE_ACCOUNT);
    }
    
    static final String[] aszIHave = new String[] {
    	"a pool", "a car", "a lawnmower", "a pickup truck", "kids", "an electric generator",
    	"a medical degree"
    };
}