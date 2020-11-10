package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long user_id;
	
	@NotBlank(message = "First name should be provided")
	private String first_name;
	
	@NotBlank(message = "Last name should be provided")
	private String last_name;
	
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Must be in an Email address format")
	private String email;
	
	@NotBlank(message = "Password must be provided")
	private String user_password;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E MMM dd HH:mm:ss yyyy")
	private Date registration_date;
	
	private boolean login_status;
	
	public User () {
		
	}
	
	public long getId() {
		return user_id;
	}

	public void setId(long id) {
		this.user_id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
	
	public boolean isLoginStatus() {
		return login_status;
	}

	public void setLoginStatus(boolean login_status) {
		this.login_status = login_status;
	}

	public String toString () {
		return "User " + user_id + ", with email address " + email;
	}
}
