package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SimilarSub extends Activity{
	
	private Intent intent;
	private Bundle extras;
	private TextView tName;
	private TextView tComment;
	private EditText eText;
	private Activity a = this;
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO response handling
		
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.sim_sub_screen);
        tName = (TextView)   findViewById(R.id.name_txt);
        tComment = (TextView) findViewById(R.id.info_txt);
        eText = (EditText)  findViewById(R.id.comment);
        
        

        intent = getIntent();
        extras = intent.getExtras();
        tComment.setText(extras.getString(BetterHood.EXTRAS_EVENT_HOST));
        tName.setText(extras.getString(BetterHood.EXTRAS_EVENT_NAME));
        Button back = (Button) findViewById(R.id.back);
        Button sendComment = (Button) findViewById(R.id.post);
        sendComment.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String query = "";
				
				query += "&sid=" + extras.getString(BetterHood.EXTRAS_SESSION_ID);
				query += "&user_name=" + extras.getString(BetterHood.EXTRAS_ACCOUNT_USERNAME);
				query += "&comment_recipient=" + extras.getString(BetterHood.EXTRAS_EVENT_NAME);
				query += "&comment_topic=" + eText.getText().toString();
				Log.i("what do i gots =", query);
				Intent iHaveIntent = new Intent(a, ConnectionResource.class);
				iHaveIntent.putExtra(BetterHood.EXTRAS_QUERY, query);
				iHaveIntent.putExtra(BetterHood.EXTRAS_REQUEST_CODE, BetterHood.REQ_SIMILAR_SUB);
				startActivityForResult(iHaveIntent, BetterHood.REQ_SIMILAR_SUB);
				setResult(RESULT_OK, intent);
				finish();
				
				
			}
        });
        back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
	        	setResult(RESULT_CANCELED, intent);
	        	finish();;
			}        	
        });
        
        
        
        
}
}
