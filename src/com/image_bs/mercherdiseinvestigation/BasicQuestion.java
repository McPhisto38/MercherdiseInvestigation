/*
 * Copyright (C) 2013 Image Business Solutions
 * Developed by Nikolai Vasilev - coder.servoper@gmail.com
 */

package com.image_bs.mercherdiseinvestigation;

/**
 * The Class BasicQuestion is object for questions with answers that are not
 * enumerable type.
 * <p>
 * BasicQuestion is question with basic answer type as: int, float, percent, boolean...
 */
public class BasicQuestion implements Question {
	/*
	 * Why this fields are public? For faster dalvik work!
	 */
	/** The question. */
	public String question;
	
	/** The answer type. */
	public String answerType;
	
	/** The question id. */
	public String questionId;
	
	/** The relation id. */
	public String relID;
	
	/** The answer type id. */
	public String answerTypeID;
	
	/*
	 * Why then this field is private? Because we override the set method from
	 * the interface that class extends
	 */
	/** The current answer value. */
	private String mCurrentAnswerValue = null;
	
	/*
	 * Because of no set methods the constructor is fully setting the 
	 */
	/**
	 * Instantiates a new basic question.
	 * 
	 * @param questionId
	 *            the question id
	 * @param question
	 *            the question
	 * @param answerType
	 *            the answer type
	 * @param relID
	 *            the relation id
	 * @param answerTypeID
	 *            the type id
	 */
	public BasicQuestion(String questionId, String question, String answerType,
			String relID, String answerTypeID) {
		this.questionId = questionId;
		this.question = question;
		this.answerType = answerType;
		this.relID = relID;
		this.answerTypeID = answerTypeID;
	}

	/**
	 * @see com.image_bs.mercherdiseinvestigation.Question#isBasicQuestion()
	 * 
	 * @return true if is basic question
	 */
	@Override
	public boolean isBasicQuestion() {
		return true;
	}

	/**
	 * @see com.image_bs.mercherdiseinvestigation.Question#setCurrentValue(java.lang.String)
	 */
	@Override
	public void setCurrentValue(String s) {
		this.mCurrentAnswerValue = s;
	}

	/**
	 * @see com.image_bs.mercherdiseinvestigation.Question#getCurrentValue()
	 */
	@Override
	public String getCurrentValue() {
		return this.mCurrentAnswerValue;
	}
}
