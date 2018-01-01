/*
 * Register application in GCM server and send the key to application server only once
 */


package com.example.webclient;
import java.io.IOException;

import java.util.concurrent.atomic.AtomicInteger;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
develop test
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class GCMRegister extends Activity implements AsyncResponse{

	private SendRequestTask sendRequestTask;
	private SendIDToServerTask sendIDToServerTask;
	private ServerConnection serverConnection;
	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String SENDER_ID = "670485556512";//project ID - project map view
    private String GCMServerRespond; //if only app is successfully registered,we need to send that registration ID to application server
    private String index;
    private static final String TAG = "GCMRegister";// logcat purposes

    private TextView message;// to display the output
    
    private GoogleCloudMessaging gcm;
    AtomicInteger msgId; //is used to perform the atomic operation over an integer, its an alternative when you don't want to use synchornized keyword.
    SharedPreferences prefs; //used to store data
    private Context context;

    String regid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gcm);
		/////////////////////initialization//////////////////////////
		 sendRequestTask=new SendRequestTask(); // use to send registration request to GCM server.
		 sendIDToServerTask=new SendIDToServerTask();//use to send key to application server
		 serverConnection=new ServerConnection(); // get connection to application server
		 msgId = new AtomicInteger();
		 sendRequestTask.delegate=this;
		 //////////////////////////////////////////////
        
		 String APP_PREFS="user_details";
	     SharedPreferences details = getSharedPreferences(APP_PREFS, 0);//0= mode
	     index = details.getString("index", "");
        
        message = (TextView) findViewById(R.id.gcmMessage);

        context = getApplicationContext();

        // Check device for Play Services APK. If check succeeds, proceed with
        //  GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

         // app has not registered in the GCM so register in the GCM
              if (regid.isEmpty()) 
              {
                  registerInBackground();
              }
           // app has already registered   
              else{
            	  String APP_PREFS1="user_details";
       		      SharedPreferences mySharedPreferences = getSharedPreferences(APP_PREFS1,Activity.MODE_PRIVATE);
       	         SharedPreferences.Editor editor = mySharedPreferences.edit();
       	         editor.putString("isRegistered","Yes");
       	         editor.apply();
            	  
            	  Toast.makeText(this, "Registration Suceessful", Toast.LENGTH_LONG).show();
            	  finish();
              }
           
          } 
        else {
            Log.i(TAG, "No valid Google Play Services APK found.");// no google play service in the device
        }
    }
		
	
//check whether the device has google play service
   private boolean checkPlayServices() {
    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    if (resultCode != ConnectionResult.SUCCESS) {
        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
        } else {
            Log.i(TAG, "This device is not supported.");
            finish();
        }
        return false;
    }
    return true;
 }
   
   //check whether the device is already registered or not
   //if application has already installed return the registration ID or else return an empty string
   private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
   
   // application stores the registration ID in shared preferences
   private SharedPreferences getGCMPreferences(Context context) {
	    
	    return getSharedPreferences(GCMRegister.class.getSimpleName(),Context.MODE_PRIVATE);
	}
 //use to get the application version
   private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	       
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
   
   // this is used to send the registration ID to server after successful registration in GCM
   private class SendIDToServerTask extends AsyncTask<String,Void,String>{

	   @Override
		protected String doInBackground(String... params) {
			String result= serverConnection.connectToServer(params[0]);
	        return result;
	     }

		
		@Override
       protected void onPostExecute(String result) {
			
			message.append(result );
      }
		
 }
   
   //use to send registration request to the GCM server
   private class SendRequestTask extends AsyncTask<Void,Void,String>{
	   public AsyncResponse delegate=null;
	   @Override
       protected String doInBackground(Void... params) {
           String msg = "";
           try {
               if (gcm == null) {
                   gcm = GoogleCloudMessaging.getInstance(context);
               }
               regid = gcm.register(SENDER_ID);
               msg = "Device registered, registration ID=" + regid;

               //Store the registration ID  so need to register again.
               storeRegistrationId(context, regid);
           } 
           catch (IOException ex) {
               msg = "Error :" + ex.getMessage();
              
           }
           return msg;
       }
	   
	   @Override
       protected void onPostExecute(String msg) {
          message.setText(msg);
          delegate.processFinish(msg);
       }

	
	   
}
   private void registerInBackground() {
	   
	   boolean isRegisteredInGCM=false;
	   final ProgressDialog progressDialog=ProgressDialog.show(this,"Loading","Loading University News"); 
	   sendRequestTask.execute();//register application in the GCM server
	   //check the server response
	   if(GCMServerRespond.equals("Device registered, registration ID="+regid)){
	     isRegisteredInGCM=true;
		 String user_name=index;
	     String URL="http://192.168.42.35/WebServer/GCMRegistration.php?user_name="+user_name+"&key="+regid;
	     sendIDToServerTask.execute(URL);
	   }
	   progressDialog.dismiss();
	   if(isRegisteredInGCM){
		   Toast.makeText(this, "Registration Suceessful", Toast.LENGTH_LONG).show();
		   String APP_PREFS="user_details";
		   SharedPreferences mySharedPreferences = getSharedPreferences(APP_PREFS,Activity.MODE_PRIVATE);
	       SharedPreferences.Editor editor = mySharedPreferences.edit();
	       editor.putString("isRegistered","Yes");
	       editor.apply();
	   }
	   finish();
	    
	    
	   
	}
	    
	    private void storeRegistrationId(Context context, String regId) {
	        final SharedPreferences prefs = getGCMPreferences(context);
	        int appVersion = getAppVersion(context);
	        Log.i(TAG, "Saving regId on app version " + appVersion);
	        SharedPreferences.Editor editor = prefs.edit();
	        editor.putString(PROPERTY_REG_ID, regId);
	        editor.putInt(PROPERTY_APP_VERSION, appVersion);
	        editor.commit();
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	
	//not used in this context
	@Override
	public void processFinish(User user) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void processFinish(String message) {
		GCMServerRespond=message;
		
	}

}
