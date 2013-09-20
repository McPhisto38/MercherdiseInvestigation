/*
 ** Copyright (C) 2013 Image Business Solutions
 ** Developed by Nikolai Vasilev - coder.servoper@gmail.com
 **/

package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

/**
 * Used to specify question of the merchandiser form.
 * EnmQuestion is question with enumerable answer.
 */
public class EnumQuestion implements Question{

	/*
	 * public is used instead of get and set because work faster for
	 * dalvik.
	 */
	/** The question */
	public String question;
	
	/** The answer type. */
	public String answerType;
	
	/** The question id. */
	public String questionId;
	
	/** The relation id. */
	public String relID;
	
	/** The answer values. */
	public ArrayList<String> answerValues;
	
	/** The type id. */
	public String answerTypeID;
	
	/** The m current value. */
	private String mCurrentAnswerValue = null;
	
	/**
	 * * Fields are set by the constructor.
	 * 
	 * @param questionId
	 *            the question id
	 * @param question
	 *            the question
	 * @param answerType
	 *            the answer type
	 * @param relID
	 *            the relation id
	 * @param answerValues
	 *            the answer values
	 * @param answerTypeID
	 *            the answer type id
	 */
	public EnumQuestion(String questionId, String question, String answerType, String relID, ArrayList<String> answerValues, String answerTypeID) {
		this.questionId = questionId;
		this.question = question;
		this.answerType = answerType;
		this.answerValues = answerValues;
		this.relID = relID;
		this.answerTypeID = answerTypeID;
	}

	/**
	 * @return true, if is basic question
	 * 
	 * @see com.image_bs.mercherdiseinvestigation.Question#isBasicQuestion()
	 */
	@Override
	public boolean isBasicQuestion() {
		return false;
	}

	/**
	 * @param value
	 *            the new current value
	 *            
	 * @see com.image_bs.mercherdiseinvestigation.Question#setCurrentValue(java.lang.String)
	 */
	@Override
	public void setCurrentValue(String value) {
		this.mCurrentAnswerValue = value;
	}

	/**
	 * * (non-Javadoc) * @see
	 * com.image_bs.mercherdiseinvestigation.Question#getCurrentValue()
	 * 
	 * @return the current value
	 */
	@Override
	public String getCurrentValue() {
		return this.mCurrentAnswerValue;
	}
}
