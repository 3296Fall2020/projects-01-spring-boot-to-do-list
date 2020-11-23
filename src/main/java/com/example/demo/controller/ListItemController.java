package com.example.demo.controller;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Comment;
import com.example.demo.model.ListItem;
import com.example.demo.service.ListItemService;

// Rest Controller, this portion takes in HTTP Requests made with a given URL, and then returns items depending on what request was made
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/item")
public class ListItemController {
	
	@Autowired
	private ListItemService service;
	
	/* GET methods; Get all list items, get all the items associated with a specific list, 
	 * get a specific item through its id, and get the deadline of an item */
	
	// e.g. http://localhost:8080/allItems
	@GetMapping("/allItems")
	public ResponseEntity<List<ListItem>> getAllItems () {
		List<ListItem> allItems = service.getAllItems();
		
		return ResponseEntity.ok(allItems);
	}
	
	@GetMapping("/getListItems/{id}")
	public List<ListItem> getItemsByList (@PathVariable("id") Long id) {
		return service.getItemsByList(id);
	}
	
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
	
	// e.g. http://localhost:8080/checkItemDeadline/1
	@GetMapping("/checkItemDeadline/{id}")
	public ResponseEntity<ListItem> itemDeadline (@PathVariable("id") Long id) {
		Optional<ListItem> item = service.getItemById(id);
		
		if (item.isPresent()) {
			Date deadline = item.get().getDeadline();
			if (deadline == null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(item.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/* POST method; Add a new item to the list */
	
	// e.g. http://localhost:8080/addItem/1
	/* Add a new item to a list with the given id */
	@PostMapping(path="/addItem/{id}")
	public ResponseEntity<ListItem> addNewItem (@PathVariable("id") Long id, @Valid @RequestBody ListItem item) throws ParseException {
		ListItem newItem = service.createItem(id, item.getTask_name(), item.getDescription(), item.getDeadline());
		
		if (newItem == null) {
			return ResponseEntity.notFound().build(); 
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/getItem/{id}").buildAndExpand(newItem.getId()).toUri();
			return ResponseEntity.created(uri).body(newItem);
		}
	}
	
	/* PUT methods; Update the descriptive parts of an item, assign an item to a user, remove ownership of an item, and modify item's completion status */
	
	// e.g. http://localhost:8080/update/1
	@PutMapping("/update/{id}")
	public ResponseEntity<ListItem> updateItem (@PathVariable("id") Long id, @RequestBody ListItem item) {
		ListItem updatedItem = service.updateTaskText(id, item.getTask_name(), item.getDescription(), item.getDeadline());
		
		if (updatedItem != null) {
			return ResponseEntity.ok(updatedItem);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// e.g. http://localhost:8080/assign
	@PutMapping("/assign")
	public ResponseEntity<ListItem> addOwnership (@RequestParam Long item_id, @RequestParam Long owner_id) {
		ListItem updatedItem = service.addOwnership(item_id, owner_id);
		
		if (updatedItem != null) {
			return ResponseEntity.ok(updatedItem);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// e.g. http://localhost:8080/retract/1
	@PutMapping("/retract/{id}")
	public ResponseEntity<ListItem> removeOwnership (@PathVariable("id") Long id) {
		ListItem updatedItem = service.removeOwnership(id);
		
		if (updatedItem != null) {
			return ResponseEntity.ok(updatedItem);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// e.g. http://localhost:8080/changeStatus
	@PutMapping("/changeStatus")
	public ResponseEntity<ListItem> changeStatus (@RequestParam Long item_id, @RequestParam Long status, @RequestBody ListItem item) {
		ListItem finishedItem = service.configureCompletion(item_id, status);
		
		if (finishedItem != null) {
			return ResponseEntity.ok(finishedItem);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	/* DELETE method; Remove an item from a list */
	
	// e.g. http://localhost:8080/removeItem/1
	@DeleteMapping("/removeItem/{id}")
	public ResponseEntity<ListItem> removeItem(@PathVariable("id") Long id) {
		service.deleteItem(id);
		
		return ResponseEntity.accepted().build();
	}
	
	/* ~~~~~~~~~~~~~~~~ Comment-related Operations ~~~~~~~~~~~~~~~~ */
	
	/* GET methods; Get a specific comment and get all of the comments for a specific item */
	
	@GetMapping("/allComments")
	public List<Comment> allComments () {
		return service.getAllComments();
	}
	
	// e.g. http://localhost:8080/getComment/1
	@GetMapping("/getComment/{id}")
	public ResponseEntity<Comment> getComment (@PathVariable("id") Long id) {
		Optional<Comment> comment = service.getComment(id);
		
		if (comment.isPresent()) {
			return ResponseEntity.ok(comment.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// e.g. http://localhost:8080/getItemComments/1
	@GetMapping("/getItemComments/{id}")
	public List<Comment> getItemComments (@PathVariable("id") Long id) {
		return service.getItemComments(id);
	}
	
	/* POST method; Add a new comment to a given list item */
	
	// e.g. http://localhost:8080/makeComment
	@PostMapping(path="/makeComment")
	public ResponseEntity<Comment> writeComment (@RequestParam Long item_id, @RequestParam Long user_id, @Valid @RequestBody Comment item) {
		Comment comment = service.addComment(item_id, user_id, item.getDescription());
		
		if (comment == null) {
			return ResponseEntity.notFound().build(); 
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/getComment/{id}").buildAndExpand(comment.getId()).toUri();
			return ResponseEntity.created(uri).body(comment);
		}
	}
	
	/* PUT method; Edit a given comment */
	
	// e.g. http://localhost:8080/editComment/1
	@PutMapping("/editComment/{id}")
	public ResponseEntity<Comment> editComment (@PathVariable("id") Long id, @RequestBody Comment comment) {
		Comment newComment = service.editComment(id, comment.getDescription());
		
		if (newComment != null) {
			return ResponseEntity.ok(newComment);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	/* DELETE method; Delete a given comment */
	
	// e.g. http://localhost:8080/deleteComment/1
	@DeleteMapping("/deleteComment/{id}")
	public ResponseEntity<Comment> deleteComment (@PathVariable("id") Long id) {
		service.deleteComment(id);
		
		return ResponseEntity.accepted().build();
	}
}
