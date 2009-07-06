package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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
	private	EditText editEmail;
	private EditText editCommunityCode;
	private EditText editUsername;
	private EditText editPassword;
	private EditText editPasswordConfirm;
	
	private boolean bEditUsernameHasFocus;
   
	private static int[] illegalCharacters = { KeyEvent.KEYCODE_SLASH, KeyEvent.KEYCODE_SPACE, KeyEvent.KEYCODE_APOSTROPHE, KeyEvent.KEYCODE_STAR };
    
    
	/** Called when the activity is first created. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    	}
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_1);
        
        intent = getIntent();
        
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button) findViewById(R.id.buttonForward);
        
        editName = (EditText) findViewById(R.id.editName);
        editAddress = (EditText) findViewById(R.id.editAddress);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editCommunityCode = (EditText) findViewById(R.id.editCommunityCode);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPasswordConfirm = (EditText) findViewById(R.id.editPasswordConfirm);
        
        if (BetterHood.DEBUG) {
        	editName.setText("George Burdell");
        	editAddress.setText("123 Hearthwood Cir");
        	editEmail.setText("gpb2009@gatech.edu");
        	editCommunityCode.setText("123456");
        	editUsername.setText("gpb2009");
        	editPassword.setText("gatech");
        	editPasswordConfirm.setText("gatech");
        }
        
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
				
				String tempName, tempAddress, tempEmail, tempCommunityCode, tempUsername, 
					tempPassword, tempPasswordConfirm;
				
				tempName = editName.getText().toString();
				tempAddress = editAddress.getText().toString();
				tempEmail = editEmail.getText().toString();
				tempCommunityCode = editCommunityCode.getText().toString();
				tempUsername = editUsername.getText().toString();
				tempPassword = editPassword.getText().toString();
				tempPasswordConfirm = editPasswordConfirm.getText().toString();
				
				//check for errors on the form
				// TODO more robust error checking on the form, this is pretty horrible
				
				if (tempName.length() == 0) {
					Toast.makeText(view.getContext(), "Error: " + "'Name' cannot be left blank.", BetterHood.TOAST_TIME).show();
				} else if (tempAddress.length() == 0){
					Toast.makeText(view.getContext(), "Error: " + "'Address' cannot be left blank.", BetterHood.TOAST_TIME).show();
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
				} else {	
				
					// on to the next screen...
					Intent inCreateAccount2 = new Intent(view.getContext(), CreateAccountScreen2.class);
					
					inCreateAccount2.putExtra(BetterHood.EXTRAS_ACCOUNT_NAME, tempName);
					inCreateAccount2.putExtra(BetterHood.EXTRAS_ACCOUNT_ADDRESS, tempAddress);
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
