/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

/**
 * The Class PlaceReader.
 */
public class PlaceReader extends ServerResponse {

    /** The ProgressDialog. */
    private ProgressDialog mPdialog;
    
    /** The application resources. */
    private Resources mAppResources;
    
    /** The context. */
    private Context mContext;
    
    /** The server action listener. */
    private ServerActionListener mServerActionListener = null;
	
	/**
	 * Instantiates a new place reader initializes class fields and run
	 * http request.
	 * 
	 * @param context
	 *            the context
	 * @param myParams
	 *            the my params
	 * @param serverActionListener
	 *            the server action listener
	 */
	PlaceReader(Context context, HashMap<String, String> myParams,
			ServerActionListener serverActionListener) {
		this.mContext = context;
		this.mAppResources = context.getResources();
        String url = this.mAppResources.getString(R.string.url_places);
		this.mServerActionListener = serverActionListener;
		setParametersAndRunTask(myParams, url);
	}

	/* (non-Javadoc)
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse#onResponseHandler(boolean, org.json.JSONObject)
	 */
	@Override
	void onResponseHandler(boolean isSuccessful, JSONObject json) {
		if(this.mPdialog != null)
			if(this.mPdialog.isShowing())
				mPdialog.dismiss();
		/** is http request successful */
		if( isSuccessful )
		{
			try {
				int status = json.getInt("status");
				
				/** 
				 * if status is not 0 then response need to be the desired
				 * one.
				 */
				if(status != 0)
				{
					
					if(this.mServerActionListener !=  null)
					{
						this.mServerActionListener.PostAction(isSuccessful, json);
					}
					
				}
				else
				{
		    		new ShowToastMessage(this.mContext, json.getString("message"));
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else
		{
    		new ShowToastMessage(this.mContext, this.mAppResources.getString(R.string.empty_json));
		}
		
	}

	/* (non-Javadoc)
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse#onRequestHandler()
	 */
	@Override
	void onRequestHandler() {
        mPdialog = new ProgressDialog(this.mContext);
        mPdialog.setCancelable(true);
        mPdialog.setMessage("Searching the magic!");
    	mPdialog.show();
	}

	
	/*
	 *  TODO getIsPostActionCalled
	 *  
	 *  @see com.image_bs.mercherdiseinvestigation.FormReader#getIsPostActionCalled();
	 */

}
