package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Completion;

@Repository
public interface CompletionRepository extends JpaRepository<Completion, Long> {
	
}
