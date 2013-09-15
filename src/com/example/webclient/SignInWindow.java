/*
 * this class is for the sign in window
 */

package com.example.webclient;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
//this window will be used for sign in purposes, functionalities yet to be implemented
import android.widget.Toast;

public class SignInWindow extends Activity implements AsyncResponse {
	private DownloadUserInfoTask downloadUserInfoTask;
	private User user; 
	private boolean isBack;
	private boolean isGoInTo; 
	private String serverMessage="test";//for testing purposes
	
	private boolean isTest;// for testing purposes
	 Context context;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_window);
		context=this;
		isBack=false;
		isTest=true;// for test purposes
		isGoInTo=false;//not used
		if(downloadUserInfoTask==null){
		 downloadUserInfoTask=new DownloadUserInfoTask();
		}
		downloadUserInfoTask.delegate=this;

	}
	//////////////////////////error check////////////////////////////////
	//I have added isTest for testing purposes . remove it when run the application if error is found
	/////////////////////////////////////////////////////////////////////////
	@Override
	protected void onStop() {
		super.onStop();
		if(!isBack && !isTest){
		String APP_PREFS="user_details";
		 
         SharedPreferences mySharedPreferences = getSharedPreferences(APP_PREFS,Activity.MODE_PRIVATE);
         SharedPreferences.Editor editor = mySharedPreferences.edit();
         editor.putString("isRegistered",user.getIsRegistered() );
         editor.putString("index",user.getIndex_number() );
         editor.putString("first_name",user.getFirst_name());
         editor.putString("last_name",user.getLast_name() );
         editor.putString("department",user.getDepartment() );
         editor.putString("faculty",user.getFaculty() );
         editor.putString("semester",user.getSemester() );
         editor.putString("year",user.getYear() );
         editor.putString("email",user.getEmail() );
         editor.apply();
		}
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		isBack=true;
		finish();
	}
	// when user press sign button ,takes the values in text fields and validate and send to server
	public void sign(View view){
		
		 String user_name=((EditText)findViewById(R.id.userName)).getText().toString();
		 String password=((EditText)findViewById(R.id.password)).getText().toString();
		 
		 Toast toast;
		
		boolean isConnected =isNetworkAvailable();
		
		//check the network connection
		if(isConnected){
		 if(user_name.equals("") || password.equals("")){
			 serverMessage="Please Fill All The Fields";
			 toast = Toast.makeText(this,"Please Fill All The Fields", Toast.LENGTH_LONG);
				toast.show();
		 }
		 else{
		 String URL="http://192.168.42.35/WebServer/SignIn.php?user_name="+user_name+"&password="+password+"&register=Register";
		 if(downloadUserInfoTask==null){
			 downloadUserInfoTask=new DownloadUserInfoTask();
			 downloadUserInfoTask.delegate=this;
			}
		 downloadUserInfoTask.execute(URL);
		 }
		}
		// display error message
		else{
			toast = Toast.makeText(this,"No Network Connection", Toast.LENGTH_LONG);
			serverMessage="No Network Connection";
			toast.show();
			isBack=true;
			finish();
		}
		 
		
	}
	
	public void goBack(View view){
		
		//is back=true, other wise onStop function try to store data in user object
		isBack=true;
		finish();
	}

	//get user details and store them in shared preferences for later use, password not stored
	@Override
	public void processFinish(User user) {
		String APP_PREFS="user_details";
		 SharedPreferences mySharedPreferences = getSharedPreferences(APP_PREFS,Activity.MODE_PRIVATE);
         SharedPreferences.Editor editor = mySharedPreferences.edit();
         editor.putString("isRegistered",user.getIsRegistered() );
         editor.putString("index",user.getIndex_number() );
         editor.putString("first_name",user.getFirst_name() );
         editor.putString("last_name",user.getLast_name() );
         editor.putString("department",user.getDepartment());
         editor.putString("faculty",user.getFaculty());
         editor.putString("semester",user.getSemester());
         editor.putString("year",user.getYear());
         editor.putString("email",user.getEmail() );
         editor.apply();
		
		Intent intent=new Intent(this,AccountDetailsWindow1.class);
		this.user=user;
		isGoInTo=true;
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
			              serverMessage=result;//for testing purposes
			              //210 - invalid sign up
			              //Error - server error
			              
			                if(result.equals("210") || (result.equals("Error"))){
			            	  downloadUserInfoTask=null;
			            	  return result;
			              }
			              
			             else {
			              try {
			            	 
			  					URL url=new URL(result);
			  					xmlParser.processFeed(url);// send URL to XML parser to pass
			  				} catch (MalformedURLException e) {
			  				
			  					e.printStackTrace();
			  				}
			              		user=xmlParser.getUserInfo();//get user information
			              		return xmlParser.getUserInfo().getFirst_name();//this is not used
			              }
				
			              
			             
				}

				
				@Override
		        protected void onPostExecute(String result) {
					
					if(result.equals("210") || result.equals("Error")){
						delegate.processFinish(result);
					}
					else{
						delegate.processFinish(user);
					}
		       }
				
				

				
				
			}

   // handles error messages in this scenario
	@Override
	public void processFinish(String message) {
		if(message.equals("Error")){
			 Toast toast = Toast.makeText(this,"Server Error- Try Later", Toast.LENGTH_LONG);
				toast.show();
		}
		else{
		 Toast toast = Toast.makeText(this,"Invalide user name or password", Toast.LENGTH_LONG);
			toast.show();
		}
		
	}
	//for testing purposes
	public String getServerMessage(){
		return serverMessage;
	}
	//for testing purposes
	public User getUser() {
		return user;
	}
	//check network connection
	public boolean isNetworkAvailable() {
	    ConnectivityManager cm = (ConnectivityManager) 
	      getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    // if no network is available networkInfo will be null
	    // otherwise check if we are connected
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	} 

}
