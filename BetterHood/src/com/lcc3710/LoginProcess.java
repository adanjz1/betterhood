package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoginProcess extends Activity {
	
	private Intent inLoginProcess;
	private Bundle extras;
	
	private String szUsername;
	private String szPassword;
	
	private String szSessionId;
	
	private String szErrorMessage;
	
	boolean loginSuccess;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        inLoginProcess = getIntent();
        extras = inLoginProcess.getExtras();
        
        if (extras != null) {
        	szUsername = extras.getString("username");
        	szPassword = extras.getString("password");
        	
        	// TODO database interaction
        	
        	
        	if (loginSuccess) {
        		// TODO get a session id for this user
        		//szSessionId = ...
        		
        		setResult(RESULT_OK, inLoginProcess);
        		finish();
        	} else {
        		szErrorMessage = "Incorrect password";
        		inLoginProcess.putExtra(BetterHood.EXTRAS_ERROR_MESSAGE, "Incorrect password for " + szUsername);
        		
        		setResult(RESULT_CANCELED, inLoginProcess);
        		finish();
        	}
        } else {
        	// we don't have any extras
        	szErrorMessage = "LoginProcess.class recieved a null extras package";
        	inLoginProcess.putExtra(BetterHood.EXTRAS_ERROR_MESSAGE, szErrorMessage);
        	
        	setResult(RESULT_CANCELED, inLoginProcess);
        	finish();
        }

	} 
}
