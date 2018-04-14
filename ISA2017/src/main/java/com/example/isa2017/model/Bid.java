package com.example.isa2017.model;


import java.sql.Time;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date date;
	private Time time;
	@ManyToOne()
	private User buyer;
	@ManyToOne()
	private UserItem item;
	private int price;
	
	public Bid() {
		super();
	}

	
	public Bid(Long id, Date date, Time time, User buyer, UserItem item, int price) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.buyer = buyer;
		this.item = item;
		this.price = price;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public UserItem getItem() {
		return item;
	}

	public void setItem(UserItem item) {
		this.item = item;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}
