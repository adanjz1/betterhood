package com.lcc3710;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

@SuppressWarnings("deprecation")
public class ShareView extends AbsoluteLayout {
	private int screenWidth;
	private int screenHeight;
	
	private String sessionID;
	
	private Bitmap homeIcon;
	
	private Template[] events;
	private HashMap<String, Bitmap> icons;
	private HashMap<String, Integer> types;
	
	private OnClickListener listener;
	private Context context;
	
	public ShareView(Context context, String sessionID) {
		super(context);
		this.sessionID = sessionID;
		this.context = context;
		init();
	}

	public ShareView(Context context, AttributeSet attrs, String sessionID) {
		super(context, attrs);
		this.sessionID = sessionID;
		init();
	}

	public ShareView(Context context, AttributeSet attrs, int defStyle, String sessionID) {
		super(context, attrs, defStyle);
		this.sessionID = sessionID;
		init();
	}
	
	private void init() {
		this.setBackgroundColor(Color.WHITE);
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
	}
	
	public void initViews() {
		TemplateFactory tf = new TemplateFactory(sessionID);
		events = tf.getTemplates(TemplateFactory.POPULATE_INTERESTS);
		
		// get icons and see how many of each type we have
		if ((icons == null) || (types == null)) {
			icons = new HashMap<String, Bitmap>();
			types = new HashMap<String, Integer>();
			for (int i = 0; i < events.length; i++) {
				if (!icons.containsKey(events[i].title)) {
					// download the event icon
					Bitmap bubbleIcon;
					String iconName = events[i].icon;
					try {
						URL iconURL = new URL(BetterHood.URL_BASE + "icons/" + iconName);
						bubbleIcon = BitmapFactory.decodeStream(iconURL.openStream());
						Log.i("EventOverlay", iconURL.toExternalForm() + " downloaded successfully.");
					} catch (Exception e) {
						// use the default balloon icon
						Log.i("EventOverlay", "Icon download failed: " + e.getMessage());
						bubbleIcon = BitmapFactory.decodeResource(this.getResources(),R.drawable.balloon);
					}
					icons.put(events[i].title, bubbleIcon);
				}
				if (types.containsKey(events[i].title)) {
					int count = types.get(events[i].title);
					types.remove(events[i].title);
					count++;
					types.put(events[i].title, count);
				} else {
					types.put(events[i].title, 1);
				}
			}
		}
		
		int radius = (screenWidth > screenHeight) ? (int)(screenHeight/2*0.65) : (int)(screenWidth/2*0.65);
		Point[] points = getPoints(radius, types.size(), new Point(screenWidth/2,screenHeight/2));
		
		// remove existing views
		this.removeAllViews();
		
		// draw each icon
		int baseWidth = 35;
		int multiplier = 15;
		String[] keys = types.keySet().toArray(new String[0]);
		for (int i = 0; i < keys.length; i++) {
			ImageView icon = new ImageView(this.getContext());
			icon.setTag(keys[i]);
			if (listener == null) {
				listener = getListener();
				icon.setOnClickListener(listener);
			} else {
				icon.setOnClickListener(listener);
			}
			
			int numEvents = types.get(keys[i]);
			int width = baseWidth + (multiplier * numEvents);
			Bitmap big = (icons.containsKey(keys[i])) ? (icons.get(keys[i])) : 
				(BitmapFactory.decodeResource(this.getResources(), R.drawable.balloon));
			Bitmap b = Bitmap.createScaledBitmap(big, width, width, true);
			icon.setImageBitmap(b);
			
			int x = points[i].x - b.getWidth()/2;
			int y = points[i].y - b.getHeight()/2;
			
			AbsoluteLayout.LayoutParams layout = new AbsoluteLayout.LayoutParams(width, width, x, y);
			this.addView(icon, layout);
		}
	}
	
	public void setEvents(Template[] events) {
		this.events = events;
	}
	
	private OnClickListener getListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				Log.i("ShareView", (String)v.getTag());
				getEventDialog((String)v.getTag()).show();
			}
		};
	}
	
	private Dialog getEventDialog(String type) {
		Dialog d = new Dialog(this.getContext());
		d.setCancelable(true);
		d.setCanceledOnTouchOutside(true);
		d.setTitle(type + " Events");
		
		ArrayList<String> alEvents = new ArrayList<String>();
		for (int i = 0; i < events.length; i++) {
			Template e = events[i];
			String title = e.title;
			String event_title = "*error*";
			if (title.equals(type)) {
				for (int j = 0; j < e.widgets.length; j++) {
					TemplateWidget w = e.widgets[j];

					if (w.label.equals("Title")) {
						event_title = w.value;
						break;
					}
				}
				alEvents.add(event_title);
			}
		}
		
		ListView list = new ListView(d.getContext());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				d.getContext(), 
				android.R.layout.simple_list_item_1, 
				alEvents.toArray(new String[0]));
		list.setAdapter(adapter);
		
		d.setContentView(list);
		
		return d;
	}

	/* (non-Javadoc)
	 * @see android.view.View#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		
		if (homeIcon == null) {
			homeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon);
			homeIcon = Bitmap.createScaledBitmap(homeIcon, 45, 45, true);
		}
		if ((screenWidth == 0) | (screenHeight == 0)) {
			screenWidth = this.getWidth();
			screenHeight = this.getHeight();
			initViews();
		}
		
		canvas.drawBitmap(homeIcon, 
				screenWidth/2 - homeIcon.getWidth()/2, 
				screenHeight/2 - homeIcon.getHeight()/2, 
				null);
	}
	
	private Point[] getPoints(int radius, int points, Point center) {
		Point[] out = new Point[points];
		
		double alpha = Math.PI * 2 / points;
	    
	    for (int i = 0; i < points; i++)
	    {
	        double theta = alpha * i;
	        Point pointOnCircle = new Point((int)( Math.cos(theta) * radius),(int)(Math.sin(theta) * radius));
	        out[i] = new Point(center.x + pointOnCircle.x, center.y + pointOnCircle.y);
	    }
	    
		return out;
	}
}
