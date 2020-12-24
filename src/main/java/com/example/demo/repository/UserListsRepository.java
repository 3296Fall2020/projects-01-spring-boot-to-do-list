package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.UserLists;

public interface UserListsRepository extends JpaRepository<UserLists, Long> {
	@Query(value = "SELECT * FROM sys.user_lists WHERE users_id = ?1", nativeQuery = true)
	Set<UserLists> findByUserId (Long users_id);
	
	@Query(value = "SELECT * FROM sys.user_lists WHERE lists_id = ?1", nativeQuery = true)
	Set<UserLists> findByListId (Long lists_id);
	
	@Query(value = "SELECT * FROM sys.user_lists WHERE users_id = ?1 AND lists_id = ?2", nativeQuery = true)
	Optional<UserLists> findByCompositeKey (Long users_id, Long lists_id);
	
	@Query(value = "SELECT ul.lists_id FROM user_lists ul "
			+ "JOIN sys.lists l ON ul.lists_id = l.id "
			+ "JOIN sys.user_lists ul2 ON l.id = ul2.lists_id "
			+ "WHERE ul2.users_id = ?1 "
			+ "GROUP BY ul.lists_id "
			+ "HAVING count(ul.users_id) = 1", nativeQuery = true)
	List<Long> findUnsharedLists (Long user_id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM sys.user_lists WHERE users_id = ?1", nativeQuery = true)
	void deleteAllByUserId (Long user_id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM sys.lists WHERE id IN (:id_list)", nativeQuery = true)
	void deleteListSeries (@Param("id_list") List<Long> id_list);
}
