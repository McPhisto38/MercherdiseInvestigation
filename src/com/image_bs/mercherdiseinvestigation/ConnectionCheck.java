/*
 ** Copyright (C) 2013 Image Business Solutions
 ** Developed by Nikolai Vasilev - coder.servoper@gmail.com
 **/

package com.image_bs.mercherdiseinvestigation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * For checking network connection.
 */
public class ConnectionCheck {
	/**
	 * Checking if data service or wifi adapter is connected to some network
	 * <p>
	 * IMPORTANT: Doesn't check if there is bandwidth. It means if the
	 * device is connected to local network it will be true
	 * 
	 * @param context
	 *            the context
	 * @return true if it's connected to a network in other case false
	 */
	public static boolean isOnline(Context context) {
	    ConnectivityManager cm =
	        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	

	/*
	 * TODO: make url check to the login page or page passed as parameter
	 * 
	 * @see com.image_bs.mercherdiseinvestigation.SplashScreen.CheckServerTask
	 */
}
