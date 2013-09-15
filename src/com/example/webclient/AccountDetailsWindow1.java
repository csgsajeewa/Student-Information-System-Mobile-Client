//display customer details- in the profile window

package com.example.webclient;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AccountDetailsWindow1 extends Activity  implements AsyncResponse{
   
	////////////user details to display in window///////////////////
	private String index; //use as session variable -if it is not set consider as user logout from the application
	private String isRegistered;
	private String first_name;
	private String last_name;
	private String department;
	private String faculty;
	private String semester;
	private String year;
	private String email;
	private String serverMessage;
	///////////////////////////////////////////////////////////////////
	private AlertDialog.Builder ad;
	private UnregisterTask unregisterTask; // network activities cannot be done in main thread so that it has done in ASYNCTASK
	private Button b1 ;
	private Button b2;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acct_details_window1);
		////////////////////////Dialog unregister-display when user needs to unregister///////////////////////////////////
		unregisterTask=new UnregisterTask();
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
				unregister();
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
	                     
	        }
	     }
	   );
		//////////////////////////////////////////////////////////////
		String APP_PREFS="user_details";
		SharedPreferences details = getSharedPreferences(APP_PREFS, 0);// access data set by sign in window
		
		isRegistered=details.getString("isRegistered", "");
		//text of the button b1 is changed according to user details.
		//Ex- isRegistered=false ->b1 text=Register and check news button is visible
		if(isRegistered.equals("Yes")){
			
			b1= (Button)findViewById(R.id.Register);
			b1.setText("Unregister From News Service");
		
			
		}
		
		if(isRegistered.equals("No")){
			
			 b2 = (Button)findViewById(R.id.checkNews);
			b2.setVisibility(View.GONE);
		}
		//get data from shared preferences
		 index = details.getString("index", "");
		 first_name=  details.getString("first_name", "");
		 last_name =  details.getString("last_name", "");
		 department= details.getString("department", "");
		 faculty= details.getString("faculty", "");
		 semester= details.getString("semester", "");
	     year= details.getString("year", "");
		 email= details.getString("email", "");
		 //get reference to the UI elements
		TextView textView1=(TextView)findViewById(R.id.profileName1);
		TextView textView2=(TextView)findViewById(R.id.profileIndex1);
		TextView textView3=(TextView)findViewById(R.id.profileDepartment1);
		TextView textView4=(TextView)findViewById(R.id.profileFaculty1);
		TextView textView5=(TextView)findViewById(R.id.profileSemseter1);
		TextView textView6=(TextView)findViewById(R.id.profileStudyYear1);
		TextView textView7=(TextView)findViewById(R.id.profileEmail1);
		String name= first_name+" "+last_name;
		//set values to UI elements
		textView1.setText(name);
		textView2.setText(index);
		textView3.setText(department);
		textView4.setText(faculty);
		textView5.setText(semester);
		textView6.setText(year);
		textView7.setText(email);
		
		
	}
	@Override
	protected void onRestart() {
		//restore the state on restart
		super.onRestart();
		String APP_PREFS="user_details";
		SharedPreferences details = getSharedPreferences(APP_PREFS, 0);
		
		isRegistered=details.getString("isRegistered", "");
        if(isRegistered.equals("Yes")){
			
			b1= (Button)findViewById(R.id.Register);
			b1.setText("Unregister From News Service");
			b2 = (Button)findViewById(R.id.checkNews);
		    b2.setVisibility(View.VISIBLE);
		
			
		}
		
		if(isRegistered.equals("No")){
			
			b2 = (Button)findViewById(R.id.checkNews);
			b2.setVisibility(View.GONE);
		}
		
	}
	//set index=""
	public void signOut(View view)
	{
      ////////////////////shared preferences/////////////
        String APP_PREFS="user_details";
        SharedPreferences mySharedPreferences = getSharedPreferences(APP_PREFS,Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("index", "");
         editor.apply();//commit changes
       ///////////////////////////////////////////////////////////////
		
		finish();
		
	
	}
	// if user is not registered for the news service this function will register the user. 
	//if he has already registered then unregister
   public void register(View view){
	   //unregister function-show dialog
	   if(isRegistered.equals("Yes")){
		  ad.show();
		  // if user clicks move forward unregister function is used  
		   
		   
	   }
		//register function
	   if(isRegistered.equals("No")){
	   Intent intent=new Intent(this,RegisterWindow.class);
		startActivity(intent);
	   }
		
	}
   //unregister function
   public void unregister(){
	   String URL="http://192.168.42.35/WebServer/Unregister.php?index="+index;
	   if(unregisterTask==null){
		   unregisterTask=new UnregisterTask();
		   unregisterTask.delegate=this;
	   }
	   //check the network status
	   if(isNetworkAvailable()){
		unregisterTask.execute(URL); 
		
		
	   }
	   
	   
	   else{
		   Toast toast = Toast.makeText(this, "No Network Connection!", Toast.LENGTH_LONG);
			toast.show(); 
	   }
		
		
   }
   //use to display news
   public void checkNews(View view){
		
	   Intent intent=new Intent(this,NewsWindow.class);
	   startActivity(intent);
		
	
	}
  //AsynTask-class allows to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers. 
   private class UnregisterTask extends AsyncTask<String,Void,String>{
	   
		ServerConnection serverConnection=new ServerConnection();//get server connection
		public AsyncResponse delegate=null;
		
		@Override
		protected String doInBackground(String... params) {
			
			 String result= serverConnection.connectToServer(params[0]);//get server result
			 serverMessage=result;
			 return result;
			  
	     }

		
		@Override
       protected void onPostExecute(String result) {
			
			 delegate.processFinish(result);
			 unregisterTask=null;///////////////other wise when we unregister multiple times system gives an error- asyncTask has already started
			
      }
		
		
	}

@Override // from AsyncRespons interface
public void processFinish(User user) {
	//not used
	
}

@Override // from AsyncRespons interface
public void processFinish(String message) {
	//handling server connection errors
	if(message.equals("Error")){
		serverMessage="Error";
		Toast toast = Toast.makeText(this, "Server Error, Try Lator", Toast.LENGTH_LONG);
		toast.show();
	}
	else{
	   Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
	
		isRegistered="No";
		SharedPreferences mySharedPreferences = getSharedPreferences("user_details",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("isRegistered",isRegistered );
        editor.apply();
		b1.setText("Register For News Service ");
		Button b2 = (Button)findViewById(R.id.checkNews);
		b2.setVisibility(View.GONE);
		toast.show();
	}
	
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
