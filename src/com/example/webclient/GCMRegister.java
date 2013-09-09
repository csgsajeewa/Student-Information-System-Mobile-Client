package com.example.webclient;



import java.io.IOException;

import java.util.concurrent.atomic.AtomicInteger;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class GCMRegister extends Activity {

	SendRequestTask sendRequestTask=new SendRequestTask();
	SendIDToServerTask sendIDToServerTask=new SendIDToServerTask();
	ServerConnection serverConnection=new ServerConnection();
	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String SENDER_ID = "670485556512";//project ID - project map view
    static final String TAG = "GCMDemo";

    TextView mDisplay;
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;

    String regid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.gcm);
        mDisplay = (TextView) findViewById(R.id.gcmMessage);

        context = getApplicationContext();

        // Check device for Play Services APK. If check succeeds, proceed with
        //  GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground();
            }
            else{
            	 
            	//////////////this part should actually come to regiter in background/////
            	String user_name="100470N";
         	   String URL="http://192.168.42.35/WebServer/GCMRegistration.php?user_name="+user_name+"&key="+regid;
         	   sendIDToServerTask.execute(URL);
         	   ////////////////////////////////////////////////////
            	 mDisplay.setText(regid);
            }
        } 
        else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }
		
	
//ok
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
   //ok
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
   
   //ok
   private SharedPreferences getGCMPreferences(Context context) {
	    //  app persists the registration ID in shared preferences
	    return getSharedPreferences(GCMRegister.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
   //ok
   private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	       
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
   private class SendIDToServerTask extends AsyncTask<String,Void,String>{

	   @Override
		protected String doInBackground(String... params) {
			
			
			
	              String result= serverConnection.connectToServer(params[0]);
	              
	              return result;
	              
		}

		
		@Override
       protected void onPostExecute(String result) {
			
			mDisplay.append(result );
      }
		
 }
   private class SendRequestTask extends AsyncTask<Void,Void,String>{

	   @Override
       protected String doInBackground(Void... params) {
           String msg = "";
           try {
               if (gcm == null) {
                   gcm = GoogleCloudMessaging.getInstance(context);
               }
               regid = gcm.register(SENDER_ID);
               msg = "Device registered, registration ID=" + regid;

               // You should send the registration ID to your server over HTTP,
               // so it can use GCM/HTTP or CCS to send messages to your app.
               // The request to your server should be authenticated if your app
               // is using accounts.
              // sendRegistrationIdToBackend();

               // For this demo: we don't need to send it because the device
               // will send upstream messages to a server that echo back the
               // message using the 'from' address in the message.

               // Persist the regID - no need to register again.
               storeRegistrationId(context, regid);
           } catch (IOException ex) {
               msg = "Error :" + ex.getMessage();
              
           }
           return msg;
       }
	   
	   @Override
       protected void onPostExecute(String msg) {
           mDisplay.setText(msg);
       }

	
	   
}
   private void registerInBackground() {
	   
	       //String URL="http://192.168.42.35/WebServer/SignIn.php?user_name="+user_name+"&password="+password+"&register=
	   sendRequestTask.execute();
	   String user_name="100470N";
	   String URL="http://192.168.42.35/WebServer/GCMRegistration.php?user_name="+user_name+"&key="+mDisplay.getText();
	   sendIDToServerTask.execute(URL);
	   Intent intent=new Intent(this,AccountDetailsWindow1.class);
	   intent.putExtra("com.example.webclient.isRegistered","Yes");
//  		startActivity(intent);
	    
	    /**
	     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
	     * or CCS to send messages to your app. Not needed for this demo since the
	     * device sends upstream messages to a server that echoes back the message
	     * using the 'from' address in the message.
	     */
	   
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

}
