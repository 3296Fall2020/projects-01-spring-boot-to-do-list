package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "user_list")
public class UserLists {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
    private Long id;
 
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
 
    @ManyToOne
    @JoinColumn(name = "lists_id")
    private Lists lists;
    
    @Column(name = "link_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E MMM dd HH:mm:ss yyyy")
	private Date link_date;

	public UserLists() {
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Lists getLists() {
		return lists;
	}

	public void setLists(Lists lists) {
		this.lists = lists;
	}

	public Date getLink_date() {
		return link_date;
	}

	public void setLink_date(Date link_date) {
		this.link_date = link_date;
	}
    
    
}
