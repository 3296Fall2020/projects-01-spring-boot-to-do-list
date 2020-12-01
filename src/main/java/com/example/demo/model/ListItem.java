package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// Model, this item represents the object representation of a To Do List Item
@Entity
@Table(name = "list_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ListItem {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Please provide a name for your Task")
	private String task_name;
	
	private String description;
	
	@DateTimeFormat(pattern="yyyy.MM.dd HH:mm")
	private LocalDateTime deadline;
	
	@ManyToOne
	@JoinColumn(name = "owner")
	@JsonBackReference(value = "delegation")
	private User owner;
	
	@ManyToOne
	@JoinColumn(name = "completion")
	private Completion completion;
	
	@ManyToOne
	@JoinColumn(name = "list_container")
	@JsonBackReference(value = "series")
	private Lists list_container;
	
	public ListItem() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Lists getList_container() {
		return list_container;
	}

	public void setList_container(Lists list_container) {
		this.list_container = list_container;
	}

	public Completion getCompletion() {
		return completion;
	}

	public void setCompletion(Completion completion) {
		this.completion = completion;
	}
	
	@Override
	public String toString () {
		return "Item " + task_name + ", with a deadline at " + deadline + " and delegated to " + owner.getEmail();
	}
}
