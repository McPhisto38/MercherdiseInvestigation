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
    	retval.add(new PlaceData("A", "345 ���", "�����, ���. ��� ����� III  (�������� ���� �����)", "Yesterday"));
    	retval.add(new PlaceData("A", "������� 2012 ��", "�����, ��. ������ ����� 12 ", "3 days ago" ));
    	retval.add(new PlaceData("A", "����� � ��� ���", "�����, ��. ������ ������ 1 �� ��. 205", "20 days ago" ));
    	retval.add(new PlaceData("A", "����� � ��� ��� (2)", "�����, ��. ������ 36", "5 days ago" ));
    	retval.add(new PlaceData("A", "��� ����", "�����, ��. ������ ��������� 20", "3 days ago" ));
    	retval.add(new PlaceData("A", "���� ���", "�����, ��� ���� ����� II", "5 days ago" ));
    	retval.add(new PlaceData("A", "���� ���", "�����, ��� ���� ����� II", "2 days ago" ));
		retval.add(new PlaceData("A", "���� ��� (2)", "�����, ��. ���� �����, ��. �������� �� ��. 422", "6 days ago" ));
    	retval.add(new PlaceCategory("b"));
		retval.add(new PlaceData("B", "������� 34 ����", "�����, ��. ������� �������", "20 days ago" ));
		retval.add(new PlaceData("B", "������� 34 ���� (2)", "�. ����� ������, ��. 1, � 28", "2 days ago" ));
		retval.add(new PlaceData("B", "����� � ��� ��� (3)", "�����, ��. �. ����� 1-19 ��. 54", "12 days ago" ));
		retval.add(new PlaceData("B", "������ 2020 ����", "�����, ��. ������� ��� 38", "33 days ago" ));
		retval.add(new PlaceData("B", "������ ������� ���", "�����, ��. ��� ������ ��.247", "2 days ago" ));
		retval.add(new PlaceData("B", "��� ���������� ����", "�����, ��. ��������� 31", "43 days ago" ));
		retval.add(new PlaceData("B", "���� ������ ����", "�����, ��. �������, ��. ����� 3�", "Yesterday" ));
    	retval.add(new PlaceCategory("C"));
		retval.add(new PlaceData("C", "��� - 2 - ���� ����", "�����, ��. ���, ��. �. ������� 4", "23 days ago" ));
		retval.add(new PlaceData("C", "���� ������������ ����", "�����, ��. �������, ��. �. ������ 23", "5 days ago" ));
		retval.add(new PlaceData("C", "������ 97 ���", "������, ��. �. ����� 26", "2 days ago" ));
		retval.add(new PlaceData("C", "���� - ���� ����", "�����, ��. �. ������ 66", "9 days ago" ));
		retval.add(new PlaceData("C", "���� 05 ����", "�����, ��. �. �������� �� ��. 34�", "4 days ago" ));
		retval.add(new PlaceData("C", "�� ���� ����� - ������ ��������", "�����, ��. ��������� 15", "18 days ago" ));
		
    	return retval;
    }

}
