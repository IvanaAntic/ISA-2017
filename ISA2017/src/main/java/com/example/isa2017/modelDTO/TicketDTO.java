package com.example.isa2017.modelDTO;

public class TicketDTO {

	private Long Id;
	private Long projectionId;
	private Long projectionMovieCinemaId;
	private String projectionMovieCinemaName;
	private String projectionMovieName;
	private String projectionMovieId;
	private Long projectionTheatreCinemaId;
	private String projectionTheatreCinemaName;
	private String projectionTheatreName;
	private String projectionTheatreId;
	private String date;
	private String time;
	private String projectionPrice;
	private Long seatId;
	private int seatRowNumber;
	private int seatColumnNumber;
	private String projectionHallName;
	private int discount;
	
	
	public TicketDTO() {
		super();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getProjectionMovieName() {
		return projectionMovieName;
	}

	public void setProjectionMovieName(String projectionMovieName) {
		this.projectionMovieName = projectionMovieName;
	}

	public Long getProjectionId() {
		return projectionId;
	}

	public void setProjectionId(Long projectionId) {
		this.projectionId = projectionId;
	}

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Long getProjectionMovieCinemaId() {
		return projectionMovieCinemaId;
	}

	public void setProjectionMovieCinemaId(Long projectionMovieCinemaId) {
		this.projectionMovieCinemaId = projectionMovieCinemaId;
	}

	public String getProjectionPrice() {
		return projectionPrice;
	}

	public void setProjectionPrice(String projectionPrice) {
		this.projectionPrice = projectionPrice;
	}

	public String getProjectionHallName() {
		return projectionHallName;
	}

	public void setProjectionHallName(String projectionHallName) {
		this.projectionHallName = projectionHallName;
	}

	public int getSeatRowNumber() {
		return seatRowNumber;
	}

	public void setSeatRowNumber(int seatRowNumber) {
		this.seatRowNumber = seatRowNumber;
	}

	public int getSeatColumnNumber() {
		return seatColumnNumber;
	}

	public void setSeatColumnNumber(int seatColumnNumber) {
		this.seatColumnNumber = seatColumnNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getProjectionMovieCinemaName() {
		return projectionMovieCinemaName;
	}

	public void setProjectionMovieCinemaName(String projectionMovieCinemaName) {
		this.projectionMovieCinemaName = projectionMovieCinemaName;
	}

	public String getProjectionMovieId() {
		return projectionMovieId;
	}

	public void setProjectionMovieId(String projectionMovieId) {
		this.projectionMovieId = projectionMovieId;
	}

	public Long getProjectionTheatreCinemaId() {
		return projectionTheatreCinemaId;
	}

	public void setProjectionTheatreCinemaId(Long projectionTheatreCinemaId) {
		this.projectionTheatreCinemaId = projectionTheatreCinemaId;
	}

	public String getProjectionTheatreCinemaName() {
		return projectionTheatreCinemaName;
	}

	public void setProjectionTheatreCinemaName(String projectionTheatreCinemaName) {
		this.projectionTheatreCinemaName = projectionTheatreCinemaName;
	}

	public String getProjectionTheatreName() {
		return projectionTheatreName;
	}

	public void setProjectionTheatreName(String projectionTheatreName) {
		this.projectionTheatreName = projectionTheatreName;
	}

	public String getProjectionTheatreId() {
		return projectionTheatreId;
	}

	public void setProjectionTheatreId(String projectionTheatreId) {
		this.projectionTheatreId = projectionTheatreId;
	}
	
}
