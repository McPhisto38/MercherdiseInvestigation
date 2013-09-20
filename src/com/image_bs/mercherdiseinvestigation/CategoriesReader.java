/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
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
 * Extends ServerResonse to specify server request to get categories
 * from a specific company.
 * <p>
 * Initializes onResponseHandler and onRequestHandler from ServerResonse
 * to handle result.
 * <p>
 * Provides access to categories data.
 */
public class CategoriesReader extends ServerResponse {
	/*
	 * TODO: change it to ArrayList<ArrayList<String>> or other collection type
	 * Or create it's own Category type
	 */
	/** The categories data. */
	private ArrayList<String[]> mCategoriesData;
	
	/** The context of the calling activity. */
	private Context mContext;
	
	/** The application resources. */
	private Resources mAppResources;
	
	/** The server action listener. */
	private ServerActionListener mServerActionListener;
    
    /** The m pdialog. */
    private ProgressDialog mPdialog = null;
	
    /**
	 * Class Constructor. 
	 * <p>
	 * Initializes the class fields and sends request to specified url
	 * with company id as parameter
	 * 
	 * @param companyID
	 *            the id of the company that matches the requested one
	 * @param context
	 *            the context of the calling activity
	 * @param serverActionListener
	 *            the server action listener
	 */
	CategoriesReader(String companyID, Context context, ServerActionListener serverActionListener)
	{
		this.mContext = context;

		/**
		 * Get application resources and get url for categories from
		 * the strings
		 * 
		 * See res/values/urls.xml
		 */
		this.mAppResources = context.getResources();
		String url = mAppResources.getString(R.string.url_category);
		
		this.mServerActionListener = serverActionListener;
        
		/**
		 * Declare and initializes HashMap with parameters of the server
		 * request
		 */
        HashMap<String, String> myParams = new HashMap<String,String>();
        myParams.put("company_id", companyID);
        
        /**
         * see package com.image_bs.mercherdiseinvestigation.ServerResponse
         * 		.setArgumentsAndRunTask(HashMap<String, String> myParams
         * 			, String url);
         */
        setParametersAndRunTask(myParams,url);
	}
	
	/**
	 * Gets the categories data.
	 * 
	 * @return the categories data
	 */
	ArrayList<String[]> getCategoriesData()
	{
		return this.mCategoriesData;
	}
	
	/**
	 * Gets labels 
	 * 
	 * @return the labels or null if category data is empty.
	 */
	ArrayList<String> getLabels()
	{
		ArrayList<String> labels = new ArrayList<String>();
		
		if( mCategoriesData != null)
		{
			for(int i = 0; i < mCategoriesData.size() ; i++)
			{
				labels.add(mCategoriesData.get(i)[1]);
			}
		}
		else
		{
			labels = null;
		}
		
		return labels;
	}

	/**
	 * Gets category id.
	 * 
	 * @param position
	 *            the position of the requested category id
	 * @return the category id or null if categories data is empty or wrong
	 * position is given
	 */
	String getCategoryID(int position)
	{
		String categoryId = null;
		
		if( position >= 0 && position <= mCategoriesData.size() )
		{
			categoryId = mCategoriesData.get(position)[0];
		}
			
		return categoryId;
	}

	/**
	 * Gets category name but returns 
	 * 
	 * @param position
	 *            the position
	 * @return the category name or null if requested position is in the 
	 * range 0 to categories data array size.
	 */
	public String getCategoryName(int position) {
		String categoryName = null;
		
		if( position >= 0 && position <= mCategoriesData.size() )
		{
			categoryName = mCategoriesData.get(position)[1];
		}
			
		return categoryName;

	}

	/**
	 * It's executed when server response is received and fills categories
	 * data. If the results are not match the requirements or exception occur
	 * then message is shown and categories data stays null.  
	 * 
	 * @param isSuccessful
	 *            is request successful
	 * @param json
	 *            received JSON result
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse
	 * #onResponseHandler(boolean, org.json.JSONObject)
	 */
	@Override
	void onResponseHandler(boolean isSuccessful, JSONObject json) {
		/**
		 * Check if httprequest is fine
		 */
		if( isSuccessful )
		{
			
			try {
				/**
				 * Get response status
				 */
				int status = json.getInt("status");
	
				/** if status is not 0 then json contains desired result */
				if( status != 0)
				{
					JSONArray jArr = json.getJSONArray("result");
					
					JSONObject jobj;
					
					this.mCategoriesData = new ArrayList<String[]>();
					
					for(int i = 0 ; i < jArr.length() ; i++)
					{
						jobj = jArr.getJSONObject(i);
						
						/** every category contains category name and id */
						String[] tmpObj = {jobj.getString("id"), jobj.getString("name")};
						/** add the read object to the categories data */
						this.mCategoriesData.add(tmpObj);
					}
				}
				else
				{
					/** if status is 0 then set the categories data to null */
					mCategoriesData = null;
					/** shwo error message */
					new ShowToastMessage(this.mContext, json.getString("message"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				mCategoriesData = null;
				new ShowToastMessage(this.mContext, this.mAppResources.
						getString(R.string.empty_json));
			}
		}
		else
		{
			mCategoriesData = null;
			new ShowToastMessage(this.mContext, this.mAppResources.getString(R.string.empty_json));
		}

		if(this.mServerActionListener != null)
		{
			this.mServerActionListener.PostAction(isSuccessful, json);
		}
		
		if(this.mPdialog != null)
			if(this.mPdialog.isShowing())
				this.mPdialog.dismiss();
	}

	/**
	 * Initializes, sets and show ProgressDialog
	 * 
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse
	 * 		#onRequestHandler()
	 */
	@Override
	void onRequestHandler() {
        mPdialog = new ProgressDialog(this.mContext);
        mPdialog.setCancelable(false);
        mPdialog.setMessage("Searching the magic!");
		mPdialog.show();
	}
	
	/**
	 *  TODO: getIsPostActionCalled
	 *  
	 *  @see com.image_bs.mercherdiseinvestigation.FormReader#getIsPostActionCalled();
	 */
}