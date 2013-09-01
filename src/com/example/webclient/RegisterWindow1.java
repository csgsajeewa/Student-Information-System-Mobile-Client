package com.example.webclient;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterWindow1 extends Activity implements AsyncResponse{
   
	RegisterTask registerTask=new RegisterTask();
	String index; //use as session variable
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acct_details_window1);
		registerTask.delegate=this;
		Intent intent = getIntent();
		 index = intent.getStringExtra("com.example.webclient.index");
		String first_name= intent.getStringExtra("com.example.webclient.first_name");
		String last_name = intent.getStringExtra("com.example.webclient.last_name");
		TextView textView1=(TextView)findViewById(R.id.subheading4);
		TextView textView2=(TextView)findViewById(R.id.subheading5);
		TextView textView3=(TextView)findViewById(R.id.subheading6);
		textView1.setText(index);
		textView2.setText(first_name);
		textView3.setText(last_name);
		
		
	}
	
	public void signOut(View view){
		
		Intent intent=new Intent(this,MainWindow.class);
		startActivity(intent);
		
	
	}
	
   public void register(View view){
		
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
