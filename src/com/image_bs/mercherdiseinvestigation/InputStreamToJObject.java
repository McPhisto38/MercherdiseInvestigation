/*
 ** Copyright (C) 2013 Image Business Solutions
 ** Developed by Nikolai Vasilev - coder.servoper@gmail.com
 **/

package com.image_bs.mercherdiseinvestigation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Class that handles InputStream containing JSONObject.
 */
public class InputStreamToJObject {

	/** The input stream. */
	private InputStream mInputStream;

	/**
	 * Constructor initializes InputStream.
	 * 
	 * @param inputStream
	 *            the input stream
	 */
	public InputStreamToJObject(InputStream inputStream) {
		this.mInputStream = inputStream;
	}

	/**
	 * @return the jSON object from input stream
	 */
	public JSONObject getJSONObjectFromInputStream() {
		JSONObject jObj = null;
		String jsonString = "";
		
		try {
			/**
			 * Buffer reader from InputStreamReader with standard browser
			 * charset and buffer size 8 for low memory devices
			 */
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	        		mInputStream, "iso-8859-1"), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        /**
	         * read new line from reader until it returns null
	         */
	        while ((line = reader.readLine()) != null) {
	        	/** if not null add newline charecter */
	            sb.append(line + "\n");
	        }
	        mInputStream.close();
	        /** convert StringBuilder to string */
	        jsonString = sb.toString();
	    } catch (Exception e) {
	        Log.e("Buffer Error", "Error converting result " + e.toString());
	    }
	
	    /** try parse the string to a JSON object */
	    try {
	    	/** add the read string to JSONObject */
	        jObj = new JSONObject(jsonString);
	    } catch (JSONException e) {
	        Log.e("JSON Parser", "Error parsing data " + e.toString());
	    }

	    return jObj;
	}
}
