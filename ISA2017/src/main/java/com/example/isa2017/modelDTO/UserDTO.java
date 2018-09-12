package com.example.isa2017.modelDTO;

import java.util.List;

import com.example.isa2017.model.Friendship;
import com.example.isa2017.model.Role;

public class UserDTO {

	private Long id;
	private String email;
	private String name;
	private String surname;
	private String password;
	private String city;
	private String phoneNumber;
	private boolean firstLogin;
	private Role role;
	private String type;
	private List<FriendshipDTO> friendship;
	private List<FriendshipDTO> friendshipReciver;
	public UserDTO(){}
	
	public List<FriendshipDTO> getFriendship() {
		return friendship;
	}

	public void setFriendship(List<FriendshipDTO> friendship) {
		this.friendship = friendship;
	}

	public List<FriendshipDTO> getFriendshipReciver() {
		return friendshipReciver;
	}

	public void setFriendshipReciver(List<FriendshipDTO> friendshipReciver) {
		this.friendshipReciver = friendshipReciver;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
	
	
	
}
