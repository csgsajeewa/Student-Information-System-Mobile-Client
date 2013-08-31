package com.example.webclient;

public class User {
	
	String index_number;
	String first_name;
	String last_name;

	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	public void setIndex_number(String index_number) {
	this.index_number = index_number;
	}
	
	public String getFirst_name() {
		return first_name;
	}
	
	public String getLast_name() {
		return last_name;
	}
	
	public String getIndex_number() {
		return index_number;
	}

}
