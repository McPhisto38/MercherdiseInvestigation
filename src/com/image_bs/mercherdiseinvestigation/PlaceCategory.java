/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

import java.util.Locale;

/**
 * The Class PlaceCategory.
 */
public class PlaceCategory implements PlaceItem {

	/** The place category name. */
	private String mPlaceCategoryName;

	/**
	 * Instantiates a new place category and initializes place category name.
	 * 
	 * @param placeCategoryName
	 *            the place category name
	 */
	public PlaceCategory(String placeCategoryName)
	{
		this.mPlaceCategoryName = placeCategoryName;
	}
	
	/**
	 * Gets the place category name.
	 * 
	 * @return the place category name
	 */
	public String getPlaceCategoryName()
	{
		return this.mPlaceCategoryName.toUpperCase();
	}
	
	/**
	 * @see com.image_bs.mercherdiseinvestigation.PlaceItem#isPlaceCategory()
	 */
	@Override
	public boolean isPlaceCategory() {
		return true;
	}

}
