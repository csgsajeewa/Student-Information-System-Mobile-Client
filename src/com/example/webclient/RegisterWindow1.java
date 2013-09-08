package com.example.webclient;


import android.app.Activity;
import android.content.Intent;
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
  		
	   Toast.makeText(this, "Registration Suceessful EFAC", Toast.LENGTH_LONG).show();
	   //Intent intent=new Intent(this,GCMRegister.class);
	   Intent intent=new Intent(this,AccountDetailsWindow1.class);
	   intent.putExtra("com.example.webclient.isRegistered","Yes");
	   intent.putExtra("com.example.webclient.index", index);
		intent.putExtra("com.example.webclient.first_name", first_name);
		intent.putExtra("com.example.webclient.last_name", last_name);
		intent.putExtra("com.example.webclient.department", department);
		intent.putExtra("com.example.webclient.faculty",faculty);
		intent.putExtra("com.example.webclient.semester",semester);
		intent.putExtra("com.example.webclient.year", year);
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
