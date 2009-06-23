package com.lcc3710;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CreateAccount2 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_2);
        
        ListView listIHave = (ListView) findViewById(R.id.listIHave);
        listIHave.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, aszIHave));
    }
    
    static final String[] aszIHave = new String[] {
    	"a pool", "a car", "a lawnmower", "a pickup truck", "kids", "an electric generator",
    	"a medical degree"
    };
}