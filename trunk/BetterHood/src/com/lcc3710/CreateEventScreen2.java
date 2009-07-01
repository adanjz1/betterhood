package com.lcc3710;



import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class CreateEventScreen2 extends Activity {
    /** Called when the activity is first created. */
	Button buttonBack;
	Button buttonForward;
	
	Intent inCreateEvent2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_2);
        
        inCreateEvent2 = getIntent();
        
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonForward = (Button) findViewById(R.id.buttonForward);
		
		buttonBack.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				setResult(RESULT_CANCELED, inCreateEvent2);
				finish();
			}
		});
		
		buttonForward.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// TODO check for errors, then add the event to database etc
				setResult(RESULT_OK, inCreateEvent2);
				finish();
			}
		});

       // TabHost mTabHost = getTabHost();
        
        //mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("TAB 1").setContent(R.id.Want));
        //mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("TAB 2").setContent(R.id.Map));
        //mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("TAB 3").setContent(R.id.Settings));
        
       // mTabHost.setCurrentTab(0);
    }

}