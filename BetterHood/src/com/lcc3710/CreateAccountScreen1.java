package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccountScreen1 extends Activity {
	private Intent intent;
	
	private Button buttonBack;
	private Button buttonForward;
    
	private EditText editName;
	private EditText editAddress;
	private EditText editZipCode;
	private	EditText editEmail;
	private EditText editCommunityCode;
	private EditText editUsername;
	private EditText editPassword;
	private EditText editPasswordConfirm;  
	
	private TextView textUsernameAvailability;
	
	private boolean bUsernameAvailable;
    
	/** Called when the activity is first created. */
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bundle extras = data.getExtras();
		
		switch (requestCode) {
    	case BetterHood.REQ_CREATE_ACCOUNT:
    		
    		if (resultCode == RESULT_OK) {
    			//intent = getIntent();
    			setResult(RESULT_OK, data);
    			finish();
    		}
    		
    		if (resultCode == RESULT_CANCELED) {
    			//stay here, the user clicked back
    		}
    		
    		break;
    	case BetterHood.REQ_CHECK_USERNAME_AVAILABILITY:
    		if (resultCode == RESULT_OK) {
    			String szWebResponse;
    			
    			if ((szWebResponse = extras.getString(BetterHood.EXTRAS_WEB_RESPONSE)) != null) {
    				Log.i(BetterHood.TAG_CREATE_ACCOUNT_SCREEN_1, "Username availability response: " + szWebResponse);
    				if (szWebResponse.equals("true")) {
    					// username is available
    					bUsernameAvailable = true;
    					textUsernameAvailability.setText("Username '" + extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME) + "' is available!");
    				} else {
    					// username is not available
    					bUsernameAvailable = false;
    					textUsernameAvailability.setText("Username '" + extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME) + "' is NOT available.");
    				}
    			} else {
    				// no web response
    			}
    			
    		}
    		if (resultCode == RESULT_CANCELED) {
    			// somethign went wrong
    		}
    	}
    }
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_1);
        
        intent = getIntent();
        
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button) findViewById(R.id.buttonForward);
        
        editName = (EditText) findViewById(R.id.editName);
        editAddress = (EditText) findViewById(R.id.editAddress);
        editZipCode = (EditText) findViewById(R.id.editZipCode);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editCommunityCode = (EditText) findViewById(R.id.editCommunityCode);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPasswordConfirm = (EditText) findViewById(R.id.editPasswordConfirm);
        
        textUsernameAvailability = (TextView) findViewById(R.id.textUsernameAvailability);
        
        if (BetterHood.DEBUG) {
        	editName.setText("George Burdell");
        	editAddress.setText("123 Hearthwood Cir");
        	editZipCode.setText("30305");
        	editEmail.setText("gpb2009@gatech.edu");
        	editCommunityCode.setText("123456");
        	editUsername.setText("gpb2009");
        	editPassword.setText("gatech");
        	editPasswordConfirm.setText("gatech");
        }
        
        bUsernameAvailable = false;
        
        editUsername.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				String tempUsername = editUsername.getText().toString();
				String tempQuery = "Name=" + tempUsername;
				Intent inCheckUsername = new Intent(v.getContext(), ConnectionResource.class);
				
				inCheckUsername.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
				inCheckUsername.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, tempUsername);
				inCheckUsername.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_CHECK_USERNAME_AVAILABILITY);
				
				startActivityForResult(inCheckUsername, BetterHood.REQ_CHECK_USERNAME_AVAILABILITY);
			}        	
        });
        
        /* buttonBack's click listener */
        buttonBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED, intent);
				finish();
			}
        });
        
        /* buttonForward's click listener */
        buttonForward.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				String tempName, tempFirstName, tempLastName, tempAddress, tempZipCode, 
					tempEmail, tempCommunityCode, tempUsername, tempPassword, tempPasswordConfirm;
				
				tempName = editName.getText().toString();
				tempAddress = editAddress.getText().toString();
				tempZipCode = editZipCode.getText().toString();
				tempEmail = editEmail.getText().toString();
				tempCommunityCode = editCommunityCode.getText().toString();
				tempUsername = editUsername.getText().toString();
				tempPassword = editPassword.getText().toString();
				tempPasswordConfirm = editPasswordConfirm.getText().toString();
				
				//check for errors on the form				
				if (tempName.length() == 0) {
					Toast.makeText(view.getContext(), "Error: " + "'Name' cannot be left blank.", BetterHood.TOAST_TIME).show();
				} else if (tempAddress.length() == 0){
					Toast.makeText(view.getContext(), "Error: " + "'Address' cannot be left blank.", BetterHood.TOAST_TIME).show();
				} else if (tempZipCode.length() == 0){
					Toast.makeText(view.getContext(), "Error: " + "'Zip Code' cannot be left blank.", BetterHood.TOAST_TIME).show();
				} else if (tempCommunityCode.length() == 0){
					Toast.makeText(view.getContext(), "Error: " + "'Community Code' cannot be left blank.", BetterHood.TOAST_TIME).show();
				} else if (tempUsername.length() == 0){
					Toast.makeText(view.getContext(), "Error: " + "'Username' cannot be left blank.", BetterHood.TOAST_TIME).show();
				} else if (tempPassword.length() == 0){
					Toast.makeText(view.getContext(), "Error: " + "'Password' cannot be left blank.", BetterHood.TOAST_TIME).show();
				} else if (tempPasswordConfirm.length() == 0){
					Toast.makeText(view.getContext(), "Error: " + "'Password Confirm' cannot be left blank.", BetterHood.TOAST_TIME).show();
				} else if (!tempPassword.equals(tempPasswordConfirm)){
					Toast.makeText(view.getContext(), "Error: " + "'Password' must be the same as 'Password Confirm'", BetterHood.TOAST_TIME).show();
				} else if (!bUsernameAvailable) {
					Toast.makeText(view.getContext(), "Error: " + "Username must be available to create an account.", BetterHood.TOAST_TIME).show();
				} else {	
					
					int nameDivider = tempName.indexOf(" ");
					tempFirstName = tempName.substring(0, nameDivider);
					tempLastName = tempName.substring(nameDivider);
					// on to the next screen...
					Intent inCreateAccount2 = new Intent(view.getContext(), CreateAccountScreen2.class);
					
					inCreateAccount2.putExtra(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME, tempFirstName);
					inCreateAccount2.putExtra(BetterHood.EXTRAS_ACCOUNT_LAST_NAME, tempLastName);
					inCreateAccount2.putExtra(BetterHood.EXTRAS_ACCOUNT_ADDRESS, tempAddress);
					inCreateAccount2.putExtra(BetterHood.EXTRAS_ACCOUNT_ZIPCODE, tempZipCode);
					inCreateAccount2.putExtra(BetterHood.EXTRAS_ACCOUNT_EMAIL, tempEmail);
					inCreateAccount2.putExtra(BetterHood.EXTRAS_ACCOUNT_COMMUNITY_CODE, tempCommunityCode);
					inCreateAccount2.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, tempUsername);
					inCreateAccount2.putExtra(BetterHood.EXTRAS_ACCOUNT_PASSWORD, tempPassword);
					
					startActivityForResult(inCreateAccount2, BetterHood.REQ_CREATE_ACCOUNT);
				}				
			}
        });
        
        OnKeyListener buttonKeyListener = new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {				
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					switch (keyCode) {										
						case KeyEvent.KEYCODE_ENTER:
							if (v.findFocus().getId() == buttonBack.getId()) {
								buttonBack.performClick();
							} 
							if (v.findFocus().getId() == buttonForward.getId()) {
								buttonForward.performClick();
							}
							return true;
					}
				}
				return false;
			}        	
        };
        
        buttonBack.setOnKeyListener(buttonKeyListener);
        buttonForward.setOnKeyListener(buttonKeyListener);
    }
}
