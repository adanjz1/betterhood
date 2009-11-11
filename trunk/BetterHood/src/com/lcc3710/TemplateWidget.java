package com.lcc3710;

public class TemplateWidget {
	public TemplateWidget(String t, String l, Boolean r) {
		this.type = t;
		this.label = l;
		this.required = r;
	}
	
	public TemplateWidget() {
		
	}
	
	public String toString() {
		String out = "";
		
		out += "Type: " + type 
			+ "; Label: " + label 
			+ "; Required: " + Boolean.toString(required);
		
		return out;
	}
	
	//required fields
	public String type;
	public String label;
	public Boolean required;
	
	//other fields
	public String inputType;
	public String size;
	public String hint;
	
	//generated fields
	public int id;
	public String value;
	public double latitude;
	public double longitude;
	
	public static enum Type {
		EditText,
		Location,
		StartDate,
		EndDate
	}
}
