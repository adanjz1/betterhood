package com.lcc3710;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    	final Bundle tempExtras;
    	final String tempUsername;
    	
    	if (data != null) {
    		tempExtras = data.getExtras();
    	} else {
    		tempExtras = new Bundle();
    	}
    	    	
    	switch (requestCode) { 
    	case BetterHood.REQ_LOGIN:
    		
    		tempUsername = tempExtras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
    		
    		AlertDialog.Builder loginAlert = new AlertDialog.Builder(this)
            .setTitle("Login")
            .setIcon(R.drawable.icon);    		
    		
    		switch (resultCode) {
    		case RESULT_OK:   		
    			    			
    			// see if we got a response
    			final String response;
    			if ((response = data.getExtras().getString(BetterHood.EXTRAS_WEB_RESPONSE)) != null) {
    				Log.i(BetterHood.TAG_LOGIN_PROCESS, "Login response: " + response);

    				if (!response.equals("false")) {
    					loginAlert.setMessage(tempUsername + " has been logged in!");
    					loginAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		                public void onClick(DialogInterface dialog, int whichButton) {
    		                	startHomeScreen(response);
    		                }
    					});
    				} else {
    					loginAlert.setMessage("Incorrect username/password combination for " + tempUsername + ". Try again!");
    					loginAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		                public void onClick(DialogInterface dialog, int whichButton) {
    		                	//clearPasswordField();
    		                }
    					});
    				}
    			} else {
    				loginAlert.setMessage(BetterHood.ERROR_PREFIX + "No server repsonse recieved.");
    				loginAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int whichButton) {
		                	//nothing
		                }
					});
    			}
    			
    			loginAlert.show();
    			
    			
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
    		
    		if ((tempUsername = tempExtras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME)) != null) {
    			AlertDialog.Builder createAccountAlert = new AlertDialog.Builder(this)
                .setTitle("Create an account")
                .setIcon(R.drawable.icon);                 
        		
        		switch (resultCode) {
        		case RESULT_OK:
        			// user created account, log him in
        			// login using the username and password from the ACCOUNT CREATION PROCESS, NOT the EditText fields
        			    			
        			createAccountAlert.setMessage("Your account has been created! Do you want to log in now?");
        			
        			editUsername.setText(tempExtras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
        			editPassword.setText(tempExtras.getString(BetterHood.EXTRAS_ACCOUNT_PASSWORD));
        			
        			createAccountAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	doLogin(tempExtras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME), tempExtras.getString(BetterHood.EXTRAS_ACCOUNT_PASSWORD));
                        }
        			});
        			
        			createAccountAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	// user doesnt want to log in yet
                        	clearPasswordField();
                        }
        			});
        			    			
        			createAccountAlert.show();
        			
        			break;
        		case RESULT_CANCELED:
        			// user clicked back
        			//clearForm();
        			String szErrorMessage;
        			
        			createAccountAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	// nothing here i guess...
                        }
        			});
        			
        			if ((szErrorMessage = data.getExtras().getString(BetterHood.EXTRAS_ERROR_MESSAGE)) != null ) {
        				createAccountAlert.setMessage("Account creation failed. " + BetterHood.ERROR_PREFIX + szErrorMessage);
        			} else {
        				createAccountAlert.setMessage("Account creation failed. " + BetterHood.ERROR_PREFIX + "Unknown error.");
        			}
        			
        			createAccountAlert.show();
        			
        			break;
        		}
    		} else {
    			// we probably exiting with the 'back' button, so no extras are saved
    			clearForm();
    		}
    		
    		break;
    	case BetterHood.REQ_HOME_SCREEN:
    		switch (resultCode) {
    		case RESULT_OK:
    			//user logged out
    			break;
    		case RESULT_CANCELED:
    			//user quit
    			clearPasswordField();
    			Toast.makeText(this, "User logged out.", BetterHood.TOAST_TIME).show();
    			//finish();
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
				doLogin(tempUsername, tempPassword);
			}
        });
        
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent inCreateAccount1 = new Intent(view.getContext(), CreateAccountScreen1.class);
				startActivityForResult(inCreateAccount1, BetterHood.REQ_CREATE_ACCOUNT);
			}
        });
    }
    
    private void doLogin(String szUsername, String szPassword) {
    					
		String tempQuery = "Name=" + szUsername + "&Password=" + szPassword;
		
		if ((szUsername.length() > 0) && (szPassword.length() > 0)) {
		
			Intent inLogIn = new Intent(this, ConnectionResource.class);
			inLogIn.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
			inLogIn.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_LOGIN);
			inLogIn.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, szUsername);
			
			// Launch ConnectionResource with the query and request code in the extras
			startActivityForResult(inLogIn, BetterHood.REQ_LOGIN);
		} else {
			Toast.makeText(this, BetterHood.ERROR_PREFIX + "One or more text fields are blank!", 5).show();
		}
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
    
    private void startHomeScreen(String sessionID) {
    	Intent home = new Intent(getBaseContext(), HomeScreen.class);
    	home.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
    	startActivityForResult(home, BetterHood.REQ_HOME_SCREEN);
    }
}