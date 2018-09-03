package com.example.isa2017.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="friendship")
public class Friendship {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	private User sender;
	
	@ManyToOne
	private User reciver;
	
	@Column(name="status")
	private String status;

	
	
	
	public Friendship() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Friendship(User sender, User reciver, String status) {
		super();
		this.sender = sender;
		this.reciver = reciver;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReciver() {
		return reciver;
	}

	public void setReciver(User reciver) {
		this.reciver = reciver;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
