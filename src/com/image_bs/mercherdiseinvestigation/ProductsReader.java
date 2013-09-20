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
 * Extends ServerResonse to specify server request to get products
 * from a specific company and category.
 * <p>
 * Initializes onResponseHandler and onRequestHandler from ServerResonse
 * to handle result.
 * <p>
 * Provides access to products data.
 */
public class ProductsReader extends ServerResponse {

	// TODO: To be made collection of object
	/** The products data. */
	private ArrayList<String[]> mProductsData;
	
	/** The server action listener. */
	private ServerActionListener mServerActionListener;
	
	/** The application resources. */
	private Resources mAppResources;
	
	/** The context. */
	private Context mContext;
	
	/** The ProgressDialog. */
	private ProgressDialog mPDialog = null;
	
    /**
	 * Class Constructor Initializes the class fields and starts http request
	 * 
	 * @param companyID
	 *            the company id
	 * @param categoryID
	 *            the category id
	 * @param context
	 *            the context
	 * @param serverActionListener
	 *            the server action listener
	 */
	ProductsReader(String companyID, String categoryID, Context context,
			ServerActionListener serverActionListener)
	{
		this.mContext = context;

		this.mAppResources = context.getResources();
		String url = mAppResources.getString(R.string.url_full_forms_list);
		
		this.mServerActionListener = serverActionListener;
        
		/** Parameters needed for this specific request */
        HashMap<String, String> myHttpRequestParams = new HashMap<String,String>();
        myHttpRequestParams.put("company_id", companyID);
        myHttpRequestParams.put("category_id", categoryID);
        /** runs the http request */
        setParametersAndRunTask(myHttpRequestParams,url);
	}
	
	/**
	 * Gets the products data.
	 * 
	 * @return the products data or null if it's empty
	 */
	ArrayList<String[]> getProductsData()
	{
		return this.mProductsData;
	}

	
	/**
	 * Gets companies names as labels.
	 * 
	 * @return the labels or null if products data is empty
	 */
	ArrayList<String> getLabels()
	{
		ArrayList<String> labels = new ArrayList<String>();
		
		if( this.mProductsData != null)
		{
			for(int i = 0; i < this.mProductsData.size() ; i++)
			{
				labels.add(this.mProductsData.get(i)[1]);
			}
		}
		else
		{
			labels = null;
		}
		
		return labels;
	}

	/**
	 * Gets product id.
	 * 
	 * @param position
	 *            the position
	 * @return the product id or null if given position is not in array range.
	 */
	String getProductID(int position)
	{
		String companyID = null;
		
		if( position >= 0 &&  position <= this.mProductsData.size() )
		{
			companyID = this.mProductsData.get(position)[0];
		}
			
		return companyID;
	}

	/**
	 * Returns product name.
	 * 
	 * @param position
	 *            the position
	 * @return the product name or null if given position is not in array range.
	 */
	public String getProductName(int position) {
		String companyName = null;
		
		if( position >= 0 &&  position <= this.mProductsData.size() )
		{
			companyName = this.mProductsData.get(position)[1];
		}
			
		return companyName;
	}


	/**
	 * It's executed when
	 * server response is received and fills products data. If the results
	 * does not match the requirements or exception occur then message is
	 * shown and products data stays null. Dismisses ProgressDialog if
	 * initialized and shown.
	 * 
	 * @param isSuccessful
	 *            is http request successful
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
	
				/** if status is not 0 then json response should be the desired
				 * one.
				 */
				if( status != 0)
				{
					JSONArray jArr = json.getJSONArray("result");
					
					JSONObject jobj;
					
					this.mProductsData = new ArrayList<String[]>();
					
					for(int i = 0 ; i < jArr.length() ; i++)
					{
						jobj = jArr.getJSONObject(i);
						
						String[] tmpObj = {jobj.getString("id"), jobj.getString("name")};
						this.mProductsData.add(tmpObj);
					}
				}
				else
				{
					mProductsData = null;
					new ShowToastMessage(this.mContext, json.getString("message"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				mProductsData = null;
				new ShowToastMessage(this.mContext, this.mAppResources
						.getString(R.string.empty_json));
			}
		}
		else
		{
			mProductsData = null;
			new ShowToastMessage(this.mContext, this.mAppResources
					.getString(R.string.empty_json));
		}

		if(this.mServerActionListener != null)
		{
			this.mServerActionListener.PostAction(isSuccessful, json);
		}
		
		if(this.mPDialog != null)
			if(this.mPDialog.isShowing())
				this.mPDialog.dismiss();
	}

	/**
	 ** (non-Javadoc)
	 ** @see com.image_bs.mercherdiseinvestigation.ServerResponse
	 ** 		#onRequestHandler()
	 ** 
	 ** Initializes, sets and show ProgressDialog
	 **/
	@Override
	void onRequestHandler() {
        mPDialog = new ProgressDialog(this.mContext);
        mPDialog.setCancelable(true);
        mPDialog.setMessage("Searching the magic!");
		mPDialog.show();
	}

	
	/**
	 **  TODO getIsPostActionCalled
	 **  
	 **  @see com.image_bs.mercherdiseinvestigation.FormReader#getIsPostActionCalled();
	 **/
}