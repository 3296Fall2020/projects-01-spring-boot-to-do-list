package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.*;

@Repository
public interface ListItemRepository extends JpaRepository<ListItem, Long>{
	
	@Query(value = "SELECT * FROM sys.list_item WHERE email = ?1", nativeQuery = true)
	List<ListItem> getItemsByUser(String email);

}
