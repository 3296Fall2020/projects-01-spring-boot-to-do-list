package com.example.demo.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.UserLists;

public interface UserListsRepository extends JpaRepository<UserLists, Long> {
	@Query(value = "SELECT * FROM sys.user_lists WHERE users_id = ?1", nativeQuery = true)
	Set<UserLists> findByUserId (Long users_id);
	
	@Query(value = "SELECT * FROM sys.user_lists WHERE lists_id = ?1", nativeQuery = true)
	Set<UserLists> findByListId (Long lists_id);
	
	@Query(value = "SELECT * FROM sys.user_lists WHERE users_id = ?1 AND lists_id = ?2", nativeQuery = true)
	Optional<UserLists> findByCompositeKey (Long users_id, Long lists_id);
	
	/*
	@Query(value = "INSERT INTO sys.user_lists (users_id, lists_id) VALUES (?1, ?2)", nativeQuery = true)
	Optional<UserLists> insertLink (Long users_id, Long lists_id);
	*/
}
