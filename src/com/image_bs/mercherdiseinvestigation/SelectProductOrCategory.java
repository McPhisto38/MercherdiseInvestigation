package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
 
public class SelectProductOrCategory extends Activity {
	private ListView mChooseProduct;
	private String mCompanyName;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.e("Test", "hello");
        
        setContentView(R.layout.forms_list);

        Intent i = getIntent();
        String categoryName = i.getStringExtra("categoryName");
        String companyName = i.getStringExtra("companyName");
        String categoryID = i.getStringExtra("categoryID");
        String companyID = i.getStringExtra("companyID");
        
        TextView tv = (TextView) findViewById(R.id.CompanyName);
        
        tv.setText(companyName);

        ArrayList<String> values = new ArrayList<String>();
        
        values.add(categoryName);
        
        ProductsReader products = new ProductsReader(companyID, categoryID);
        
        values.addAll(1, products.getLabels());
        
        mChooseProduct = (ListView) findViewById(R.id.ProductsList);
		mChooseProduct.setAdapter(new AdapterWithCheck(this, R.layout.cb_list_item, R.id.textToArrow, R.id.check, values ));
		
        // listening to single list item on click
		mChooseProduct.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View v,
              int position, long id) {

        	  CheckBox check = (CheckBox) v.findViewById(R.id.check);
        	  check.setChecked(true);
        	  check.setEnabled(false);
        	  
        	  if( position == 0)
        	  {
	              Intent i = new Intent(getApplicationContext(), FormCategoryActivity.class);
	              String categoryName = ((TextView) v.findViewById(R.id.textToArrow)).getText().toString();
	              i.putExtra("categoryName", categoryName);
	              startActivity(i);
        	  }
        	  else
        	  {
	              Intent i = new Intent(getApplicationContext(), FormProductActivity.class);
	              String productName = ((TextView) v.findViewById(R.id.textToArrow)).getText().toString();
	              i.putExtra("productName", productName);
	              startActivity(i);
        	  }
 
          }
        });
    }
}