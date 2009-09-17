package com.lcc3710;

import java.util.ArrayList;

import com.lcc3710.Event.AttributeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SimilarScreenActivity extends Activity{
	
	private Button buttonCancel;
	private ListView lv;
	private Intent intent;
	private Bundle extras;
	private Button buttonRespond;
	private String[] listAdapter;
	ArrayAdapter<String> aAdapter;
	private String sessionID;
	String[] itemsName;
	String[] itemsName2;
	private Activity a = this;
	String delims = "\\^";
	ArrayList<String[]> partyTokens = new ArrayList(3);
	String[] items, items2;
	
	
	private static final String[] aszIHave = new String[] {
    	"John", "Taylor", "Freddy Kruger", "Alex", "Tiffany", "Scott",
    	"Garden Club"
    };
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO response handling
		
    			
    		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.similar_screen);
        intent = getIntent();
        extras = intent.getExtras();
        
       
       
       
        buttonCancel = (Button) findViewById(R.id.buttonReturn);
		buttonRespond = (Button) findViewById(R.id.buttonRespond);
		lv = (ListView) findViewById(R.id.similarView);
		lv.setChoiceMode(1);
		String[] simil = extras.getStringArray(BetterHood.EXTRAS_ACCOUNT_USERNAME);
		for(int i = 0; i < simil.length; i++){
			
			Log.i("sho",  simil[i].toString());
		}
		
		//System.out.println(simil[0].toString());
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,simil));
        
        
		
       

        
        

}
	
}
