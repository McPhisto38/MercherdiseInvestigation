/*
 ** Copyright (C) 2013 Image Business Solutions
 ** Developed by Nikolai Vasilev - coder.servoper@gmail.com
 **/

package com.image_bs.mercherdiseinvestigation;
 
import java.io.InputStream;
import java.net.SocketException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Makes http requests with both POST and GET methods and  gives InputStream
 * result or null.
 * <p>
 * NULL is returned when request timeout is left or response exception occur
 */
public class HttpRequest {
	
	/** The connection timeout in milliseconds. */
	private final int mConnectionTimeout = 10000;
	
	/** The socket timeout in milliseconds. */
	private final int mSocketTimeout = 10000;
	
	/** The url. */
	private String mUrl;
	
	/** The request method type. */
	private String mRequestMethodType;
    
    /** The request parameters. */
    private List<NameValuePair> mRequestParams;
	
	/**
	 * Instantiates a new http request.
	 * 
	 * @param url
	 *            the url
	 * @param requestmethod
	 *            the method
	 * @param requestParams
	 *            the params
	 */
	public HttpRequest(String url, String requestMethod,
	        List<NameValuePair> requestParams) {
		this.mRequestParams = requestParams;
		this.mUrl = url;
		this.mRequestMethodType = requestMethod;
	}
	
	/**
	 * Makes http request and handles its response
	 * 
	 * @return the http request result
	 */
	public InputStream getHttpRequestResult() {
		InputStream inputStream = null;
	    /** check for request method */
	    if(mRequestMethodType == "POST"){
	        try{
	        	
	        	HttpPost request = new HttpPost(mUrl);
			   	HttpParams httpParameters = new BasicHttpParams();
			   
			   	/** add params to the entity object */
			   	HttpEntity inputEntity = new UrlEncodedFormEntity(mRequestParams);
			
			   	/** adds content type of the entity to the request */
			   	request.addHeader(inputEntity.getContentType());
			   	/** adds the eintities to the request */
			   	request.setEntity(inputEntity);
			   	/** 
			   	 * Set the timeout in milliseconds until a connection is established.
			   	 * The default value is zero, that means the timeout is not used. 
			   	 */
			   	HttpConnectionParams.setConnectionTimeout(httpParameters, mConnectionTimeout);
			   	/** 
			   	 * Set the default socket timeout (SO_TIMEOUT)
			   	 * in milliseconds which is the timeout for waiting for data.
			   	 */
			   	HttpConnectionParams.setSoTimeout(httpParameters, mSocketTimeout);
			   	/** create object of DefaultHttpClient adds the parameters */
			   	DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			   	/** adds json application content type */
			   	request.addHeader("Content-Type", "application/json");
			   	
			   	/** executes the request and gets the response */
			   	HttpResponse response = httpClient.execute(request);
			   	/** gets the response status line */
				StatusLine statusLine = response.getStatusLine();
				/** gets the status code from the statis line */
				int statusCode = statusLine.getStatusCode();
				/**
				 * checks for status code.. status code needs to be 200 or
				 * HttpStatus.SC_OK
				 */
				if (statusCode == HttpStatus.SC_OK) {
					/** get response entities */
				    HttpEntity entity = response.getEntity();
				    /**get inputstream of entities */
				    inputStream = entity.getContent();
				}
				/** convert entity response to string */
	       }
	     catch (SocketException e)
	      {
	          e.printStackTrace();
	         return null;
	      }
	     catch (Exception e)
	      {
	          e.printStackTrace();
	         return null;
	      }
	
	    }else if(mRequestMethodType == "GET"){
	        try{
				String paramString = URLEncodedUtils.format(mRequestParams, "utf-8");
				mUrl += "?" + paramString;
	           HttpGet request = new HttpGet(mUrl);
	           HttpParams httpParameters = new BasicHttpParams();
	           /** 
	            * Set the timeout in milliseconds until a connection is established.
	            * The default value is zero, that means the timeout is not used.
	            */
	           HttpConnectionParams.setConnectionTimeout(httpParameters, mConnectionTimeout);
	           /** 
	            * Set the default socket timeout (SO_TIMEOUT)
	            * in milliseconds which is the timeout for waiting for data. 
	            */
	           HttpConnectionParams.setSoTimeout(httpParameters, mSocketTimeout);
	           /** create object of DefaultHttpClient */
	           DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
	           request.addHeader("Content-Type", "application/json");
	           HttpResponse response = httpClient.execute(request);
	           StatusLine statusLine = response.getStatusLine();
	           int statusCode = statusLine.getStatusCode();
	           if (statusCode == 200) {
	               HttpEntity entity = response.getEntity();
	               inputStream = entity.getContent();
	
	           }
	           /** convert entity response to string */
	
	           }
	         catch (SocketException e)
	          {
	             e.printStackTrace();
	             return null;
	          }
	         catch (Exception e)
	          {
	             e.printStackTrace();
	             return null;
	          }
	    }
	
	    return inputStream;
	}
}