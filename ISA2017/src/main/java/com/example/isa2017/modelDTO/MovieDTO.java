package com.example.isa2017.modelDTO;

import java.util.List;

public class MovieDTO {


	private Long Id;
	private String name;
	private List<String> actors;
	private String genre;
	private String director;
	private String runtime;
    private byte[] image;
	private int rating;
	private String description;
	private List<String> arenas;
	private List<String> projectionTimes;
	private int price;
	
	private Long cinemaId;
	
	public MovieDTO() {
		super();
	}

	public MovieDTO(Long id, String name, List<String> actors, String genre, String director, String runtime,
			byte[] image, int rating, String description, List<String> arenas, List<String> projectionTimes,
			int price, Long cinemaId) {
		super();
		Id = id;
		this.name = name;
		this.actors = actors;
		this.genre = genre;
		this.director = director;
		this.runtime = runtime;
		this.image = image;
		this.rating = rating;
		this.description = description;
		this.arenas = arenas;
		this.projectionTimes = projectionTimes;
		this.price = price;
		this.cinemaId = cinemaId;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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

	public List<String> getArenas() {
		return arenas;
	}

	public void setArenas(List<String> arenas) {
		this.arenas = arenas;
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

	public Long getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}
	
	
	
}
