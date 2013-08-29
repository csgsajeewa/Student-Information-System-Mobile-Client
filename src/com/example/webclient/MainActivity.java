package com.example.webclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class MainActivity extends Activity implements AsyncResponse{

	
	DownloadFilesTask asyncTask =new DownloadFilesTask();
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		asyncTask.delegate = this;
		
		
		//=(TextView)findViewById(R.id.textView4);
	   // Button button = (Button) findViewById(R.id.button1);
       // button.setOnClickListener(new bbt());
             
		 
		
	}
	
	public void submit(View view){
		
		 String first_name=((EditText)findViewById(R.id.editText1)).getText().toString();
		 String last_name=((EditText)findViewById(R.id.editText2)).getText().toString();
		 String email=((EditText)findViewById(R.id.editText3)).getText().toString();
		 String semester=((EditText)findViewById(R.id.editText4)).getText().toString();
		 String year_of_study=((EditText)findViewById(R.id.editText5)).getText().toString();
		 String department=((EditText)findViewById(R.id.editText6)).getText().toString();
		 String faculty=((EditText)findViewById(R.id.editText7)).getText().toString();
		 String user_name=((EditText)findViewById(R.id.editText8)).getText().toString();
		 String password=((EditText)findViewById(R.id.editText9)).getText().toString();
		 String URL="http://192.168.42.35/WebServer/SignUp.php?first_name="+first_name+"&last_name="+last_name+"&email_address="+email+"&department="+department+"&faculty="+faculty+"&year_of_study="+year_of_study+"&semester="+semester+"&user_name="+user_name+"&password="+password+"&register=Register";
		 asyncTask.execute(URL);
	}
	
	
	
	
	private class DownloadFilesTask extends AsyncTask<String,Void,String>{

		
		public AsyncResponse delegate=null;
		@Override
		protected String doInBackground(String... params) {
			
	               return connectToServer(params[0]);
	              
		}

		
		@Override
        protected void onPostExecute(String result) {
			
			 delegate.processFinish(result);
       }
		
		

		
		
	}
	
	
	
	private String connectToServer(String URL){
		
		
		    	
		     String line = "";
		    	
		    	try {
		    		 HttpClient httpclient = new DefaultHttpClient();
		    		 //http://localhost/WebServer/SignUp.php?first_name=chamath&last_name=sajeewa&email_address=csgsajeewa%40gmail.com&department=CSE&faculty=engineering&year_of_study=2013&semester=3&user_name=chamath&password=chamath&register=Register
		    		//http://chamath.byethost15.com/WebServer/index.php?first_name=chamath&last_name=sajeewa
		    		 HttpGet httpget = new HttpGet(URL);
				     HttpResponse response = httpclient.execute(httpget);
				     
				    if(response != null) {
				       
				        InputStream inputstream = response.getEntity().getContent();
				        line = convertStreamToString(inputstream);
				       // Toast.makeText(this, line, Toast.LENGTH_SHORT).show();
				    } 
				    else {
				       // Toast.makeText(this, "Unable to complete your request", Toast.LENGTH_LONG).show();
				    	line="Unable to complete your request";
				    }
				} catch (ClientProtocolException e) {
					line="pro";
				    //Toast.makeText(this, "Caught ClientProtocolException", Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					line= "IO";
				   // Toast.makeText(this, "Caught IOException", Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					line="Exception";
				  //  Toast.makeText(this, "Caught Exception", Toast.LENGTH_LONG).show();
				}
		    	//textView.setText(line);
		    	
		    	return line;
				
		
		
	}
	
	private String convertStreamToString(InputStream is) {
	    String line = "";
	    StringBuilder total = new StringBuilder();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    try {
	        while ((line = rd.readLine()) != null) {
	            total.append(line);
	        }
	    } catch (Exception e) {
	        Toast.makeText(this, "Stream Exception", Toast.LENGTH_SHORT).show();
	    }
	    return total.toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void processFinish(String output) {
		Intent intent=new Intent(this,AccountDetailsWindow.class);
		intent.putExtra("com.example.webclient.SERVER_MESSAGE", output);
		startActivity(intent);
		
	}

}



