package com.example.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "completion", uniqueConstraints={@UniqueConstraint(columnNames={"status_name"})})
public class Completion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
    private Long id;
	
	@NotBlank(message = "Please provide a name for the Status")
	private String status_name;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "completion")
	@JsonIgnore
	private Set<ListItem> items;
	
	public Completion () {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public Set<ListItem> getItems() {
		return items;
	}

	public void setItems(Set<ListItem> items) {
		this.items = items;
	}
	
	
}
