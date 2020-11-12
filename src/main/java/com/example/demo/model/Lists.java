package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Lists {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "List name should be provided")
	private String list_name;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "userLists")
	@JsonBackReference
	Set<User> listUsers = new HashSet<>();
	
	public Lists() {
		
	}

	public Long getList_id() {
		return id;
	}

	public void setList_id(Long list_id) {
		this.id = list_id;
	}

	public String getList_name() {
		return list_name;
	}

	public void setList_name(String list_name) {
		this.list_name = list_name;
	}

	public Set<User> getListUsers() {
		return listUsers;
	}

	public void setListUsers(Set<User> listUsers) {
		this.listUsers = listUsers;
	}
	
	
}
