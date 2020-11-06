package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.TDListItemNotFoundException;
import com.example.demo.model.TDListItem;
import com.example.demo.repository.TDListItemRepository;

// Rest Controller, this portion takes in HTTP Requests made with a given URL, and then returns items depending on what request was made
@RestController
public class TDListController {
	@Autowired
	TDListItemRepository repository;
	
	// e.g. http://localhost:8080/allitems
	@GetMapping("/allitems")
	public List<TDListItem> getAllItems () {
		return repository.findAll();
	}
	
	// e.g. http://localhost:8080/createitem
	@PostMapping("/createitem")
	public TDListItem createItem (@Valid @RequestBody TDListItem item) {
		return repository.save(item);
	}
	
	// e.g. http://localhost:8080/getitem/1
	@GetMapping("getitem/{id}")
	public TDListItem getItemById(@PathVariable(value = "id") Long itemId) throws TDListItemNotFoundException {
		return repository.findById(itemId).orElseThrow(() -> new TDListItemNotFoundException(itemId));
	}
	
	// Still need to implement post requests (Update items), and delete requests (Delete a given item)
}
