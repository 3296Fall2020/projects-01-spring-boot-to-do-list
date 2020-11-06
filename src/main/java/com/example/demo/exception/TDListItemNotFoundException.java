package com.example.demo.exception;

@SuppressWarnings("serial")
public class TDListItemNotFoundException extends Exception {
	private static long TDListItemId;
	
	// Exception that occurs when a given item is not found. Initialized with each type of modeling item
	public TDListItemNotFoundException(Long tDListItemId) {
		super(String.format("Item not found with id: %s", TDListItemId));
	}
}
