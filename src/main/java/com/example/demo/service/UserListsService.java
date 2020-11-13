package com.example.demo.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Lists;
import com.example.demo.model.User;
import com.example.demo.model.UserLists;
import com.example.demo.repository.ListRepository;
import com.example.demo.repository.UserListsRepository;
import com.example.demo.repository.UserRepository;

@Component
public class UserListsService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ListRepository listRepo;
	
	@Autowired
	private UserListsRepository jointRepo;
	
	public Lists createList (String email, String name) {
		Optional<User> user = userRepo.findUserByEmail(email);
		
		if (user.isPresent()) {
			Lists list = new Lists();
			
			list.setList_name(name);
			list = listRepo.save(list);
			
			UserLists mapping = new UserLists();
			mapping.setLists(list);
			mapping.setUser(user.get());
			mapping.setLink_date(new Date());
			
			jointRepo.save(mapping);
			
			return list;
		}
		
		return null; // Very unlikely you will end up here
	}
	
	public void deleteList (Long id) {
		
	}
}
