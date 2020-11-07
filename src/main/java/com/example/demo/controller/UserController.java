package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PostMapping(path="/add")
	public ResponseEntity<User> addNewUser (@RequestParam String email, @RequestParam String password) {
		User user = service.createUser(email, password);
		
		if (user == null) {
			return ResponseEntity.notFound().build();
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("getUser/{id}").buildAndExpand(user.getId()).toUri();
			return ResponseEntity.created(uri).body(user);
		}
	}
	
	@GetMapping(path="/login")
	public ResponseEntity<User> authorizeUser (@Email @NotNull @RequestParam String email, @Valid @NotNull @RequestParam String password) {
		Optional<User> user = service.getUserByEmail(email);
		if (user.isPresent()) {
			if (user.get().getPassword().equals(password)) {
				return ResponseEntity.ok(user.get());
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
