package com.lcc3710;

public class BetterHood {
	//intent request thingies
	public static final int REQ_LOGIN = 0;
	public static final int REQ_CREATE_ACCOUNT = 1;
	public static final int REQ_HOME_SCREEN = 2;
	public static final int REQ_CREATE_EVENT = 3;
	public static final int REQ_SETTINGS_SCREEN = 4;
	public static final int REQ_CHECK_USERNAME_AVAILABILITY = 5;
	public static final int REQ_POPULATE_EVENT_LIST = 6;
	
	//debug tags
	public static final String TAG_LOGIN_SCREEN = "bh_login_screen";
	public static final String TAG_CREATE_ACCOUNT_SCREEN_1 = "bh_create_account_1";
	public static final String TAG_CREATE_ACCOUNT_SCREEN_2 = "bh_create_account_2";
	public static final String TAG_LOGIN_PROCESS = "bh_login_process";
	public static final String TAG_CONNECTION_RESOURCE = "bh_connection_resource";
	public static final String TAG_CREATE_EVENT_SCREEN_1 = "bh_create_event_1";
	public static final String TAG_CREATE_EVENT_SCREEN_2 = "bh_create_event_2";
	public static final String TAG_HOME_SCREEN = "bh_home_screen";
	
	// extras tags
	public static final String EXTRAS_QUERY = "query";
	public static final String EXTRAS_WEB_RESPONSE = "webResponse";
	public static final String EXTRAS_ERROR_MESSAGE = "error";
	public static final String EXTRAS_REQUEST_CODE = "requestCode";
	public static final String EXTRAS_SESSION_ID = "sessionID";
	
	public static final String EXTRAS_ACCOUNT_FIRST_NAME = "firstName";
	public static final String EXTRAS_ACCOUNT_LAST_NAME = "lastName";
	public static final String EXTRAS_ACCOUNT_ADDRESS = "address";
	public static final String EXTRAS_ACCOUNT_ZIPCODE = "zipCode";
	public static final String EXTRAS_ACCOUNT_EMAIL = "email";
	public static final String EXTRAS_ACCOUNT_COMMUNITY_CODE = "communityCode";
	public static final String EXTRAS_ACCOUNT_USERNAME = "username";
	public static final String EXTRAS_ACCOUNT_PASSWORD = "password";
	public static final String EXTRAS_ACCOUNT_IHAVE = "iHave";
	
	public static final String EXTRAS_EVENT_TEMPLATE_NAME = "templateName";
	public static final String EXTRAS_EVENT_NAME = "eventName";
		
	public static final String ERROR_PREFIX = "Error: ";
		
	// PHP files and their location
	public static final String URL_BASE = "http://lcc3710.lcc.gatech.edu/better_hood/";
	public static final String PHP_FILE_LOGIN = "user_login.php";
	public static final String PHP_FILE_CREATE_ACCOUNT = "add_user.php";
	public static final String PHP_FILE_CREATE_EVENT = "create_event.php";
	public static final String PHP_FILE_CHECK_USERNAME = "check_username.php";
	public static final String PHP_FILE_POPULATE_EVENT_LIST = "populate_event_list.php";
	
	// templates
	public static final String TEMPLATE_CARPOOL = "Carpool";
	public static final String TEMPLATE_MISSING_CHILD = "Missing Child";
	public static final String TEMPLATE_POOL_PARTY = "Pool Party";
	public static final String TEMPLATE_POTLUCK = "Potluck";
	
	public static final int TOAST_TIME = 5;
	
	public static final boolean DEBUG = true;
}