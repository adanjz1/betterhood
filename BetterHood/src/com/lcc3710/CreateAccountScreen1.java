package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccountScreen1 extends Activity {
	private Intent ca1;
	
	Button buttonBack;
    Button buttonForward;
    
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
    }
}
