/*
 * only of EFAC students not for others, to select the department for registration service
 */

package com.example.webclient;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import android.widget.Toast;

public class RegisterWindow1 extends Activity implements AsyncResponse{
   
	private RegisterTask registerTask;
	private String index; //use as session variable
	private String serverMessage;//for testing purposes
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register_window_1);
		registerTask=new RegisterTask();
		registerTask.delegate=this;
		//get the index number
		String APP_PREFS="user_details";
		SharedPreferences details = getSharedPreferences(APP_PREFS, 0);
		index=details.getString("index", "");
		
		
		
	}
	
	@Override
	protected void onRestart() {
		
		super.onRestart();
		finish();
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
	}
	//use to get fac code and dept code according to  button clicks
	 public void register(View view){
		   boolean checked = ((RadioButton) view).isChecked();
			
		   String fac_code="";
		   String dept_code="";
		   if(view.getId()==R.id.deptRadio0 && checked){
			   fac_code="1"; //code for EFAC
				dept_code="0";//code for  ENTC   
			  
		   }
		 
		   else if(view.getId()==R.id.deptRadio1 && checked){
			   fac_code="1"; 
				dept_code="1";
			  
		   }
		   
		   else if(view.getId()==R.id.deptRadio2 && checked){
			   fac_code="1"; 
				dept_code="2";   
			  
		   }
		   else if(view.getId()==R.id.deptRadio3 && checked){
			   fac_code="1"; 
				dept_code="3";   
			  
		   }
		   else if(view.getId()==R.id.deptRadio4 && checked){
			   fac_code="1"; 
				dept_code="4";   
			  
		   }
		   else if (view.getId()==R.id.deptRadio5 && checked){
			   fac_code="1"; 
				dept_code="5"; 
			  
		   }
		   
		   
		   String URL="http://192.168.42.35/WebServer/Register.PHP?index="+index+"&fac_code="+fac_code+"&dept_code="+dept_code;
			 registerTask.execute(URL);
			
		
		}
   
   @Override //not used 
	public void processFinish(User user) {
		
   }
   // start the GCM register class to register the device in GCM
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
  //testing purposes 	
   public String getServerMessage() {
	return serverMessage;
}
	
}
