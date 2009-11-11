package com.lcc3710;

import com.google.android.maps.GeoPoint;

public class MapLocation {	
	private GeoPoint point;
	private Template e;
	
    public MapLocation(Template e) {
    	int lat, lon;
    	TemplateWidget[] widgets = e.widgets;
    	for (int i = 0; i < widgets.length; i++) {
    		if (widgets[i].type.equals("Location")) {
    			TemplateWidget w = widgets[i];
    			lat = (int)(w.latitude * 1e6);
    			lon = (int)(w.longitude * 1e6);
    			
    			point = new GeoPoint(lat, lon);
    			break;
    		}
    	}
    	
    	this.e = e;
    }

    public GeoPoint getPoint() {
         return point;
    }
    
    public Template getTemplate() {
    	return e;
    }
} 