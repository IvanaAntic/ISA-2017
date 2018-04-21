package com.example.isa2017.modelDTO;

import java.util.List;

public class CinemaDTO {


	private Long Id;
	private String name;
	private String address;
	private String description;
	private int avgRating;
	private List<HallDTO> halls;
	CinemaDTO(){
		
	}

	public CinemaDTO(Long id, String name, String address, String description, int avgRating, List<HallDTO> halls) {
		super();
		Id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.avgRating = avgRating;
		this.halls = halls;
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

	public List<HallDTO> getHalls() {
		return halls;
	}

	public void setHalls(List<HallDTO> halls) {
		this.halls = halls;
	}

	
	
	
}
