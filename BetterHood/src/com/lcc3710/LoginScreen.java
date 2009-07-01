package com.lcc3710;

import android.app.Activity;
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
    			// login succeeded, on to the main screen
    			startHomeScreen();
    			break;
    		case RESULT_CANCELED:
    			// login failed, try again
    			clearPasswordField();
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
        	editUsername.setText("admin");
        	editPassword.setText("password");
        }
        
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String tempUsername = editUsername.getText().toString();
				String tempPassword = editPassword.getText().toString();
				
				if ((tempUsername.length() > 0) && (tempPassword.length() > 0)) {
				
					Intent inLogIn = new Intent(view.getContext(), LoginProcess.class);
					inLogIn.putExtra("username", tempUsername);
					inLogIn.putExtra("password", tempPassword);
					
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