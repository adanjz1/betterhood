package com.lcc3710;

import java.util.ArrayList;

import com.lcc3710.Event.AttributeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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
	private Button buttonRespond;
	private String[] listAdapter;
	ArrayAdapter<String> aAdapter;
	private String sessionID;
	String[] itemsName;
	String[] itemsName2, itemsName3;
	private Activity a = this;
	String delims = "\\^";
	ArrayList<String[]> partyTokens = new ArrayList(3);
	String[] items, items2, items3;
	
	
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
    			Log.i("gettin dat info=", extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR));
    			
    			/*Intent inSettings = new Intent(this.getBaseContext(), ConnectionResource.class);
				
				*/
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
    		case BetterHood.REQ_SIMILAR_SCREEN_INTEREST:
    		Log.i("you ok,", extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR));
    		if (resultCode == RESULT_OK) {
    			Log.i("gettin dat info=", data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE));
    			Log.i("gettin dat info=", data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE));
    			//partyTokens = 
    				//String what = data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE);
    			String[] interest = data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE).split(delims);;
    			partyTokens.add(1,interest) ;
    			Log.i("poart=", partyTokens.toString());
    			int num = partyTokens.get(1).length;
    		
    			 items2 = new String[num/2];
    			itemsName2 = new String[num/2];
    			int itemsNameCount = 0;
    			
    			
    			
    			String [] partyTokensReal = interest;
    			String[] name = interest[0].split("\\|");
    			int itemsCount = 0;
    			
    			
    			Event newEvent = new Event();
    			
    			for (int i = 0; i < interest.length; i++) {
    				
    				// EVENT_NAME
    				
    				if(interest[i].startsWith("|")){
    					
    					name = interest[i].split("\\|");
    					
    						//Log.i(TAG, "name = " + name[1]);
    						items2[itemsCount] = name[1];
    						itemsCount++;
    						//items[i+1] = "no";
    						
    					
    					
    				}
    				if(interest[i].startsWith(">")){
    					
    					name = interest[i].split("\\>");
    					
    						//Log.i(TAG, "name = " + name[1]);
    						itemsName2[itemsNameCount] = name[1];
    						itemsNameCount++;
    						//items[i+1] = "no";
    						 
    		
    						
    					
    					
    		}
    			}
    			partyTokens.set(1, itemsName2);
    		
    			String[]  adapt = new String[itemsName.length + itemsName2.length];;
    			System.out.println(adapt.length);
    			System.out.println(itemsName2.length);
    			System.out.println(itemsName.length);
    			String[] item = partyTokens.get(0);
    			String[] item2 = partyTokens.get(1);
    			
    			int count = 0;
    			int countme = 0;
    			//aAdapter.clear();
    			for(int i =0; i < (itemsName.length + itemsName2.length); i++){
    				
    				if(i < (itemsName.length)){
    					adapt[i] = itemsName[i];
    					String add = adapt[i];
    					//aAdapter.add(add);
    					//aAdapter.notifyDataSetChanged();
    					
    					
    				}
    				if(i >= (itemsName.length) && (i-i+count+1 <= itemsName2.length) ){
    					System.out.println(itemsName2[0]);
    					adapt[i] = itemsName2[i-i+count];
    					count++;
    				}
    				
    				countme++;
    				System.out.println(countme);
    				
    				
    			}
    			//lv.
    			
    			//aAdapter.remove(listAdapter);
    			//listAdapter = adapt;
    			//aAdapter.add(listAdapter);
    			String tempQuery="";
    			Intent in = new Intent(this, ConnectionResource.class);
    		//	in.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, adapt);
    			tempQuery += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
    			//in.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
    			in.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
    			
    			
				//in.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
				in.putExtra(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_FIRST_NAME));
				in.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_SIMILAR_SCREEN_EVENTS_HAVE);
				startActivityForResult(in, BetterHood.REQ_SIMILAR_SCREEN_EVENTS_HAVE);
    			//setContentView(R.layout.similar_screen);
    			//lv = (ListView) findViewById(R.id.similarView);
    			lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, adapt));
    		
    		}
    		
    			break;
    	
	    case BetterHood.REQ_SIMILAR_SCREEN_EVENTS_HAVE:
	    	Log.i("you ok,", data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE));
		if (resultCode == RESULT_OK) {
			String[] interest = data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE).split(delims);;
			partyTokens.add(2,interest) ;
			Log.i("poart=", partyTokens.toString());
			int num = partyTokens.get(2).length;
		
			 items3 = new String[num/2];
			itemsName3 = new String[num/2];
			int itemsNameCount = 0;
			
			
			
			String [] partyTokensReal = interest;
			String[] name = interest[0].split("\\|");
			int itemsCount = 0;
			
			
			Event newEvent = new Event();
			
			for (int i = 0; i < interest.length; i++) {
				
				// EVENT_NAME
				
				if(interest[i].startsWith("|")){
					
					name = interest[i].split("\\|");
					
						//Log.i(TAG, "name = " + name[1]);
						items3[itemsCount] = name[1];
						itemsCount++;
						//items[i+1] = "no";
						
					
					
				}
				if(interest[i].startsWith(">")){
					
					name = interest[i].split("\\>");
					
						//Log.i(TAG, "name = " + name[1]);
						itemsName3[itemsNameCount] = name[1];
						itemsNameCount++;
						//items[i+1] = "no";
						 
		
						
					
					
		}
			}
			partyTokens.set(2, itemsName2);
		
			String[]  adapt = new String[itemsName.length + itemsName2.length+items3.length];
			System.out.println(adapt.length);
			System.out.println(itemsName2.length);
			System.out.println(itemsName.length);
			String[] item = partyTokens.get(0);
			String[] item2 = partyTokens.get(1);
			
			int count = 0;
			int countme = 0;
			int count2 = 0;
			//aAdapter.clear();
			for(int i =0; i < (itemsName.length + itemsName2.length + items3.length); i++){
				
				if(i < (itemsName.length)){
					adapt[i] = itemsName[i];
					String add = adapt[i];
					//aAdapter.add(add);
					//aAdapter.notifyDataSetChanged();
					
					
				}
				else if(i >= (itemsName.length) && (i-i+count+1 <= itemsName2.length) ){
					//System.out.println(itemsName2[0]);
					adapt[i] = "Someone shares an interest in: " + itemsName2[i-i+count];
					count++;
				}
				else if(i >= (itemsName2.length+itemsName.length) && (i-i+count2 <= itemsName3.length) ){
					//System.out.println(itemsName2[0]);
					adapt[i] = "Someone Wants: " + itemsName3[i-i+count2];
					count2++;
				}
				else{
					//adapt[i] = " whyyyyyyyyy";
				}
				
				countme++;
				System.out.println(countme);
				
				
			}
			
			String[]  adaptName = new String[items.length + items2.length+items3.length];
			int count3=0;
			int count4=0;
			for(int i =0; i < (items.length + items2.length + items3.length); i++){
				
				if(i < (items.length)){
					adaptName[i] = items[i];
					String add = adapt[i];
					//aAdapter.add(add);
					//aAdapter.notifyDataSetChanged();
					
					
				}
				else if(i >= (items.length) && (i-i+count3+1 <= items2.length) ){
					System.out.println(items[0]);
					adaptName[i] = items2[i-i+count3];
					count3++;
				}
				else if(i >= (items2.length+items.length) && (i-i+count4 <= items3.length) ){
					//System.out.println(itemsName2[0]);
					adaptName[i] = items3[i-i+count4];
					count4++;
				}
				else{
					//adapt[i] = " whyyyyyyyyy";
				}
				
				countme++;
				System.out.println(countme);
				
				
			}
			final String[] adapter = adapt;
			final String[] adapterName = adaptName;
			buttonRespond.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					String name;
					String comment;
					for(int i =0; i< lv.getCount(); i++){
						if(lv.isItemChecked(i)){
							name = adapterName[i].toString();
							comment = adapter[i].toString();
							Log.i("nammmmme", name);
							Log.i("whattisit", comment);
							Intent home = new Intent(a, SimilarSub.class);
					    	home.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
					    	home.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
					    	home.putExtra(BetterHood.EXTRAS_EVENT_NAME, name);
					    	home.putExtra(BetterHood.EXTRAS_EVENT_HOST, comment);
					    	
					    	startActivityForResult(home, BetterHood.REQ_HOME_SCREEN);
							
							
							
						
					}
				
				}        	
	        };
		
		});
			lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, adapt));
			//Log.i("gettin dat info=", extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR));
			
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
        buttonCancel = (Button) findViewById(R.id.buttonReturn);
		buttonRespond = (Button) findViewById(R.id.buttonRespond);
		lv = (ListView) findViewById(R.id.similarView);
		lv.setChoiceMode(1);
		
       
		
       // Log.i("gettin dat info=", extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR));
      

        
        if (extras != null) {
        	// get sessionID
        	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);        	
        } else {
        	intent.putExtra(BetterHood.EXTRAS_ERROR_MESSAGE, "Session ID not found");
        	setResult(RESULT_CANCELED, intent);
        	finish();
        }
        Intent inSettings = new Intent(this.getBaseContext(), ConnectionResource.class);
        String tempQuery = "";
		tempQuery += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
		inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
		inSettings.putExtra(BetterHood.EXTRAS_SESSION_ID, extras.getString(BetterHood.EXTRAS_SESSION_ID));
		inSettings.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
		inSettings.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_SIMILAR_SCREEN_INTEREST);
		startActivityForResult(inSettings, BetterHood.REQ_SIMILAR_SCREEN_INTEREST);
        populateSimilar();
        
		// event was created, success!
        
        

}
	private void populateSimilar(){
		
		String[] pt = extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR).split(delims);
		partyTokens.add(0,pt);
		Log.i("poart=", partyTokens.toString());
		int num = partyTokens.get(0).length;
	
		 items = new String[num/2];
		itemsName = new String[num/2];
		int itemsNameCount = 0;
		
		String [] partyTokensReal = partyTokens.get(0);
		String[] name = partyTokensReal[0].split("\\|");
		int itemsCount = 0;
		
		
		Event newEvent = new Event();
		
		for (int i = 0; i < partyTokensReal.length; i++) {
			
			// EVENT_NAME
			
			if(partyTokensReal[i].startsWith("|")){
				
				name = partyTokensReal[i].split("\\|");
				
					//Log.i(TAG, "name = " + name[1]);
					items[itemsCount] = name[1];
					itemsCount++;
					//items[i+1] = "no";
					
				
				
			}
			if(partyTokensReal[i].startsWith(">")){
				
				name = partyTokensReal[i].split("\\>");
				
					//Log.i(TAG, "name = " + name[1]);
					itemsName[itemsNameCount] = name[1];
					itemsNameCount++;
					
					//items[i+1] = "no";
					
				
				
			}
		}
			
			//partyTokens.set(0, itemsName);
			
			
				
				listAdapter = itemsName;
			
				
			//aAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listAdapter);
		
		//lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, aszIHave));
	//	lv.setAdapter(aAdapter);
		buttonCancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent home = new Intent(a, HomeScreen.class);
					home.putExtra(BetterHood.EXTRAS_USER_LOGGED_IN, "yes");
			    	home.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
			    	home.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
			    	startActivityForResult(home, BetterHood.REQ_HOME_SCREEN);
				}        	
	        });
		buttonRespond.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String name;
				String comment;
				for(int i =0; i< lv.getCount(); i++){
					if(lv.isItemChecked(i)){
						name = itemsName[i].toString();
						comment = items[i].toString();
						Log.i("nammmmme", name);
						Log.i("whattisit", comment);
						Intent home = new Intent(a, SimilarSub.class);
				    	home.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
				    	home.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
				    	home.putExtra(BetterHood.EXTRAS_EVENT_NAME, name);
				    	home.putExtra(BetterHood.EXTRAS_EVENT_HOST, comment);
				    	
				    	startActivityForResult(home, BetterHood.REQ_HOME_SCREEN);
						
						
						
					
				}
			
			}        	
        };
	
	});
	}
}
