package com.lcc3710;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class EventListCell extends TableLayout {
	
	private TextView title;
	private TextView type;
	private TextView creator;
	private TextView date;
	private ImageView icon;
	
	private Context context;
	private Template event;
	
	public EventListCell(Context context) {
		super(context);
		this.context = context;
	}

	public EventListCell(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public void setEvent(Template event) {
		this.event = event;
		if (context != null) {
			init();
		}
	}
	
	private void init() {
		title = new TextView(context);
		type = new TextView(context);
		creator = new TextView(context);
		date = new TextView(context);
		icon = new ImageView(context);
		
		String eTitle = null;
		String eDate = null;
		TemplateWidget[] tw = event.widgets;
		for (int i = 0; i < tw.length; i++) {
			String label = tw[i].label;
			if (label.equals("Title")) {
				if (eTitle == null)
					eTitle = tw[i].value;
			} else if (label.contains("Date")) {
				if (eDate == null) {
					Calendar[] c = event.getCalendars();
					eDate = tw[i].value;
				}
			}
		}
		
		title.setText(eTitle);
		type.setText(event.title);
		creator.setText(event.creatorName);
		date.setText(eDate);
		
		display();
	}
	
	public void setIcon(Bitmap b) {
		icon.setImageBitmap(b);
	}

	private void display() {
		// table init
		this.setStretchAllColumns(true);
		
		// row init
		TableRow row1 = new TableRow(context);
		TableRow row2 = new TableRow(context);
		
		// set up title
		title.setPadding(5, 5, 5, 0);
		title.setTextSize(20);
		title.setTextColor(Color.WHITE);
		
		// set up icon
		icon.setPadding(5, 5, 5, 0);
		TableRow.LayoutParams pIcon = new TableRow.LayoutParams();
		pIcon.gravity = Gravity.RIGHT;
		
		// set up creator
		creator.setPadding(10, 5, 5, 0);
		creator.setTextSize(12);
		
		// set up date
		date.setTextSize(12);
		date.setPadding(5, 5, 5, 0);
		
		// set up row1
		row1.addView(title);
		row1.addView(icon, pIcon);
		
		// set up row2
		row2.addView(creator);
		row2.addView(date, pIcon);
		
		// set up table
		this.addView(row1);
		this.addView(row2);
	}
}
