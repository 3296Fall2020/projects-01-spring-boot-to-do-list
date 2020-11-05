package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;
	
	private String email;
	
	private String user_password;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E MMM dd HH:mm:ss yyyy")
	private Date registration_date;
	
	public User () {
		
	}
	
	public int getId() {
		return user_id;
	}

	public void setId(int id) {
		this.user_id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return user_password;
	}

	public void setPassword(String password) {
		this.user_password = password;
	}

	public Date getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}
	
	public String toString () {
		return "User " + user_id + ", with email address " + email;
	}
}
