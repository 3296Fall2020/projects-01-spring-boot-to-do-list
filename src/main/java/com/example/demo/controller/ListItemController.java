package com.example.demo.controller;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.ListItem;
import com.example.demo.service.ListItemService;

// Rest Controller, this portion takes in HTTP Requests made with a given URL, and then returns items depending on what request was made
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/item")
public class ListItemController {
	
	@Autowired
	private ListItemService service;
	
	// e.g. http://localhost:8080/all
	@GetMapping("/all")
	public List<ListItem> getAllItems () {
		return service.getAllItems();
	}
	
	/*
	@GetMapping("/userItems")
	public List<TDListItem> getItemsByUser (@RequestParam String email) {
		
	}
	*/
	
	// e.g. http://localhost:8080/getItem/1
	@GetMapping("/getItem/{id}")
	public ResponseEntity<ListItem> getItem (@PathVariable("id") Long id) {
		Optional<ListItem> item = service.getItemById(id);
		
		if (item.isPresent()) {
			return ResponseEntity.ok(item.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(path="/add")
	public ResponseEntity<ListItem> addNewItem (@Valid @RequestBody ListItem item) throws ParseException {
		ListItem newItem = service.createItem(item.getTask_name(), item.getDescription(), item.getDeadline());
		
		if (newItem == null) {
			return ResponseEntity.notFound().build(); 
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("getItem/{id}").buildAndExpand(newItem.getId()).toUri();
			return ResponseEntity.created(uri).body(newItem);
		}
	}
	
	// e.g. http://localhost:8080/update/1
	@PostMapping("update/{id}")
	public ResponseEntity<ListItem> updateItem (@PathVariable("id") Long id, @RequestBody ListItem item) {
		ListItem updatedItem = service.updateItem(id, item.getTask_name(), item.getDescription(), item.getDeadline());
		
		if (updatedItem != null) {
			return ResponseEntity.ok(updatedItem);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	// e.g. http://localhost:8080/removeitem/1
	@DeleteMapping("removeitem/{id}")
	public ResponseEntity<ListItem> removeItem(@PathVariable("id") Long id) {
		service.deleteItem(id);
		
		return ResponseEntity.accepted().build();
	}
	
	// e.g. http://localhost:8080/checkFinished/1
	@PostMapping("checkFinished/{id}")
	public ResponseEntity<ListItem> checkOffItem (@PathVariable("id") Long id, @RequestBody ListItem item) {
		ListItem finishedItem = service.configureCompletion(id, true);
		
		if (finishedItem != null) {
			return ResponseEntity.ok(finishedItem);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// e.g. http://localhost:8080/uncheck/1
	@PostMapping("uncheck/{id}")
	public ResponseEntity<ListItem> uncheckItem (@PathVariable("id") Long id, @RequestBody ListItem item) {
		ListItem unfinishedItem = service.configureCompletion(id, false);
		
		if (unfinishedItem != null) {
			return ResponseEntity.ok(unfinishedItem);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
