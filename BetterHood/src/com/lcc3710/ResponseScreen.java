package com.lcc3710;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost.TabSpec;
public class ResponseScreen extends Activity{

	     private ListView ls1;
	     private ListView ls2;   
	     private TabHost myTabHost;
	     private Activity a = this;
	     private Intent intent;
	 	private Bundle extras;
	 	private Button back;
	 	private Button backSent;
	 	 private int lastRequestCode;
	 	String[] itemsName;
	 	String[] itemsNameOut;
	 	ListView listSent;
	 	
		
		String delims = "\\^";
		String[] partyTokens;
		String[] partyTokensOut;
		String[] items;
		String[] itemsOut;
		 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				Bundle extras;
				lastRequestCode = requestCode;
				
				
				
		    	switch (requestCode) {
		    	case BetterHood.REQ_OUT_RESPONSE:
		    		final String response = data.getExtras().getString(BetterHood.EXTRAS_WEB_RESPONSE);
		    		Log.i("WTF IS HAPPENING", response);
		    		 partyTokensOut =  response.split(delims);
		    		// intent = getIntent();
			         // extras = intent.getExtras();
		    		
		    		if (resultCode == RESULT_OK) {
		    			// event was created, success!
		    			Log.i("WTF IS HAPPENING", response);
		    			//String tempUsername = extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
		    			int num = partyTokensOut.length;
		    			String[] name = partyTokensOut[0].split("\\|");
		    			itemsOut = new String[num/2];
		    	  		itemsNameOut = new String[num/2];
		    	  		int itemsCountOut = 0;
		    	  		int itemsNameCountOut = 0;
		    	  		
		    			for (int i = 0; i < partyTokensOut.length; i++) {
		    	  			
		    	  			// EVENT_NAME
		    	  			
		    	  			if(partyTokensOut[i].startsWith("|")){
		    	  				
		    	  				name = partyTokensOut[i].split("\\|");
		    	  				
		    	  					//Log.i(TAG, "name = " + name[1]);
		    	  					itemsOut[itemsCountOut] = name[1];
		    	  					itemsCountOut++;
		    	  					//items[i+1] = "no";
		    	  					
		    	  				
		    	  				
		    	  			}
		    	  			if(partyTokensOut[i].startsWith(">")){
		    	  				
		    	  				name = partyTokensOut[i].split("\\>");
		    	  				
		    	  					//Log.i(TAG, "name = " + name[1]);
		    	  					itemsNameOut[itemsNameCountOut] = name[1];
		    	  					itemsNameCountOut++;
		    	  					//items[i+1] = "no";
		    	  					
		    	  				
		    	  				
		    	  			}
		    	  		}
		    			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(a,android.R.layout.simple_list_item_multiple_choice,itemsNameOut);
		                listSent.setAdapter(adapter2); 
		                listSent.setChoiceMode(1);

		    		}
		    		
		    		if (resultCode == RESULT_CANCELED) {
		    			//something went wrong in creating the event
		    		}
		    		break;
		    	}
		 }
	     @Override
	     public void onCreate(Bundle icicle)
	     {
	          super.onCreate(icicle);
	          setContentView(R.layout.response_tab);
	          intent = getIntent();
	          extras = intent.getExtras();
	          Log.i("gettin dat info=", extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR));
	          
	          partyTokens = extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR).split(delims);
	  		  Log.i("poart=", partyTokens.toString());
	  		  int num = partyTokens.length;
	  	
	  		 items = new String[num/2];
	  		 itemsName = new String[num/2];
	  		 int itemsNameCount = 0;
	  		
	  		
	  		String[] name = partyTokens[0].split("\\|");
	  		int itemsCount = 0;
	  		
	  		listSent = (ListView) findViewById(R.id.listSent);
	  		Event newEvent = new Event();
	  		
	  		for (int i = 0; i < partyTokens.length; i++) {
	  			
	  			// EVENT_NAME
	  			
	  			if(partyTokens[i].startsWith("|")){
	  				
	  				name = partyTokens[i].split("\\|");
	  				
	  					//Log.i(TAG, "name = " + name[1]);
	  					items[itemsCount] = name[1];
	  					itemsCount++;
	  					//items[i+1] = "no";
	  					
	  				
	  				
	  			}
	  			if(partyTokens[i].startsWith(">")){
	  				
	  				name = partyTokens[i].split("\\>");
	  				
	  					//Log.i(TAG, "name = " + name[1]);
	  					itemsName[itemsNameCount] = name[1];
	  					itemsNameCount++;
	  					//items[i+1] = "no";
	  					
	  				
	  				
	  			}
	  		}
	  		
	          
	         
	          
	          
	          this.myTabHost = (TabHost)this.findViewById(R.id.th_set_menu_tabhost);
	          this.myTabHost.setup();   
	          
	          
	          
	         
	        back = (Button) findViewById(R.id.canButt);
	        backSent = (Button) findViewById(R.id.canButtSent);
	        ListView list = (ListView) findViewById(R.id.list);
	        listSent = (ListView) findViewById(R.id.listSent);
	        list.setChoiceMode(1);
	        
	        
	         ArrayAdapter<String> adapter = new ArrayAdapter<String>(a,android.R.layout.simple_list_item_multiple_choice,itemsName);
             list.setAdapter(adapter);
             
            
	         
	         
	          
	          final TabSpec ts = myTabHost.newTabSpec("TAB_TAG_1");
	          
	          ts.setIndicator("Inbox");                
	                    
	          
	          ts.setContent(new TabHost.TabContentFactory(){
	               public View createTabContent(String tag)
	               {                           
	            	   	
	            	   
	                    return findViewById(R.id.linlayoutBase);
	                    
	               }          
	                    
	               });  
	          
	          back.setOnClickListener(new OnClickListener() {
 	      			public void onClick(View v) {
 	      				Intent home = new Intent(a, HomeScreen.class);
 				    	home.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
 				    	home.putExtra(BetterHood.EXTRAS_SESSION_ID, extras.getString(BetterHood.EXTRAS_SESSION_ID));
 				    	startActivityForResult(home, BetterHood.REQ_HOME_SCREEN);
 	      			}        	
 	              });
	          backSent.setOnClickListener(new OnClickListener() {
	      			public void onClick(View v) {
	      				Intent home = new Intent(a, HomeScreen.class);
				    	home.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
				    	home.putExtra(BetterHood.EXTRAS_SESSION_ID, extras.getString(BetterHood.EXTRAS_SESSION_ID));
				    	startActivityForResult(home, BetterHood.REQ_HOME_SCREEN);
	      			}        	
	              });
	         
	          
	          
	          myTabHost.addTab(ts);
	                    
	          TabSpec ts1 = myTabHost.newTabSpec("TAB_TAG_2");
	          
	          ts1.setContent(new TabHost.TabContentFactory(){
	               public View createTabContent(String tag)
	               {            
	            	   
	            	    Intent inSettings = new Intent(a, ConnectionResource.class);
						
						String tempQuery = "";
						tempQuery += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
						//inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
						inSettings.putExtra(BetterHood.EXTRAS_QUERY, tempQuery);
						inSettings.putExtra(BetterHood.EXTRAS_ACCOUNT_USERNAME, extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME));
		    			inSettings.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_OUT_RESPONSE);
						startActivityForResult(inSettings, BetterHood.REQ_OUT_RESPONSE);	
	            	    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(a,android.R.layout.simple_list_item_multiple_choice,new String[]{"item4","item5","item6"});
	                    listSent.setAdapter(adapter2); 
	                    return findViewById(R.id.linlayoutBase2);
	                    
	               }          
	                    
	               });  
	          
	          
	          
	          ts1.setIndicator("Outbox");
	                    
	          myTabHost.addTab(ts1);
	         
	          
              
             
	        
	          
	     }
	     
	     
	     }


	     