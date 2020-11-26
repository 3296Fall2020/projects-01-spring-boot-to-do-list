package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Completion;
import com.example.demo.model.ListItem;
import com.example.demo.model.Lists;
import com.example.demo.model.User;
import com.example.demo.repository.CompletionRepository;
import com.example.demo.repository.ListItemRepository;
import com.example.demo.repository.ListRepository;
import com.example.demo.repository.UserRepository;

@Component
public class ListItemService {
	@Autowired
	private ListItemRepository repo;
	
	@Autowired
	private CompletionRepository statusRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ListRepository listRepo;
	
	private HelperService helper;
	
	public ListItemService () {
		this.helper = new HelperService();
	}
	
	/* Methods to get a specific item, get all items, get items for a specific list, and to create items and update text-related fields */
	
	public Optional<ListItem> getItemById (Long id) {
		return repo.findById(id);
	}
	
	public List<ListItem> getAllItems() {
		return repo.findAll();
	}
	
	public List<ListItem> getItemsByList (Long list_id) {
		if (listRepo.existsById(list_id)) {
			Set<ListItem> items = repo.findItemsByList(list_id);
			return items.stream().collect(Collectors.toList());
		}
		
		return null;
	}
	
	public ListItem createItem (Long list_id, String task, String desc, Date deadline) {
		Optional<Lists> list = listRepo.findById(list_id);
		Optional<Completion> status = statusRepo.findById((long) 1); /* Generally will never be null */
		
		if (list.isPresent()) {
			Lists parent = list.get();
			ListItem item = new ListItem();
			
			item.setTask_name(task);
			item.setDescription(desc);
			item.setDeadline(deadline);			
			item.setList_container(parent);
			item.setCompletion(status.get());
			
			return repo.save(item);
		}
		
		return null;
	}
	
	public ListItem updateTaskText(Long id, String task, String desc, Date deadline) {
		Optional<ListItem> item = repo.findById(id);
		
		if (item.isPresent()) {
			ListItem toUpdate = item.get();
			
			if (helper.hasText(task)) {
				toUpdate.setTask_name(task);
			}
			if (helper.hasText(desc)) {
				toUpdate.setDescription(desc);
			}
			if (deadline != null) {
				toUpdate.setDeadline(deadline);
			}
			
			return repo.save(toUpdate);
		}
		
		return null;
	}
	
	/* Methods to handle ownership of a given task */
	
	/* Method to add AND update ownership */
	public ListItem addOwnership (Long item_id, Long owner_id) {
		Optional<ListItem> resultTask = repo.findById(item_id);
		Optional<User> owner = userRepo.findById(owner_id);
		
		if (resultTask.isPresent() && owner.isPresent()) {
			ListItem task = resultTask.get();
			task.setOwner(owner.get());
			return repo.save(task);
		}
		
		return null;
	}
	
	public ListItem removeOwnership (Long item_id) {
		Optional<ListItem> resultTask = repo.findById(item_id);
		
		if (resultTask.isPresent()) {
			ListItem task = resultTask.get();
			
			if (task.getOwner() != null) {
				task.setOwner(null);
				repo.save(task);
			}
			
			return task;
		}
		
		return null;
	}
	
	/* Method to handle the completion status of a task */
	
	public ListItem configureCompletion (Long item_id, Long completion_id) {
		Optional<ListItem> item = repo.findById(item_id);
		Optional<Completion> status = statusRepo.findById(completion_id);
		
		if (item.isPresent() && status.isPresent()) {
			ListItem toUpdate = item.get();
			toUpdate.setCompletion(status.get());
			return repo.save(toUpdate);
		}
		
		return null;
	}
	
	public void deleteItem(Long id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
		}
	}
}
