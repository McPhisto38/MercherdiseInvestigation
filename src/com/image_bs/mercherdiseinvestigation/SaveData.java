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

import android.content.Context;
import android.content.res.Resources;

/**
 * The Class SaveData.
 */
public class SaveData extends ServerResponse {

	/** The server action listener. */
	private ServerActionListener mServerActionListener;
	
	/** The application resources. */
	private Resources mAppResources;

	/**
	 * Instantiates a new save data and sends data to the server.
	 * 
	 * @param questions
	 *            the questions
	 * @param placeID
	 *            the place id
	 * @param userID
	 *            the user id
	 * @param context
	 *            the context
	 * @param serverActionListener
	 *            the server action listener
	 */
	SaveData(ArrayList<Question> questions, String placeID,
			Integer userID, Context context, ServerActionListener serverActionListener)
	{
		this.mAppResources = context.getResources();
		String url = mAppResources.getString(R.string.url_save_data);
		
		this.mServerActionListener = serverActionListener;
        
		try{
			/** sent data */
	        HashMap<String, String> myHttpRequestParams = new HashMap<String,String>();
	        JSONArray jArr = new JSONArray ();
	        
	        /** converting questions objects to json */
	        jArr = questionsDataToJson(questions, placeID, userID);

	        /** attach it to the http request parameters */
	    	myHttpRequestParams.put("data", jArr.toString());
	    	
	    	/** run the http request */
	        setParametersAndRunTask(myHttpRequestParams,url);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * Questions data to json.
	 * 
	 * @param questions
	 *            the questions
	 * @param placeID
	 *            the place id
	 * @param userID
	 *            the user id
	 * @return the jSON array
	 * @throws JSONException
	 *             the jSON exception
	 */
	private JSONArray questionsDataToJson(ArrayList<Question> questions,
			String placeID, Integer userID) throws JSONException {
        JSONArray jArr = new JSONArray ();
		for(int i = 0 ; i < questions.size() ; i++)
		{
		    JSONObject jobj = new JSONObject();
		    
			if(questions.get(i).isBasicQuestion())
			{
				BasicQuestion question = (BasicQuestion) questions.get(i);

		        jobj.put("id", question.questionId);
		        jobj.put("value", question.getCurrentValue());
		        jobj.put("rel_id", question.relID);
		        jobj.put("type_id", question.answerTypeID);
		        jobj.put("user_id", userID);
		        jobj.put("place_id", placeID);
			}
			else
			{
				EnumQuestion question = (EnumQuestion) questions.get(i);

		        jobj.put("id", question.questionId);
		        jobj.put("value", question.getCurrentValue());
		        jobj.put("rel_id", question.relID);
		        jobj.put("type_id", question.answerTypeID);
		        jobj.put("user_id", userID);
		        jobj.put("place_id", placeID);
			}
			
			jArr.put(jobj);
		}
		
		return jArr;
	}
	
	/* (non-Javadoc)
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse#onResponseHandler(boolean, org.json.JSONObject)
	 */
	@Override
	void onResponseHandler(boolean isSuccessful, JSONObject json) {
		mServerActionListener.PostAction(isSuccessful, json);
	}

	/* (non-Javadoc)
	 * @see com.image_bs.mercherdiseinvestigation.ServerResponse#onRequestHandler()
	 */
	@Override
	void onRequestHandler() {
	}

}
