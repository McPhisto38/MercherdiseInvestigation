/*
 ** Copyright (C) 2013 Image Business Solutions
 ** Developed by Nikolai Vasilev - coder.servoper@gmail.com
 **/

package com.image_bs.mercherdiseinvestigation;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

/**
 * Activity for user authentication
 * Login is with email and pass. Pass field can be made visible.
 * You can set remember or not remember. When remember is checked this activity
 * will be passed in the next app launching if login information is still
 * correct
 */
public class Login extends Activity {
	
	/** The user email. */
	private EditText mUserEmail;
	
	/** The pass. */
	private EditText mUserPass;
	
	/** The password visibility CheckBox. */
	private CheckBox mCbPasswordVisibility;
	
	/** The remember state CheckBox. */
	private CheckBox mCbRememberState;
	
    /*
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
		mUserEmail = (EditText) findViewById(R.id.email);
		mUserPass = (EditText) findViewById(R.id.pass);
		/** sets input type to email */
		mUserEmail.setInputType(InputType.TYPE_CLASS_TEXT | 
				InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		mCbRememberState = ( CheckBox ) findViewById( R.id.cbRememberState );
		/** As default remember authentication need to be checked */
		mCbRememberState.setChecked(true);
		mCbPasswordVisibility = ( CheckBox ) findViewById( R.id.passwordVisibility );
		/** Password need to be visible as default */
		mCbPasswordVisibility.setChecked(false);
		mCbPasswordVisibility.setOnCheckedChangeListener(passwordVisibilityChangeListener);

		Button submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener( new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				submitLogin();
				
			}
			
		});
		Button clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener( new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				clearInputViews();				
			}
			
		});
    }
    
    /** The password visibility change listener. */
    private OnCheckedChangeListener passwordVisibilityChangeListener = new OnCheckedChangeListener()
	{
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	    {
	        if ( isChecked )
	        {
	        	/**
	        	 ** If password visibility CheckBox is checked, password
	        	 ** field need to be changed to normal TransofmationMethod
	        	 **/
	        	mUserPass.setTransformationMethod(new SingleLineTransformationMethod());
	        }
	        else
	        {
	        	/**
	        	 ** If password visibility CheckBox is unchecked, password
	        	 ** field need to be changed to password TransofmationMethod
	        	 **/
	        	mUserPass.setTransformationMethod(new PasswordTransformationMethod());
	        }

	    }
	};
    
	/**
	 * Submits the login info and checks it.
	 */
    public void submitLogin()
    {
    	/** Checks if there is network connection */
    	if(ConnectionCheck.isOnline(this))
    	{
    		/** Check if input fields are empty */
    		if( mUserEmail.getText().toString().matches("") && mUserPass.getText().toString().matches(""))
    		{
    			/** Show error message */
    			new ShowToastMessage(this, getString(R.string.login_empty_input));
    		}
    		else
    		{
    			/*
    			 * TODO
    			 * Uncomment for release
    			 * if( Validator.isEmailValid(this.mEmail.getText().toString()) )
    			 */
    			if(true)
    			{
		    		HashMap<String, String> myParam = new HashMap<String, String>();
		    		/** add input to param object */
		    		myParam.put(LoginStorage.EMAIL_TAG, mUserEmail.getText().toString());
		    		myParam.put(LoginStorage.PASS_TAG, mUserPass.getText().toString());
		    		/** starts login request */
		    		new LoginRequest(this, myParam, this.mCbRememberState.isChecked());
    			}
    			else
    			{
    				/** show invalid email error */
        			new ShowToastMessage(this, getString(R.string.login_incorrect_input));
    			}
    		}
    	}
    	else
    	{
    		/** show no network connection error */
    		new ShowToastMessage(this, getString(R.string.not_connected));
    	}
    }

    /**
	 * Clear input views and update it's hints.
	 */
    public void clearInputViews()
    {
    	EditText email = (EditText) findViewById(R.id.email);
    	EditText password = (EditText) findViewById(R.id.pass);

    	/** set inputfields text to empty string */
    	email.setText("");
    	password.setText("");
    	
    	/** return hint state */
    	updateHints();
    }

    /**
	 * Update hints
	 */
	private void updateHints() {
    	EditText email = (EditText) findViewById(R.id.email);
    	EditText password = (EditText) findViewById(R.id.pass);

    	email.setHint(R.string.email);
    	password.setHint(R.string.password);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		/** set "want to quit?"  dialog */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.on_back_button_title);
        builder.setMessage(R.string.on_back_button_message);
        builder.setPositiveButton(R.string.yes, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	/** on Yes clicked finish activity */
                finish();
                /** close application */
                System.exit(0);
            }
        });
        /** add negative button with no action. used to close alert dialog */
        builder.setNegativeButton(R.string.no, null);
        /** show the alert dialog */
        builder.show();
    }
}
