package com.example.webclient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NewsWindow extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_window);
		TextView textView=(TextView)findViewById(R.id.newsTest);
		textView.setText("Work Correctlyyyy");
	}
}
