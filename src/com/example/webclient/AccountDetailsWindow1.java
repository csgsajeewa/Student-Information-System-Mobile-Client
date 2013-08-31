package com.example.webclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AccountDetailsWindow1 extends Activity {

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acct_details_window1);
		Intent intent = getIntent();
		String index = intent.getStringExtra("com.example.webclient.index");
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
}
