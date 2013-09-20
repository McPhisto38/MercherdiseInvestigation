/*
 ** Copyright (C) 2013 Image Business Solutions
 ** Developed by Nikolai Vasilev - coder.servoper@gmail.com
 **/

package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Activity class for forms showing questions and answers and ability
 * to choose answers. After form is filled user can sent the data manually
 * to the server or onPause if is finished and data is not sent it will be 
 * sent automatically.
 * <p>
 * It's same for product form and category form
 */
public class FormActivity extends Activity{
	
	/** is product. */
	private boolean mIsProduct  = false;
	
	/** The item id. */
	private String mItemID = null;
	
	/** The form reader. */
	private FormReader mFormReader;
	
	/** The layout inflater. */
	private LayoutInflater mLayoutInflater;
	
	/** The main layout. */
	private LinearLayout mMainLayout;
	
	/** The questions list. */
	private ArrayList<Question> mQuestionsList;
	
	/** is form saved. */
	private boolean mIsFormSaved = false;
	
	/** The place id. */
	private String mPlaceID;
	
	/** The user id. */
	private Integer mUserID;
	
	/** Listener for clicking sent button. */
	private OnClickListener mSendDataOnClickListener = new OnClickListener(){

		/**
		 * Sends the data to the server and change mIsSent to false because
		 * it needs to be true only if data is sent and there is positive
		 * response
		 * 
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			mIsFormSaved = false;
			sendDataToServer(v);
		}
		
	};
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.form);
        
        Intent i = getIntent();

        this.mPlaceID = i.getStringExtra("placeID");
        this.mUserID = i.getIntExtra("userID", 0);
        Log.v("FormActivity", Integer.toString(this.mUserID));
        
        String productName = i.getStringExtra("productName");
        String categoryName = i.getStringExtra("categoryName");
        
        /**
         * Checks if productName extra is received from the intent.
         * If it's not then the calling activity is expecting to receive
         * different than product form. For that we use boolean variable
         */
        if( productName != null)
        {
        	this.mIsProduct = true;
        	this.mItemID = i.getStringExtra("productID");
        }
        else
        {
        	this.mIsProduct = false;
        	this.mItemID = i.getStringExtra("categoryID");
        }
		
        /**
         * Start data receiving
         * @see com.image_bs.mercherdiseinvestigation.FormReadera
         */
		mFormReader = new FormReader(this.mItemID, this.mIsProduct, this, mPostActionListener);
        
        TextView tvFormTitle = (TextView) findViewById(R.id.ProductName);
        
        if( this.mIsProduct)
        	tvFormTitle.setText(productName);
        else
        	tvFormTitle.setText(categoryName);        	

		CheckBox check = (CheckBox) findViewById(R.id.ProductsCheck);
		check.setChecked(false);
		check.setEnabled(true);
		
		
		mLayoutInflater = LayoutInflater.from(this);
		mMainLayout = (LinearLayout)findViewById(R.id.QuestionsForm);
		
		RelativeLayout navBar = (RelativeLayout) findViewById(R.id.NavigationBar);
		navBar.setOnClickListener(mSendDataOnClickListener);
    }
    
    /**
	 * Wait's for server response or prerequest and do UI actions on specific
	 * result.
	 */
    private ServerActionListener mPostActionListener = new ServerActionListener(){

    	/**
    	 * It's called after server response is received
    	 * @see com.image_bs.mercherdiseinvestigation.FormReader
    	 * <p>
    	 * Adds the received result to the UI
    	 * 
    	 * @see com.image_bs.mercherdiseinvestigation.ServerActionListener#PostAction(boolean, org.json.JSONObject)
    	 */
		@Override
		public void PostAction(boolean isSuccessful, JSONObject json) {
			if(isSuccessful)
			{
				try {
					int status = json.getInt("status");
					
					/** If status is not 0 adds received form data to the UI */
					if( status != 0 )
					{
						showQuestionsDataInLayout();
					}
					else
					{
						/*
						 * TODO: show UI for the situation when requested
						 * data does not exist
						 */
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else
			{
				/*
				 * TODO: show UI for the situation when request failed.
				 */
			}
		}

		/**
		 * Must be called after server response is non empty question data.
		 * Its function is to update UI with questions and their answer's input
		 * fields. Different answer input field type is shown for the different
		 * questions answer type
		 */
		private void showQuestionsDataInLayout() {
			/** if onResponseHandler is not executed questions data will be empty */
			if(mFormReader.getIsResponseHandlerExecuted())
			{
				mQuestionsList = mFormReader.getQuestionsData();
				
				for(int i = 0 ; i < mQuestionsList.size() ; i++)
				{
					/**
					 *  These are not initialized because we don't know
					 *  question type yet
					 */
					
					RelativeLayout layout;
					TextView tvQuestion;
					
					/** check question type */
					if(mQuestionsList.get(i).isBasicQuestion())
					{
						/**
						 * When we know the type new object need to be made.
						 * It's final because we will use it in the listener
						 */
						final BasicQuestion question = (BasicQuestion) mQuestionsList.get(i);
						
						/** Different question types have different layout */
						if(question.answerType.equalsIgnoreCase("bool"))
						{
							layout = (RelativeLayout) mLayoutInflater.inflate(R.layout.question_boolean, null, false);
	
							tvQuestion = (TextView) layout.findViewById(R.id.BooleanQuestion);
							
							CheckBox check = (CheckBox) layout.findViewById(R.id.BooleanAnswer);
							
							check.setOnCheckedChangeListener(new OnCheckedChangeListener(){
	
								@Override
								public void onCheckedChanged(CompoundButton buttonView,
										boolean isChecked) {
									/**
									 * On every change form need to be marked 
									 * as unsaved because it's instance is
									 * different than the saved one
									 */
									mIsFormSaved = false;
									question.setCurrentValue(Boolean.toString(isChecked));
								}
								
							});
							
							check.setChecked(Boolean.getBoolean(question.getCurrentValue()));
						}
						else 
						{
							layout = (RelativeLayout) mLayoutInflater.inflate(R.layout.question_int, null, false);
	
							tvQuestion = (TextView) layout.findViewById(R.id.IntegerQuestion);
							
							EditText answer = (EditText) layout.findViewById(R.id.IntegerAnswer);
							MyTextWatcher watcher = new MyTextWatcher(question){
								@Override
								public void afterTextChanged(Editable s) {
									this.question.setCurrentValue(s.toString());
									/**
									 * On every change form need to be marked 
									 * as unsaved because it's instance is
									 * different than the saved one
									 */
									mIsFormSaved = false;
								}
							};
							answer.addTextChangedListener(watcher);
							
							answer.setText(question.getCurrentValue());
						}
						
						tvQuestion.setText(question.question);
					}
					else
					{
						EnumQuestion question = (EnumQuestion) mQuestionsList.get(i);
						
						layout = (RelativeLayout) mLayoutInflater.inflate(R.layout.question_enum, null, false);
						
						tvQuestion = (TextView) layout.findViewById(R.id.EnumQuestion);

						/**
						 * Question is set as tag so in the listener to be
						 * able to have full control
						 */
						tvQuestion.setTag(question);
						tvQuestion.setOnClickListener(mMultipleAnswerOnClickListener);
						tvQuestion.setText(question.question);
						
						TextView tvAnswer = (TextView) layout.findViewById(R.id.EnumAnswer);
						tvAnswer.setText(question.answerValues.get(0));
						
						/**
						 * Question is set as tag so in the listener to be
						 * able to have full control
						 */
						tvAnswer.setTag(question);
						tvAnswer.setOnClickListener(mMultipleAnswerOnClickListener);
					}
					
					/** add last inflated layout is added to the main layout */
					mMainLayout.addView(layout);
				}
			}
		}

		@Override
		public void PreExecuteAction() {
			
		}
	};


	/** OnClickListener for enumerable question's answer picking */
	private OnClickListener mMultipleAnswerOnClickListener = new OnClickListener(){
		String[] items;
		EnumQuestion question;
		
		@Override
		public void onClick(View v) {
			/* EnumQuestion object must be given as a tag */
			question = (EnumQuestion) v.getTag();
			/**
			 * Initialize items array with the values from the EnumQuestion
			 * object
			 */
		    items = question.answerValues.toArray(new String[question.answerValues.size()]);
			
			AlertDialog.Builder abChooseAnswer=new AlertDialog.Builder(FormActivity.this);
			
			String dialogTitle = question.question;
			/**
			 * Question can be too long but the dialog title shouldn't
			 * so we need to limit it if it's too long
			 */ 
			if( dialogTitle.length() > 50)
			{
				dialogTitle = dialogTitle.substring(0,
						Math.min(dialogTitle.length(), 50)) + "...";
			}
			
	        abChooseAnswer.setTitle(dialogTitle);
	        abChooseAnswer.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialogInterface, int choice) {
		        	/** if answer is chosen form must be marked as unsaved */
					mIsFormSaved = false;
					
					/** Changing the value of the question object is a must */
		        	question.setCurrentValue(items[choice]);
		        	TextView tvEnumAnswer = (TextView) findViewById(R.id.EnumAnswer);
		        	
		        	/** Updating the view of the answer */
		        	tvEnumAnswer.setText(question.getCurrentValue());
		        	
		        	/** closing the dialog on choose */
		        	dialogInterface.cancel();
		        }
	        });
	        
	        abChooseAnswer.show();
		}
		
	};

   /**
    * Overloads Activity's onPause and if activity is finishing and data is not
    * saved or sent dose it!
    * 
    * @see android.app.Activity#onPause()
    */
   protected void onPause(){
        super.onPause();
        
        /**
         * If activity is finishing and form is not saved we need to send the
         * data to the server
         */
        if(this.isFinishing() && !this.mIsFormSaved)
        {
        	/**
        	 * Changing the isFormSaved to true and running sendData(Viev v)
        	 */
        	this.mIsFormSaved  = true;
        	sendDataToServer(new View(this));
        }
    }
    
   /**
	 * It's function is to send questions data to the server.
	 * 
	 * @param v
	 *            the v
	 */
    private void sendDataToServer(View v)
    {
    	/** check if device is connected to network */
    	if(ConnectionCheck.isOnline(this))
    	{
    		/** used as refresh button */
	    	final ImageView syncButton = (ImageView) findViewById(R.id.sync);
	    	/** on clicked rotation animation is activated */
	    	syncButton.startAnimation( 
	    		    AnimationUtils.loadAnimation(this, R.anim.rotate_sync) );
	    	
	    	/**
	    	 * Starts send request for selected place and user. Also overrides
	    	 * callback function for ui update
	    	 */
	    	new SaveData(mQuestionsList, mPlaceID, mUserID, this, new ServerActionListener(){

	    		/**
	    		 * 
	    		 * Callback function updating views and layout
	    		 *
	    		 * @see com.image_bs.mercherdiseinvestigation.ServerActionListener
	    		 * #PostAction(boolean, org.json.JSONObject)
	    		 */
				@Override
				public void PostAction(boolean isSuccessful, JSONObject json) {
					/** stops the animation of the sync button */
					syncButton.clearAnimation();
					
					/** check is response is received */
					if( isSuccessful )
					{
						/** try json code */
						try {
							/**  gets result status */
							int status = json.getInt("status");
				
							/** if status is not 0 then there is something received for handling */
							if( status != 0)
							{
								/**
								 * if status is not 0 then data is marked as
								 * saved in server's database
								 */
								mIsFormSaved = true;

								new ShowToastMessage(getApplicationContext(),
										getApplicationContext().getResources()
										.getString(R.string.saved));
							}
							else
							{
								/**  if status is 0, json need to contain error message */
								new ShowToastMessage(getApplicationContext(),
										json.getString("message"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
							/**  on json exception we show empty_json toast */
							new ShowToastMessage(getApplicationContext(),
									getApplicationContext().getResources()
									.getString(R.string.empty_json));
						}
					}
					else
					{
						/**  request is not successful so message is shown */
						new ShowToastMessage(getApplicationContext(),
								getApplicationContext().getResources()
								.getString(R.string.cant_send));
					}
				}

				/**  action called before response start */
				@Override
				public void PreExecuteAction() {
				}
	    		
	    	});
    	}
    	else
    	{
    		/**  not connected toast message */
    		new ShowToastMessage(this, this.getResources().getString(R.string.not_connected));
    	}
    }
	
    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 * 
	 * @param menu
	 *            the menu
	 * @return true, if successful
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**  Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 * 
	 * @param item
	 *            the item
	 * @return true, if successful
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**  Handle item selection */
        switch (item.getItemId()) {
        case R.id.action_logout:
        	/**  if action logout is selected run logout method */
            logout();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    /**
	 * Logout the user and starting loginactivity.
	 */
	private void logout() {
		finish();
		/**  calls LoginStorage static method logout */
		LoginStorage.logout(this);
		/**  starts the loginactivity */
		Intent i = new Intent(this, Login.class);
		this.startActivity(i);
	}
}
