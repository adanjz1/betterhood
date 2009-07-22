package com.lcc3710;

import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

public class DissectAddress {

	private String address;
	double latitude, longitude;
	Context context;
	
	DissectAddress(String a,Context c){
		address = a;
		context = c;
		
		Geocoder gc = new Geocoder(context);
		
		try{
			List<Address> add = gc.getFromLocationName(address, 1);
			
			// try and see if we get any results
			
			
			//Log.d(context, "where is this event suppose to occur" + "lat is" + ad.getLatitude() + "long is" + ad.getLongitude() );
			
			//here we create an even with the attributes of the event object if there is at
			// least any addresses 
			
			if(add.size()>=0){
				Address ad = add.get(0);
				latitude = ad.getLatitude();
				longitude = ad.getLongitude();
					
			}
		}
			catch(Exception e){
				//todo
			}
			
	}
	

	
	public double getLatitude(){
		return latitude;
		
}
	public double getLongitude(){
		return longitude;
	}
}
