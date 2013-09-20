/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerResponse.
 */
public abstract class ServerResponse extends Activity {

	/** The url. */
	private String mUrl;
	
	/** The http request params. */
	private HashMap<String, String> mHttpRequestParams;

	/**
	 * On response handler.
	 * 
	 * @param isSuccessful
	 *            the is http request successful
	 * @param json
	 *            the json response
	 */
	abstract void onResponseHandler(boolean isSuccessful, JSONObject json/*, Context context*/); 
	
	/**
	 * On request handler.
	 */
	abstract void onRequestHandler();
	
	/**
	 * Sets the http parameters and run task.
	 * 
	 * @param httpParams
	 *            the http params
	 * @param url
	 *            the url
	 */
	public void setParametersAndRunTask(HashMap<String, String> httpParams, String url)
	{
		this.mHttpRequestParams = httpParams;
		this.mUrl = url;
    	new ConnectionTask().execute();
	}

    /**
	 * The Class ConnectionTask.
	 */
    class ConnectionTask extends AsyncTask<String, String, String> {
        
        /** is there response. */
        private boolean mIsThereResponse;
        
        /** The json response. */
        private JSONObject mJson;

        /**
		 * Instantiates a new connection task.
		 */
        public ConnectionTask()
        {
        	super();
        }
        
        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /** set to false for beginning */
            mIsThereResponse = false;
    		onRequestHandler();
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            /** if there are parameters add them to the list of paramseters */
            if( mHttpRequestParams != null)
            {
                for (Entry<String, String> entry : mHttpRequestParams.entrySet())
    	            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            
            HttpRequest httpRequester = new HttpRequest(mUrl, "POST", params);
            
            /** run the request */
            InputStreamToJObject isToJObj = 
            		new InputStreamToJObject(httpRequester.getHttpRequestResult());

            mJson = isToJObj.getJSONObjectFromInputStream();
 
 
            if( mJson != null )
            {
            	mIsThereResponse = true;
            }
            else
            {
            	mIsThereResponse = false;
            }
            
            return null;
        }
 
        /**
		 * After completing background task Dismiss the progress dialog *.
		 * 
		 * @param file_url
		 *            the file_url
		 */
        protected void onPostExecute(String file_url) {
	        runOnUiThread(new Runnable() {
	            public void run() {
	            	onResponseHandler(mIsThereResponse, mJson);
	            }
	        });
        }
    }
}
