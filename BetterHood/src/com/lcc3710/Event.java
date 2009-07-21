package com.lcc3710;

import java.util.HashMap;

public class Event {
	public enum AttributeList {
		EVENT_NAME, EVENT_ADDRESS, EVENT_DESCRIPTION, EVENT_TYPE, EVENT_DATE, EVENT_ID, EVENT_PHONE_NUMBER, EVENT_EMAIL, EVENT_LONGITUDE, EVENT_LATITUDE;
	}
	
	private HashMap<AttributeList, String> attributes;
	
	public Event() {
		attributes = new HashMap<AttributeList, String>();
	}
	
	public void setAttribute(AttributeList key, String value) {
		attributes.put(key, value);
	}
	
	public String getAttribute(AttributeList key) {
		return attributes.get(key);
	}
}
