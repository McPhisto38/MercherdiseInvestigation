/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The Activity class SelectPlace.
 */
public class SelectPlace extends Activity{
	 
	/** The data adapter. */
	PlaceListAdapter mDataAdapter = null;
	
	/** The user id. */
	private Integer mUserID;
	
	/** The place list. */
	private ArrayList<PlaceItem> mPlaceList = null;
	 
	 /* (non-Javadoc)
 	 * @see android.app.Activity#onCreate(android.os.Bundle)
 	 */
 	@Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.place_list);
      Intent i = getIntent();
      
      mUserID = i.getIntExtra("userID", 0);
	 }

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		new PlaceReader(this, null , new ServerActionListener()
		{
			@Override
			public void PostAction(boolean isSuccessful, JSONObject json) 
			{
				try {
					JSONArray jArr = json.getJSONArray("result");
					
					JSONObject jobj;
					String placeClass = null;
					mPlaceList = new ArrayList<PlaceItem>();
					
					LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

					/**
					 * device location is get only by the network at the
					 * moment
					 * 
					 *  TODO: if GPS is started use GPS if enough
					 *  satelits are found
					 */
					Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					
					for(int i = 0 ; i < jArr.length() ; i++)
					{
						jobj = jArr.getJSONObject(i);
						/**
						 * if it's not from the same place class add place
						 * category object as next item
						 */
						if( !jobj.getString("place_class").equalsIgnoreCase(placeClass) )
						{
							placeClass = jobj.getString("place_class");
							mPlaceList.add( new PlaceCategory(placeClass) );
						}

						Location dest = new Location("");
						double latitude = jobj.getDouble("lat");
						dest.setLatitude(latitude);
						double longitude = jobj.getDouble("lng");
						dest.setLongitude(longitude);
						
						/** calculate the distance to the place */
						float distance = location.distanceTo(dest);
						
						String distanceString = "";
						
						/** 
						 * if distance is more than 1000 then add km prefix
						 * if not meter prefix need to be added
						 */
						if( distance > 1000)
						{
							distance = distance/1000;
							distanceString = String.format("%.01f", distance) + " km";
						}
						else
						{
							int dis = (int) distance;
							distanceString = Integer.toString(dis) + " m";
						}
						
						/**
						 * add placedata object as new item
						 */
						mPlaceList.add(new PlaceData(placeClass, jobj.getString("name"),
								jobj.getString("address"), distanceString, jobj.getString("id")));
					}
	            	
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				if(mPlaceList != null)
				{
					/**create an ArrayAdaptar from the String Array */
					  mDataAdapter = new PlaceListAdapter(getApplicationContext(),
					    R.layout.place_item, mPlaceList);
					  ListView listView = (ListView) findViewById(R.id.PlaceList);
					  /** Assign adapter to ListView */
					  listView.setAdapter(mDataAdapter);
					 
					  /**enables filtering for the contents of the given ListView */
					  listView.setTextFilterEnabled(true);

				      listView.setOnItemClickListener(new OnItemClickListener() {
					          public void onItemClick(AdapterView<?> parent, View v,
					              int position, long id) {
					        	  
					              Intent i = new Intent(getApplicationContext(), SelectCompany.class);
					              String placeName = ((TextView) v.findViewById(R.id.tvPlaceName)).getText().toString();
					              i.putExtra("placeName", placeName);
					              i.putExtra("userID", mUserID);
					              String placeID = ((PlaceData)mPlaceList.get(position)).placeID;
					              i.putExtra("placeID", placeID);
					              startActivity(i);
					 
					          }
					        });
					 
					  EditText myFilter = (EditText) findViewById(R.id.searchPlace);
					  myFilter.addTextChangedListener(new TextWatcher() {
					 
					  public void afterTextChanged(Editable s) {
					  }
					 
					  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					  }
					 
					  public void onTextChanged(CharSequence s, int start, int before, int count) {
						  /** searchbox filtering */
					   mDataAdapter.getFilter().filter(s.toString());
					  }
					  });
				}
			}

			@Override
			public void PreExecuteAction() {
				
			}
			
		});
	}
	
	/**
	 * When back button is pressed show application closing dialog
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.on_back_button_title);
        builder.setMessage(R.string.on_back_button_message);
        builder.setPositiveButton(R.string.yes, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                /** garante app closing */
                System.exit(0);
            }
        });
        builder.setNegativeButton(R.string.no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
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
