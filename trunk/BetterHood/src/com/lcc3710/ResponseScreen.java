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
	 	String[] itemsName;
		
		String delims = "\\^";
		String[] partyTokens;
		String[] items;
	     
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
	        ListView list = (ListView) findViewById(R.id.list);
	        final ListView listSent = (ListView) findViewById(R.id.listSent);
	        
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
 	      				setResult(RESULT_CANCELED, intent);
 	      				finish();
 	      			}        	
 	              });
	         
	          
	          
	          myTabHost.addTab(ts);
	                    
	          TabSpec ts1 = myTabHost.newTabSpec("TAB_TAG_2");
	          
	          ts1.setContent(new TabHost.TabContentFactory(){
	               public View createTabContent(String tag)
	               {                           
	            	   	
	            	    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(a,android.R.layout.simple_list_item_multiple_choice,new String[]{"item4","item5","item6"});
	                    listSent.setAdapter(adapter2); 
	                    return findViewById(R.id.linlayoutBase2);
	                    
	               }          
	                    
	               });  
	          
	          
	          
	          ts1.setIndicator("Outbox");
	                    
	          myTabHost.addTab(ts1);
	         
	          
              
             
	        
	          
	     }
	     
	     
	     }


	     