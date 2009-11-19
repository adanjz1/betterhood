package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gregbugaj.tabwidget.Tab;
import com.gregbugaj.tabwidget.TabHost;
import com.gregbugaj.tabwidget.TabHostProvider;

public class ShareScreen extends Activity {
	private String sessionID;
	
	private Intent intent;
	private Bundle extras;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		intent = getIntent();
        extras = intent.getExtras();
		// extract session id and username
    	sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);
		
		TextView shareView = new TextView(this);
		shareView.setText("TODO");
		
		TabHostProvider tabProvider = new BHTabProvider(this);
        TabHost tabHost = tabProvider.getTabHost(sessionID);
        tabHost.setCurrentView(shareView);
        Tab t = tabHost.getTab("Share");
        t.setSelected(true);
         
        setContentView(tabHost.render());
	}

}
