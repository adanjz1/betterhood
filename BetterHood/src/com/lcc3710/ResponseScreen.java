package com.lcc3710;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	     
	     @Override
	     public void onCreate(Bundle icicle)
	     {
	          super.onCreate(icicle);
	          setContentView(R.layout.response_tab);
	          intent = getIntent();
	          extras = intent.getExtras();
	          
	          
	          
	         
	          
	          
	          this.myTabHost = (TabHost)this.findViewById(R.id.th_set_menu_tabhost);
	          this.myTabHost.setup();   
	          
	          
	          
	         
	          back = (Button) findViewById(R.id.canButt);
	         ListView list = (ListView) findViewById(R.id.list);
	        final ListView listSent = (ListView) findViewById(R.id.listSent);
	        
	         ArrayAdapter<String> adapter = new ArrayAdapter<String>(a,android.R.layout.simple_list_item_multiple_choice,new String[]{"item1","item2","item3"});
             list.setAdapter(adapter);
             
            
	         
	         
	          
	          final TabSpec ts = myTabHost.newTabSpec("TAB_TAG_1");
	          
	          ts.setIndicator("Inbox");                
	                    
	          
	          ts.setContent(new TabHost.TabContentFactory(){
	               public View createTabContent(String tag)
	               {                           
	            	   	
	            	   back.setOnClickListener(new OnClickListener() {
	   	      			public void onClick(View v) {
	   	      				setResult(RESULT_CANCELED, intent);
	   	      				finish();
	   	      			}        	
	   	              });
	                    return findViewById(R.id.linlayoutBase);
	                    
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


	     