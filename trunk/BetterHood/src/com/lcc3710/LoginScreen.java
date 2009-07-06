package com.lcc3710;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends Activity {
	
	private Button buttonLogIn;
	private Button buttonCreateAccount;
	
	private EditText editUsername;
	private EditText editPassword;
	
    /** Called when the activity is first created. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) { 
    	case BetterHood.REQ_LOGIN:
    		switch (resultCode) {
    		case RESULT_OK:
    			// see if we got a response
    			String response;
    			if ((response = data.getExtras().getString(BetterHood.EXTRAS_WEB_RESPONSE)) != null) {
    				if (response.equals("true")) {
    					Toast.makeText(this, "Logged in!", BetterHood.TOAST_TIME);
    	    			startHomeScreen();
    				} else {
    					Toast.makeText(this, "Incorrect username/password combination. Try again!", BetterHood.TOAST_TIME).show();
    				}
    			} else {
    				Toast.makeText(this, BetterHood.ERROR_PREFIX + "No server response", BetterHood.TOAST_TIME);
    			}
    			
    			
    			break;
    		case RESULT_CANCELED:
    			// login failed, lets find out why
    			Bundle extras = data.getExtras();
    			String szErrorMessage;
    			
    			if ((szErrorMessage = extras.getString(BetterHood.EXTRAS_ERROR_MESSAGE)) != null) {
    				//we have an error message
    				Toast.makeText(this, BetterHood.ERROR_PREFIX + szErrorMessage, BetterHood.TOAST_TIME).show();
    			}
    			
    			break;
    		}
    		break;
    	case BetterHood.REQ_CREATE_ACCOUNT:
    		switch (resultCode) {
    		case RESULT_OK:
    			//user created account, log him in
    			startHomeScreen();
    			break;
    		case RESULT_CANCELED:
    			// user clicked back
    			//clearForm();
    			break;
    		}
    		break;
    	case BetterHood.REQ_HOME_SCREEN:
    		switch (resultCode) {
    		case RESULT_OK:
    			//user logged out
    			break;
    		case RESULT_CANCELED:
    			//user quit
    			finish();
    			break;
    		}
    	}
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        
        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        
        //auto put some stuff into the fields, for debug
        if (BetterHood.DEBUG) {
        	editUsername.setText("Cat");
        	editPassword.setText("Cat");
        }
        
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String tempUsername = editUsername.getText().toString();
				String tempPassword = editPassword.getText().toString();				
				String tempQuery = "Name=" + tempUsername + "&Password=" + tempPassword;
				
				if ((tempUsername.length() > 0) && (tempPassword.length() > 0)) {
				
					Intent inLogIn = new Intent(view.getContext(), ConnectionResource.class);
					inLogIn.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
					inLogIn.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_LOGIN);
					
					startActivityForResult(inLogIn, BetterHood.REQ_LOGIN);
				} else {
					Toast.makeText(view.getContext(), "One or more text fields are blank!", 5).show();
				}
			}
        });
        
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent inCreateAccount1 = new Intent(view.getContext(), CreateAccountScreen1.class);
				startActivityForResult(inCreateAccount1, BetterHood.REQ_CREATE_ACCOUNT);
			}
        });
    }
    
    private void clearForm() {
    	clearUsernameField();
    	clearPasswordField();
    }
    
    private void clearUsernameField() {
    	editUsername.setText("");
    }
    
    private void clearPasswordField() {
    	editPassword.setText("");
    }
    
    private void startHomeScreen() {
    	Intent home = new Intent(getBaseContext(), HomeScreen.class);
    	startActivityForResult(home, BetterHood.REQ_HOME_SCREEN);
    }
}