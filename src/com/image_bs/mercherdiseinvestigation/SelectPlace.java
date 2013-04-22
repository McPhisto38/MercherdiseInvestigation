package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectPlace extends Activity{
	 
	PlaceListAdapter dataAdapter = null;
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.place_list);
	 
	  //Generate list View from ArrayList
	  displayListView();
	 
	 }
	 
	 private void displayListView() {
	 
	  //Array list of countries
	  ArrayList<Item> placeList = new ArrayList<Item>();
	  placeList.addAll(readFile());
	 
	  //create an ArrayAdaptar from the String Array
	  dataAdapter = new PlaceListAdapter(this,
	    R.layout.place_item, placeList);
	  ListView listView = (ListView) findViewById(R.id.PlaceList);
	  // Assign adapter to ListView
	  listView.setAdapter(dataAdapter);
	 
	  //enables filtering for the contents of the given ListView
	  listView.setTextFilterEnabled(true);

      listView.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View v,
	              int position, long id) {
	        	  
	              Intent i = new Intent(getApplicationContext(), SelectCompany.class);
	              String placeAddress = ((TextView) v.findViewById(R.id.tvPlaceAddress)).getText().toString();
	              i.putExtra("placeAddress", placeAddress);
	              startActivity(i);
	 
	          }
	        });
	 
	  EditText myFilter = (EditText) findViewById(R.id.searchPlace);
	  myFilter.addTextChangedListener(new TextWatcher() {
	 
	  public void afterTextChanged(Editable s) {
	  }
	 
	  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	  }
	 
	  public void onTextChanged(CharSequence s, int start, int before, int count) {
	   dataAdapter.getFilter().filter(s.toString());
	  }
	  });
	 }
	 
	
	public ArrayList<Item> readFile(){
    	ArrayList<Item> retval = new ArrayList<Item>();


    	retval.add(new PlaceCategory("A"));
    	retval.add(new PlaceData("A", "345 ООД", "София, бул. Цар Борис III  (Автогара Овча купел)", "Yesterday"));
    	retval.add(new PlaceData("A", "Бурлекс 2012 АД", "София, ул. Никола Жеков 12 ", "3 days ago" ));
    	retval.add(new PlaceData("A", "Буров и Син ООД", "София, ул. Пражка пролет 1 до бл. 205", "20 days ago" ));
    	retval.add(new PlaceData("A", "Буров и Син ООД (2)", "София, ул. Боряна 36", "5 days ago" ));
    	retval.add(new PlaceData("A", "ВМВ ЕООД", "София, ул. Никола Габровски 20", "3 days ago" ));
    	retval.add(new PlaceData("A", "Коме ООД", "София, РУМ Овча купел II", "5 days ago" ));
    	retval.add(new PlaceData("A", "Коме ООД", "София, РУМ Овча купел II", "2 days ago" ));
		retval.add(new PlaceData("A", "Коме ООД (2)", "София, кв. Овча купел, ул. Обиколна до бл. 422", "6 days ago" ));
    	retval.add(new PlaceCategory("b"));
		retval.add(new PlaceData("B", "Брилянт 34 ЕООД", "София, кв. Ботунец площада", "20 days ago" ));
		retval.add(new PlaceData("B", "Брилянт 34 ЕООД (2)", "с. Долни Богров, ул. 1, № 28", "2 days ago" ));
		retval.add(new PlaceData("B", "Буров и Син ООД (3)", "София, ул. Д. Матов 1-19 бл. 54", "12 days ago" ));
		retval.add(new PlaceData("B", "Валдис 2020 ЕООД", "София, ул. Дравски бой 38", "33 days ago" ));
		retval.add(new PlaceData("B", "Вашият магазин ООД", "София, ул. Цар Симеон бл.247", "2 days ago" ));
		retval.add(new PlaceData("B", "ВИК Инженеринг ЕООД", "София, ул. Княжевска 31", "43 days ago" ));
		retval.add(new PlaceData("B", "Дани Комерс ЕООД", "София, кв. Ботунец, ул. Искър 3Б", "Yesterday" ));
    	retval.add(new PlaceCategory("C"));
		retval.add(new PlaceData("C", "ВДК - 2 - Деси ЕООД", "София, кв. Яна, ул. Ю. Гагарин 4", "23 days ago" ));
		retval.add(new PlaceData("C", "Вита Интернешънъл ЕООД", "София, кв. Ботунец, ул. В. Левски 23", "5 days ago" ));
		retval.add(new PlaceData("C", "Грифон 97 ООД", "Бухово, ул. Н. Бонев 26", "2 days ago" ));
		retval.add(new PlaceData("C", "Дъга - Роси ЕООД", "София, ул. Д. Петков 66", "9 days ago" ));
		retval.add(new PlaceData("C", "Елем 05 ЕООД", "София, ул. Н. Генадиев до бл. 34Б", "4 days ago" ));
		retval.add(new PlaceData("C", "ЕТ Буен поток - Йордан Димитров", "София, ул. Планиница 15", "18 days ago" ));
		
    	return retval;
    }

}
