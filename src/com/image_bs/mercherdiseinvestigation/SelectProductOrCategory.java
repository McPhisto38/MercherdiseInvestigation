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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
 
/**
 * The Activty Class SelectProductOrCategory.
 */
public class SelectProductOrCategory extends Activity {
	
	/** The choose product list view. */
	private ListView mLvChooseProduct;
	/** The company id. */
	private String mCompanyID;
	
	/** The category name. */
	private String mCategoryName;
	
	/** The category id. */
	private String mCategoryID;
	
	/** The products reader. */
	private ProductsReader mProductsReader;
	
	/** The names list. */
	private ArrayList<String> mNamesList;
	
	/** The this activity. */
	private Activity mThisActivity;
	
	/** The place id. */
	private String mPlaceID;
	
	/** The user id. */
	private Integer mUserID;
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.simple_list);
        
        this.mThisActivity = this;
        
        TextView tv = (TextView) findViewById(R.id.titleName);

        Intent i = getIntent();
        this.mPlaceID = i.getStringExtra("placeID");
        this.mUserID = i.getIntExtra("userID", 0);
        
        String companyName = i.getStringExtra("companyName");
        tv.setText(companyName);

        

        String categoryName = i.getStringExtra("categoryName");
        this.mCategoryName = categoryName;
        mNamesList = new ArrayList<String>();
        mNamesList.add(categoryName);

        String companyID = i.getStringExtra("companyID");
        this.mCompanyID = companyID;
        String categoryID = i.getStringExtra("categoryID");
        this.mCategoryID = categoryID;
        
		mProductsReader = new ProductsReader(companyID, categoryID, this, mServerActionListener);
        
    }

    /** The server action listener. */
    private ServerActionListener mServerActionListener = new ServerActionListener(){

    	/**
    	 * Called when http request post action is executed.
    	 * 
    	 * @param isSuccessful
    	 * 			is http request successful
    	 * @param json
    	 * 			http response as JSONObject
    	 */
		@Override
		public void PostAction(boolean isSuccessful, JSONObject json) {
			if(isSuccessful)
			{
				try {
					int status = json.getInt("status");
					
					/**
					 * if is not 0 then response need to be the desired one
					 */
					if( status != 0 )
					{
				        mNamesList.addAll(1, mProductsReader.getLabels());
				        
				        mLvChooseProduct = (ListView) findViewById(R.id.simpleListView);
						mLvChooseProduct.setAdapter(new AdapterWithCheck(mThisActivity,
								R.layout.cb_list_item, R.id.textToArrow, R.id.check, mNamesList ));
						
				        /** listening to single list item on click */
						mLvChooseProduct.setOnItemClickListener(new OnItemClickListener() {
				          public void onItemClick(AdapterView<?> parent, View v,
				              int position, long id) {
			
				        	  CheckBox check = (CheckBox) v.findViewById(R.id.check);
				        	  check.setChecked(true);
				        	  check.setEnabled(false);

				              Intent i = new Intent(getApplicationContext(), FormActivity.class);
				              i.putExtra("companyID", mCompanyID);
				              
				              /**
				               * the first item is for the category form
				               */
				        	  if( position == 0)
				        	  {
					              i.putExtra("categoryID", mCategoryID);
					              i.putExtra("categoryName", mCategoryName);
					              i.putExtra("placeID", mPlaceID);
					              i.putExtra("userID", mUserID);
					              startActivity(i);
				        	  }
				        	  else
				        	  {
					              String productName = mProductsReader.getProductName(position-1);
					              i.putExtra("productName", productName);
					              String productID = mProductsReader.getProductID(position-1);
					              i.putExtra("productID", productID);
					              i.putExtra("placeID", mPlaceID);
					              i.putExtra("userID", mUserID);
					              startActivity(i);
				        	  }
				 
				          }
				        });
					}
					else
					{
						/** finish(); */
					}
				} catch (JSONException e) {
					/** TODO Auto-generated catch block */
					e.printStackTrace();
				}
			}
		}

		@Override
		public void PreExecuteAction() {
			/* TODO Auto-generated method stub */
			
		}};
	
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