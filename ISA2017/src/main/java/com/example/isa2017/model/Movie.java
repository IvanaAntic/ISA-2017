package com.example.isa2017.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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
	
	@Column
	private int rating;
	
	@Column(columnDefinition="VARCHAR(500)")
	private String description;
	
	@ElementCollection
	@Column
	private List<String> arenas;
	
	@ElementCollection
	@Column
	private List<String> projectionTimes;
	
	@Column
	private int price;
	
	public Movie(){
		
	}
	
	public Movie(String name, List<String> actors, String genre, String director, String runtime, int rating,
			String description, List<String> projectionTimes, int price) {
		super();
		this.name = name;
		this.actors = actors;
		this.genre = genre;
		this.director = director;
		this.runtime = runtime;
		this.rating = rating;
		this.description = description;
		this.projectionTimes = projectionTimes;
		this.price = price;
	}
	
	public Movie(String name, List<String> actors, String genre, String director, String runtime, int rating,
			String description, List<String> projectionTimes, int price, byte[] image) {
		super();
		this.name = name;
		this.actors = actors;
		this.genre = genre;
		this.director = director;
		this.runtime = runtime;
		this.rating = rating;
		this.description = description;
		this.projectionTimes = projectionTimes;
		this.price = price;
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

	public List<String> getProjectionTimes() {
		return projectionTimes;
	}

	public void setProjectionTimes(List<String> projectionTimes) {
		this.projectionTimes = projectionTimes;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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
	
	
	
}
