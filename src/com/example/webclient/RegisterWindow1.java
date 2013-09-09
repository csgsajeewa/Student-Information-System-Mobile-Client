package com.example.webclient;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import android.widget.Toast;
//only of EFAC students not for others
public class RegisterWindow1 extends Activity implements AsyncResponse{
   
	RegisterTask registerTask=new RegisterTask();
	String index; //use as session variable
	String isRegistered;
	String first_name;
	String last_name;
	String department;
	String faculty;
	String semester;
	String year;
	String email;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register_window_1);
		registerTask.delegate=this;
		String APP_PREFS="user_details";
		SharedPreferences details = getSharedPreferences(APP_PREFS, 0);
		index=details.getString("index", "");
		
		
		
	}
	
	 public void register(View view){
		   boolean checked = ((RadioButton) view).isChecked();
			
		   String fac_code="";
		   String dept_code="";
		   if(view.getId()==R.id.deptRadio0 && checked){
			   fac_code="1"; //code for IT
				dept_code="0";//no dept	   
			  
		   }
		 
		   else if(view.getId()==R.id.deptRadio1 && checked){
			   fac_code="1"; //code for IT
				dept_code="1";//no dept	   
			  
		   }
		   
		   else if(view.getId()==R.id.deptRadio2 && checked){
			   fac_code="1"; //code for IT
				dept_code="2";//no dept	   
			  
		   }
		   else if(view.getId()==R.id.deptRadio3 && checked){
			   fac_code="1"; //code for IT
				dept_code="3";//no dept	   
			  
		   }
		   else if(view.getId()==R.id.deptRadio4 && checked){
			   fac_code="1"; //code for IT
				dept_code="4";//no dept	   
			  
		   }
		   else if (view.getId()==R.id.deptRadio5 && checked){
			   fac_code="1"; //code for IT
				dept_code="5";//no dept	   
			  
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
