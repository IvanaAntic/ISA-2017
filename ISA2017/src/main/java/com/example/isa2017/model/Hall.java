package com.example.isa2017.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="hall")
public class Hall {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Seat> seats;
	
	@Column
	private String name;

	public Hall() {
		super();
	}

	public Hall(List<Seat> seats, String name) {
		super();
		this.seats = seats;
		this.name = name;

	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	
	
	
}
