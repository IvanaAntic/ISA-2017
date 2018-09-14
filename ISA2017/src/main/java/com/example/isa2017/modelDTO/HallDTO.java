package com.example.isa2017.modelDTO;

import java.util.List;

public class HallDTO {

	
	private Long Id;
	private Long cinemaId;
	private Long theatreId;
	private String hallName;
	private List<SeatDTO> seats;
	
	public HallDTO() {
		super();
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getHallName() {
		return hallName;
	}
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}
	public List<SeatDTO> getSeats() {
		return seats;
	}
	public void setSeats(List<SeatDTO> seats) {
		this.seats = seats;
	}
	public Long getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

	public Long getTheatreId() {
		return theatreId;
	}

	public void setTheatreId(Long theatreId) {
		this.theatreId = theatreId;
	}
	
}
