package com.example.webclient;



import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;
import android.widget.RadioButton;

public class RegisterWindow extends Activity implements AsyncResponse{
   
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
		
		setContentView(R.layout.register_window);
		registerTask.delegate=this;
		Intent intent = getIntent();
		 index = intent.getStringExtra("com.example.webclient.session_variable");
		 isRegistered=intent.getStringExtra("com.example.webclient.isRegistered");
		 first_name= intent.getStringExtra("com.example.webclient.first_name");
		 last_name = intent.getStringExtra("com.example.webclient.last_name");
		 department=intent.getStringExtra("com.example.webclient.department");
		 faculty=intent.getStringExtra("com.example.webclient.faculty");
		 semester=intent.getStringExtra("com.example.webclient.semester");
	     year=intent.getStringExtra("com.example.webclient.year");
		 email=intent.getStringExtra("com.example.webclient.email");
		
	
		
		
	}
	
	public void selectDept(View view){
		
		Intent intent=new Intent(this,RegisterWindow1.class);
		intent.putExtra("com.example.webclient.session_variable", index);
		intent.putExtra("com.example.webclient.isRegistered","No");
		intent.putExtra("com.example.webclient.first_name", first_name);
			intent.putExtra("com.example.webclient.last_name", last_name);
			intent.putExtra("com.example.webclient.department", department);
			intent.putExtra("com.example.webclient.faculty",faculty);
			intent.putExtra("com.example.webclient.semester",semester);
			intent.putExtra("com.example.webclient.year", year);
			intent.putExtra("com.example.webclient.email", email);
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
	   Intent intent=new Intent(this,AccountDetailsWindow1.class);
	   
	   intent.putExtra("com.example.webclient.isRegistered","Yes");
	   
		intent.putExtra("com.example.webclient.session_variable", index);
		intent.putExtra("com.example.webclient.first_name", first_name);
		intent.putExtra("com.example.webclient.last_name", last_name);
		intent.putExtra("com.example.webclient.department", department);
		intent.putExtra("com.example.webclient.faculty",faculty);
		intent.putExtra("com.example.webclient.semester",semester);
		intent.putExtra("com.example.webclient.year", year);
		
		intent.putExtra("com.example.webclient.email", email);
  		//go to account details 1 window
	   startActivity(intent);
  		
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
