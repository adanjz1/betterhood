package com.lcc3710;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        
        Button buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        Button buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			
			}
        });
    }
}