package com.example.demo.service;

public class HelperService {
	boolean hasText(String str) {
		if (str != null && !str.isBlank()) {
			return true;
		}
		return false;
	}
}
