package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.ListItem;
import com.example.demo.model.Lists;
import com.example.demo.model.User;
import com.example.demo.model.UserLists;
import com.example.demo.repository.ListItemRepository;
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
	
	@Autowired
	private ListItemRepository itemRepo;
	
	private UserLists findUserListLink (Long user_id, Long list_id) {
		if (userRepo.existsById(user_id) && listRepo.existsById(list_id)) {
			Optional<UserLists> foundCombo = jointRepo.findByCompositeKey(user_id, list_id);
			
			if (foundCombo.isPresent()) {
				return foundCombo.get();
			}
		}
		
		return null;
	}
	
	/* Delete unshared lists, and remove all of a specific user's involvement with their lists */
	private void removeUserAssociation (Long id) {
		List<Long> unshared = jointRepo.findUnsharedLists(id); // Find the lists that are *exclusive* to the user
		
		Set<UserLists> toDelete = jointRepo.findByUserId(id); // Find all associations that the user is a part of
		
		// Delete all of the user's associations with lists
		if (!toDelete.isEmpty()) {
			jointRepo.deleteInBatch(toDelete);
		}
		
		// Handle list items before deleting the lists
		itemRepo.deleteAllByListSeries(unshared);
		
		jointRepo.deleteListSeries(unshared);
	}
	
	private void removeListAssociation (Long id) {
		Set<UserLists> toDelete = jointRepo.findByListId(id);
		
		if (!toDelete.isEmpty()) {
			jointRepo.deleteInBatch(toDelete);
		}
	}
	
	/* For use with the deletion of a user, removing all of their ownership of their items */
	private void removeItemOwnership (Long id) {
		itemRepo.removeAllUserOwnership(id);
	}
	
	/* Mass delete all the items in a list */
	private void removeAllItemsFromList(Long list_id) {
		Set<ListItem> listItems = itemRepo.findItemsByList(list_id);
		
		if (!listItems.isEmpty()) {
			itemRepo.deleteInBatch(listItems);
		}
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
	
	public Lists addUserToList (Long user_id, Long list_id) {
		UserLists combo = findUserListLink(user_id, list_id);
		
		if (combo == null) {
			combo = new UserLists();
			
			Optional<User> user = userRepo.findById(user_id);
			Optional<Lists> list = listRepo.findById(list_id);
			
			if (user.isPresent() && list.isPresent()) {
				combo.setUser(user.get());
				combo.setLists(list.get());
				combo.setLink_date(new Date());
				
				return jointRepo.save(combo).getLists();
			}
		}
		
		return null;
	}
	
	public void deleteUser(Long id) {
		removeItemOwnership(id);
		removeUserAssociation(id);
		
		userRepo.deleteById(id);
	}
	
	public void deleteList (Long id) {
		removeListAssociation(id);
		removeAllItemsFromList(id);
		
		listRepo.deleteById(id);
	}
	
	public void removeUserFromList (Long user_id, Long list_id) {
		UserLists combo = findUserListLink(user_id, list_id);
		
		if (combo != null) {
			jointRepo.delete(combo);
			
			Set<UserLists> remaining = jointRepo.findByListId(list_id);
			
			if (remaining.isEmpty()) {
				listRepo.deleteById(list_id);
			}
		}
	}
}
