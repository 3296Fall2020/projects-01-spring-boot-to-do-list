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

import com.example.demo.model.TDListItem;
import com.example.demo.service.ListItemService;

// Rest Controller, this portion takes in HTTP Requests made with a given URL, and then returns items depending on what request was made
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/item")
public class TDListController {
	
	@Autowired
	private ListItemService service;
	
	// e.g. http://localhost:8080/all
	@GetMapping("/all")
	public List<TDListItem> getAllItems () {
		return service.getAllItems();
	}
	
	// e.g. http://localhost:8080/getItem/1
	@GetMapping("/getItem/{id}")
	public ResponseEntity<TDListItem> getItem (@PathVariable("id") Long id) {
		Optional<TDListItem> item = service.getItemById(id);
		
		if (item.isPresent()) {
			return ResponseEntity.ok(item.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(path="/add")
	public ResponseEntity<TDListItem> addNewItem (@Valid @RequestBody TDListItem item) throws ParseException {
		TDListItem newItem = service.createItem(item.getTask_name(), item.getDescription(), item.getDeadline());
		
		if (newItem == null) {
			return ResponseEntity.notFound().build(); 
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("getItem/{id}").buildAndExpand(newItem.getId()).toUri();
			return ResponseEntity.created(uri).body(newItem);
		}
	}
	
	// e.g. http://localhost:8080/update/1
	@PostMapping("update/{id}")
	public ResponseEntity<TDListItem> updateItem (@PathVariable("id") Long id, @RequestBody TDListItem item) {
		TDListItem updatedItem = service.updateItem(id, item.getTask_name(), item.getDescription(), item.getDeadline());
		
		return ResponseEntity.ok(updatedItem);
	}

	// e.g. http://localhost:8080/removeitem/1
	@DeleteMapping("removeitem/{id}")
	public ResponseEntity<TDListItem> removeItem(@PathVariable("id") Long id) {
		service.deleteItem(id);
		
		return ResponseEntity.accepted().build();
	}
}
