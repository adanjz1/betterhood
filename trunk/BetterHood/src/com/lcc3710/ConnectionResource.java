package com.lcc3710;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ConnectionResource extends Activity {
	/** Called when the activity is first created. */
	private String queryBase;
	private String query;
	private Intent intent;
	private int requestCode;
	

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		intent = getIntent();
		
		Bundle extras = intent.getExtras();
		query = "";
		
		if (extras != null)
		{
			query = extras.getString(BetterHood.EXTRAS_QUERY);
			requestCode = extras.getInt(BetterHood.EXTRAS_REQUEST_CODE);
		} else {
			//something bad happened
			intent.putExtra(BetterHood.EXTRAS_ERROR_MESSAGE, "ConnectionResource did not receive a query");
			setResult(RESULT_CANCELED, intent);
			finish();
		}
		
		switch (requestCode) {
			case BetterHood.REQ_LOGIN:
				queryBase = BetterHood.PHP_FILE_LOGIN;
				break;
			case BetterHood.REQ_CREATE_ACCOUNT:
				queryBase = BetterHood.PHP_FILE_CREATE_ACCOUNT;
				break;
			case BetterHood.REQ_CREATE_EVENT:
				queryBase = BetterHood.PHP_FILE_CREATE_EVENT;
				break;
			case BetterHood.REQ_CHECK_USERNAME_AVAILABILITY:
				queryBase = BetterHood.PHP_FILE_CHECK_USERNAME;
				break;
			case BetterHood.REQ_POPULATE_EVENT_LIST:
				queryBase = BetterHood.PHP_FILE_POPULATE_EVENT_LIST;
				break;
			case BetterHood.REQ_EVENT_COMMENT:
				queryBase = BetterHood.PHP_FILE_POPULATE_EVENT_COMMENT_LIST;
				break;
			case BetterHood.REQ_SETTINGS_SCREEN:
				queryBase = BetterHood.PHP_FILE_ADD_TO_IHAVE;
				break;
			case BetterHood.REQ_SIMILAR_SCREEN:
				queryBase = BetterHood.PHP_FILE_POPULATE_SIMILAR_LIST;
				break;
			case BetterHood.REQ_SIMILAR_SUB:
				queryBase = BetterHood.PHP_FILE_SIMILAR_SUB_COMMENT;
				break;
			case BetterHood.REQ_SIMILAR_RESPONSE: 
				queryBase = BetterHood.PHP_FILE_POPULATE_RESPONSES;
				break;
			case BetterHood.REQ_OUT_RESPONSE: 
				queryBase = BetterHood.PHP_FILE_POPULATE_OUT_RESPONSES;
				break;
			default:
				intent.putExtra(BetterHood.EXTRAS_ERROR_MESSAGE, "ConnectionResource received an invalid request code");
				setResult(RESULT_CANCELED, intent);
				finish();
		}
		
		String val = textURL(query);
		
		Log.i(BetterHood.TAG_CONNECTION_RESOURCE, "textURL( " + BetterHood.URL_BASE + queryBase + "?" + query + ")");
		Log.i(BetterHood.TAG_CONNECTION_RESOURCE, "textURL() returned: " + val);
		
		intent.putExtra(BetterHood.EXTRAS_WEB_RESPONSE, val);
		setResult(RESULT_OK, intent);
		finish();
	}

	public String textURL(String vars)
	{
		//vars = "xml=<NGP>" + vars + "</NGP>";
		int BUFFER_SIZE = 2000;
		InputStream in = null;
		try {
			HttpURLConnection con = (HttpURLConnection) (new URL(BetterHood.URL_BASE + queryBase + "?" + query)).openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("METHOD", "POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			// add url form parameters
			DataOutputStream ostream = null;
			try {
				ostream = new DataOutputStream(con.getOutputStream());
				ostream.writeBytes(vars);
			} finally {
				if (ostream != null) {
					ostream.flush();
					ostream.close();
				}
			}
			in = con.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			return e1.toString();
		}

		InputStreamReader isr = new InputStreamReader(in);
		int charRead;
		String str = "";
		char[] inputBuffer = new char[BUFFER_SIZE];
		try {
			while ((charRead = isr.read(inputBuffer)) > 0)
			{
				// ---convert the chars to a String---
				String readString =
				String.copyValueOf(inputBuffer, 0, charRead);
				str += readString;
				inputBuffer = new char[BUFFER_SIZE];
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "FAILED";
		}
		return str;
	}
}
