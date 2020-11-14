package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.ListItem;
import com.example.demo.repository.ListItemRepository;

@Component
public class ListItemService {
	@Autowired
	private ListItemRepository repo;
	private HelperService helper;
	
	public ListItemService () {
		this.helper = new HelperService();
	}
	
	public Optional<ListItem> getItemById (long id) {
		return repo.findById(id);
	}
	
	public List<ListItem> getAllItems() {
		return repo.findAll();
	}
	
	public ListItem createItem (String task, String desc, Date deadline) {
		ListItem item = new ListItem();
		
		item.setTask_name(task);
		item.setDescription(desc);
		item.setDeadline(deadline);
		
		return repo.save(item);
	}
	
	public ListItem updateItem (Long id, String task, String desc, Date deadline) {
		ListItem item = repo.getOne(id);
		
		if (helper.hasText(task)) {
			item.setTask_name(task);
		}
		if (helper.hasText(desc)) {
			item.setDescription(desc);
		}
		if (deadline == null) {
			item.setDeadline(deadline);
		}
		
		return repo.save(item);
	}
	
	public void deleteItem(Long id) {
		Optional<ListItem> item = repo.findById(id);
		
		if (item.isPresent()) {
			repo.deleteById(id);
		}
	}
}
