package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Lists;

public interface ListRepository extends JpaRepository <Lists, Long> {
	
}
