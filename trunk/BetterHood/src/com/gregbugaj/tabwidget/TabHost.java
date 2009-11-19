package com.gregbugaj.tabwidget;

import android.view.View;

import com.gregbugaj.tabwidget.view.TabView;
import com.gregbugaj.tabwidget.view.TabViewConfig;

/**
 * Facade class to help us create Tabs
 * @author devil
 *
 */
public class TabHost {
	private TabView tabView;
	
	
	public enum Orientation {
		TOP,
		BOTTOM
	};
	public TabHost(TabViewConfig config) {
		tabView=new TabView(config);		
	}

	public void addTab(Tab tab){
		tabView.addTab(tab);
	}
	
	public Tab getTab(String tag){
		return tabView.getTab(tag);
	}
	
	public void setCurrentView(View currentView) {
		tabView.setCurrentView(currentView);
	}

	public void setCurrentView(int  resourceViewID) {
		tabView.setCurrentView(resourceViewID);
	}

	public View render(){
		return tabView.render();	
	}

	
}
