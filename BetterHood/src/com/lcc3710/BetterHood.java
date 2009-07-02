package com.lcc3710;

public class BetterHood {
	//intent request thingies
	public static final int REQ_LOGIN = 0;
	public static final int REQ_CREATE_ACCOUNT = 1;
	public static final int REQ_HOME_SCREEN = 2;
	public static final int REQ_CREATE_EVENT = 3;
	
	//debug tags
	public static final String TAG_LOGIN_SCREEN = "login";
	public static final String TAG_CREATE_ACCOUNT_SCREEN_1 = "ca1";
	public static final String TAG_CREATE_ACCOUNT_SCREEN_2 = "ca2";
	public static final String TAG_LOGIN_PROCESS = "login_process";
	
	//extras tags
	public static final String EXTRAS_QUERY = "query";
	public static final String EXTRAS_WEB_RESPONSE = "webResponse";
	public static final String EXTRAS_ERROR_MESSAGE = "error";
	
	public static final String ERROR_PREFIX = "Error: ";
	
	public static final int TOAST_TIME = 5;
	
	public static final boolean DEBUG = true;
}
