package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class LoginProcess extends Activity {
	
	private Intent in;
	private Bundle extras;
	
	private String szUsername;
	private String szPassword;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        in = getIntent();
        extras = in.getExtras();
        
        if (extras != null) {
        	szUsername = extras.getString("username");
        	szPassword = extras.getString("password");
        	
        	// do login
        	setResult(RESULT_OK, in);
        	finish();
        } else {
        	// something went wrong
        	setResult(RESULT_CANCELED, in);
        	finish();
        }

	}
}
