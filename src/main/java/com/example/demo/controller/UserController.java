package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = "*")
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
	public ResponseEntity<User> addNewUser (@Valid @RequestBody User user) {
		
		User newUser = service.createUser(user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword()); // Creates a user object and save it to the database
		
		if (newUser == null) {
			/* Just a fallback function if somehow the creation process fails, since if the client passes 
			 * an invalid parameter (blank, null, etc.), the User object would throw a validation constraint error */
			
			return ResponseEntity.notFound().build(); 
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("getUser/{id}").buildAndExpand(newUser.getId()).toUri();
			return ResponseEntity.created(uri).body(newUser);
		}
	}
	
	@PutMapping(path="/update/{id}")
	public ResponseEntity<User> updateUser (@PathVariable("id") Long id, @RequestBody User user) {
		User updatedUser = service.updateUser(id, user.getFirst_name(), user.getLast_name(), user.getPassword());
		
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping(path="/delete/{id}")
	public ResponseEntity<User> deleteUser (@PathVariable("id") Long id) {
		service.deleteUser(id);
		
		return ResponseEntity.accepted().build();
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
