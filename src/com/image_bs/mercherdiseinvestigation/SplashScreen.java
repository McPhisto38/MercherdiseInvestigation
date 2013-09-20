/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
 
/**
 * The Activity Class SplashScreen.
 */
public class SplashScreen extends Activity {
	
	/** The splash screen life in seconds. */
	private final short SPLASH_SCREEN_LIFE_SECONDS = 2;
	
    /** The starting time. */
    private long mStartingTime;
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.splash_screen);
        
        /** set staqrting time to system's time */
        mStartingTime = System.nanoTime();
        
        new CheckServerTask().execute();
    }
    
    /**
	 * The AsyncTask Class CheckServerTask.
	 */
    private class CheckServerTask extends AsyncTask<Void,Void, Void>{
    	
	    /** The is there network. */
	    private boolean isOK = false;
    	
		/**
		 * Makes http connection to the login url to check if there is
		 * connection to the server or no.
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Void... params) {
			try {
			    URL url = new URL(getString(R.string.url_login));
			    HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
			    urlcon.connect();
		    	Log.v("gay", "i'm gay");
			    if (urlcon.getResponseCode() == HttpStatus.SC_OK) {
			    	Log.v("gay", "haaaay");
			    	isOK = true;
			    }else{
			    	Log.v("gay", "not gey :(");
			    }
	
			} catch (MalformedURLException e1) {
			            e1.printStackTrace();
			} catch (IOException e) {
			            e.printStackTrace();
			}
			
			return null;
		}
		
		/**
		 * On post execute if task is initialized then start login task.
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override 
		protected void onPostExecute(Void params)
		{
			if(isOK)
			{
		        LoginStorage loginStorage = new LoginStorage(getApplicationContext());
		
				HashMap<String, String> myParam = new HashMap<String, String>();
				/** add input to param object */
				myParam.put(LoginStorage.EMAIL_TAG, loginStorage.getEmail());
				myParam.put(LoginStorage.PASS_TAG, loginStorage.getPassword());
				
				/** starting the lgin task on the splash screen */
		        new SplashScreenLogin(getApplicationContext(), myParam, true, loginStorage);
			}
			else
			{
				/** 
				 * if is not initialized the network then get current time
				 * and calculate what time passed between current time and starting time
				 */
				long time = System.nanoTime() - mStartingTime;
				/** convert to seconds */
				double timeInSeconds = time/1e9;

				/**
				 * if execution time is less then splash screen life time
				 * then run thread to slow the starting of the next activity
				 */
				if( timeInSeconds < SPLASH_SCREEN_LIFE_SECONDS)
				{
				
					new Handler().postDelayed(new Runnable()
					{
					    @Override
					    public void run()
					    {
				    		SplashScreen.this.startActivity(
				    				new Intent(SplashScreen.this, NoServerConnection.class));
					    	finish();
					    }
					}, (long) ((SPLASH_SCREEN_LIFE_SECONDS - timeInSeconds ) * 1000));
				}
				else
				{
		    		SplashScreen.this.startActivity(
		    				new Intent(SplashScreen.this, NoServerConnection.class));
					finish();
				}
				
			}
		}
    }
    
    /**
	 * The LoginRequest Class SplashScreenLogin.
	 */
    private class SplashScreenLogin extends LoginRequest{
		
		/** The to start login. */
		private boolean mStartLogin;
		
		/** The login storage. */
		private LoginStorage mLoginStorage;

		/**
		 * Instantiates a new splash screen login and initializes the login
		 * storage.
		 * 
		 * @param context
		 *            the context
		 * @param httpParams
		 *            the http params
		 * @param toRemember
		 *            the to remember
		 * @param loginStorage
		 *            the login storage
		 */
		SplashScreenLogin(Context context, HashMap<String, String> httpParams,
				boolean toRemember, LoginStorage loginStorage) {
			super(context, httpParams, toRemember);
			
			this.mLoginStorage  = loginStorage;
		}
		
		/* (non-Javadoc)
		 * @see com.image_bs.mercherdiseinvestigation.LoginRequest#onRequestHandler()
		 */
		@Override
		void onRequestHandler() {
		}

		/**
		 * Executes on post execute of the task. Checks if desired data is
		 * received.
		 * 
		 * @param isSuccessful
		 *            the is http request successful
		 * @param json
		 *            the json response
		 * @see com.image_bs.mercherdiseinvestigation
		 * .ServerResponse#onResponseHandler(boolean, org.json.JSONObject)
		 */
		@Override
		void onResponseHandler(boolean isSuccessful, JSONObject json) {
			if( isSuccessful )
			{
				try {
					/** reds the response status */
					int status = json.getInt("status");
					
					/** if status is not null then response is received without errors */
					if(status != 0)
					{
						this.mStartLogin = false;
						endSplashScreen();
					}
					else
					{
						this.mStartLogin = true;
						endSplashScreen();
					}
					
				} catch (JSONException e) {
					this.mStartLogin = true;
					endSplashScreen();
					e.printStackTrace();
				}
			}
			else
			{
				this.mStartLogin = true;
				endSplashScreen();
			}
		}
    	
		/**
		 * End splash screen.
		 */
		private void endSplashScreen()
		{
			/** 
			 * if is not initialized the network then get current time
			 * and calculate what time passed between current time and starting time
			 */
			long time = System.nanoTime() - mStartingTime;
			/** convert to seconds */
			double timeInSeconds = time/1e9;

			/**
			 * if execution time is less then splash screen life time
			 * then run thread to slow the starting of the next activity
			 */
			if( timeInSeconds < SPLASH_SCREEN_LIFE_SECONDS)
			{
				new Handler().postDelayed(new Runnable()
				{
				    @Override
				    public void run()
				    {
				    	startNextActivity();
				    }
				}, (long) ((SPLASH_SCREEN_LIFE_SECONDS - timeInSeconds ) * 1000));
			}
			else
			{
		    	startNextActivity();
			}
		}


		/**
		 * Starting the next activity
		 */
		private void startNextActivity() {
			if(mStartLogin)
	    		SplashScreen.this.startActivity(new Intent(SplashScreen.this,
	    				Login.class));
	    	else
	    	{
				Intent intent = new Intent(SplashScreen.this, SelectPlace.class);
		    	intent.putExtra("userID", mLoginStorage.getUserId());
		    	/**
		    	 * Clears all active child activities of the 
		    	 * starting one.
		    	 */
			    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    SplashScreen.this.startActivity(intent);
	    	}
	    	
	    	finish();
		}
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause()
    {
    	super.onPause();
    	if(!isFinishing())
    		finish();                          
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
    	finish();
    	System.exit(0);
    }
}