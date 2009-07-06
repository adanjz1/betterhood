package com.lcc3710;






import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

public class CreateEventScreen1 extends Activity {
    /** Called when the activity is first created. */
	private Button buttonBack;
	private Button buttonForward;
	
	private Intent intent;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
    	case BetterHood.REQ_CREATE_EVENT:
    		
    		if (resultCode == RESULT_OK) {
    			// event was created, success!
    			setResult(RESULT_OK, intent);
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
        setContentView(R.layout.create_event_1);
        
        intent = getIntent();
        
		buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonForward = (Button) findViewById(R.id.buttonForward);
		
		buttonBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
		
		buttonForward.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// TODO check for errors and pack up extras
				Intent myIntent = new Intent(view.getContext(), CreateEventScreen2.class);
				startActivityForResult(myIntent, BetterHood.REQ_CREATE_EVENT);
			}
		});
    }

}