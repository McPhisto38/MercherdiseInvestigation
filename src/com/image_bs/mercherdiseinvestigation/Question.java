/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */
package com.image_bs.mercherdiseinvestigation;

// TODO: Auto-generated Javadoc
/**
 * The Interface Question.
 */
public interface Question {
	
	/**
	 * Checks if is basic question.
	 * 
	 * @return true, if is basic question
	 */
	public boolean isBasicQuestion();

	/**
	 * Sets the current value.
	 * 
	 * @param s
	 *            the new current value
	 */
	public void setCurrentValue(String s);
	
	/**
	 * Gets the current value.
	 * 
	 * @return the current value
	 */
	public String getCurrentValue();
}
