package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
//import android.widget.Filter.FilterResults;
import android.widget.TextView;

 public class PlaceListAdapter extends ArrayAdapter<Item> {
 
  private ArrayList<Item> originalList;
  private ArrayList<Item> viewPlaceList;
  private PlaceFilter filter;
  private Context context;
 
  public PlaceListAdapter(Context context, int textViewResourceId, 
    ArrayList<Item> countryList) {
   super(context, textViewResourceId, countryList);
   this.context = context;
   this.viewPlaceList = new ArrayList<Item>();
   this.viewPlaceList.addAll(countryList);
   this.originalList = new ArrayList<Item>();
   this.originalList.addAll(countryList);
  }
 
	@Override
	public Filter getFilter() 
	{
		if (filter == null){
			filter  = new PlaceFilter();
		}
		return filter;
	}
 
  	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   
		if(viewPlaceList.get(position).isPlaceCategory())
		{
			v = vi.inflate(R.layout.place_category_item, null);
			   
			v.setOnClickListener(null);
			v.setOnLongClickListener(null);
			v.setLongClickable(false);
			   
			TextView categoryName = (TextView) v.findViewById(R.id.placeCategoryName);
			
			PlaceCategory category = (PlaceCategory) viewPlaceList.get(position);
			categoryName.setText(category.getPlaceCategoryName());
		}
		else
		{
			v = vi.inflate(R.layout.place_item, null);
			 
			TextView placeName = (TextView) v.findViewById(R.id.tvPlaceName);
			TextView placeAddress = (TextView) v.findViewById(R.id.tvPlaceAddress);
			TextView placeLastVisit = (TextView) v.findViewById(R.id.tvLastVisit);
			
			PlaceData place = (PlaceData) viewPlaceList.get(position);
			placeName.setText(place.getPlaceName());
			placeAddress.setText(place.getPlaceAddress());
			placeLastVisit.setText(place.getLastVisit());
		}
			  
		return v;
	}
 
  private class PlaceFilter extends Filter
  {
 
   @Override
   protected FilterResults performFiltering(CharSequence constraint) {
 
    constraint = constraint.toString().toLowerCase();
    FilterResults result = new FilterResults();
    if(constraint != null && constraint.toString().length() > 0)
    {
    ArrayList<PlaceData> filteredItems = new ArrayList<PlaceData>();
 
    for(int i = 0, l = originalList.size(); i < l; i++)
    {
    	if(!originalList.get(i).isPlaceCategory())
    	{
			PlaceData place = (PlaceData) originalList.get(i);
			if(place.toString().toLowerCase().contains(constraint))
				filteredItems.add(place);
    	}
    }
    result.count = filteredItems.size();
    result.values = filteredItems;
    }
    else
    {
     synchronized(this)
     {
      result.values = originalList;
      result.count = originalList.size();
     }
    }
    return result;
   }
 
   @SuppressWarnings("unchecked")
   @Override
   protected void publishResults(CharSequence constraint, 
     FilterResults results) {
 
    viewPlaceList = (ArrayList<Item>)results.values;
    notifyDataSetChanged();
    clear();
    for(int i = 0, l = viewPlaceList.size(); i < l; i++)
     add(viewPlaceList.get(i));
    notifyDataSetInvalidated();
   }
  }
 
 
 }