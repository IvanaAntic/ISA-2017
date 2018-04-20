package com.example.isa2017.modelDTO;

import java.util.Date;



public class ProjectionDTO {

	private Long Id;
	private String date;	
	private String price;	
	private HallDTO hall;
	private MovieDTO movie;
	public ProjectionDTO() {
		super();
	}
	public ProjectionDTO(Long id, String date, String price, HallDTO hall, MovieDTO movie) {
		super();
		Id = id;
		this.date = date;
		this.price = price;
		this.hall = hall;
		this.movie = movie;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public HallDTO getHall() {
		return hall;
	}
	public void setHall(HallDTO hall) {
		this.hall = hall;
	}
	public MovieDTO getMovie() {
		return movie;
	}
	public void setMovie(MovieDTO movie) {
		this.movie = movie;
	}
	
	
}
