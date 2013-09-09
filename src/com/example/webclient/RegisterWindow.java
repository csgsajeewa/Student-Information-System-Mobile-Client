package com.example.webclient;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;
import android.widget.RadioButton;

public class RegisterWindow extends Activity implements AsyncResponse{
   
	RegisterTask registerTask=new RegisterTask();
	String index; //use as session variable
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register_window);
		registerTask.delegate=this;
		String APP_PREFS="user_details";
		SharedPreferences details = getSharedPreferences(APP_PREFS, 0);
		
		index=details.getString("index", "");
		
	
		
		
	}
	
	@Override
	protected void onRestart() {
		
		super.onRestart();
		finish();
	}
	
	public void selectDept(View view){
		
		Intent intent=new Intent(this,RegisterWindow1.class);
		startActivity(intent);
		
	
	}
	
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
   
   @Override
  	public void processFinish(String message ) {
  		
	   Toast.makeText(this, "Registration Suceessful", Toast.LENGTH_LONG).show();
	   String APP_PREFS="user_details";
	   SharedPreferences mySharedPreferences = getSharedPreferences(APP_PREFS,Activity.MODE_PRIVATE);
       SharedPreferences.Editor editor = mySharedPreferences.edit();
       editor.putString("isRegistered","Yes");
       editor.apply();
	   finish();
  		
  	}
   
   private class RegisterTask extends AsyncTask<String,Void,String>{
	   
		ServerConnection serverConnection=new ServerConnection();
		public AsyncResponse delegate=null;
		
		@Override
		protected String doInBackground(String... params) {
			 String result= serverConnection.connectToServer(params[0]);
	         return result;
	              
	              
		}

		
		@Override
       protected void onPostExecute(String result) {
			
			 delegate.processFinish(result);
      }
		
	}
  	
	
}
