package com.example.demo.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ListItem;

@Repository
public interface ListItemRepository extends JpaRepository<ListItem, Long>{
	@Query(value = "SELECT * FROM sys.list_item WHERE list_container = ?1", nativeQuery = true)
	Set<ListItem> findItemsByList(Long list_id);
	
	@Query(value = "SELECT id FROM sys.list_item WHERE owner = ?1", nativeQuery = true)
	List<Long> findItemsByOwner(Long user_id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE sys.list_item SET owner = null WHERE owner = ?1", nativeQuery = true)
	void removeAllUserOwnership(Long user_id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM sys.list_item WHERE owner IN (:id_list)", nativeQuery = true)
	void deleteAllByListSeries (@Param("id_list") List<Long> id_list);
}
