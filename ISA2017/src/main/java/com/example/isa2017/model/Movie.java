package com.example.isa2017.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;


/*
svaka stranica predstave/filma sadrži:
	• naziv
	• spisak glumaca
	• žanr
	• ime reditelja
	• trajanje
	• poster (sliku)
	• prosečnu ocenu
	• kratak opis
	• sale u kojima se vrše projekcije
	• termine u kojima se vrše projekcije
	• cenu
*/


@Entity(name="movie")
public class Movie {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@Column(columnDefinition="VARCHAR(40)")
	private String name;
	
	@ElementCollection
	@Column
	private List<String> actors;
	
	@Column(columnDefinition="VARCHAR(40)")
	private String genre;
	
	@Column(columnDefinition="VARCHAR(80)")
	private String director;
	
	@Column(columnDefinition="VARCHAR(10)")
	private String runtime;
	
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
	
	@OneToMany
	private List<Projection> projections;
	
	public Movie(){
		
	}
	
	public Movie(String name, List<String> actors, String genre, String director, String runtime, int rating,
			String description) {
		super();
		this.name = name;
		this.actors = actors;
		this.genre = genre;
		this.director = director;
		this.runtime = runtime;
		this.rating = rating;
		this.description = description;
	}
	
	public Movie(String name, List<String> actors, String genre, String director, String runtime, int rating,
			String description, byte[] image) {
		super();
		this.name = name;
		this.actors = actors;
		this.genre = genre;
		this.director = director;
		this.runtime = runtime;
		this.rating = rating;
		this.description = description;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
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
	
}
