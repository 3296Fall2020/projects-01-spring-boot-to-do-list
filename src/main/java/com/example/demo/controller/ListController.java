package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.example.demo.model.Lists;
import com.example.demo.model.User;
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
	
	/* GET methods; Get all lists, get List by its id, and get the Users associated with a List */
	
	@GetMapping(path = "/all")
	public List<Lists> getAllLists () {
		return service.getAllLists();
	}
	
	@GetMapping(path = "/getListById/{id}")
	public ResponseEntity<Lists> getListById (@PathVariable("id") Long id) {
		Optional<Lists> lists = service.getListById(id);
		
		if (lists.isPresent()) {
			return ResponseEntity.ok(lists.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(path = "/getListUsers/{id}")
	public List<User> getListUsers (@PathVariable("id") Long id) {
		return service.getListUsers(id);
	}
	
	/* POST methods; Create a new list, and add user to a list */
	
	@PostMapping(path = "/add")
	public ResponseEntity<Lists> addNewList (@RequestParam String email, @RequestParam String name) {
		Lists newList = userService.createList(email, name);
		
		if (newList == null) {
			return ResponseEntity.notFound().build(); 
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/getListById/{id}").buildAndExpand(newList.getId()).toUri();
			return ResponseEntity.created(uri).body(newList);
		}
	}
	
	@PostMapping(path = "/addUserToList")
	public ResponseEntity<Lists> addUserToList (@RequestParam Long user_id, @RequestParam Long list_id) {
		Lists jointList = userService.addUserToList(user_id, list_id);
		
		if (jointList == null) {
			return ResponseEntity.notFound().build();
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/getListById/{id}").buildAndExpand(jointList.getId()).toUri();
			return ResponseEntity.created(uri).body(jointList);
		}
	}
	
	/* PUT methods; Update the list. No need to update the UserLists object's foreign keys */
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<Lists> updateList (@PathVariable("id") Long id, @RequestBody Lists list) {
		Lists updatedList = service.updateList(id, list.getList_name());
		
		return ResponseEntity.ok(updatedList);
	}
	
	/* DELETE methods; Delete a list, and remove users from a list */
	
	@DeleteMapping(path = "/deleteList/{id}")
	public ResponseEntity<Lists> deleteList () {
		return null;
	}
	
	@DeleteMapping(path = "remove")
	public ResponseEntity<Lists> removeUserFromList (@RequestParam Long list_id, @RequestParam Long user_id) {
		return null;
	}
	
}
