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

public class CreateAccountScreen1 extends Activity {
	private Intent ca1;
	
	private Button buttonBack;
	private Button buttonForward;
    
	private EditText editName;
	private EditText editAddress;
	private EditText editCommunityCode;
	private EditText editUsername;
	private EditText editPassword;
	private EditText editPasswordConfirm;
    
	private TextView textUsernameAvailability;
   
	private static int[] illegalCharacters = { KeyEvent.KEYCODE_SLASH, KeyEvent.KEYCODE_SPACE, KeyEvent.KEYCODE_APOSTROPHE, KeyEvent.KEYCODE_STAR };
    
    
	/** Called when the activity is first created. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
    	case LoginScreen.REQ_CREATE_ACCOUNT:
    		
    		if (resultCode == RESULT_OK) {
    			setResult(RESULT_OK, ca1);
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
        
        ca1 = getIntent();
        
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button) findViewById(R.id.buttonForward);
        
        editName = (EditText) findViewById(R.id.editName);
        editAddress = (EditText) findViewById(R.id.editAddress);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPasswordConfirm = (EditText) findViewById(R.id.editPasswordConfirm);
        
        textUsernameAvailability = (TextView) findViewById(R.id.textUsernameAvailability);
        
        /* buttonBack's click listener */
        buttonBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED, ca1);
				finish();
			}
        });
        
        /* buttonForward's click listener */
        buttonForward.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent ca2 = new Intent(view.getContext(), CreateAccountScreen2.class);
				startActivityForResult(ca2, LoginScreen.REQ_CREATE_ACCOUNT);
			}
        });
        
        editUsername.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				boolean result = false;
				
				for (int i = 0; i < illegalCharacters.length; i++) {
					if (keyCode == illegalCharacters[i]) {
						result = true;
						break;
					}
				}
				
				String tempUsername = editUsername.getText().toString().trim();
				
				if (tempUsername.length() > 6) {
					textUsernameAvailability.setText("Username looks good so far...");
				} else {
					textUsernameAvailability.setText("Username must be at least 6 characters");
				}
				return result;
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
