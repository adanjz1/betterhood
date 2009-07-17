package com.lcc3710;

import android.graphics.Bitmap;

import com.google.android.maps.GeoPoint;

public class MapLocation {

    private GeoPoint    point;
    private String      name;
    public String		typeName, description, time;
    private Bitmap      bubbleIcon, shadowIcon;
    

    public MapLocation(String name,double latitude, double longitude, String typeName,
    		String description, String time) {
         this.name = name;
         this.typeName = typeName;
         this.description = description;
         this.time = time;
         point = new GeoPoint((int)(latitude*1e6),(int)(longitude*1e6));
         
    }

    public GeoPoint getPoint() {
         return point;
    }

    public String getName() {
         return name;
    }
    
    public String getType(){
    	return typeName;
    	
    }
    public String getDescription(){
    	return description;
}
	public String getTime(){
		return time;
	}
    
} 