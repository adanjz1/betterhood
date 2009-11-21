package com.lcc3710;

import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class EventListAdapter extends BaseAdapter {
	private Template[] events;
	private ListView listView;
	private Activity context;
	
	private HashMap<String, Bitmap> icons;
	@SuppressWarnings("unused")
	private String sessionID;
	
	public EventListAdapter(final Activity context, ListView listView, final Template[] events, final String sessionID) {
		this.context = context;
		this.listView = listView;
		this.events = events;
		this.sessionID = sessionID;
		
		sortEvents();
		icons = new HashMap<String, Bitmap>();
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// open info view
				Log.i("EventListAdapter", "item clicked");
				Intent ev = new Intent(context, MapEventScreen.class);
				ev.putExtra(BetterHood.EXTRAS_SESSION_ID, sessionID);
				ev.putExtra(BetterHood.EXTRAS_EVENT_ID, events[position].id);
				context.startActivity(ev);
			}
		});
		
		this.listView.setAdapter(this);
	}
	
	public int getCount() {
		return events.length;
	}
	
	private void sortEvents() {
		Arrays.sort(events, new Comparator<Template>() {
			public int compare(Template o1, Template o2) {
				long now = new GregorianCalendar().getTimeInMillis();
				long c1 = 0;
				long c2 = 0;
				
				Calendar[] temp = o1.getCalendars();
				for (int i = 0; i < temp.length; i++) {
					long t = temp[i].getTimeInMillis();
					long d = Math.abs(now - t);
					if (c1 == 0) {
						c1 = d;
					} else if (d < c1) {
						c1 = d;
					}
				}
				temp = o2.getCalendars();
				for (int i = 0; i < temp.length; i++) {
					long t = temp[i].getTimeInMillis();
					long d = Math.abs(now - t);
					if (c2 == 0) {
						c2 = d;
					} else if (d < c2) {
						c2 = d;
					}
				}
				return (int)(c2 - c1);
			}
		});
	}

	public Object getItem(int position) {
		return events[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Template t = events[position];
		EventListCell cell = new EventListCell(context);
		cell.setEvent(t);
		cell.setIcon(getIcon(t.icon));
		return cell;
	}
	
	private Bitmap getIcon(String file) {
		Bitmap bubbleIcon;
		if (icons.containsKey(file)) {
			bubbleIcon = icons.get(file);
		} else {
			try {
				URL iconURL = new URL(BetterHood.URL_BASE + "icons/" + file);
				bubbleIcon = BitmapFactory.decodeStream(iconURL.openStream());
				icons.put(file, bubbleIcon);
				Log.i("EventListAdapter", iconURL.toExternalForm() + " downloaded successfully.");
			} catch (Exception e) {
				// use the default balloon icon
				Log.i("EventListAdapter", "Icon download failed: " + e.getMessage());
				bubbleIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.balloon);
			}
		}
		int max = 30;
		bubbleIcon = Bitmap.createScaledBitmap(bubbleIcon, max, max, true);
		return bubbleIcon;
	}

}
