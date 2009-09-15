package com.lcc3710;

import java.util.HashMap;

public class Event {
	public enum AttributeList {		
		EVENT_NAME,
		EVENT_TYPE,
		EVENT_HOST,
		EVENT_DESCRIPTION ,
		EVENT_START_DATE,
		EVENT_LOCATION_ADDRESS,
		EVENT_LOCATION_LATITUDE,
		EVENT_LOCATION_LONGITUDE,
		EVENT_PHONE_NUMBER,
		EVENT_CONTACT_EMAIL,
		EVENT_ID;
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
	
	public Double getLatitude() {
		return Double.valueOf(getAttribute(AttributeList.EVENT_LOCATION_LATITUDE));
	}
	
	public Double getLongitude() {
		return Double.valueOf(getAttribute(AttributeList.EVENT_LOCATION_LONGITUDE));
	}
}
