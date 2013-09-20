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
 * Extends ServerResonse to specify server request to get form
 * that specific merchandiser must fill.
 * 
 * Initializes onResponseHandler and onRequestHandler from ServerResonse
 * to handle result.
 * 
 * Provides access to form data as questions from that form.
 */
public class FormReader extends ServerResponse {

	/** The questions data. */
	private ArrayList<Question> mQuestionsData;
	
	/** The server action listener. */
	private ServerActionListener mServerActionListener;
	
	/** The application resources. */
	private Resources mAppRes;
	
	/** The calling activity context. */
	private Context mContext;
	
	/** The ProgressDialog. */
	private ProgressDialog mPDialog = null;
	
	/** is post action called. */
	private boolean mIsPostActionCalled = false;
	
	/**
	 * Instantiates a new form reader. And sends http request.
	 * 
	 * @param itemID
	 *            the item id
	 * @param isProduct
	 *            the is product
	 * @param context
	 *            the context
	 * @param serverActionListener
	 *            the server action listener
	 */
	public FormReader(String itemID, boolean isProduct, Context context,
			ServerActionListener serverActionListener) {
		this.mContext = context;

		this.mAppRes = context.getResources();
		String url = mAppRes.getString(R.string.url_questions);
		
		this.mServerActionListener = serverActionListener;
        
		/** http request parameters */
        HashMap<String, String> myParams = new HashMap<String,String>();
        
        /** request url is same just differs by the parameters */
        if(isProduct)
        	myParams.put("product_id", itemID);
        else
        	myParams.put("category_id", itemID);
        setParametersAndRunTask(myParams,url);
	}

	/**
	 * Gets the questions data.
	 * 
	 * @return the questions data
	 */
	ArrayList<Question> getQuestionsData()
	{
		return this.mQuestionsData;
	}

	/**
	 * Gets the question id.
	 * 
	 * @param position
	 *            the position
	 * @return the question id or null if desired position is not in the
	 * collection range
	 */
	String getQuestionID(int position)
	{
		String companyID = null;
		
		if( position >= 0 && position <= this.mQuestionsData.size() )
		{
			if( this.mQuestionsData.get(position).isBasicQuestion())
			{
				companyID = ((BasicQuestion)this.mQuestionsData.get(position)).questionId;
			}
			else
			{
				companyID = ((EnumQuestion)this.mQuestionsData.get(position)).questionId;
			}
		}
			
		return companyID;
	}

	/**
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse
	 * #onResponseHandler(boolean, org.json.JSONObject)
	 */
	@Override
	void onResponseHandler(boolean isSuccessful, JSONObject json) {
		
		this.mIsPostActionCalled = true;
		
		/** it's true if http request is successful */
		if( isSuccessful )
		{
			try {
				int status = json.getInt("status");
	
				/** if status is not 0 request should be desired one */
				if( status != 0)
				{
					JSONArray jArr = json.getJSONArray("result");
					
					JSONObject jobj;
					
					this.mQuestionsData = new ArrayList<Question>();
					
					for(int i = 0 ; i < jArr.length() ; i++)
					{
						jobj = jArr.getJSONObject(i);
						
						Question tmpObj;
						/** 
						 * there are two types of questions:
						 * <p>
						 * that are with enumerable question type or basic ones
						 */
						if(jobj.getString("type").equalsIgnoreCase("enum"))
						{
							ArrayList<String> values = new ArrayList<String>();
							JSONObject jobjTmp;
							while( i < jArr.length() )
							{
								jobjTmp = jArr.getJSONObject(i++);
								/**
								 * SQL response gives multiple lines with
								 * same question information that differs only
								 * by value.
								 * <p>
								 * That is happening because we need to receive
								 * all enumerable answer values for the desired
								 * question.
								 * <p>
								 * So we don't need to save these as new
								 * question object but just to add it's values
								 * to the existing one
								 * <p>
								 * after question id is different it's
								 * new question
								 */
								if(jobjTmp.getString("id").equalsIgnoreCase(
										jobj.getString("id")))
									values.add(jobjTmp.getString("value"));
								else break;
							}
							/** 
							 * last index increasing is a mistake so index
							 * need to be returned one position back
							 */
							i--;
							
							tmpObj = new EnumQuestion(jobj.getString("id"),
									jobj.getString("question"), jobj.getString("type"),
									jobj.getString("rel_id"), values,
									jobj.getString("type_id") );
						}
						else
						{
							/**
							 * for BasicQuestion it's easier
							 */
							tmpObj = new BasicQuestion(jobj.getString("id"),
									jobj.getString("question"), jobj.getString("type"),
									jobj.getString("rel_id"), jobj.getString("type_id") );
						}
						
						this.mQuestionsData.add(tmpObj);
					}
				}
				else
				{
					mQuestionsData = null;
					new ShowToastMessage(this.mContext, json.getString("message"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				mQuestionsData = null;
				new ShowToastMessage(this.mContext, this.mAppRes.getString(R.string.empty_json));
			}
		}
		else
		{
			mQuestionsData = null;
			new ShowToastMessage(this.mContext, this.mAppRes.getString(R.string.empty_json));
		}

		if(this.mServerActionListener != null && json != null && mQuestionsData != null)
		{
			this.mServerActionListener.PostAction(isSuccessful, json);
		}
		else
		{
			new ShowToastMessage(this.mContext, this.mAppRes.getString(R.string.empty_json));
		}
		
		if(this.mPDialog != null)
			if(this.mPDialog.isShowing())
				this.mPDialog.dismiss();
	}

	/**
	 * Gets the checks if is response handler executed.
	 * 
	 * @return the checks if is response handler executed
	 */
	public boolean getIsResponseHandlerExecuted()
	{
		return this.mIsPostActionCalled;
	}
	// Merchandise
	
	/* (non-Javadoc)
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse#onRequestHandler()
	 */
	@Override
	void onRequestHandler() {
        mPDialog = new ProgressDialog(this.mContext);
        mPDialog.setCancelable(true);
        mPDialog.setMessage("Searching the magic!");
		mPDialog.show();
	}

}
