package com.lcc3710;

import com.google.android.maps.GeoPoint;
import com.lcc3710.Event.AttributeList;

public class MapLocation {	
	private GeoPoint point;
	private Event e;
	
    public MapLocation(Event e) {
    	point = new GeoPoint((int)(e.getLatitude()*1e6),(int)(e.getLongitude()*1e6));
    	this.e = e;
    }

    public GeoPoint getPoint() {
         return point;
    }
    public String getName() {
         return e.getAttribute(AttributeList.EVENT_NAME);
    }
    public String getType(){
    	return e.getAttribute(AttributeList.EVENT_TYPE);
    }
    public String getHost() {
    	return e.getAttribute(AttributeList.EVENT_HOST);
    }
    public String getDescription(){
    	return e.getAttribute(AttributeList.EVENT_DESCRIPTION);
}
	public String getTime(){
		return e.getAttribute(AttributeList.EVENT_START_DATE);
	}
	public String getAddress() {
		return e.getAttribute(AttributeList.EVENT_LOCATION_ADDRESS);
	}
	public String getPhoneNumber() {
		return e.getAttribute(AttributeList.EVENT_PHONE_NUMBER);
	}
    public String getContactEmail() {
    	return e.getAttribute(AttributeList.EVENT_CONTACT_EMAIL);
    }
} 