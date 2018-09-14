package com.example.isa2017.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Version;


/*Neprijavljeni korisnici mogu videti osnovne informacije o
svakom pozorištu/bioskopu kao što su naziv, adresa (dodatno lokacija korišćenjem
Google mapa), promotivni opis, prosečna ocena.*/

@Entity(name="theatre")
public class Theatre {
	
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
	
	@OneToMany(mappedBy = "theatre", orphanRemoval = true)
	private List<Play> plays;
	
	@OneToMany(mappedBy = "theatre", orphanRemoval = true)
	private List<Hall> halls;
	
	@Lob
    @Column(name="RATING_LIST", columnDefinition="mediumblob")
	private HashMap<Long, Integer> ratingList;
	
	@Column
	private int rating;
	
	@Version
	private Long version;
	
	public Theatre(){
		ratingList = new HashMap<>();
		this.halls = new ArrayList<>();
		this.plays = new ArrayList<>();
	}

	public Theatre(String name, String address, String description, int avgRating) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.avgRating = avgRating;
		ratingList = new HashMap<>();
		this.halls = new ArrayList<>();
		this.plays = new ArrayList<>();
	}
	
	

	public Theatre(String name, String address, String description, int avgRating, List<Play> plays) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.avgRating = avgRating;
		this.plays = plays;
		ratingList = new HashMap<>();
		this.halls = new ArrayList<>();
		this.plays = new ArrayList<>();
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

	public List<Play> getPlays() {
		return plays;
	}

	public void setPlays(List<Play> plays) {
		this.plays = plays;
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

	public HashMap<Long,Integer> getRatingList() {
		return ratingList;
	}

	public void setRatingList(HashMap<Long,Integer> ratingList) {
		this.ratingList = ratingList;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
