package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SimilarSub extends Activity{
	
	private Intent intent;
	private Bundle extras;
	private TextView tName;
	private TextView tComment;
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO response handling
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.sim_sub_screen);
        tName = (TextView)   findViewById(R.id.name_txt);
        tComment = (TextView) findViewById(R.id.info_txt);
        
        

        intent = getIntent();
        extras = intent.getExtras();
        tName.setText(extras.getString(BetterHood.EXTRAS_EVENT_HOST));
        tComment.setText(extras.getString(BetterHood.EXTRAS_EVENT_NAME));
        
        
        
        
}
}
