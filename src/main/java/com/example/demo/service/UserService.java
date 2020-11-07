package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Component
public class UserService {
	@Autowired
	private UserRepository repo;
	
	public Optional<User> getUserByEmail (String email) {
		return repo.findUserByEmail(email);
	}
	
	public List<User> getAllUsers () {
		return repo.findAll();
	}
	
	public User createUser (String email, String password) {
		User user = new User();
		
		user.setEmail(email);
		user.setPassword(password);
		user.setRegistration_date(new Date());
		
		repo.save(user);
		
		return user;
	}
}
