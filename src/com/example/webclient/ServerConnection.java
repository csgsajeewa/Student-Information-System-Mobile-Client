/*
 * used to connect to the application server
 */
package com.example.webclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

sdfasdfasdf
asfsdfasdfsd
public class ServerConnection {

	
	public String connectToServer(String URL){
		
		
    	
	     String line = "";
	    	
	    	try {
	    		 HttpClient httpclient = new DefaultHttpClient();
	    		//connect to the server and get response
	    		 HttpGet httpget = new HttpGet(URL);
			     HttpResponse response = httpclient.execute(httpget);
			     //connect successfully
			    if(response != null) {
			       
			        InputStream inputstream = response.getEntity().getContent();
			        line = convertStreamToString(inputstream);
			      
			    } 
			    else {
			      
			    	line="Error"; //used by application to identify the server error
			    }
			} catch (ClientProtocolException e) {
				line="Error"; //used by application to identify the server error
			    
			} catch (IOException e) {
				line= "Error"; //used by application to identify the server error
			 
			} catch (Exception e) {
				line="Error"; //used by application to identify the server error
			  
			}
	    	
	    	
	    	return line; // return response
			
	
	
}


private String convertStreamToString(InputStream is) {
   String line = "";
   StringBuilder total = new StringBuilder();
   BufferedReader rd = new BufferedReader(new InputStreamReader(is));
   try {
       while ((line = rd.readLine()) != null) {
           total.append(line);
       }
   } catch (Exception e) {
       
   }
   return total.toString();
}

}
