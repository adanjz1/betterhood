package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends Activity {
	
	private Button buttonLogIn;
	private Button buttonCreateAccount;
	
	private EditText editUsername;
	private EditText editPassword;
	
	public static final int REQ_LOGIN = 0;
	public static final int REQ_CREATE_ACCOUNT = 1;
	public static final int REQ_HOME_SCREEN = 2;
	
    /** Called when the activity is first created. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) { 
    	case REQ_LOGIN:
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
    	case REQ_CREATE_ACCOUNT:
    		switch (resultCode) {
    		case RESULT_OK:
    			//user created account, log him in
    			startHomeScreen();
    			break;
    		case RESULT_CANCELED:
    			// user clicked back
    			clearForm();
    			break;
    		}
    		break;
    	case REQ_HOME_SCREEN:
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
        
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				String tempUsername = editUsername.getText().toString();
				String tempPassword = editPassword.getText().toString();
				
				if ((tempUsername != null) && (tempPassword != null)) {
				
					Intent inLogIn = new Intent(view.getContext(), LoginProcess.class);
					inLogIn.putExtra("username", tempUsername);
					inLogIn.putExtra("password", tempPassword);
					
					startActivityForResult(inLogIn, REQ_LOGIN);
				}
			}
        });
        
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent ca1 = new Intent(view.getContext(), CreateAccountScreen1.class);
				startActivityForResult(ca1, REQ_CREATE_ACCOUNT);
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
    	startActivityForResult(home, REQ_HOME_SCREEN);
    }
}