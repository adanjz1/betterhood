package com.lcc3710;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccount1 extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_1);
        
        Button buttonBack = (Button) findViewById(R.id.buttonBack);
        Button buttonForward = (Button) findViewById(R.id.buttonForward);
        
        buttonBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			}
        });
        
        buttonForward.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			}
        });
    }
}
