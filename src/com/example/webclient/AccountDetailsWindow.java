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
		String index = intent.getStringExtra("com.example.webclient.index");
		String first_name= intent.getStringExtra("com.example.webclient.first_name");
		String last_name = intent.getStringExtra("com.example.webclient.last_name");
		TextView textView1=(TextView)findViewById(R.id.subheading1);
		TextView textView2=(TextView)findViewById(R.id.subheading2);
		TextView textView3=(TextView)findViewById(R.id.subheading3);
		textView1.setText(index);
		textView2.setText(first_name);
		textView3.setText(last_name);
		
		
	}
	
	public void signIn(View view){
		
		Intent intent=new Intent(this,SignInWindow.class);
		startActivity(intent);
		
	
	}

}
