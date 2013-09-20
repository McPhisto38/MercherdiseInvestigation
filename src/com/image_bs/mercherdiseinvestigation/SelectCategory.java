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
 * The Activity class SelectCategory.
 */
public class SelectCategory extends Activity {

	/** The company name. */
	private String mCompanyName;
	
	/** The company id. */
	private String mCompanyID;
	
	/** The categories reader. */
	private CategoriesReader mCategoriesReader;
	
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

        
        TextView tvCompanyName = (TextView) findViewById(R.id.titleName);
        Intent i = getIntent();
        this.mPlaceID = i.getStringExtra("placeID");
        this.mUserID = i.getIntExtra("userID", 0);

        mCompanyName = i.getStringExtra("companyName");
        tvCompanyName.setText(mCompanyName);
        
        mCompanyID = i.getStringExtra("companyID");
        
        /** start reading categories */
		mCategoriesReader = new CategoriesReader(mCompanyID, this,
    			mServerActionListener);
    }
    
    // TODO: can be optimized
    /** The server action listener. */
    private ServerActionListener mServerActionListener = new ServerActionListener(){

    	/*
    	 * (non-Javadoc)
    	 * @see com.image_bs.mercherdiseinvestigation
    	 * .ServerActionListener#PostAction(boolean, org.json.JSONObject)
    	 */
		@Override
		public void PostAction(boolean isSuccessful, JSONObject json) {

			if(isSuccessful)
			{
				try {
					int status = json.getInt("status");
					if( status != 0 )
					{
						/**
						 * if status is not 0 result need to be the desired
						 * one so we can start updating the listview.
						 */
			        	loadCategoriesToListView();
					}
					else
					{
						//finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Updating the ListView with the categories data
		 */
		private void loadCategoriesToListView() {
			ArrayList<String> values = mCategoriesReader.getLabels();
			
			ArrayAdapter<String> adapter = 
					new ArrayAdapter<String>(getApplicationContext(),
					R.layout.simple_list_item, R.id.simple_list_item_text, values);

			ListView chooseProduct = (ListView) findViewById(R.id.simpleListView);
			chooseProduct.setAdapter(adapter);

			chooseProduct.setOnItemClickListener(new OnItemClickListener() {
			  public void onItemClick(AdapterView<?> parent, View v,
			      int position, long id) {
				  
			      Intent i = new Intent(getApplicationContext(), SelectProductOrCategory.class);
			      
			      i.putExtra("companyName", mCompanyName);
			      i.putExtra("companyID", mCompanyID);
			      
			      String categoryName = mCategoriesReader.getCategoryName(position);
			      i.putExtra("categoryName", categoryName);
			      
			      String categoryID = mCategoriesReader.getCategoryID(position);
			      i.putExtra("categoryID", categoryID);

	              i.putExtra("placeID", mPlaceID);
	              i.putExtra("userID", mUserID);
	              
			      startActivity(i);
 
			  }
			});
		}

		@Override
		public void PreExecuteAction() {
			// TODO Auto-generated method stub
			
		}};
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
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