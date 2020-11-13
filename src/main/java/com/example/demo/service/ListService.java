package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Lists;
import com.example.demo.model.UserLists;
import com.example.demo.repository.ListRepository;
import com.example.demo.repository.UserListsRepository;

@Component
public class ListService {
	@Autowired
	private ListRepository repo;
	
	@Autowired
	private UserListsRepository userRepo;
	
	private StringHelper helper;
	
	public ListService () {
		this.helper = new StringHelper();
	}
	
	public List<Lists> getAllLists() {
		return repo.findAll();
	}
	
	public Lists updateList (Long id, String name) {
		Lists list = repo.getOne(id);
		
		if (helper.hasText(name)) {
			list.setList_name(name);
		}
		
		return repo.save(list);
	}
	
	
}
