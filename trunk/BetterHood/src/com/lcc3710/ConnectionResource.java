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

public class ConnectionResource extends Activity {
	/** Called when the activity is first created. */
	public final static String queryBase = "http://lcc3710.lcc.gatech.edu/better_hood/better_hood_login.php?";
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		Bundle extras = getIntent().getExtras();
		String q = "";
		if (extras != null)
		{
			q = extras.getString("query");
		}
		Intent intent = getIntent();
		String val = textURL(q);
		intent.putExtra("webResponse", val);
		setResult(RESULT_OK, intent);
		finish();
	}

	public String textURL(String vars)
	{
		//vars = "xml=<NGP>" + vars + "</NGP>";
		int BUFFER_SIZE = 2000;
		InputStream in = null;
		try {
			HttpURLConnection con = (HttpURLConnection) (new URL(queryBase)).openConnection();
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED";
		}
		return str;
	}
}
