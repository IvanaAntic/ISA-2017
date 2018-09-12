package com.example.isa2017.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="email")
	
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="name")
	private String name;
	
	@Column(name="surname")
	private String surname;
	
	@Column(name="phoneNumber")
	private String phoneNumber;
	
	@Column(name="city")
	private String city;
	
	@Column(name="role")
	private Role role;
	
	@Column(name="is_verified")
	private boolean active;
	
	@Column(name="first_login")
	private boolean firstLogin = true;
	
	@OneToMany(mappedBy = "user", orphanRemoval = true)
	private List<Ticket> tickets;

	@Column(name="type")
	private String type;
	
	@OneToMany(mappedBy = "sender", orphanRemoval = true)
	private List<Friendship> friendship;

	@OneToMany(mappedBy = "reciver", orphanRemoval = true)
	private List<Friendship> friendshipReciver;
	
	public User(){
		this.tickets = new ArrayList<>();
		this.friendship=new ArrayList<>();
		this.friendshipReciver=new ArrayList<>();
	}
	
	
	public User(String email, String password, String name, String surname, String phoneNumber, String city,Role role,boolean active,String type) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.role=role;
		this.active=active;
		this.type=type; 
		this.tickets = new ArrayList<>();
		this.friendship=new ArrayList<>();
		this.friendshipReciver=new ArrayList<>();
		
	}
	
	public List<Friendship> getFriendshipReciver() {
		
		return friendshipReciver;
	}


	public void setFriendshipReciver(List<Friendship> friendshipReciver) {
		this.friendshipReciver = friendshipReciver;
	}


	public List<Friendship> getFriendship() {
		return friendship;
	}


	public void setFriendship(List<Friendship> friendship) {
		this.friendship = friendship;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}
	
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", name=" + name + ", surname="
				+ surname + ", phoneNumber=" + phoneNumber + ", city=" + city + ", role=" + role + ", active=" + active
				+ ", firstLogin=" + firstLogin + ", tickets=" + tickets + ", type=" + type + ", friendship="
				+ friendship + ", friendshipReciver=" + friendshipReciver + "]";
	}
	
	
}
