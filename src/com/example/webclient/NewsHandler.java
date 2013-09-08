package com.example.webclient;
/*server sends data as xml file
 *  <item>
	<title>Wave NET work shop</title>
	<details>Work shop is today.</details>
	<date>2013-09-04</date>
	</item>
 * 
 * parse that data create news item objects and return a list of news items
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;

public class NewsHandler extends DefaultHandler {
	
	private boolean inItem = false;
	private boolean inTitle = false;
	
	private boolean inDescription = false;
	private boolean inDate = false;

	private String title = null;
	private String description = null;
	private String date = null;
	private ArrayList<NewsItem>newsItems=new ArrayList<NewsItem>();
	private int index=-1;
	
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		if (qName.equals("item")) 
			{
			  inItem=true;
			  index++;    
			  newsItems.add(new NewsItem());
			   
			}
		
		
		if (qName.equals("title")) 
					inTitle=true;
		
		
				
		if (qName.equals("details")) 
					inDescription = true;
					
				
		if (qName.equals("date")) 
					inDate = true;
				
	}
		
	
	
	 public void characters(char ch[], int start, int length) {
		
		 
		 if (inTitle && inItem) { 
			
			inTitle=false;
			title=new String(ch,start,length);
			newsItems.get(index).setTitle(title);
		 }
		 
		
			 
		 
		 if (inDescription && inItem) { 
			
			 inDescription=false;
			 description=new String(ch,start,length);;
			 newsItems.get(index).setDescription(description);
		 }
		 
		 if (inDate && inItem){
			 
			 inDate=false;
			 date=new String(ch,start,length);;
			 newsItems.get(index).setPubDate(date);
		 }
		 
	 }
	 
	 
	
	public void processFeed( URL url) {
        try {
        	
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            xr.setContentHandler(this);
         
            xr.parse(new InputSource(url.openStream()));
         
        } catch (IOException e) {
            Log.e("", e.toString());
        } catch (SAXException e) {
            Log.e("", e.toString());
        } catch (ParserConfigurationException e) {
            Log.e("", e.toString());
        }
	}

  public ArrayList<NewsItem> getNewsItems() {
	return newsItems;
  }
	
	
	
}


