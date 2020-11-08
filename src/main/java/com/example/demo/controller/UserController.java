package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(path="/user")
public class UserController {
	
	@Autowired
	private UserService service;

	@GetMapping(path="/all")
	public List<User> getAllUsers () {
		return service.getAllUsers();
	}
	
	@GetMapping(path="getUser/{id}")
	public ResponseEntity<User> getUser (@PathVariable("id") Long id) {
		Optional<User> user = service.getUserById(id);
		
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(path="/add")
	public ResponseEntity<User> addNewUser (@Valid @RequestParam String first_name, @Valid @RequestParam String last_name, 
			@Valid @RequestParam String email, @Valid @RequestParam String password) {
		
		User user = service.createUser(first_name, last_name, email, password); // Creates a user object and save it to the database
		
		if (user == null) {
			/* Just a fallback function if somehow the creation process fails, since if the client passes 
			 * an invalid parameter (blank, null, etc.), the User object would throw a validation constraint error */
			
			return ResponseEntity.notFound().build(); 
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("getUser/{id}").buildAndExpand(user.getId()).toUri();
			return ResponseEntity.created(uri).body(user);
		}
	}
	
	@GetMapping(path="/update")
	public ResponseEntity<User> updateUser () {
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(path="/delete")
	public ResponseEntity<User> deleteUser () {
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(path="/login")
	public ResponseEntity<User> authorizeUser (@Valid @RequestParam String email, @Valid @RequestParam String password) {
		Optional<User> user = service.getUserByEmail(email);
		if (user.isPresent()) {
			User access = user.get();
			
			if (access.getPassword().equals(password)) {
				if (access.isLoginStatus()) { // If the user is already logged in
					return ResponseEntity.noContent().build();
				}
				service.changeLoginStatus(access);
				return ResponseEntity.ok(user.get());
			}
			return ResponseEntity.badRequest().build(); // If the user's password doesn't match what is on the database
		}
		return ResponseEntity.notFound().build(); // If the user cannot be found
	}
	
	@GetMapping(path="/logout")
	public ResponseEntity<User> logout (@RequestParam long id) {
		Optional<User> user = service.getUserById(id);
		
		if (user.isPresent()) {
			User access = user.get();
			
			if (access.isLoginStatus()) {
				service.changeLoginStatus(access);
				return ResponseEntity.accepted().build();
			}
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
