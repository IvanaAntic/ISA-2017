package com.example.isa2017.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;



@Entity(name="movie")
public class Movie {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@Column(columnDefinition="VARCHAR(40)")
	private String movieName;
	
	@ElementCollection
	@Column
	private List<String> actors;
	
	@Column(columnDefinition="VARCHAR(40)")
	private String genre;
	
	@Column(columnDefinition="VARCHAR(80)")
	private String director;
	
	@Column
	private int runtime;
	
	@Lob
    @Column(name="MOVIE_IMAGE", columnDefinition="mediumblob")
    private byte[] image;
	
	@Column(name="averageRating")
	private double avgRating;
	
	@Column
	private int rating;
	
	@ElementCollection
	@Column
	private List<Integer> ratingList;
	
	@Column(columnDefinition="VARCHAR(500)")
	private String description;
	
	@OneToMany(mappedBy = "movie", orphanRemoval = true)
	private List<Projection> projections;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Cinema cinema;
	
	@Version
	private Long version;
	
	public Movie(){
		
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<Projection> getProjections() {
		return projections;
	}

	public void setProjections(List<Projection> projections) {
		this.projections = projections;
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	public List<Integer> getRatingList() {
		return ratingList;
	}

	public void setRatingList(List<Integer> ratingList) {
		this.ratingList = ratingList;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	
}
