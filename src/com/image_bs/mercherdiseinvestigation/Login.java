package com.image_bs.mercherdiseinvestigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    
    public void onSubmit(View v)
    {
        startActivity( new Intent(getApplicationContext(), SelectPlace.class) );
    }
    
    public void onClear(View v)
    {
    	EditText email = (EditText) findViewById(R.id.email);
    	EditText password = (EditText) findViewById(R.id.pass);

    	email.setText("");
    	password.setText("");
    	
    	updateHints();
    }

	private void updateHints() {
    	EditText email = (EditText) findViewById(R.id.email);
    	EditText password = (EditText) findViewById(R.id.pass);

    	email.setHint(R.string.email);
    	password.setHint(R.string.password);
	}
}
