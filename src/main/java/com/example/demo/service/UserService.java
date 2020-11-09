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
	private StringHelper helper;
	
	public UserService () {
		this.helper = new StringHelper();
	}

	public Optional<User> getUserById(long id) {
		return repo.findById(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return repo.findUserByEmail(email);
	}

	public List<User> getAllUsers() {
		return repo.findAll();
	}

	public User createUser(String first, String last, String email, String password) {
		User user = new User();

		user.setFirst_name(first);
		user.setLast_name(last);
		user.setEmail(email);
		user.setPassword(password);
		user.setRegistration_date(new Date());
		user.setLoginStatus(false);

		return repo.save(user);
	}

	public User updateUser (Long id, String first, String last, String password) {
		User user = repo.getOne(id);
		
		if (helper.hasText(first)) {
			user.setFirst_name(first);
		}
		if (helper.hasText(last)) {
			user.setLast_name(last);
		}
		if (helper.hasText(password)) {
			user.setPassword(password);
		}
		
		return repo.save(user);
	}

	public void deleteUser(Long id) {
		Optional<User> user = repo.findById(id);
		
		if (user.isPresent()) {
			repo.deleteById(id);
		}
	}

	public void changeLoginStatus(User user) {
		user.setLoginStatus(!user.isLoginStatus());

		repo.save(user);
	}
}
