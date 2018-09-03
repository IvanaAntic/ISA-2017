package com.example.isa2017.modelDTO;

import com.example.isa2017.model.User;

public class FriendshipDTO {

	private Long id;
	private User sender;
	private User reciver;
	private String status;
	public FriendshipDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FriendshipDTO( User sender, User reciver, String status) {
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
