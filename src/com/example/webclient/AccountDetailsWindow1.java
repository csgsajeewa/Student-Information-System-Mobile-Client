package com.example.webclient;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccountDetailsWindow1 extends Activity {
   
	
	String index; //use as session variable
	String isRegistered;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acct_details_window1);
		
		Intent intent = getIntent();
		isRegistered=intent.getStringExtra("com.example.webclient.isRegistered");
		if(isRegistered.equals("Yes")){
			
			Button b = (Button)findViewById(R.id.Register);
			b.setText("Dergister From News Service");
		
			//b.setVisibility(View.GONE);
		}
		
		if(isRegistered.equals("No")){
			
			Button b = (Button)findViewById(R.id.checkNews);
			b.setVisibility(View.GONE);
		}
		
		 index = intent.getStringExtra("com.example.webclient.index");
		String first_name= intent.getStringExtra("com.example.webclient.first_name");
		String last_name = intent.getStringExtra("com.example.webclient.last_name");
		TextView textView1=(TextView)findViewById(R.id.subheading4);
		TextView textView2=(TextView)findViewById(R.id.subheading5);
		TextView textView3=(TextView)findViewById(R.id.subheading6);
		textView1.setText(index);
		textView2.setText(first_name);
		textView3.setText(last_name);
		
		
	}
	
	public void signOut(View view){
		
		Intent intent=new Intent(this,MainWindow.class);
		startActivity(intent);
		
	
	}
	// if user is not registered for the news service this function will register the user. 
	//if he has already registered then check news
   public void register(View view){
	   //deregister function
	   if(isRegistered.equals("Yes")){
		   //to be implemented
		   
	   }
		//register function
	   if(isRegistered.equals("No")){
	   Intent intent=new Intent(this,RegisterWindow.class);
		intent.putExtra("com.example.webclient.session_variable", index);
		startActivity(intent);
	   }
		
	}
   
   public void checkNews(View view){
		//news window to be implemented
	   Intent intent=new Intent(this,NewsWindow.class);
		intent.putExtra("com.example.webclient.session_variable", index);
		startActivity(intent);
		
	
	}
   
  
   
 }
