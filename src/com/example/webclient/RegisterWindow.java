/*
 * this is used to display news registration details to user
 * EFAC student need to go to "RegisterWindow1" to select dept"
 */
package com.example.webclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class RegisterWindow extends Activity implements AsyncResponse{
   
   private RegisterTask registerTask;
   private String index; //use as session variable
   private String serverMessage;//for testing purposes
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register_window);
		registerTask=new RegisterTask();
		registerTask.delegate=this;
		String APP_PREFS="user_details";
		// retrieve index value from shared preferences 
		SharedPreferences details = getSharedPreferences(APP_PREFS, 0);
		
		index=details.getString("index", "");
		
	
		
		
	}
	
	@Override
	protected void onRestart() {
		
		super.onRestart();
		finish();
	}
	
	@Override
	protected void onPause() {
	
		super.onPause();
	}
	// user clicks EFAC button
	public void selectDept(View view){
		
		Intent intent=new Intent(this,RegisterWindow1.class);
		startActivity(intent);
		
	
	}
	//user clicks the Archi/IT button
   public void register(View view){
	   boolean checked = ((RadioButton) view).isChecked();
		
	   String fac_code="";
	   String dept_code="";
	   if(view.getId()==R.id.facRadio2 && checked){
		   fac_code="2"; //code for IT
			dept_code="0";//no department	   
		  
	   }
	 
	   if(view.getId()==R.id.facRadio3 && checked){
		   fac_code="3"; //code for Architecture
			dept_code="0";//no department	   
		  
	   }
	   String URL="http://192.168.42.35/WebServer/Register.PHP?index="+index+"&fac_code="+fac_code+"&dept_code="+dept_code;
		 registerTask.execute(URL);
		
	
	}
   
   @Override //not used 
	public void processFinish(User user) {
		
   }
   //start the GCM class to register device in the GCM in server
   @Override
  	public void processFinish(String message ) {
  		
	 
	   Intent intent =new Intent(this,GCMRegister.class);
		 startActivity(intent);
  		
  	}
   
   private class RegisterTask extends AsyncTask<String,Void,String>{
	   
		ServerConnection serverConnection=new ServerConnection();
		public AsyncResponse delegate=null;
		
		@Override
		protected String doInBackground(String... params) {
			 String result= serverConnection.connectToServer(params[0]);
			 serverMessage=result;
	         return result;
	              
	              
		}

		
		@Override
       protected void onPostExecute(String result) {
			
			 delegate.processFinish(result);
      }
		
	}
   
   //for testing
   public String getServerMessage() {
	return serverMessage;
   }
  	
	
}
