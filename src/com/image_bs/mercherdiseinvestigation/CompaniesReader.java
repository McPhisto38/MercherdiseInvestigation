/*
 ** Copyright (C) 2013 Image Business Solutions
 ** Developed by Nikolai Vasilev - coder.servoper@gmail.com
 **/

package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

/**
 * Extends ServerResonse to specify server request to get Companies
 * that specific merchandiser is authenticated to check.
 * 
 * Initializes onResponseHandler and onRequestHandler from ServerResonse
 * to handle result.
 * 
 * Provides access to companies data.
 */
public class CompaniesReader extends ServerResponse {
	
	// TODO: change to collection object or/and create companies class
	/** The companies data. */
	private String[][] mCompaniesData;
	
	/** The context of the calling activity. */
	private Context mContext;
    
    /** The ProgressDialog. */
    private ProgressDialog mPdialog = null;
	
	/** The application resources. */
	private Resources mAppResources;
	
	/** The server action listener. */
	private ServerActionListener mServerActionListener = null;

    /**
	 * Class Constructor.
	 * <p>
	 * Initializes the class fields and sends request to specified url with
	 * merchandiser id as parameter.
	 * 
	 * @param merchandiserID
	 *            the merchandiser id
	 * @param context
	 *            the calling activity's context
	 * @param serverActionListener
	 *            the server action listener
	 */
	CompaniesReader(String merchandiserID, Context context, ServerActionListener serverActionListener)
	{	
		this.mContext = context;

		this.mAppResources = context.getResources();
		String url = mAppResources.getString(R.string.url_company);
		
		this.mServerActionListener = serverActionListener;
        
		/** http request parameters */
        HashMap<String, String> myParams = new HashMap<String,String>();
        myParams.put("merchendizer_id", merchandiserID);
        /** run http request task */
        setParametersAndRunTask(myParams,url);
	}
	
	/**
	 * Gets the companies data.
	 * 
	 * @return the companies data
	 */
	String[][] getCompaniesData()
	{
		return this.mCompaniesData;
	}
	
	/**
	 * Gets companies names.
	 * 
	 * @return the labels or null if companies data is empty.
	 */
	ArrayList<String> getLabels()
	{
		ArrayList<String> labels = new ArrayList<String>();
		
		if( mCompaniesData != null)
		{
			for(int i = 0; i < mCompaniesData.length ; i++)
			{
				labels.add(mCompaniesData[i][1]);
			}
		}
		else
		{
			labels = null;
		}
		
		return labels;
	}

	/**
	 * Gets company id.
	 * 
	 * @param position
	 *            the desired id's position
	 * @return the company id or null if given position is not in array range.
	 */
	String getCompanyID(int position)
	{
		String companyID = null;
		
		if( position >= 0 && position <= mCompaniesData.length )
		{
			companyID = mCompaniesData[position][0];
		}
			
		return companyID;
	}

	/**
	 * It's executed when server response is received and fills companies data.
	 * If the results does not match the requirements or exception occur then
	 * message is shown and companies data stays null. Dismisses ProgressDialog
	 * if initialized and shown.
	 * 
	 * @param isSuccessful
	 *            the is http request successful
	 * @param json
	 *            the json response
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse
	 * #onResponseHandler(boolean, org.json.JSONObject)
	 */
	@Override
	void onResponseHandler(boolean isSuccessful, JSONObject json) {
		if( isSuccessful )
		{
			try {
				int status = json.getInt("status");

				/** if status is not 0 then json contains desired result */
				if( status != 0)
				{
					JSONArray jArr = json.getJSONArray("result");
					
					JSONObject jobj;
					
					/** initialize companies data array */
					this.mCompaniesData = new String[jArr.length()][2];
					
					for(int i = 0 ; i < jArr.length() ; i++)
					{
						jobj = jArr.getJSONObject(i);
						
						// TODO: must be change to collection type
						this.mCompaniesData[i][0] = jobj.getString("id");
						this.mCompaniesData[i][1] = jobj.getString("name");
					}
				}
				else
				{
					mCompaniesData = null;
					new ShowToastMessage(this.mContext, json.getString("message"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				mCompaniesData = null;
				new ShowToastMessage(this.mContext, this.mAppResources.getString(R.string.empty_json));
			}
		}
		else
		{
			mCompaniesData = null;
			new ShowToastMessage(this.mContext, this.mAppResources.getString(R.string.empty_json));
		}

		if(this.mServerActionListener != null)
		{
			this.mServerActionListener.PostAction(isSuccessful, json);
		}
		
		if(this.mPdialog != null)
			if( this.mPdialog.isShowing())
				this.mPdialog.dismiss();
	}

	/**
	 * Initializes, sets and show ProgressDialog
	 *
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse
	 * #onRequestHandler()
	 */
	@Override
	void onRequestHandler() {
        mPdialog = new ProgressDialog(this.mContext);
        mPdialog.setCancelable(false);
        mPdialog.setMessage("Searching the magic!");
		mPdialog.show();
	}
	
	/**
	 *  TODO getIsPostActionCalled
	 *  
	 *  @see com.image_bs.mercherdiseinvestigation.FormReader
	 * #getIsPostActionCalled();
	 */
}
