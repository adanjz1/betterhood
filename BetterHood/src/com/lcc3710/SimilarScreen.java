package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SimilarScreen extends Activity{
	
	private Button buttonCancel;
	private ListView lv;
	private Intent intent;
	private Bundle extras;
	private String sessionID;
	
	private static final String[] aszIHave = new String[] {
    	"John", "Taylor", "Freddy Kruger", "Alex", "Tiffany", "Scott",
    	"Garden Club"
    };
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO response handling
		intent = getIntent();
        extras = intent.getExtras();
		switch (requestCode) {
    	case BetterHood.REQ_SIMILAR_SCREEN:
    		
    		if (resultCode == RESULT_OK) {
    			// event was created, success!
    			if (extras != null) {
    				String szWebResponse;
    				if ((szWebResponse = extras.getString(BetterHood.EXTRAS_WEB_RESPONSE)) != null) {
    					Log.i(BetterHood.TAG_HOME_SCREEN, "pop sim returned response: " + szWebResponse);
    				}
    		}
    		}
    		
    		if (resultCode == RESULT_CANCELED) {
    			//something went wrong in creating the event
    		}
    		break;
		}
    			
    		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.similar_screen);
        intent = getIntent();
        extras = intent.getExtras();
      

        
        if (extras != null) {
        	// get sessionID
        	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);        	
        } else {
        	intent.putExtra(BetterHood.EXTRAS_ERROR_MESSAGE, "Session ID not found");
        	setResult(RESULT_CANCELED, intent);
        	finish();
        }
        populateSimilar();
        
        

}
	private void populateSimilar(){
		buttonCancel = (Button) findViewById(R.id.buttonReturn);
		lv = (ListView) findViewById(R.id.similarView);
		lv.setChoiceMode(1);
		
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, aszIHave));
		buttonCancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					setResult(RESULT_CANCELED, intent);
					finish();
				}        	
	        });
	}
}
