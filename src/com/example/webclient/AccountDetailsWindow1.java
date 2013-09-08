package com.example.webclient;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//display customer details
public class AccountDetailsWindow1 extends Activity  implements AsyncResponse{
   
	
	String index; //use as session variable
	String isRegistered;
	String first_name;
	String last_name;
	String department;
	String faculty;
	String semester;
	String year;
	String email;
	AlertDialog.Builder ad;
	UnregisterTask unregisterTask=new UnregisterTask();
	Button b1 ;
	Button b2;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acct_details_window1);
		////////////////////////Dialog unregister///////////////////////////////////
		unregisterTask.delegate=this;
		Context context = AccountDetailsWindow1.this;
		String title = "UOM Info System";
		String message = "Do you really want to deregister from the news service !!";
		String button1String = "Unregister";
		String button2String = "Go Back";
		
		 ad = new AlertDialog.Builder(context);
		ad.setTitle(title);
		ad.setMessage(message);
		
		ad.setPositiveButton(button1String,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				unregister();// this function to be implemented 
			}
		}
	   );
	
	
	
		ad.setNegativeButton(button2String,new DialogInterface.OnClickListener(){
			      public void onClick(DialogInterface dialog, int arg1) {
			                 // do nothing user has decided not to unregister
			       }
			}
	   );
	
		ad.setCancelable(true);
		ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
	            public void onCancel(DialogInterface dialog) {
	                     // eatenByGrue(); this function is not required
	        }
	     }
	   );
		//////////////////////////////////////////////////////////////
		Intent intent = getIntent();
		isRegistered=intent.getStringExtra("com.example.webclient.isRegistered");
		if(isRegistered.equals("Yes")){
			
			b1= (Button)findViewById(R.id.Register);
			b1.setText("Unregister From News Service");
		
			//b.setVisibility(View.GONE);
		}
		
		if(isRegistered.equals("No")){
			
			 b2 = (Button)findViewById(R.id.checkNews);
			b2.setVisibility(View.GONE);
		}
		
		 index = intent.getStringExtra("com.example.webclient.index");
		 first_name= intent.getStringExtra("com.example.webclient.first_name");
		 last_name = intent.getStringExtra("com.example.webclient.last_name");
		 department=intent.getStringExtra("com.example.webclient.department");
		 faculty=intent.getStringExtra("com.example.webclient.faculty");
		 semester=intent.getStringExtra("com.example.webclient.semester");
	     year=intent.getStringExtra("com.example.webclient.year");
		email=intent.getStringExtra("com.example.webclient.email");
		TextView textView1=(TextView)findViewById(R.id.profileName1);
		TextView textView2=(TextView)findViewById(R.id.profileIndex1);
		TextView textView3=(TextView)findViewById(R.id.profileDepartment1);
		TextView textView4=(TextView)findViewById(R.id.profileFaculty1);
		TextView textView5=(TextView)findViewById(R.id.profileSemseter1);
		TextView textView6=(TextView)findViewById(R.id.profileStudyYear1);
		TextView textView7=(TextView)findViewById(R.id.profileEmail1);
		String name= first_name+" "+last_name;
		textView1.setText(name);
		textView2.setText(index);
		textView3.setText(department);
		textView4.setText(faculty);
		textView5.setText(semester);
		textView6.setText(year);
		textView7.setText(email);
		
		
	}
	
	public void signOut(View view){
		
		Intent intent=new Intent(this,MainWindow.class);
		startActivity(intent);
		
	
	}
	// if user is not registered for the news service this function will register the user. 
	//if he has already registered then check news
   public void register(View view){
	   //unregister function-show dialog
	   if(isRegistered.equals("Yes")){
		  ad.show();
		  // if user clicks move forward unregister function is used  
		   
		   
	   }
		//register function
	   if(isRegistered.equals("No")){
	   Intent intent=new Intent(this,RegisterWindow.class);
		intent.putExtra("com.example.webclient.session_variable", index);
		intent.putExtra("com.example.webclient.isRegistered",isRegistered);
		
		intent.putExtra("com.example.webclient.first_name", first_name);
		intent.putExtra("com.example.webclient.last_name", last_name);
		intent.putExtra("com.example.webclient.department", department);
		intent.putExtra("com.example.webclient.faculty", faculty);
		intent.putExtra("com.example.webclient.semester", semester);
		intent.putExtra("com.example.webclient.year", year);
		
		intent.putExtra("com.example.webclient.email",email);
		startActivity(intent);
	   }
		
	}
   public void unregister(){
	   String URL="http://192.168.42.35/WebServer/Unregister.php?index="+index;
		unregisterTask.execute(URL); 
		isRegistered="No";
		b1.setText("Register For News Service ");
		Button b2 = (Button)findViewById(R.id.checkNews);
			b2.setVisibility(View.GONE);
   }
   
   public void checkNews(View view){
		//news window to be implemented
	   Intent intent=new Intent(this,NewsWindow.class);
		intent.putExtra("com.example.webclient.session_variable", index);
		
		intent.putExtra("com.example.webclient.isRegistered",isRegistered);
		
		intent.putExtra("com.example.webclient.first_name", first_name);
		intent.putExtra("com.example.webclient.last_name", last_name);
		intent.putExtra("com.example.webclient.department", department);
		intent.putExtra("com.example.webclient.faculty", faculty);
		intent.putExtra("com.example.webclient.semester", semester);
		intent.putExtra("com.example.webclient.year", year);
		
		intent.putExtra("com.example.webclient.email",email);
		startActivity(intent);
		
	
	}
   
   private class UnregisterTask extends AsyncTask<String,Void,String>{
	   
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

@Override
public void processFinish(User user) {
	// TODO Auto-generated method stub
	
}

@Override
public void processFinish(String message) {
	Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
	toast.show();
	
}

   
  
   
 }
