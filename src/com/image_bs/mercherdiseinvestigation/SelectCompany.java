/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The Activity class SelectCompany.
 */
public class SelectCompany extends Activity {
	
	/** The choose company. */
	private ListView mChooseCompany;
	
	/** The companies reader. */
	private CompaniesReader mCompaniesReader;
	
	/** The place id. */
	private String mPlaceID;
	
	/** The user id. */
	private Integer mUserID;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list);
        
        TextView tvPlaceName = (TextView) findViewById(R.id.titleName);
        
        Intent i = getIntent();

        this.mPlaceID = i.getStringExtra("placeID");
        this.mUserID = i.getIntExtra("userID", 0);
        
        String placeName = i.getStringExtra("placeName");
        int userID = i.getIntExtra("userID", 0);
        
        tvPlaceName.setText(placeName);
        
        mCompaniesReader = new CompaniesReader(String.valueOf(userID), this
        		, new ServerActionListener()
			        {
        				/**
        				 * Called when http request post action is executed
        				 */
						@Override
						public void PostAction(boolean isSuccessful,
								JSONObject json) {
							if(isSuccessful)
							{
								try {
									int status = json.getInt("status");
									
									/**
									 * if status is not 0 then response needs
									 * to be the desired one. 
									 */
									if( status != 0 )
									{
										ArrayList<String> values = new ArrayList<String>();
										values = mCompaniesReader.getLabels();
										ArrayAdapter<String> adapter = 
												new ArrayAdapter<String>(getApplicationContext(),
												  R.layout.simple_list_item,
												  R.id.simple_list_item_text, values);

										/** Assign adapter to ListView */
								        mChooseCompany = (ListView) findViewById(R.id.simpleListView);
										mChooseCompany.setAdapter(adapter);

								        /** listening to single list item on click */
										mChooseCompany.setOnItemClickListener(new OnItemClickListener() {
								          public void onItemClick(AdapterView<?> parent, View v,
								              int position, long id) {
								        	  
								              /** 
								               * selected item
								               * <p>
								               * Launching new Activity on selecting single
								               *  List Item
								               */
								              Intent i = new Intent(getApplicationContext(),
								            		  SelectCategory.class);
								              /** sending data to new activity */
								              
								              String companyName = 
								            		  ((TextView) v.findViewById(
								            				  R.id.simple_list_item_text))
								            				  .getText().toString();
								              
								              String companyID = mCompaniesReader
								            		  .getCompanyID(position);
								              
								              i.putExtra("companyID", companyID);
								              i.putExtra("companyName", companyName);
								              i.putExtra("placeID", mPlaceID);
								              i.putExtra("userID", mUserID);
								              startActivity(i);
								          }
								        });
									}
									else
									{
										/** finish(); */
									}
								} catch (JSONException e) {
									/* TODO: Auto-generated catch block */
									e.printStackTrace();
								}
							}
							
						}

						@Override
						public void PreExecuteAction() {
							/* TODO Auto-generated method stub */
							
						}
			        	
			        });
    }
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /** Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /** Handle item selection */
        switch (item.getItemId()) {
        case R.id.action_logout:
            logout();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
	/**
	 * Starts login activity and stops current one
	 */
	private void logout() {
		finish();
		LoginStorage.logout(this);
		Intent i = new Intent(this, Login.class);
		this.startActivity(i);
	}
}
