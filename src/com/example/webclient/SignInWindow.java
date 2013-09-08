package com.example.webclient;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
//this window will be used for sign in purposes, functionalities yet to be implemented
import android.widget.Toast;

public class SignInWindow extends Activity implements AsyncResponse {
	DownloadUserInfoTask downloadUserInfoTask;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_window);
		if(downloadUserInfoTask==null){
		 downloadUserInfoTask=new DownloadUserInfoTask();
		}
		downloadUserInfoTask.delegate=this;

	}
	
	public void sign(View view){
		
		 String user_name=((EditText)findViewById(R.id.userName)).getText().toString();
		 String password=((EditText)findViewById(R.id.password)).getText().toString();
		 String URL="http://192.168.42.35/WebServer/SignIn.php?user_name="+user_name+"&password="+password+"&register=Register";
		 if(downloadUserInfoTask==null){
			 downloadUserInfoTask=new DownloadUserInfoTask();
			 downloadUserInfoTask.delegate=this;
			}
		 downloadUserInfoTask.execute(URL);
		 
		
	}
	
	public void goBack(View view){
		
		Intent intent=new Intent(this,MainWindow.class);
		startActivity(intent);
	}

	@Override
	public void processFinish(User user) {
		
		Intent intent=new Intent(this,AccountDetailsWindow1.class);
		
		
		intent.putExtra("com.example.webclient.isRegistered",user.isRegistered);
		intent.putExtra("com.example.webclient.index", user.index_number);
		intent.putExtra("com.example.webclient.first_name", user.first_name);
		intent.putExtra("com.example.webclient.last_name", user.last_name);
		//add other details
		intent.putExtra("com.example.webclient.isRegistered", user.isRegistered);
		
		startActivity(intent);
	}
	
	
	private class DownloadUserInfoTask extends AsyncTask<String,Void,String>{
		   
				ServerConnection serverConnection=new ServerConnection();
				public AsyncResponse delegate=null;
				User user;
				@Override
				protected String doInBackground(String... params) {
					
					XMLParser xmlParser=new XMLParser();
					
					
			              String result= serverConnection.connectToServer(params[0]);
			              if(!(result.equals("210"))){
			              try {
			            	 
			  					URL url=new URL(result);
			  					xmlParser.processFeed(url);
			  				} catch (MalformedURLException e) {
			  				
			  					e.printStackTrace();
			  				}
			              		user=xmlParser.getUserInfo();
			              		return xmlParser.getUserInfo().first_name;
			              }
			              else{
			            	  downloadUserInfoTask=null;
			            	  return result;
			              }
			              
			             
				}

				
				@Override
		        protected void onPostExecute(String result) {
					
					if(result.equals("210")){
						delegate.processFinish(result);
					}
					else{
						delegate.processFinish(user);
					}
		       }
				
				

				
				
			}

   //not used
	@Override
	public void processFinish(String message) {
		 Toast toast = Toast.makeText(this,"Invalide user name or password", Toast.LENGTH_LONG);
			toast.show();
		
	}

}
