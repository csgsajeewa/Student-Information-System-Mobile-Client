package com.example.webclient;

import android.app.Activity;
import android.os.Bundle;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;


public class NewsWindow extends Activity {
	
	private  String URL; 
	private NewsHandler newsHandler;
	Handler handler;
	NewsAdapter adapter=new NewsAdapter();
	String index; //use as session variable
	String isRegistered;
	String first_name;
	String last_name;
	String department;
	String faculty;
	String semester;
	String year;
	String email;
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_window);
        Intent intent = getIntent();
		index = intent.getStringExtra("com.example.webclient.session_variable");
		first_name= intent.getStringExtra("com.example.webclient.first_name");
		 last_name = intent.getStringExtra("com.example.webclient.last_name");
		 department=intent.getStringExtra("com.example.webclient.department");
		 faculty=intent.getStringExtra("com.example.webclient.faculty");
		 semester=intent.getStringExtra("com.example.webclient.semester");
	     year=intent.getStringExtra("com.example.webclient.year");
		email=intent.getStringExtra("com.example.webclient.email");
		isRegistered="Yes";
		URL="http://192.168.42.35/WebServer/ProvideNews.php?index="+index;
        refreshFromFeed();
        handler=new Handler();
	}
    
	
	
	
	public void onRefresh(View view){
		refreshFromFeed();
	}
	
	public void goBack(View view){
		Intent intent=new Intent(this,AccountDetailsWindow1.class);
		intent.putExtra("com.example.webclient.index", index);
		
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
	
	
	private void refreshFromFeed() {
		
		final ProgressDialog progressDialog=ProgressDialog.show(this,"Loading","Loading University News");
		
		Thread th=new Thread(){
			 public void run(){
				 
				newsHandler = new NewsHandler();
					try {
						 newsHandler.processFeed(new URL(URL));
						  
						
					} catch (MalformedURLException e) {
						
						e.printStackTrace();
					}
					
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							
							adapter.setList(newsHandler.getNewsItems());
							ListView list=(ListView)findViewById(R.id.newsList);
							list.setAdapter(adapter);
							progressDialog.dismiss();
							
						}
					});
					
				 
			 }
		};
		
		th.start();
		
		
	}
    
}