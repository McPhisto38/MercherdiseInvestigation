/*
 ** Copyright (C) 2013 Image Business Solutions
 ** Developed by Nikolai Vasilev - coder.servoper@gmail.com
 **/

package com.image_bs.mercherdiseinvestigation;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

/**
 * Class that prepares and handles login request.
 */
public class LoginRequest extends ServerResponse {

    /** The ProgressDialog. */
    private ProgressDialog mPdialog;
	
	/** The application resources. */
	private Resources mAppResources;
	
	/** The calling activity context. */
	private Context mContext;
	
	/** The my http request parameters. */
	private HashMap<String, String> mMyHttpRequestParams;
	
	/** to remember user. */
	private boolean mToRememberUser = false;
	
	/**
	 * Constructor initializes LoginRequest ands starts http request.
	 * 
	 * @param context
	 *            the context
	 * @param myHttpRequestParams
	 *            the my http request parameters
	 * @param toRememberUser
	 *            the to remember user for next time or no
	 */
	LoginRequest(Context context, HashMap<String, String> myHttpRequestParams,
			boolean toRememberUser) {
		this.mToRememberUser = toRememberUser;
		this.mContext = context;
		/** get application resources */
		this.mAppResources = context.getResources();
		/** url that request will be made to */
        String url = mAppResources.getString(R.string.url_login);
		this.mMyHttpRequestParams = myHttpRequestParams;
		/** start request task */
		setParametersAndRunTask(myHttpRequestParams, url);
	}

	/**
	 * Executed on pre execution of the task.
	 * Sets and Shows loading dialog
	 * 
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse#onRequestHandler()
	 */
	@Override
	void onRequestHandler() {
        mPdialog = new ProgressDialog(this.mContext);
        mPdialog.setCancelable(true);
        mPdialog.setMessage("Searching the magic!");
		mPdialog.show();
	}

	/**
	 * Executes on post execute of the task. Checks if desired data is
	 * received and stops the loading box. If toRemember is true saves data in
	 * file
	 * 
	 * @param isSuccessful
	 *            is http successful
	 * @param json
	 *            the json
	 *            
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse
	 * #onResponseHandler(boolean, org.json.JSONObject)
	 */
	@Override
	void onResponseHandler(boolean isSuccessful, JSONObject json) {
		/**
		 *  Hide/dismiss the loading at the beginning if it's showing
		 */
		if(this.mPdialog.isShowing())
			mPdialog.dismiss();
		
		if( isSuccessful )
		{
			try {
				/** reads the response status */
				int status = json.getInt("status");
				
				/** if status is not null then response is received without errors */
				if(status != 0)
				{ 
			    	LoginStorage loginStorage = new LoginStorage(this.mContext);

			    	/** check if login data need to be remembered */
			    	if(this.mToRememberUser)
			    	{
			    		/**
			    		 * if login data need to be remembered it's taken
			    		 * from the params and is saved in file with received
			    		 * user id
			    		 */
				    	loginStorage.setUser(
				    			this.mMyHttpRequestParams.get(LoginStorage.EMAIL_TAG),
				    			this.mMyHttpRequestParams.get(LoginStorage.PASS_TAG),
				    			Integer.toString(json.getInt("id"))
				    		);
			    	}
			    	else
			    	{
			    		/**
			    		 * if login data must not be remembered empty file is created
			    		 */
			    		loginStorage.setUser("", "", Integer.toString(json.getInt("id")));
			    	}
			    	
			    	/**
			    	 *  if json status is not 0 then user is recognized and
			    	 *  the activity is no more needed so it's finished
			    	 */
			    	((Activity) this.mContext).finish();
			    	
			    	Intent i = new Intent(this.mContext, SelectPlace.class);
					i.putExtra("userID", json.getInt("id"));
					/** clear all child activities of SelectPlace. starting clear instance */
				    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    this.mContext.startActivity(i);
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

}
