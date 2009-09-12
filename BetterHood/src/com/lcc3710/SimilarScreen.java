package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SimilarScreen extends Activity{
	
	private Button buttonCancel;
	private Intent intent;
	private Bundle extras;
	
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
        populateSimilar();
        
        

}
	private void populateSimilar(){
		buttonCancel = (Button) findViewById(R.id.buttonReturn);
		buttonCancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					setResult(RESULT_CANCELED, intent);
					finish();
				}        	
	        });
	}
}
