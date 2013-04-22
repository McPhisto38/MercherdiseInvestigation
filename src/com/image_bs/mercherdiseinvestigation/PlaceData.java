package com.image_bs.mercherdiseinvestigation;

//import android.content.Context;

public class PlaceData implements Item {
	private String mPlaceName;
	private String mPlaceAddress;
	private String mLastVisit;
	private String mPlaceCategory;
 
	public PlaceData(String placeCategory, String placeName, String placeAddress, String lastVisit) {
		this.mPlaceName = placeName;
		this.mPlaceAddress = placeAddress;
		this.mLastVisit = lastVisit;
		this.mPlaceCategory = placeCategory;
	}
 
	public String getPlaceName(){
		return mPlaceName;
	}
 
	public String getPlaceAddress(){
		return mPlaceAddress;
	}

	public String getLastVisit(){
		return mLastVisit;
	}
	public String getPlaceCategory(){
		return mPlaceCategory;
	}

	@Override
	public boolean isPlaceCategory() {
		// TODO Auto-generated method stub
		return false;
	}
	

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