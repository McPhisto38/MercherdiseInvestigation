package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
 
public class SelectCategory extends Activity {

	private String mCompanyName;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forms_list);

        Intent i = getIntent();
        mCompanyName = i.getStringExtra("companyName");
        
        TextView tvCompanyName = (TextView) findViewById(R.id.CompanyName);
        
        tvCompanyName.setText(mCompanyName);
        
        final ArrayList<String> values;
        final String companyID = i.getStringExtra("companyID");
        
    	final CategoriesReader categories = new CategoriesReader(companyID);

    	if( companyID.equalsIgnoreCase("company_id_2") )
    		values = ChangeLabels(categories.getLabels());
    	else
    		values = categories.getLabels();

    	ListView chooseProduct = (ListView) findViewById(R.id.ProductsList);

        try{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		  R.layout.simple_list_item, R.id.text1, values);
		chooseProduct.setAdapter(adapter);
        // listening to single list item on click
		chooseProduct.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View v,
              int position, long id) {
        	  
              Intent i = new Intent(getApplicationContext(), SelectProductOrCategory.class);
              String categoryName = ((TextView) v.findViewById(R.id.text1)).getText().toString();
              i.putExtra("categoryName", categoryName);
              i.putExtra("companyName", mCompanyName);
              i.putExtra("companyID", companyID);
              String categoryID = categories.getCategoryID(position);
              i.putExtra("categoryID", categoryID);
              startActivity(i);
 
          }
        });
        }
        catch ( Exception e)
        {
        	Log.e("sda", values.get(0));
        }
    }
    
    private ArrayList<String> ChangeLabels(ArrayList<String> oldValues)
    {
    	ArrayList<String> newValues = new ArrayList<String>();
    	
    	for( int index = 0 ; index < oldValues.size() ; index++)
    	{
    		newValues.add(mCompanyName + " " + oldValues.get(index));
    	}
    	
    	return newValues;
    }
}