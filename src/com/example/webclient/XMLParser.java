package com.example.webclient;


	
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
	
	import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;

	public class XMLParser extends DefaultHandler{
		
		private boolean inItem = false;
		private boolean inTitle = false;
		private boolean inIndex = false;
		private boolean inFirstName = false;
		private boolean inLastName = false;
		
		private String title=null;
		private String index =null;
		private String firstName = null;
		private String lastName = null;
		private User user=new User();
		
		
		
		
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			
			if (qName.equals("item")) 
				{
				  inItem=true;
				 
				   
				}
			
			
			if (qName.equals("title")) 
						inTitle=true;
			
			if (qName.equals("index")) 
				inIndex = true;
					
			if (qName.equals("first_name")) 
						inFirstName = true;
						
					
			if (qName.equals("last_name")) 
						inLastName = true;
					
		}
			
		
		
		 public void characters(char ch[], int start, int length) {
			 
			 
			 if (inTitle && inItem) { 
				
				inTitle=false;
				title=new String(ch,start,length);
				
				
			 }
			 
			 if (inIndex && inItem) { 
					
					inIndex=false;
					index=new String(ch,start,length);
					user.setIndex_number(index);
				    
				 }
				 
			 
			 if (inFirstName && inItem) { 
				
				 inFirstName=false;
				 firstName=new String(ch,start,length);
				 user.setFirst_name(firstName);
			 }
			 
			 if (inLastName && inItem){
				 
				 inLastName=false;
				lastName=new String(ch,start,length);
				 user.setLast_name(lastName);
			 }
			 
		 }
		 
		 
		 
		
		public void processFeed( URL url) {
	        try {
	        	
	            SAXParserFactory spf = SAXParserFactory.newInstance();
	            SAXParser sp = spf.newSAXParser();
	            XMLReader xr = sp.getXMLReader();
	           // File file = new File("WebClient\\src\\com\\example\\webclient\\rss.xml");
				//FileInputStream fis = new FileInputStream(file);
	            xr.setContentHandler(this);
	            user.index_number="ddsdsd";
	           URL url1= new URL("http://192.168.42.35:8080/WebServer/AccountDetails.php?index=100470N");
	           
	           xr.parse(new InputSource(url.openStream()));
	         
	         
	        } catch (IOException e) {
	            Log.e("", e.toString());
	        } catch (SAXException e) {
	            Log.e("", e.toString());
	        } catch (ParserConfigurationException e) {
	            Log.e("", e.toString());
	        }
		}

	  public User getUserInfo(){
		  
		  
		  return user;
	  }
		
		
		
	}




