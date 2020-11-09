package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.TDListItem;
import com.example.demo.repository.TDListItemRepository;

@Component
public class ListItemService {
	@Autowired
	private TDListItemRepository repo;
	private StringHelper helper;
	
	public ListItemService () {
		this.helper = new StringHelper();
	}
	
	public Optional<TDListItem> getItemById (long id) {
		return repo.findById(id);
	}
	
	public List<TDListItem> getAllItems() {
		return repo.findAll();
	}
	
	public TDListItem createItem (String task, String desc, Date deadline) {
		TDListItem item = new TDListItem();
		
		item.setTask_name(task);
		item.setDescription(desc);
		item.setDeadline(deadline);
		
		return repo.save(item);
	}
	
	public TDListItem updateItem (Long id, String task, String desc, Date deadline) {
		TDListItem item = repo.getOne(id);
		
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
		Optional<TDListItem> item = repo.findById(id);
		
		if (item.isPresent()) {
			repo.deleteById(id);
		}
	}
}
