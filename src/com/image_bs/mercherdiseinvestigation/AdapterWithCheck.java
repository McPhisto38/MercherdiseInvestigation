package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class AdapterWithCheck extends ArrayAdapter
{
    Activity context;
    ArrayList<String> items;
    int layoutId;
    int textId;
    int mCheckId;
 
    AdapterWithCheck(Activity context, int layoutId, int textId, int checkId, ArrayList<String> items)
    {
        super(context, layoutId, items);
 
        this.context = context;
        this.items = items;
        this.layoutId = layoutId;
        this.textId = textId;
        this.mCheckId = checkId;
    }
 
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View row=inflater.inflate(layoutId, null);
        TextView label=(TextView)row.findViewById(textId);
 
        label.setText(items.get(pos));
         
		
		return(row);
    }
}