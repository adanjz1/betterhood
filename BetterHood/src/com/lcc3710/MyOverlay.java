package com.lcc3710;

import com.google.android.maps.Overlay;

public abstract class MyOverlay extends Overlay {
	
	abstract String type();
	abstract void draw();
	abstract boolean onTap();
	
	}


