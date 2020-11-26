package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class HelperService {
	boolean hasText(String str) {
		if (str != null && !str.isBlank()) {
			return true;
		}
		return false;
	}
	
	LocalDateTime convertToUTC(LocalDateTime date) {
		ZonedDateTime zonedDate = date.atZone(ZoneId.systemDefault());
		ZonedDateTime utc = zonedDate.withZoneSameInstant(ZoneId.of("UTC"));
		return utc.toLocalDateTime();
	}
}
