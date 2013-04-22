
package com.image_bs.mercherdiseinvestigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class FormCategoryActivity extends Activity{
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.category_form);
        
        Intent i = getIntent();
        String categoryName = i.getStringExtra("categoryName");
        
        TextView productTitle = (TextView) findViewById(R.id.CategoryName);
        
        productTitle.setText(categoryName);

    	  CheckBox check = (CheckBox) findViewById(R.id.CategoryCheck);
    	  check.setChecked(true);
    	  check.setEnabled(false);
    }

    public void OnMultipleChoiceQuestionClick(View v) {
		final String items[] = {"0%", "10%", "30%", "-10%", "-20%", "-30%" };
		
		int id = v.getId();
		
		AlertDialog.Builder ab=new AlertDialog.Builder(FormCategoryActivity.this);
        ab.setTitle(Integer.toString(id));
        ab.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface d, int choice) {
	        	TextView tv = (TextView) findViewById(R.id.QuestionAnswer_5_1);
	        	tv.setText(items[choice]);
	        	d.cancel();
	        }
        });
        ab.show();
	}
    
    public void sync(View v)
    {
    	ImageView syncButton = (ImageView) v.findViewById(R.id.sync);
    	syncButton.startAnimation( 
    		    AnimationUtils.loadAnimation(this, R.anim.rotate_sync) );
    }
}
