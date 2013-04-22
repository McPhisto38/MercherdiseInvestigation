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
					{ "company_id_1", "������"},
					{ "company_id_2", "������" }, {"company_id_2", "�������"},
					{ "company_id_2", "Samsung"}, { "company_id_2", "HTC"},
					{ "company_id_2", "LG"}, { "company_id_2", "���"},
					{ "company_id_2", "����"}, { "company_id_2", "����"},
					{ "company_id_2", "��������"}, { "company_id_2", "����� ���"},
					{ "company_id_2", "�������"}, { "company_id_2", "������" }
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
