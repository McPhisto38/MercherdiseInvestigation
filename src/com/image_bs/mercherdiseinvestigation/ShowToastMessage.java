/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

import android.content.Context;
import android.widget.Toast;

/**
 * The Class ShowToastMessage.
 */
public class ShowToastMessage {
	
	/**
	 * Instantiates a new show toast message and shows it.
	 * 
	 * @param context
	 *            the context
	 * @param message
	 *            the message
	 */
	public ShowToastMessage(Context context, String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}
