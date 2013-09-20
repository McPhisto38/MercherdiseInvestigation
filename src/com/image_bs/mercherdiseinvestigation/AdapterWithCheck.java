/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */

package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Custom ArrayAdapter for ListView with text and CheckBox.
 */

public class AdapterWithCheck extends ArrayAdapter {
    /** The calling activity. */
    private Activity mActivity;
    
    /** The items. */
    private ArrayList<String> mItems;
    
    /** The list layout id. */
    private int mListLayoutId;
    
    /** The TextView id. */
    private int mTextId;
    
    /** The CheckBox id. */
    private int mCheckId;
 
    /**
	 * Constructor with parameters overloading super constructor and
	 * initializing all fields of the class.
	 * 
	 * @param activity
	 *            the calling activity
	 * @param layoutId
	 *            the layout id
	 * @param textId
	 *            the TextView id
	 * @param checkId
	 *            the CheckBox id
	 * @param items
	 *            the items
	 */
    AdapterWithCheck(Activity activity, int layoutId, int textId, int checkId,
    		ArrayList<String> items) {
        super(activity, layoutId, items);
 
        this.mActivity = activity;
        this.mItems = items;
        this.mListLayoutId = layoutId;
        this.mTextId = textId;
        this.mCheckId = checkId;
    }
 
    /* TODO: set CheckBox checked state to the one given in the items.
     */
    /**
	 * Sets the text of the row's TextView
	 * 
	 * @param pos
	 *            the position of the item in the list
	 * @param convertView
	 *            the converted view
	 * @param parent
	 *            instance to the parent ViewGroup
	 * @return the ViewGroup of the row layout
	 */
    public View getView(int pos, View convertView, ViewGroup parent) {
    	/**
    	 * Inflate calling activity's layout
    	 */
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View row = inflater.inflate(mListLayoutId, null);
        TextView label = (TextView)row.findViewById(mTextId);
 
        label.setText(mItems.get(pos));
         
		// TODO: set checked state to CheckBox view
        
		return(row);
    }
    
    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#notifyDataSetChanged()
     */
    @Override
    public void notifyDataSetChanged() {
    	super.notifyDataSetChanged();
    	// TODO: plan changes of the dataset
    }
}