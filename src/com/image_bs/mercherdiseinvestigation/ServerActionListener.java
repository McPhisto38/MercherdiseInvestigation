/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

import org.json.JSONObject;

/**
 * The listener interface for receiving serverAction events. The class that is
 * interested in processing a serverAction event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addServerActionListener<code> method. When
 * the serverAction event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see ServerActionEvent
 */
public interface ServerActionListener {

	/**
	 * Post action.
	 * 
	 * @param isSuccessful
	 *            the is http request successful
	 * @param json
	 *            the json
	 */
	void PostAction(boolean isSuccessful, JSONObject json);   
	
	/**
	 * Pre execute action.
	 */
	void PreExecuteAction();  
}
