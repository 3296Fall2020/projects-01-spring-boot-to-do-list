package com.example.demo.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.UserLists;

public interface UserListsRepository extends JpaRepository<UserLists, Long> {
	@Query(value = "SELECT * FROM sys.user_lists WHERE users_id = ?1", nativeQuery = true)
	Set<UserLists> findByUserId (Long id);
	
	@Query(value = "SELECT * FROM sys.user_lists WHERE lists_id = ?1", nativeQuery = true)
	Set<UserLists> findByListId (Long id);
}
