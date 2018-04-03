package com.example.isa2017.FanZone.model;

import javax.persistence.*;

import com.example.isa2017.model.User;

@Entity
public class AdminItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private double price;
	private boolean isReserved;
	//private User buyer;
	
	public AdminItem() {
		super();
	}

	public AdminItem(Long id, String name, String description, double price, boolean isReserved) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.isReserved = isReserved;
		//this.buyer = buyer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

/*	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}*/
	
	
	
}
