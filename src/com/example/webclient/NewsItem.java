package com.example.webclient;
// store news item details

public class NewsItem {
	
	private String title;
	
	private String description;//xml - details
	private String pubDate;//xml -date
	
	public String getDescription() {
		return description;
	}
	
	
	
	public String getPubDate() {
		return pubDate;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
