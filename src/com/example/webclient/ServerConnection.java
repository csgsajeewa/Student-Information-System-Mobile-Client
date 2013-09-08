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

//used to connect to the server

public class ServerConnection {

	
	public String connectToServer(String URL){
		
		
    	
	     String line = "";
	    	
	    	try {
	    		 HttpClient httpclient = new DefaultHttpClient();
	    		
	    		 HttpGet httpget = new HttpGet(URL);
			     HttpResponse response = httpclient.execute(httpget);
			     
			    if(response != null) {
			       
			        InputStream inputstream = response.getEntity().getContent();
			        line = convertStreamToString(inputstream);
			      
			    } 
			    else {
			       // Toast.makeText(this, "Unable to complete your request", Toast.LENGTH_LONG).show();
			    	line="Unable to complete your request";
			    }
			} catch (ClientProtocolException e) {
				line="pro";
			    //Toast.makeText(this, "Caught ClientProtocolException", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				line= "IO";
			   // Toast.makeText(this, "Caught IOException", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				line="Exception";
			  //  Toast.makeText(this, "Caught Exception", Toast.LENGTH_LONG).show();
			}
	    	//textView.setText(line);
	    	
	    	return line;
			
	
	
}

public String convertEmail(String email){
	
	int ascii_value;
	for(int i=0;i<email.length();i++){
		ascii_value=email.charAt(i);
		if(ascii_value==64){
			
			
		}
	}
	
	return email;
	
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
