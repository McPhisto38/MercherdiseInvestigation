/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * The Activity NoServerConnection need to be opened where there is network
 * connection but there is no connection with the application's server.
 */
public class NoServerConnection extends Activity {

	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_server_connection);
        
        Button btClose = (Button) findViewById(R.id.btCloseServerDown);
        
        btClose.setOnClickListener( new OnClickListener()
        {
			@Override
			public void onClick(View v) {
				closeApp();
			}
        	
        });
    }
    
	/**
	 * Close the application.
	 */
	private void closeApp()
	{
		finish();
		System.exit(0);
	}

	/**
	 * The application is closed.
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		closeApp();
    }
}
