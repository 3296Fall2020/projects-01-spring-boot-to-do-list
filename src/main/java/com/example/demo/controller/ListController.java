package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Lists;
import com.example.demo.service.ListService;
import com.example.demo.service.UserListsService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/list")
public class ListController {
	@Autowired
	private ListService service;
	
	@Autowired
	private UserListsService userService;
	
	@GetMapping(path = "/all")
	public List<Lists> getAllLists () {
		return service.getAllLists();
	}
	
	@GetMapping(path = "/getList")
	public ResponseEntity<Lists> getList (@RequestParam String list_name) {
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping(path = "/getListById/{id}")
	public ResponseEntity<Lists> getListById (@PathVariable("id") Long id) {
		return ResponseEntity.accepted().build();
	}
	
	@PostMapping(path = "/add")
	public ResponseEntity<Lists> addNewLists (@RequestParam String email, @RequestParam String name) {
		
		Lists newList = userService.createList(email, name);
		
		if (newList == null) {
			return ResponseEntity.notFound().build(); 
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/getListById/{id}").buildAndExpand(newList.getList_id()).toUri();
			return ResponseEntity.created(uri).body(newList);
		}
	}
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<Lists> updateList (@PathVariable("id") Long id, @RequestBody Lists list) {
		Lists updatedList = service.updateList(id, list.getList_name());
		
		return ResponseEntity.ok(updatedList);
	}
	
	
}
