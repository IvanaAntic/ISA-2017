package com.example.isa2017.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*Neprijavljeni korisnici mogu videti osnovne informacije o
svakom pozorištu/bioskopu kao što su naziv, adresa (dodatno lokacija korišćenjem
Google mapa), promotivni opis, prosečna ocena.*/

@Entity(name="cinema")
public class Cinema {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name="name", columnDefinition="VARCHAR(40)")
	private String name;
	
	@Column(name="address", columnDefinition="VARCHAR(40)")
	private String address;
	
	@Column(name="description", columnDefinition="VARCHAR(200)")
	private String description;
	
	@Column(name="averageRating")
	private int avgRating;
	
	public Cinema(){
		
	}

	public Cinema(String name, String address, String description, int avgRating) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.avgRating = avgRating;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(int avgRating) {
		this.avgRating = avgRating;
	}
	
	

}
