package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.TDListItemNotFoundException;
import com.example.demo.model.TDListItem;
import com.example.demo.repository.TDListItemRepository;

@RestController
public class TDListController {
	@Autowired
	TDListItemRepository repository;
	
	@GetMapping("/listitem")
	public List<TDListItem> getAllItems () {
		return repository.findAll();
	}
	
	@PostMapping("/listitem")
	public TDListItem createItem (@Valid @RequestBody TDListItem item) {
		return repository.save(item);
	}
	
	@GetMapping("listitem/{id}")
	public TDListItem getItemById(@PathVariable(value = "id") Long itemId) throws TDListItemNotFoundException {
		return repository.findById(itemId).orElseThrow(() -> new TDListItemNotFoundException(itemId));
	}
}
