package com.lcc3710;

import android.graphics.Bitmap;

import com.google.android.maps.GeoPoint;

public class MapLocation {

    private GeoPoint    point;
    private String      name;
    public String		typeName;
    private Bitmap bubbleIcon, shadowIcon;
    

    public MapLocation(String name,double latitude, double longitude, String typeName ) {
         this.name = name;
         this.typeName = typeName;
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
} 