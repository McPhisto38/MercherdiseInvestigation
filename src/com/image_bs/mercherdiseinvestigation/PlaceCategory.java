package com.image_bs.mercherdiseinvestigation;

public class PlaceCategory implements Item {

	private String mPlaceCategoryName;

	public PlaceCategory(String placeCategoryName)
	{
		this.mPlaceCategoryName = placeCategoryName;
	}
	
	public String getPlaceCategoryName()
	{
		return this.mPlaceCategoryName.toUpperCase();
	}
	
	@Override
	public boolean isPlaceCategory() {
		return true;
	}

}
