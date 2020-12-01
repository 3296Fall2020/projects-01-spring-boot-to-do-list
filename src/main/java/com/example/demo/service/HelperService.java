package com.example.demo.service;

import java.time.LocalDateTime;

public class HelperService {
	boolean hasText(String str) {
		if (str != null && !str.isBlank()) {
			return true;
		}
		return false;
	}
	
	LocalDateTime setFutureDate (LocalDateTime dateTime) {
		LocalDateTime curr = LocalDateTime.now();
		
		int compareValue = dateTime.compareTo(curr);
		
		if (compareValue < 0) {
			return curr.plusDays(1);
		}
		
		return dateTime;
	}
}
