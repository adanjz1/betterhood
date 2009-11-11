package com.lcc3710;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SQLQuery {
	private String target;
	private String query;
	
	public SQLQuery(String target, String query) {
		this.target = target;
		this.query = query;
	}
	
	public String submit() {		
		return textURL(query);
	}
	
	public String textURL(String vars)
	{
		//vars = "xml=<NGP>" + vars + "</NGP>";
		int BUFFER_SIZE = 2000;
		InputStream in = null;
		try {
			HttpURLConnection con = (HttpURLConnection) (new URL(BetterHood.URL_BASE + target + "?" + query)).openConnection();
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
