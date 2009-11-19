package com.gregbugaj.tabwidget;

import android.app.Activity;

public abstract class TabHostProvider {
	public Activity context;
	public TabHostProvider(Activity context){
		this.context=context;
	}
	public abstract TabHost getTabHost(String category);
}
