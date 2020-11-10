package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "SELECT * FROM sys.user WHERE email = ?1", nativeQuery = true)
	Optional<User> findUserByEmail (String email);
}
