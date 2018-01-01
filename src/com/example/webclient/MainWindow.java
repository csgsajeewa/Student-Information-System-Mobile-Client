//start window of the application 
package com.example.webclient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


develop remote changes

public class MainWindow extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_window);
		 
		
	}gi
	asfasdfasdf
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}sdaf
	
		//join button pressed - start MainActivity
		public void join(View view){
			
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			
		}
		
		//sign in button pressed- start SignInWindow
		public void signIn(View view){
			
			Intent intent = new Intent(this, SignInWindow.class);
			startActivity(intent);
			
		}
		
}
	
	


