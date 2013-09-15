/*
 * use to store data about users
 */
package com.example.webclient;

public class User {
	
	private String index_number;
	private String first_name;
	private String last_name;
	private String department;
	private String faculty;
	private String year;
	private String semester;
	private String email;
	private String isRegistered;

	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	public void setIndex_number(String index_number) {
	this.index_number = index_number;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public void setIsRegistered(String isRegistered) {
		this.isRegistered = isRegistered;
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
	
	public String getDepartment() {
		return department;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getFaculty() {
		return faculty;
	}
	
	public String getSemester() {
		return semester;
	}
	
	public String getYear() {
		return year;
	}
	
	public String getIsRegistered() {
		return isRegistered;
	}

}
