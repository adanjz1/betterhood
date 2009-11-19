package com.gregbugaj.tabwidget.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.gregbugaj.tabwidget.Tab;
import com.gregbugaj.tabwidget.TabHost.Orientation;

public class TabView {
	private static final String TAG = TabView.class.getSimpleName();
	private List<Tab> tabSet=new ArrayList<Tab>();
	private Bitmap mHeader;
	private int mHeaderWidth;
	private int mHeaderHeight;
	private Context context;
	private View currentView;

	private Orientation orientation;
	private int backgroundID;
	private Bitmap iconSeparator;
	private int separatorID;

	public TabView(TabViewConfig config) {
		this.context=config.context;
		this.orientation=config.orientation;
		this.backgroundID=config.headerResourceId;
		this.separatorID=config.separatorId;
		preapareViewResources();
	}


	private void preapareViewResources() {
		mHeader = BitmapFactory.decodeResource(context.getResources(), backgroundID);
		mHeaderWidth = mHeader.getWidth();
		mHeaderHeight = mHeader.getHeight();
	}

	public void addTab(Tab tab){	
		tab.preferedHeight=mHeaderHeight;
		tabSet.add(tab);
	}

	public View render(){

		return renderTOP();
	}
	
	private View getSeparatorView() {
		if(iconSeparator==null){
			iconSeparator = BitmapFactory.decodeResource(context.getResources(), separatorID);
		}
		int separatorHeigth=iconSeparator.getHeight();
		ImageButton separatorView=new ImageButton(context);
		separatorView.setMaxHeight(separatorHeigth);
		separatorView.setMinimumHeight(separatorHeigth);
		separatorView.setMaxWidth(10);

		int topPadding=0;
		if(mHeaderHeight>separatorHeigth){
			topPadding=(mHeaderHeight-separatorHeigth)/2;
		}

		separatorView.setPadding(0, topPadding, 0, 0);
		separatorView.setBackgroundColor(0x00);
		separatorView.setImageBitmap(iconSeparator);
		return separatorView;
	}

	/**
	 * This method needs to be called after all tabs have been added
	 */
	public View renderTOP(){	
		int tabsize=tabSet.size();

		FrameLayout.LayoutParams pTable = new FrameLayout.LayoutParams(
			TableRow.LayoutParams.FILL_PARENT,
			TableRow.LayoutParams.FILL_PARENT);

		TableLayout table=new TableLayout(context);
		table.setLayoutParams(pTable);
		
		// figure out empty space
		int tabWidth = 64;
		int freeSpace = mHeaderWidth - (tabsize * tabWidth) - ((tabsize-1) * 2);

		TableRow rowContent=new TableRow(context);
		TableRow.LayoutParams pRowContent=new TableRow.LayoutParams();
		pRowContent.span=tabsize;
		pRowContent.width=TableRow.LayoutParams.FILL_PARENT;
		pRowContent.height=TableRow.LayoutParams.WRAP_CONTENT;
		pRowContent.weight=1;

		ViewGroup.LayoutParams scrollerParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

		ScrollView scroller=new ScrollView(context);
		scroller.setLayoutParams(scrollerParams);

		scroller.addView(currentView,   scrollerParams);
		rowContent.addView(scroller, pRowContent);

		TableRow rowTabs=new TableRow(context);
		rowTabs.setBackgroundResource(backgroundID);
		
		int j=0;
		for(int i=0;i<tabsize;i++){
			Tab tab=tabSet.get(i);
			View view=tab.getView();
			if (i == 0) {
				view.setPadding(freeSpace/2, 0, 0, 0);
			}
			if (i == tabsize-1) {
				view.setPadding(0, 0, freeSpace/2, 0);
			}
			TableRow.LayoutParams pCol=new TableRow.LayoutParams();
			pCol.weight=0;
			rowTabs.addView(view, pCol);
			
			//Handle the separators between tabs
			if(j%2==0 && i < tabsize-1 ){
				TableRow.LayoutParams pSpanCol=new TableRow.LayoutParams();
				rowTabs.addView(getSeparatorView(), pSpanCol);
				j++;
			}
			j++;
		}

		TableRow.LayoutParams pRowTabs=new TableRow.LayoutParams();
		pRowTabs.height=TableRow.LayoutParams.WRAP_CONTENT;
		pRowTabs.weight=1;

		table.addView(rowTabs, pRowTabs);
		table.addView(rowContent);

		return table;
	}

	/*
	@Override
	protected void dispatchDraw(Canvas canvas) {
		//canvas.drawBitmap(mHeader, 0, 0, null);
		super.dispatchDraw(canvas);
	}

	 */
	public void setCurrentView(View currentView) {
		this.currentView = currentView;
	}

	public void setCurrentView(int  resourceViewID) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);      
		View view=inflater.inflate(resourceViewID, null);
		setCurrentView(view);
	}


	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}


	public void setBackgroundID(int backgroundID) {
		this.backgroundID = backgroundID;
	}



	public Tab getTab(String tag) {
		for(int i=0;i<tabSet.size();i++){
			Tab t=tabSet.get(i);
			if(tag.equals(t.getTag())){
				return t;
			}
		}
		throw new IllegalArgumentException("Tab \""+tag+"\" not found");
	}

}
