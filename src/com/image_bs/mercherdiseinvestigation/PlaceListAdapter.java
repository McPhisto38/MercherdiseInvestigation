/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

/**
	 * The Class PlaceListAdapter.
	 */
 public class PlaceListAdapter extends ArrayAdapter<PlaceItem> {
 
  /** The original list. */
  private ArrayList<PlaceItem> mOriginalList;
  
  /** The view place list. */
  private ArrayList<PlaceItem> mViewPlaceList;
  
  /** The place filter. */
  private PlaceFilter mPlaceFilter;
  
  /** The context. */
  private Context mContext;
 
  /**
	 * Instantiates a new place list adapter.
	 * 
	 * @param context
	 *            the context
	 * @param textViewResourceId
	 *            the text view resource id
	 * @param countryList
	 *            the country list
	 */
  public PlaceListAdapter(Context context, int textViewResourceId, 
    ArrayList<PlaceItem> countryList) {
   super(context, textViewResourceId, countryList);
   this.mContext = context;
   this.mViewPlaceList = new ArrayList<PlaceItem>();
   this.mViewPlaceList.addAll(countryList);
   this.mOriginalList = new ArrayList<PlaceItem>();
   this.mOriginalList.addAll(countryList);
  }
 
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getFilter()
	 */
	@Override
	public Filter getFilter() 
	{
		if (mPlaceFilter == null){
			mPlaceFilter  = new PlaceFilter();
		}
		return mPlaceFilter;
	}
 
  	/* (non-Javadoc)
	   * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	   */
	  @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		LayoutInflater vi = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		/**
		 * In place list there is category view and place information view
		 * so we need to check what the current item is
		 */
		if(mViewPlaceList.get(position).isPlaceCategory())
		{
			v = vi.inflate(R.layout.place_category_item, null);
			
			/**
			 * if it's category the item should not be clickable
			 */
			v.setOnClickListener(null);
			v.setOnLongClickListener(null);
			v.setLongClickable(false);
			   
			TextView categoryName = (TextView) v.findViewById(R.id.placeCategoryName);
			
			PlaceCategory category = (PlaceCategory) mViewPlaceList.get(position);
			categoryName.setText(category.getPlaceCategoryName());
		}
		else
		{
			v = vi.inflate(R.layout.place_item, null);
			 
			TextView placeName = (TextView) v.findViewById(R.id.tvPlaceName);
			TextView placeAddress = (TextView) v.findViewById(R.id.tvPlaceAddress);
			TextView placeLastVisit = (TextView) v.findViewById(R.id.tvLastVisit);
			
			PlaceData place = (PlaceData) mViewPlaceList.get(position);
			placeName.setText(place.getPlaceName());
			placeAddress.setText(place.getPlaceAddress());
			placeLastVisit.setText(place.getLastVisit());
		}
			  
		return v;
	}
 
  /**
	 * The Class PlaceFilter for filtering list items.
	 */
  private class PlaceFilter extends Filter
  {
 
   /* (non-Javadoc)
    * @see android.widget.Filter#performFiltering(java.lang.CharSequence)
    */
   @Override
   protected FilterResults performFiltering(CharSequence constraint) {
 
    constraint = constraint.toString().toLowerCase(Locale.getDefault());
    FilterResults result = new FilterResults();
    
    /** checks if there is some filtering constraint */
    if(constraint != null && constraint.toString().length() > 0)
    {
    	/** ArrayList which will contain filtered items */
	    ArrayList<PlaceData> filteredItems = new ArrayList<PlaceData>();
	 
	    for(int i = 0, l = mOriginalList.size(); i < l; i++)
	    {
	    	/** we don't want to filter categories */
	    	if(!mOriginalList.get(i).isPlaceCategory())
	    	{
				PlaceData place = (PlaceData) mOriginalList.get(i);
				if(place.toString().toLowerCase(Locale.getDefault()).contains(constraint))
					filteredItems.add(place);
	    	}
	    }
	    /**
	     * adding the filtered items to the actual result
	     */
	    result.count = filteredItems.size();
	    result.values = filteredItems;
    }
    else
    {
    	/** if there is no constraint return the original items to the list */
     synchronized(this)
     {
      result.values = mOriginalList;
      result.count = mOriginalList.size();
     }
    }
    return result;
   }
 
   /* (non-Javadoc)
    * @see android.widget.Filter#publishResults(java.lang.CharSequence, android.widget.Filter.FilterResults)
    */
   @SuppressWarnings("unchecked")
   @Override
   protected void publishResults(CharSequence constraint, 
     FilterResults results) {
 
	   /** refreshing the views */
    mViewPlaceList = (ArrayList<PlaceItem>)results.values;
    notifyDataSetChanged();
    clear();
    for(int i = 0, l = mViewPlaceList.size(); i < l; i++)
     add(mViewPlaceList.get(i));
    notifyDataSetInvalidated();
   }
  }
 
 
 }