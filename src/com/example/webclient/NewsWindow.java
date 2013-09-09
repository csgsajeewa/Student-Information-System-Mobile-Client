package com.example.webclient;

import android.app.Activity;
import android.os.Bundle;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;


public class NewsWindow extends Activity {
	
	private  String URL; 
	private NewsHandler newsHandler;
	Handler handler;
	NewsAdapter adapter=new NewsAdapter();
	String index; //use as session variable
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_window);
        String APP_PREFS="user_details";
		SharedPreferences details = getSharedPreferences(APP_PREFS, 0);
		
		index=details.getString("index", "");
		
		URL="http://192.168.42.35/WebServer/ProvideNews.php?index="+index;
        refreshFromFeed();
        handler=new Handler();
	}
    
	
	
	
	public void onRefresh(View view){
		refreshFromFeed();
	}
	
	public void goBack(View view){
		finish();
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