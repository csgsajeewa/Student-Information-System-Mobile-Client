package com.example.webclient;



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


/*server transfer data(account details etc) to mobile app as a xml document 
 * this class will accept that and parse that and extract the details
 * 
 */
	public class XMLParser extends DefaultHandler{
		
		private boolean inItem = false;
		private boolean inTitle = false;
		private boolean inIndex = false;
		private boolean inFirstName = false;
		private boolean inLastName = false;
		private boolean inDepartment=false;
		private boolean inFaculty=false;
		private boolean inYear=false;
		private boolean inSemester=false;
		private boolean inEmail=false;
		private boolean inRegistered=false;
		
		private String title=null;
		private String index =null;
		private String firstName = null;
		private String lastName = null;
		private String department=null;
		private String faculty =null;
		private String year = null;
		private String semester = null;
		private String email=null;
		private String registered =null;
		
		
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
			if (qName.equals("department")) 
				inDepartment = true;
			if (qName.equals("faculty")) 
				inFaculty = true;
			
			if (qName.equals("year")) 
				inYear = true;
			if (qName.equals("semester")) 
				inSemester = true;
			if (qName.equals("email")) 
				inEmail = true;
			if (qName.equals("isRegistered")) 
				inRegistered = true;
					
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
			 
			 if (inDepartment && inItem){
				 
				 inDepartment=false;
				department=new String(ch,start,length);
				 user.setDepartment(department);
			 }
			 
			 if (inFaculty && inItem){
				 
				 inFaculty=false;
				faculty=new String(ch,start,length);
				 user.setFaculty(faculty);
			 }
			 
			 if (inYear && inItem){
				 
				 inYear=false;
				year=new String(ch,start,length);
				 user.setYear(year);
			 }
			 
			 if (inSemester && inItem){
				 
				 inSemester=false;
				year=new String(ch,start,length);
				 user.setYear(year);
			 }
			 
			 if (inEmail && inItem){
				 
				 inEmail=false;
				email=new String(ch,start,length);
				 user.setEmail(email);
			 }
			 if (inRegistered && inItem){
				 
				 inRegistered=false;
				registered=new String(ch,start,length);
				 user.setIsRegistered(registered);
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





