package com.example.webclient;
//this window will show the account detail after a successfull registration
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AccountDetailsWindow extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acct_details_window);
		 
		Intent intent = getIntent();
		String message = intent.getStringExtra("com.example.webclient.SERVER_MESSAGE");
		TextView textView=(TextView)findViewById(R.id.serverMessage);
		textView.setText(message);
		
	}
	
	public void signIn(View view){
		
		Intent intent=new Intent(this,SignInWindow.class);
		startActivity(intent);
		
	
	}

}
