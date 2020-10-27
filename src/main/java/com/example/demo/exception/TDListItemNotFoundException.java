package com.example.demo.exception;

@SuppressWarnings("serial")
public class TDListItemNotFoundException extends Exception {
	private static long TDListItemId;

	public TDListItemNotFoundException(Long tDListItemId) {
		super(String.format("Item not found with id: %s", TDListItemId));
	}
}
