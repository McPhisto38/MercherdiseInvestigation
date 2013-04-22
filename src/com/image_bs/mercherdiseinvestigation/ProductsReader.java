package com.image_bs.mercherdiseinvestigation;

import java.util.ArrayList;

public class ProductsReader {

	private String mCompanyID;
	private String mCategoryID;
	private ArrayList<String[]> mProductsData;
	
	ProductsReader(String companyID, String categoryID)
	{
		this.mCompanyID = companyID;
		this.mCategoryID = categoryID;
		DownloadCompaniesData();
	}
	
	private void DownloadCompaniesData()
	{
			String[][] productsData = new String[][] {
					{ "company_id_1", "category_id_1", "product_id_24", "Компас Апетит 180г." }, { "company_id_1", "category_id_1", "product_id_23", "Компас Свински 180г"},
					{ "company_id_1", "category_id_1", "product_id_25", "Компас Гъши 180г." }, { "company_id_1", "category_id_1", "product_id_22", "Компас Апетит 100г."},
					{ "company_id_1", "category_id_1", "product_id_26", "Компас Junior Свински 100гр." }, { "company_id_1", "category_id_1", "product_id_21", "Компас Свински 300г."},
					{ "company_id_1", "category_id_1", "product_id_27", "Каро 180г."}, { "company_id_1", "category_id_1", "product_id_20", "Каро 300г."},
					{ "company_id_1", "category_id_2", "product_id_28", "Компас Русенско 180г."}, { "company_id_1", "category_id_2", "product_id_19", "Компас Русенско 300г."},
					{ "company_id_1", "category_id_2", "product_id_29", "Каро Русенско 180г."}, { "company_id_1", "category_id_2", "product_id_31", "Свинско собствен сос 180г."},
					{ "company_id_1", "category_id_2", "product_id_32", "Телешко собствен сос 180г."}, { "company_id_1", "category_id_3", "product_id_18", "Русалка Скумрия дом. сос 160г."},
					{ "company_id_1", "category_id_3", "product_id_30", "Славянска Скумрия дом. сос 160г."}, { "company_id_1", "category_id_4", "product_id_17", "Фамила Класик 250г."},
					{ "company_id_2", "category_id_5", "product_id_1", "Продукт 1" }, { "company_id_2", "category_id_5", "product_id_2", "Продукт 2"},
					{ "company_id_2", "category_id_6", "product_id_3", "Продукт 3" }, { "company_id_2", "category_id_6", "product_id_4", "Продукт 4"},
					{ "company_id_2", "category_id_7", "product_id_5", "Продукт 5" }, { "company_id_2", "category_id_7", "product_id_6", "Продукт 6"},
					{ "company_id_2", "category_id_8", "product_id_7", "Продукт 7"}, { "company_id_2", "category_id_8", "product_id_8", "Продукт 8"},
					{ "company_id_2", "category_id_9", "product_id_9", "Продукт 9"}, { "company_id_2", "category_id_9", "product_id_10", "Продукт 10"},
					{ "company_id_2", "category_id_10", "product_id_11", "Продукт 11"}, { "company_id_2", "category_id_10", "product_id_12", "Продукт 12"},
					{ "company_id_2", null, "product_id_", "Продукт 13"}, { "company_id_2", null, "product_id_14", "Продукт  14"},
					{ "company_id_2", null, "product_id_", "Продукт 15"}, { "company_id_2", null, "product_id_16", "Продукт 16" }
			};

			this.mProductsData =  new ArrayList<String[]>();
			
			for( int i = 0 ; i< productsData.length ; i++)
			{
				if( this.mCompanyID != null)
				{
					if( productsData[i][0].equalsIgnoreCase(this.mCompanyID) )
					{
						if(this.mCategoryID != null)
						{
							if( this.mCategoryID.equalsIgnoreCase(productsData[i][1]) )
							{
								this.mProductsData.add(productsData[i]);
							}
							else
							{
								continue;
							}
						}
						else
						{
							this.mProductsData.add(productsData[i]);
						}
					}
				}
				else
				{
					break;
				}
			}
			
			if( this.mProductsData.isEmpty() )
			{
				this.mProductsData = null;
			}
	}
	
	ArrayList<String[]> getProductsData()
	{
		return this.mProductsData;
	}
	
	ArrayList<String> getLabels()
	{
		ArrayList<String> labels = new ArrayList<String>();
		
		if( this.mProductsData != null)
		{
			for(int i = 0; i < this.mProductsData.size() ; i++)
			{
				labels.add(this.mProductsData.get(i)[3]);
			}
		}
		else
		{
			labels = null;
		}
		
		return labels;
	}
	
	ArrayList<String> getLabels(String companyID, String categoryID)
	{
		ArrayList<String> labels = new ArrayList<String>();
		
		if( this.mProductsData != null)
		{
			for(int i = 0; i < this.mProductsData.size() ; i++)
			{
				labels.add(this.mProductsData.get(i)[3]);
			}
		}
		else
		{
			labels = null;
		}
		
		return labels;
	}

	String getProductID(int position)
	{
		String companyID = null;
		
		if( position <= this.mProductsData.size() )
		{
			companyID = this.mProductsData.get(position)[2];
		}
			
		return companyID;
	}
}