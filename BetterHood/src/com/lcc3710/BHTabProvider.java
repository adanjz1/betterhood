package com.lcc3710;

import android.app.Activity;
import android.content.Intent;

import com.gregbugaj.tabwidget.Tab;
import com.gregbugaj.tabwidget.TabHost;
import com.gregbugaj.tabwidget.TabHostProvider;
import com.gregbugaj.tabwidget.view.TabViewConfig;

public class BHTabProvider extends TabHostProvider {
	
	private String sessionID;
	
	public BHTabProvider(Activity context) {
		super(context);
	}

	@Override
	public TabHost getTabHost(String sessionID) {
		TabHost tabHost=new TabHost(
			new TabViewConfig()
			.context(context)
			.headerResourceId(R.drawable.tab_bg)
			.separatorId(R.drawable.tab_divider)
			.orientation(TabHost.Orientation.TOP)
		);
		
		Intent in1 = new Intent(context, ShareScreen.class);
		in1.putExtra(BetterHood.EXTRAS_SESSION_ID, this.sessionID);
		Intent in2 = new Intent(context, HomeScreen.class);
		in2.putExtra(BetterHood.EXTRAS_SESSION_ID, this.sessionID);
		Intent in3 = new Intent(context, EventListScreen.class);
		in3.putExtra(BetterHood.EXTRAS_SESSION_ID, this.sessionID);
		this.sessionID = sessionID;
		
		Tab t1=new Tab(context, "Share");
		t1.setIcon(R.drawable.tab_left);
		t1.setIconSelected(R.drawable.tab_left_selected);
		//t1.setIntent(in1);
		
	
		Tab t2=new Tab(context, "Map");
		t2.setIcon(R.drawable.tab_center);
		t2.setIconSelected(R.drawable.tab_center_selected);
		//t2.setIntent(in2);
	
		Tab t3=new Tab(context, "List");
		t3.setIcon(R.drawable.tab_right);
		t3.setIconSelected(R.drawable.tab_right_selected);
		//t3.setIntent(in3);
		
		tabHost.addTab(t1);
		tabHost.addTab(t2);
		tabHost.addTab(t3);
		
		return tabHost;
	}

}
