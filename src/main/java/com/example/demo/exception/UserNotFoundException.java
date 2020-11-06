package com.example.demo.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {
	public UserNotFoundException(Long userId) {
		super(String.format("User not found with id: %s", userId));
	}
}
