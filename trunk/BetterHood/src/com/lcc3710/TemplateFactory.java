package com.lcc3710;

import java.util.ArrayList;

import android.util.Log;

public class TemplateFactory {
	public static final int POPULATE_EVENTS = 0;
	public static final int POPULATE_TEMPLATES = 1;
	public static final int POPULATE_INTERESTS = 2;
	
	private String sessionID;
	
	public TemplateFactory(String sessionID) {
		this.sessionID = sessionID;
	}
	
	public Template[] getTemplates(int code) {
		// grab our templates from database
		SQLQuery query = null;
		if (code == POPULATE_TEMPLATES) {
			query = new SQLQuery(BetterHood.PHP_FILE_POPULATE_TEMPLATES, "sid=" + sessionID);
		} else if (code == POPULATE_EVENTS) {
			query = new SQLQuery(BetterHood.PHP_FILE_POPULATE_EVENT_XML, "sid=" + sessionID);
		} else if (code == POPULATE_INTERESTS) {
			query = new SQLQuery(BetterHood.PHP_FILE_POPULATE_EVENT_INTERESTED, "sid=" + sessionID);
		}
		String result = query.submit()
		.replace("\n", "")
		.replace("\t", "")
		.replace("\r", "")
		.replace("\f", "");
		// split templates by | delimiter
		String[] raw = result.split("\\|");
		
		ArrayList<Template> templates = new ArrayList<Template>();
		
		// process each template into Template objects
		for (int i = 1; i < raw.length; i++) {
			int event_id = Integer.parseInt(raw[i]);
			i++;
			Log.i("TemplateFactory", raw[i]);
			Template temp = new Template(raw[i]);
			temp.id = event_id;
			templates.add(temp);
		}
		
		return templates.toArray(new Template[0]);
	}
}
