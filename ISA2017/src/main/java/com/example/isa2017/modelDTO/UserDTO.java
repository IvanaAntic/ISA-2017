package com.example.isa2017.modelDTO;

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
	
	public UserDTO(){}
	
	public UserDTO(String email, String name, String surname, String password, String city, String phoneNumber,Role role) {
		super();
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.city = city;
		this.phoneNumber = phoneNumber;
	
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
