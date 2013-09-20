/*
 ** Copyright (C) 2013 Image Business Solutions
 ** Developed by Nikolai Vasilev - coder.servoper@gmail.com
 **/

package com.image_bs.mercherdiseinvestigation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

/**
 * * Stores user information in the application private data.
 */
public class LoginStorage {

	/** filename of the login information data */
	final static private String FILENAME = "secure.xml";
	/** user tag used as xml item tag */
	final static private String USER_TAG = "user";
	/** email tag used for user name param in http request */
	final static public String EMAIL_TAG = "email";
	/** id tag used for id param in http request */
	final static public String ID_TAG = "id";
	/** pass tag used for pass param in http request */
	final static public String PASS_TAG = "password";
	/** calling activity context */
	private Context mContext;
	
	/**
	 * * Constructor initializing the context of the calling activity.
	 * 
	 * @param context
	 *            the context
	 */
	public LoginStorage(Context context)
	{
		this.mContext = context;
	}
	
	/**
	 * Saves user information in xml file
	 * 
	 * @param userEmail
	 *            the user email
	 * @param userPassword
	 *            the user password
	 * @param userId
	 *            the user id
	 */
	public final void setUser(String userEmail, String userPassword, String userId)
	{
		XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    try {
	    	/** Set's the output stream */
	        serializer.setOutput(writer);
	        /** define charset encoding */
	        serializer.startDocument("UTF-8", true);
	        /** start new item with user tag */
	        serializer.startTag("", LoginStorage.USER_TAG );
	        
	        /** starting new object tag */
            serializer.startTag("", LoginStorage.EMAIL_TAG);
            /** adding a value */
            serializer.text(userEmail);
            /** ending tag */
            serializer.endTag("", LoginStorage.EMAIL_TAG);
            
            serializer.startTag("", LoginStorage.ID_TAG);
            serializer.text(userId);
            serializer.endTag("", LoginStorage.ID_TAG);
            serializer.startTag("", LoginStorage.PASS_TAG);
            serializer.text(userPassword);
            serializer.endTag("", LoginStorage.PASS_TAG);
	        
            /** end item tag */
	        serializer.endTag("", LoginStorage.USER_TAG);
	        /** end document */
	        serializer.endDocument();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	    
	    /** gets writer content as string and pass it to file creation */
	    createFile(writer.toString());
	}

	/**
	 * creates file from xml string.
	 * 
	 * @param xml
	 *            the xml
	 * @return true, if successful
	 */
	private boolean createFile(String xml)
	{
		/** initialize result var as false at the begining */
		boolean isSuccessful = false;

		try {
			/**
			 ** Output file stream with predefined filename and writeing in
			 ** private app storage
			 **/
			FileOutputStream fOut = this.mContext.openFileOutput(LoginStorage.FILENAME,
					Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			/** write */
			osw.write(xml);
			/** clean buffer */
			osw.flush();
			/** close outputstreamwriter */
			osw.close();
			/** flag as successful */
			isSuccessful = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return isSuccessful;
	}

	/**
	 * Gets element by tag from xml file
	 * 
	 * @param elementTag
	 *            the element tag
	 * @return the string
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the sAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private String readElementFromXmlFile(String elementTag)
			throws ParserConfigurationException, SAXException, IOException
	{
		/** gets path of the private moded file */
		File file = this.mContext.getFileStreamPath(LoginStorage.FILENAME);
	    FileInputStream fis = new FileInputStream(file);
		 
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
		        .newDocumentBuilder();
		
		/** xml document */
		Document doc = builder.parse(fis);
		
		/** default value for the result is null */
		String responseValue = null;
		/** if documents exists */
		if (doc != null) {
			/** nodes child of the requested tag */
		    NodeList nl = doc.getElementsByTagName(elementTag);
		    
		    /** if is not empty */
		    if (nl.getLength() > 0) {
		    	/**
		    	 * we expect element with requested tag not to have child
		    	 * elements so we get only the first item(it should be the value)
		    	 */
		        Node node = nl.item(0);
		        /** initialize the result string */
		        responseValue = new String();
		        /** fill result string with node value */
		        responseValue = node.getTextContent();
		    }
		}
		
		return responseValue;
	}
	
	/**
	 * Gets the user id from the saved file.
	 * 
	 * @return the user id
	 */
	public Integer getUserId()
	{
		try {
			return Integer.parseInt( readElementFromXmlFile(LoginStorage.ID_TAG) );
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets the user email from the saved file.
	 * 
	 * @return the email
	 */
	public String getEmail()
	{
		try {
			return readElementFromXmlFile(LoginStorage.EMAIL_TAG);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets the user pass from the saved file.
	 * 
	 * @return the password
	 */
	public String getPassword()
	{
		try {
			return readElementFromXmlFile(LoginStorage.PASS_TAG);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Static method so doesn't need an isntance.
	 * <p>
	 * Deletes the user data file.
	 * 
	 * @param context
	 *            the context
	 */
	public static void logout(Context context) {
		File file = context.getFileStreamPath(LoginStorage.FILENAME);
		file.delete();
	}
}
