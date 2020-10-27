package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class TDListItem {
	@Id @GeneratedValue
	private Long id;
	
	@NotBlank
	private String task_name;
	
	@NotBlank
	private String description;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E MMM dd HH:mm:ss yyyy")
	private Date deadline;
	
	public TDListItem() {
		super();
	}

	public TDListItem(Long id, @NotBlank String task_name, @NotBlank String description, Date deadline) {
		super();
		this.id = id;
		this.task_name = task_name;
		this.description = description;
		this.deadline = deadline;
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	
	
}
