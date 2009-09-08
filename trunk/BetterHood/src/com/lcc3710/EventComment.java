package com.lcc3710;

import java.util.HashMap;

import com.lcc3710.Event.AttributeList;

public class EventComment {
	public enum AttributeList {		
		COMMENT_TEXT,
		COMMENT_USER,
		COMMENT_EVENT_ID;
	}
	
	private HashMap<AttributeList, String> attributes;
	
	public EventComment() {
		attributes = new HashMap<AttributeList, String>();
	}
	
	public void setAttribute(AttributeList key, String value) {
		attributes.put(key, value);
	}
	
	public String getAttribute(AttributeList key) {
		return attributes.get(key);
	}

}
