package com.gregbugaj.tabwidget.view;

import com.gregbugaj.tabwidget.TabHost.Orientation;

import android.content.Context;


@SuppressWarnings("unused")
public class TabViewConfig {
	Context context;
	int headerResourceId;
	int separatorId;
	Orientation orientation;

	public TabViewConfig context(Context context){
		this.context=context;
		return this;
	}

	public TabViewConfig headerResourceId(int headerResourceId){
		this.headerResourceId=headerResourceId;
		return this;
	}
	
	public TabViewConfig separatorId(int separatorId){
		this.separatorId=separatorId;
		return this;
	}

	public TabViewConfig orientation(Orientation orientation){
		this.orientation=orientation;
		return this;
	}
}
