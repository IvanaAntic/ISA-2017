package com.example.isa2017.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;


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
	
	@Column(name="description", columnDefinition="VARCHAR(260)")
	private String description;
	
	@Column(name="averageRating")
	private double avgRating;
	
	@OneToMany
	private List<Movie> movies;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Hall> halls;
	
	@ElementCollection
	@Column
	private List<Integer> ratingList;
	
	@Column
	private int rating;
	
	public Cinema(){
		ratingList = new ArrayList<>();
	}

	public Cinema(String name, String address, String description, int avgRating) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.avgRating = avgRating;
		ratingList = new ArrayList<>();
	}
	
	

	public Cinema(String name, String address, String description, int avgRating, List<Movie> movies) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.avgRating = avgRating;
		this.movies = movies;
		ratingList = new ArrayList<>();
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

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public List<Hall> getHalls() {
		return halls;
	}

	public void setHalls(List<Hall> halls) {
		this.halls = halls;
	}
	
	public double calculateAverage(List <Integer> marks) {
		  Integer sum = 0;
		  if(!marks.isEmpty()) {
		    for (Integer mark : marks) {
		        sum += mark;
		    }
		    return sum.doubleValue() / marks.size();
		  }
		  return sum;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public List<Integer> getRatingList() {
		return ratingList;
	}

	public void setRatingList(List<Integer> ratingList) {
		this.ratingList = ratingList;
	}
	
	
	

}
