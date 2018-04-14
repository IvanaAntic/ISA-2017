package com.example.isa2017.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class AdminItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private int price;
	private boolean isReserved;
	@ManyToOne
	private Theatre theatre;
	@ManyToOne
	private Cinema cinema;
	@ManyToOne
	private User buyer;
	@ManyToOne
	private User postedBy;
	
	public AdminItem() {
		super();
	}
	
	public AdminItem( String name, String description, int price, boolean isReserved, Theatre theatre,
			Cinema cinema, User buyer, User postedBy) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.isReserved = isReserved;
		this.theatre = theatre;
		this.cinema = cinema;
		this.buyer = buyer;
		this.postedBy = postedBy;
	}

	public AdminItem(Long id, String name, String description, int price, boolean isReserved) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.isReserved = isReserved;
		this.buyer = buyer;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
	@OneToMany
	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public User getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}
	
	
	
}
