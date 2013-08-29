package com.example.webclient;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainWindow extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_window);
		 
		//Button joinButton=(Button)findViewById(R.id.button2);
		//Button signInButton=(Button)findViewById(R.id.button3);
		//joinButton.setOnClickListener(new JoinButtonListener());
		//signInButton.setOnClickListener(new SignInButtonListener());
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
	
	


