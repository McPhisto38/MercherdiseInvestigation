package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

public class CompaniesReader {
	private String mMercherdiserID;
	private String[][] mCompaniesData;
	
	CompaniesReader(String mercherdiserID)
	{
		this.mMercherdiserID = mercherdiserID;
		DownloadCompaniesData();
	}
	
	private void DownloadCompaniesData()
	{
		if( mMercherdiserID.equalsIgnoreCase(mMercherdiserID) )
		{
			mCompaniesData = new String[][] {
					{ "company_id_1", "Компас"},
					{ "company_id_2", "Дерони" }, {"company_id_2", "Загорка"},
					{ "company_id_2", "Samsung"}, { "company_id_2", "HTC"},
					{ "company_id_2", "LG"}, { "company_id_2", "КЕН"},
					{ "company_id_2", "Сачи"}, { "company_id_2", "Леки"},
					{ "company_id_2", "Добруджа"}, { "company_id_2", "Житен Дар"},
					{ "company_id_2", "Престиж"}, { "company_id_2", "Нестле" }
			};
		}
		else
		{
			mCompaniesData = null;
		}
	}
	
	String[][] getCompaniesData()
	{
		return this.mCompaniesData;
	}
	
	ArrayList<String> getLabels()
	{
		ArrayList<String> labels = new ArrayList<String>();
		
		if( mCompaniesData != null)
		{
			for(int i = 0; i < mCompaniesData.length ; i++)
			{
				labels.add(mCompaniesData[i][1]);
			}
		}
		else
		{
			labels = null;
		}
		
		return labels;
	}
	
	String getCompanyID(int position)
	{
		String companyID = null;
		
		if( position <= mCompaniesData.length )
		{
			companyID = mCompaniesData[position][0];
		}
			
		return companyID;
	}
}
