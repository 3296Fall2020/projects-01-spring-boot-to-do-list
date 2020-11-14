package com.example.demo.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "lists")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Lists {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "List name should be provided")
	private String list_name;
	
	@OneToMany(mappedBy = "lists")
	Set<UserLists> listUsers; /* = new HashSet<>(); */
	
	public Lists() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long list_id) {
		this.id = list_id;
	}

	public String getList_name() {
		return list_name;
	}

	public void setList_name(String list_name) {
		this.list_name = list_name;
	}

	public Set<UserLists> getListUsers() {
		return listUsers;
	}

	public void setListUsers(Set<UserLists> listUsers) {
		this.listUsers = listUsers;
	}
	
	
}
