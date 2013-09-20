/*
 ** Copyright (C) 2013 Image Business Solutions
 ** Developed by Nikolai Vasilev - coder.servoper@gmail.com
 **/

package com.image_bs.mercherdiseinvestigation;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Abstract class used to make custom TextWatcher if question answer changes
 * to do some action.
 */
public abstract class MyTextWatcher implements TextWatcher {
	
	/** The question. */
	protected Question question;
	
	/**
	 * Instantiates a new my text watcher. And initializes the question.
	 * 
	 * @param q
	 *            the question
	 */
	MyTextWatcher(Question q){
		super();
		this.question = q;
	}

	/* (non-Javadoc)
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public abstract void afterTextChanged(Editable s);

	/* (non-Javadoc)
	 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void beforeTextChanged(CharSequence s,
			int start, int count, int after) {
		
	}

	/* (non-Javadoc)
	 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void onTextChanged(CharSequence s,
			int start, int before, int count) {
		
	}
}
