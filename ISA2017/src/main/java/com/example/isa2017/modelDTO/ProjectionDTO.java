package com.example.isa2017.modelDTO;

public class ProjectionDTO {

	private Long Id;
	private String date;
	private String time;
	private String price;	
	private Long hallId;
	private Long movieId;
	private Long playId;
	
	private String movieMovieName;
	private String playPlayName;
	private String hallHallName;
	
	public ProjectionDTO() {
		super();
	}
	
	public ProjectionDTO(Long id, String date, String price, Long hallId, Long movieId) {
		super();
		Id = id;
		this.date = date;
		this.price = price;
		this.hallId = hallId;
		this.movieId = movieId;
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

	public Long getHallId() {
		return hallId;
	}

	public void setHallId(Long hallId) {
		this.hallId = hallId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getMovieMovieName() {
		return movieMovieName;
	}

	public void setMovieMovieName(String movieMovieName) {
		this.movieMovieName = movieMovieName;
	}

	public String getHallHallName() {
		return hallHallName;
	}

	public void setHallHallName(String hallHallName) {
		this.hallHallName = hallHallName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getPlayId() {
		return playId;
	}

	public void setPlayId(Long playId) {
		this.playId = playId;
	}

	public String getPlayPlayName() {
		return playPlayName;
	}

	public void setPlayPlayName(String playName) {
		this.playPlayName = playName;
	}
	
}
