/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

// TODO: Auto-generated Javadoc
//import android.content.Context;

/**
 * The Class PlaceData.
 */
public class PlaceData implements PlaceItem {
	
	/** The place name. */
	private String mPlaceName;
	
	/** The place address. */
	private String mPlaceAddress;
	
	/** The last visit. */
	private String mLastVisit;
	
	/** The place type. */
	private String mPlaceType;
	
	/** The place id. */
	public String placeID;
 
	/**
	 * Instantiates a new place data and initializes class fields.
	 * 
	 * @param placeType
	 *            the place type
	 * @param placeName
	 *            the place name
	 * @param placeAddress
	 *            the place address
	 * @param lastVisit
	 *            the last visit
	 * @param placeID
	 *            the place id
	 */
	public PlaceData(String placeType, String placeName, String placeAddress, String lastVisit, String placeID) {
		this.placeID = placeID;
		this.mPlaceName = placeName;
		this.mPlaceAddress = placeAddress;
		this.mLastVisit = lastVisit;
		this.mPlaceType = placeType;
	}
 
	/**
	 * Gets the place name.
	 * 
	 * @return the place name
	 */
	public String getPlaceName(){
		return mPlaceName;
	}
 
	/**
	 * Gets the place address.
	 * 
	 * @return the place address
	 */
	public String getPlaceAddress(){
		return mPlaceAddress;
	}

	/**
	 * Gets the last visit.
	 * 
	 * @return the last visit
	 */
	public String getLastVisit(){
		return mLastVisit;
	}
	
	/**
	 * Gets the place category.
	 * 
	 * @return the place category
	 */
	public String getPlaceCategory(){
		return mPlaceType;
	}

	/* (non-Javadoc)
	 * @see com.image_bs.mercherdiseinvestigation.PlaceItem#isPlaceCategory()
	 */
	@Override
	public boolean isPlaceCategory() {
		// TODO Auto-generated method stub
		return false;
	}
	

	 /**
	  * Gets string containg both place address and names so we can filter
	  * later to both this parameters
 	 * @see java.lang.Object#toString()
 	 */
 	@Override
	 public String toString() {
	  return  this.mPlaceAddress + " " + this.mPlaceName;
	 }
 
	/**
	public int getImageResId(Context context){
		return context.getResources().getIdentifier("com.justcallmebrian.stateflower:drawable/" + mLastVisit, null, null);
	}
	*/
}