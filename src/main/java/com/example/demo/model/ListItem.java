package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

// Model, this item represents the object representation of a To Do List Item
@Entity
public class ListItem {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long item_id;
	
	@NotBlank(message = "Please provide a name for your Task")
	private String task_name;
	
	private String description;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E MMM dd HH:mm:ss yyyy")
	private Date deadline;
	
	private String owner;
	
	private boolean completion;
	
	public ListItem() {
		
	}

	public Long getId() {
		return item_id;
	}

	public void setId(Long item_id) {
		this.item_id = item_id;
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isCompletion() {
		return completion;
	}

	public void setCompletion(boolean completion) {
		this.completion = completion;
	}
	
	
	
}
