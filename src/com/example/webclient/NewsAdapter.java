package com.example.webclient;

/*
 * get new items list , send it to the list view
 */
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 branch chane
public class NewsAdapter extends BaseAdapter{
	
	private ArrayList<NewsItem>list;

	
	public NewsAdapter() {
		
	}
	
	
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int index) {
		
		return list.get(index);
	}

	@Override
	public long getItemId(int index) {
		
		return index;
	}

	@Override
	public View getView(int index, View view,ViewGroup parent) {
		
		if (view == null) {
			
			LayoutInflater inflater =LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.newslist, parent, false);
		}
		
		NewsItem news=list.get(index);
		
		TextView titleTextView=(TextView)view.findViewById(R.id.title);
		titleTextView.setTextColor(0xff000000);
		titleTextView.setText(news.getTitle());
		
		TextView descriptionTextView=(TextView)view.findViewById(R.id.description);
		descriptionTextView.setTextColor(0xff000000);
		descriptionTextView.setText(news.getDescription());
		
		
		TextView dateTextView=(TextView)view.findViewById(R.id.pubDate);
		dateTextView.setTextColor(0xff000000);
		dateTextView.setText(news.getPubDate());
		
		
		
		return view;
		
	}

	public void setList(ArrayList<NewsItem> newsItems) {
		this.list = newsItems;
		
	}

	
}
