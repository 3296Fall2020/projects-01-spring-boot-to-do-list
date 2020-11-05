package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.UserRepository;

import com.example.demo.model.User;

@RestController
@RequestMapping(path="/user")
public class UserController {
	@Autowired
	private UserRepository repo;

	@GetMapping(path="/all")
	public List<User> getAllUsers () {
		return repo.findAll();
	}
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewUser (@RequestParam String email, @RequestParam String password) {
		User user = new User();
		
		user.setEmail(email);
		user.setPassword(password);
		user.setRegistration_date(new Date());
		repo.save(user);
		
		return "Saved user";
	}
	
	@GetMapping(path="/login")
	public @ResponseBody String authorizeUser (@RequestParam String email, @RequestParam String password) {
		
		
		
		
		return "";
	}
}
