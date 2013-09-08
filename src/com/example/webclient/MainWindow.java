package com.example.webclient;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//start window of the application no errors

public class MainWindow extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_window);
		 
		
	}
		
		public void join(View view){
			
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			
		}
		
		
		public void signIn(View view){
			
			Intent intent = new Intent(this, SignInWindow.class);
			startActivity(intent);
			
		}
}
	
	


