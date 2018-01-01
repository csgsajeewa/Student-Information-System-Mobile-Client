sdfsdfsd
//this activity is used to register users in the site
//link with activity_main.xml

package com.example.webclient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
develop change
asfsdfsa asfasd

public class MainActivity extends Activity implements AsyncResponse{

	private String message;//testing purposes
	private DownloadFilesTask asyncTask;
	private AlertDialog.Builder ad;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		asyncTask =new DownloadFilesTask(); // use to send data in background
		asyncTask.delegate = this;
		message = "Test";//testing purposes

		///////////////////////////dialog configuration/////////////////////
		Context context = MainActivity.this;
		String title = "UOM Info System";
		String button1String = "Go Back";
		String button2String = "Move Forward";
		String DMessage="User has already registered !!";
		 ad = new AlertDialog.Builder(context);
		 ad.setTitle(title);
		 ad.setMessage(DMessage);
		
		ad.setPositiveButton(button1String,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				// user mistakenly enter existing password ,stay in the same page
			}
		}
	   );
	
	
	
		ad.setNegativeButton(button2String,new DialogInterface.OnClickListener(){
			      public void onClick(DialogInterface dialog, int arg1) {
			    	  gotoMainWindow();
			       }
			}
	   );
		/////////////////////////////end////////////////////////////////////////////
		
	}
	
	
	@Override
	protected void onPause() {
		
		super.onPause();
		message="Test";
	}
	////testing////////
	public String getMessage() {
		return message;
	}
	////////////////////////
	public void gotoMainWindow(){
		
		Intent intent=new Intent(this,MainWindow.class);
		  startActivity(intent);
	}
	//get values from UI text fields and handle input validation
	public void submit(View view){
		
		 Toast toast;
		
		 String first_name=((EditText)findViewById(R.id.editText1)).getText().toString();
		 String last_name=((EditText)findViewById(R.id.editText2)).getText().toString();
		 String email=((EditText)findViewById(R.id.editText3)).getText().toString();
		 String semester=((EditText)findViewById(R.id.editText4)).getText().toString();
		 String year_of_study=((EditText)findViewById(R.id.editText5)).getText().toString();
		 String department=((EditText)findViewById(R.id.editText6)).getText().toString();
		 String faculty=((EditText)findViewById(R.id.editText7)).getText().toString();
		 String user_name=((EditText)findViewById(R.id.editText8)).getText().toString();
		 String password=((EditText)findViewById(R.id.editText9)).getText().toString();
		 
		 if(first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || semester.isEmpty() || year_of_study.isEmpty() ||  faculty.isEmpty() || user_name.isEmpty() || password.isEmpty()){
			  message="Please Fill All The Fields";
			 toast = Toast.makeText(this,"Please Fill All The Fields", Toast.LENGTH_LONG);
				toast.show();
		 
		 }
		 else if(faculty.equals("EFAC") && department.isEmpty()){
			 message="Please Fill Department Field";
			 toast = Toast.makeText(this,"Please Fill Department Field", Toast.LENGTH_LONG);
				toast.show();
		 }
		 else{
			 if(department.isEmpty()){
				 department="No";
			 }
			 String URL="http://192.168.42.35/WebServer/SignUp.php?first_name="+first_name+"&last_name="+last_name+"&email_address="+email+"&department="+department+"&faculty="+faculty+"&year_of_study="+year_of_study+"&semester="+semester+"&user_name="+user_name+"&password="+password+"&register=Register";
		    message=URL;
			 if(asyncTask==null){
			 asyncTask=new DownloadFilesTask();
			 asyncTask.delegate=this;
			}
		 
		 	asyncTask.execute(URL);
		 }
	}
	
	
	
	/* String-Params, the type of the parameters sent to the task upon execution.
	void- the type of the progress units published during the background computation.
	String, the type of the result of the background computation. */
	private class DownloadFilesTask extends AsyncTask<String,Void,String>{
   
		ServerConnection serverConnection=new ServerConnection();
		public AsyncResponse delegate=null;
		
		@Override
		protected String doInBackground(String... params) {
			
				String result= serverConnection.connectToServer(params[0]);
				asyncTask=null;
	            return result;
	              
	     }

		
		@Override
        protected void onPostExecute(String result) {
			
				delegate.processFinish(result);
			
       }
		
}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void processFinish(User user) {
		
		//not used
		
	}
  
	@Override
	public void processFinish(String message) {
		//there is a user with a same index number
		if(message.equals("        200")){
		ad.show();
		}
		//registration successful back to main window
		else
		{
			Intent intent=new Intent(this,MainWindow.class);
		    Toast toast = Toast.makeText(this,"Registration Is Successful", Toast.LENGTH_LONG);
			toast.show();
			startActivity(intent);
			
		}
		
		
	}

	
	
	
	

}



