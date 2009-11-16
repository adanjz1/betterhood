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
	public static final int REQ_EVENT_LIST_SCREEN = 7;
	public static final int REQ_EVENT_COMMENT= 8;
	public static final int REQ_SIMILAR_SCREEN= 9;
	public static final int REQ_SIMILAR_SUB= 10;
	public static final int REQ_SIMILAR_RESPONSE = 11;
	public static final int REQ_OUT_RESPONSE = 12;
	public static final int REQ_JOIN_EVENT = 13;
	public static final int REQ_SETTINGS_SCREEN_INTEREST = 14;
	public static final int REQ_SIMILAR_SCREEN_INTEREST = 15;
	public static final int REQ_SIMILAR_SCREEN_EVENTS_HAVE = 16;
	public static final int REQ_COMMENT_POST = 17;
	public static final int REQ_COMMENT_POPULATE = 18;
	
	//debug tags
	public static final String TAG_LOGIN_SCREEN = "bh_login_screen";
	public static final String TAG_CREATE_ACCOUNT_SCREEN_1 = "bh_create_account_1";
	public static final String TAG_CREATE_ACCOUNT_SCREEN_2 = "bh_create_account_2";
	public static final String TAG_LOGIN_PROCESS = "bh_login_process";
	public static final String TAG_CONNECTION_RESOURCE = "bh_connection_resource";
	public static final String TAG_CREATE_EVENT_SCREEN_1 = "bh_create_event_1";
	public static final String TAG_CREATE_EVENT_SCREEN_2 = "bh_create_event_2";
	public static final String TAG_HOME_SCREEN = "bh_home_screen";
	public static final String TAG_EVENT_LIST = "bh_event_list";
	public static final String TAG_EVENT_OVERLAY = "bh_event_overlay";
	
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
	public static final String[] EXTRAS_SIMILAR = {"iHave"};
	public static final String EXTRAS_EVENT_TEMPLATE_NAME = "templateName";
	public static final String EXTRAS_EVENT_NAME = "eventName";
	public static final String EXTRAS_EVENT_LOCATION_LATITUDE = "eventLatitude";
	public static final String EXTRAS_EVENT_LOCATION_LONGITUDE = "eventLongitude";
	public static final String EXTRAS_EVENT_LOCATION_ADDRESS = "eventAddress";
	public static final String EXTRAS_EVENT_CONTACT_EMAIL = "eventContactEmail";
	public static final String EXTRAS_EVENT_PHONE_NUMBER = "eventPhoneNumber";
	public static final String EXTRAS_EVENT_START_DATE = "eventStartDate";
	public static final String EXTRAS_EVENT_MESSAGE = "eventMessage";
	public static final String EXTRAS_EVENT_HOST = "eventHost";
	public static final String EXTRAS_EVENT_SIMILAR = "eventHost";
	public static final String EXTRAS_RESPONSES = "eventHost";
	public static final String EXTRAS_EVENT_ID = "eventHost";
	public static final String EXTRAS_USER_LOGGED_IN = "logged in";
	
	public static final String EXTRAS_EVENT_LIST = "eventList";
	
	public static final String EXTRAS_CURRENT_LOCATION = "currentLocation";
		
	public static final String ERROR_PREFIX = "Error: ";
		
	// PHP files and their location
	public static final String URL_BASE = "http://lcc3710.lcc.gatech.edu/better_hood/";
	public static final String PHP_FILE_LOGIN = "user_login.php";
	public static final String PHP_FILE_CREATE_ACCOUNT = "add_user.php";
	public static final String PHP_FILE_CREATE_EVENT_XML = "create_event_xml.php";
	public static final String PHP_FILE_CHECK_USERNAME = "check_username.php";
	public static final String PHP_FILE_GET_USERID = "get_userid.php";
	public static final String PHP_FILE_POPULATE_TEMPLATES = "populate_templates.php";
	public static final String PHP_FILE_POPULATE_EVENT_LIST = "populate_event_list.php";
	public static final String PHP_FILE_POPULATE_EVENT_XML = "populate_event_xml.php";
	public static final String PHP_FILE_POPULATE_EVENT_COMMENT_LIST = "populate_event_list_comment.php";
	public static final String PHP_FILE_ADD_TO_IHAVE = "add_to_ihave.php";
	public static final String PHP_FILE_POPULATE_SIMILAR_LIST = "populate_similar.php";
	public static final String PHP_FILE_SIMILAR_SUB_COMMENT = "post_similar_comment.php";
	public static final String PHP_FILE_POPULATE_RESPONSES = "populate_response.php";
	public static final String PHP_FILE_POPULATE_OUT_RESPONSES = "populate_out_response.php";
	public static final String PHP_FILE_JOIN_EVENT = "php_join_event.php";
	public static final String PHP_FILE_ADD_TO_IHAVE_INTEREST = "add_to_ihave_interest.php";
	public static final String PHP_FILE_POPULATE_SIMILAR_INTEREST_LIST = "populate_similar_interest.php";
	public static final String PHP_FILE_POPULATE_SIMILAR_EVENTS_HAVE = "populate_similar_events.php";
	public static final String PHP_FILE_POPULATE_EVENT_COMMENT = "populate_event_comment.php";
	public static final String PHP_FILE_POST_EVENT_COMMENT= "post_event_comment.php";
	// templates	
	public static final String TEMPLATE_BABYSITTER = "Babysitter";
	public static final String TEMPLATE_CAR = "Car";
	public static final String TEMPLATE_CARPOOL = "Carpool";
	public static final String TEMPLATE_CHARITY = "Charity";
	public static final String TEMPLATE_LAWNMOWER = "Lawnmower";
	public static final String TEMPLATE_MISSING_CHILD = "Missing Child";
	public static final String TEMPLATE_PARTY = "Party";
	public static final String TEMPLATE_PLAYDATE = "Playdate";
	public static final String TEMPLATE_POOL_PARTY = "Pool Party";
	public static final String TEMPLATE_POTLUCK = "Potluck";
	
	public static final int TOAST_TIME = 5;
	
	public static final boolean DEBUG = true;
}
