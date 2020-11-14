package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Lists;
import com.example.demo.model.User;
import com.example.demo.repository.ListRepository;
import com.example.demo.repository.UserRepository;

/* Helper object that provides access to different functions that will be referenced often to different classes */
@Component
public class HelperService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ListRepository listRepo;
	
	public boolean hasText(String str) {
		if (str != null && !str.isBlank()) {
			return true;
		}
		return false;
	}
	
	public Optional<User> getUserById(Long id) {
		return userRepo.findById(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepo.findUserByEmail(email);
	}
	
	public Optional<Lists> getListById (Long id) {
		return listRepo.findById(id);
	}
}
