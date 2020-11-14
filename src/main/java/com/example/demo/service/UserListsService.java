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
	
	private HelperService helper;
	
	public UserListsService () {
		this.helper = new HelperService();
	}
	
	private UserLists findUserListLink (Long user_id, Long list_id) {
		Optional<User> foundUser = helper.getUserById(user_id);
		Optional<Lists> foundList = helper.getListById(list_id);
		
		if (foundUser.isPresent() && foundList.isPresent()) {
			Optional<UserLists> foundCombo = jointRepo.findByCompositeKey(user_id, list_id);
			
			if (foundCombo.isPresent()) {
				return foundCombo.get();
			}
		}
		
		return null;
	}
	
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
	
	public Lists addUserToList (Long user_id, Long list_id) {
		UserLists combo = findUserListLink(user_id, list_id);
		
		if (combo == null) {
			combo = new UserLists();
			
			Optional<User> linkedUser = helper.getUserById(user_id);
			Optional<Lists> linkedList = helper.getListById(list_id);
			
			if (linkedUser.isPresent() && linkedList.isPresent()) {
				User user = linkedUser.get();
				Lists list = linkedList.get();
				
				combo.setUser(user);
				combo.setLists(list);
				combo.setLink_date(new Date());
				
				return jointRepo.save(combo).getLists();
			}
		}
		
		return null;
	}
	
	public void removeUserFromList (Long user_id, Long list_id) {
		
	}
}
