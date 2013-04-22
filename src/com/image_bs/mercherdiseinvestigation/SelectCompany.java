package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectCompany extends Activity {
	
	private ListView mChooseCompany;
	private CompaniesReader mCompanies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companies_list);
        
        mCompanies = new CompaniesReader("here is the mercherdiser id");
        
        TextView tvPlaceAddress = (TextView) findViewById(R.id.CompanyName);
        
        Intent i = getIntent();
        
        String placeAddress = i.getStringExtra("placeAddress");
        
        tvPlaceAddress.setText(placeAddress);
        
        ArrayList<String> values = mCompanies.getLabels();

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		  R.layout.simple_list_item, R.id.text1, values);


		// Assign adapter to ListView
        mChooseCompany = (ListView) findViewById(R.id.ChooseCompany);
		mChooseCompany.setAdapter(adapter);

        // listening to single list item on click
		mChooseCompany.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View v,
              int position, long id) {
        	  
              // selected item
 
              // Launching new Activity on selecting single List Item
              Intent i = new Intent(getApplicationContext(), SelectCategory.class);
              // sending data to new activity
              
              String companyName = ((TextView) v.findViewById(R.id.text1)).getText().toString();
              
              String companyID = mCompanies.getCompanyID(position);
              
              i.putExtra("companyID", companyID);
              i.putExtra("companyName", companyName);
              startActivity(i);
 
          }
        });
    }

    /*
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.on_back_button_title);
        builder.setMessage(R.string.on_back_button_message);
        builder.setPositiveButton(R.string.yes, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */
}
