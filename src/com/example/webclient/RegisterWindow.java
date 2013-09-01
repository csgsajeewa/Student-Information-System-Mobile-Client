package com.example.webclient;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterWindow extends Activity implements AsyncResponse{
   
	RegisterTask registerTask=new RegisterTask();
	String index; //use as session variable
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acct_details_window1);
		registerTask.delegate=this;
		Intent intent = getIntent();
		 index = intent.getStringExtra("com.example.webclient.session_variable");
		
	
		
		
	}
	
	public void selectDept(View view){
		
		Intent intent=new Intent(this,RegisterWindow1.class);
		intent.putExtra("com.example.webclient.session_variable", index);
		startActivity(intent);
		
	
	}
	
   public void register(View view){
		
	   if(view.getId()==R.id.){
		   
	   }
	   String URL="http://192.168.42.35/WebServer/SignIn.php?user_name="+index;
		 registerTask.execute(URL);
		
	
	}
   
   @Override //not used 
	public void processFinish(User user) {
		
   }
   
   @Override
  	public void processFinish(String message ) {
  		
  		
  		
  		
  		
  	}
   
   private class RegisterTask extends AsyncTask<String,Void,String>{
	   
		ServerConnection serverConnection=new ServerConnection();
		public AsyncResponse delegate=null;
		User user;
		@Override
		protected String doInBackground(String... params) {
			
			XMLParser xmlParser=new XMLParser();
			
			
	              String result= serverConnection.connectToServer(params[0]);
	              try {
	            	 
	  				URL url=new URL(result);
	  				xmlParser.processFeed(url);
	  			} catch (MalformedURLException e) {
	  				
	  				e.printStackTrace();
	  			}
	              user=xmlParser.getUserInfo();
	  			return xmlParser.getUserInfo().first_name;
	              
	              
		}

		
		@Override
       protected void onPostExecute(String result) {
			
			 delegate.processFinish(user);
      }
		
	}
  	
	
}
