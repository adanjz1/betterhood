	package com.lcc3710;

	import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
public class EventCommentPage extends Activity {

		private Button buttonCancel;
		private ListView lv;
		private Intent intent;
		private Bundle extras;
		private Button buttonRespond;
		private EditText commentTXT;
		private String[] listAdapter;
		private TextView commentText;
		ArrayAdapter<String> aAdapter;
		private String sessionID;
		String[] itemsName;
		String[] itemsName2, itemsName3;
		private Activity a = this;
		String delims = "\\^";
		ArrayList<String[]> partyTokens = new ArrayList(3);
		String[] items, items2, items3;
		
		
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO response handling
			intent = getIntent();
	        extras = intent.getExtras();
			switch (requestCode) {
			case BetterHood.REQ_COMMENT_POST:
				if(resultCode == RESULT_OK){
					String query = "";
					query += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
					//query += "&user_name=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
					query += "&event_id=" + extras.getString(BetterHood.EXTRAS_EVENT_ID);
					//query += "&comment_text=" + commentTXT.getText().toString();
					
					
					Intent iHaveIntent = new Intent(a, ConnectionResource.class);
					iHaveIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
					iHaveIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_COMMENT_POPULATE);
					iHaveIntent.putExtra(BetterHood.EXTRAS_EVENT_ID,extras.getString(BetterHood.EXTRAS_EVENT_ID));
					startActivityForResult(iHaveIntent, BetterHood.REQ_COMMENT_POPULATE);
				}
				break;
	    		case BetterHood.REQ_COMMENT_POPULATE:
	    		//Log.i("you ok,", extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR));
	    		
	    		if (resultCode == RESULT_OK) {
	    			Log.i("gettin dat info=", data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE));
	    			Log.i("gettin dat info=", data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE));
	    			//partyTokens = 
	    				//String what = data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE);
	    			String[] interest = data.getStringExtra(BetterHood.EXTRAS_WEB_RESPONSE).split(delims);
	    			
	    			
	    			int num = interest.length;
	    		
	    			items2 = new String[num/2];
	    			itemsName2 = new String[num/2];
	    			int itemsNameCount = 0;
	    			
	    			
	    			
	    			String [] partyTokensReal = interest;
	    			String[] name = interest[0].split("\\|");
	    			int itemsCount = 0;
	    			
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
	    			
	    		
	    		
	    			
	    			int countme = 0;
	    			//aAdapter.clear();
	    			String comments = "";
	    			for(int i =0; i < (itemsName2.length); i++){
	    				
	    				if(i < (itemsName2.length)){
	    					comments += items2[i] + "said:  ";
	    					
	    					
	    				}
	    				
	    				if(i < (itemsName2.length)){
	    					comments += itemsName2[i]+"\n";
	    			
	    					
	    				}
	    				
	    				
	    				countme++;
	    				System.out.println(countme);
	    				
	    				
	    			}
	    			commentText.setText(comments);
	    			
	    			
	    			
	    		
	    			
	    		
	    		}
	    		
	    			break;
	    	
		    
			}
	    			
	    		
		}
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.community_board);
	        intent = getIntent();
	        extras = intent.getExtras();
	        buttonCancel = (Button) findViewById(R.id.cancelComment);
			buttonRespond = (Button) findViewById(R.id.postComment);
			commentTXT = (EditText) findViewById(R.id.commentBox);
			commentText = (TextView) findViewById(R.id.eventComments);
			String query = "";
			query += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
			//query += "&user_name=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
			query += "&event_id=" + extras.getString(BetterHood.EXTRAS_EVENT_ID);
			//query += "&comment_text=" + commentTXT.getText().toString();
			
			
			Intent iHaveIntent = new Intent(a, ConnectionResource.class);
			iHaveIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
			iHaveIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_COMMENT_POPULATE);
			iHaveIntent.putExtra(BetterHood.EXTRAS_EVENT_ID,extras.getString(BetterHood.EXTRAS_EVENT_ID));
			startActivityForResult(iHaveIntent, BetterHood.REQ_COMMENT_POPULATE);
			
			
			buttonRespond.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					System.out.println("i got here");
			if(commentTXT.getText().toString() != null){
			String query = "";
			query += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
			query += "&user_name=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
			query += "&event_id=" + extras.getString(BetterHood.EXTRAS_EVENT_ID);
			System.out.println(extras.getString(BetterHood.EXTRAS_EVENT_ID));
			query += "&comment_text=" + commentTXT.getText().toString();
			Intent iHaveIntent = new Intent(a, ConnectionResource.class);
			iHaveIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
			iHaveIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_COMMENT_POST);
			iHaveIntent.putExtra(BetterHood.EXTRAS_EVENT_ID,extras.getString(BetterHood.EXTRAS_EVENT_ID));
			startActivityForResult(iHaveIntent, BetterHood.REQ_COMMENT_POST);
			}
			
				}        	
	        });
			
			buttonCancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					setResult(RESULT_CANCELED, intent);
					finish();
				}        	
	        });
			
			
	       
			
	       // Log.i("gettin dat info=", extras.getString(BetterHood.EXTRAS_EVENT_SIMILAR));
	      

	        
	        if (extras != null) {
	        	// get sessionID
	        	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);        	
	        } else {
	        	intent.putExtra(BetterHood.EXTRAS_ERROR_MESSAGE, "Session ID not found");
	        	setResult(RESULT_CANCELED, intent);
	        	finish();
	        }
	       
	        
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
			
			
			buttonCancel.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						setResult(RESULT_CANCELED, intent);
						finish();
					}        	
		        });
			buttonRespond.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					String name;
					String comment;
					for(int i =0; i< lv.getCount(); i++){
						if(lv.isItemChecked(i)){
							finish();
							
							
							
						
					}
				
				}        	
	        };
		
		});
		}
	}
