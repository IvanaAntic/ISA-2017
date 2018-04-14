package com.example.isa2017.modelDTO;

public class CinemaDTO {


	private Long Id;
	private String name;
	private String address;
	private String description;
	private int avgRating;
	
	CinemaDTO(){
		
	}

	public CinemaDTO(Long id, String name, String address, String description, int avgRating) {
		super();
		Id = id;
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
