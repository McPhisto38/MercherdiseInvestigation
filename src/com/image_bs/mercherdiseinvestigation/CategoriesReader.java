package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

public class CategoriesReader {

	private String mCompanyID;
	private ArrayList<String[]> mCategoriesData;
	
	CategoriesReader(String companyID)
	{
		this.mCompanyID = companyID;
		DownloadCompaniesData();
	}
	
	private void DownloadCompaniesData()
	{
		String[][] categoriesData = new String[][] {
				{ "company_id_1" , "category_id_1" , "������" },
				{ "company_id_1" , "category_id_2", "�����"},
				{ "company_id_1" , "category_id_3", "�����" },
				{ "company_id_1" , "category_id_4", "��������"},
				{ "company_id_2" , "category_id_5", "��������� 1" }, { "company_id_2" , "category_id_6", "��������� 2"},
				{ "company_id_2" , "category_id_7", "��������� 3" }, { "company_id_2" , "category_id_8", "��������� 4"},
				{ "company_id_2" , "category_id_9", "��������� 5" }, { "company_id_2" , "category_id_10", "��������� 6"}
		};
		
		this.mCategoriesData =  new ArrayList<String[]>();
		
		for(int i = 0 ; i< categoriesData.length ; i++)
		{
			if( this.mCompanyID != null)
			{
				if( this.mCompanyID.equalsIgnoreCase(categoriesData[i][0]))
				{
					this.mCategoriesData.add(categoriesData[i]);
				}
			}
			else
			{
				break;
			}
		}

		if( this.mCategoriesData.isEmpty() )
		{
			mCategoriesData = null;
		}
	}
	
	ArrayList<String[]> getCategoriesData()
	{
		return this.mCategoriesData;
	}
	
	ArrayList<String> getLabels()
	{
		ArrayList<String> labels = new ArrayList<String>();
		
		if( mCategoriesData != null)
		{
			for(int i = 0; i < mCategoriesData.size() ; i++)
			{
				labels.add(mCategoriesData.get(i)[2]);
			}
		}
		else
		{
			labels = null;
		}
		
		return labels;
	}

	String getCategoryID(int position)
	{
		String companyID = null;
		
		if( position <= mCategoriesData.size() )
		{
			companyID = mCategoriesData.get(position)[1];
		}
			
		return companyID;
	}
}