package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Lists;
import com.example.demo.model.User;
import com.example.demo.model.UserLists;
import com.example.demo.repository.ListRepository;
import com.example.demo.repository.UserListsRepository;

@Component
public class ListService {
	@Autowired
	private ListRepository repo;
	
	@Autowired
	private UserListsRepository userRepo;
	
	private HelperService helper;
	
	public ListService () {
		this.helper = new HelperService();
	}
	
	public Optional<Lists> getListById (Long id) {
		return helper.getListById(id);
	}
	
	public List<Lists> getAllLists() {
		return repo.findAll();
	}
	
	public List<User> getListUsers (Long id) {
		Optional<Lists> foundList = helper.getListById(id);
		
		if (foundList.isPresent()) {
			Lists item = foundList.get();
			Set<UserLists> userLists = userRepo.findByListId(item.getId());
			
			List<User> items = new ArrayList<>();
			
			for (UserLists user : userLists) {
				items.add(user.getUser());
			}
			
			return items;
		}
		
		return null;
	}
	
	public Lists updateList (Long id, String name) {
		Lists list = repo.getOne(id);
		
		if (helper.hasText(name)) {
			list.setList_name(name);
		}
		
		return repo.save(list);
	}
	
	
}
