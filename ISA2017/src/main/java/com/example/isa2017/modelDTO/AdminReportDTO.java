package com.example.isa2017.modelDTO;

public class AdminReportDTO {

	private String dateBeggining;
	private String dateEnding;
	private Long cinemaId;
	
	public AdminReportDTO() {
		super();
	}

	public String getDateBeggining() {
		return dateBeggining;
	}

	public void setDateBeggining(String dateBeggining) {
		this.dateBeggining = dateBeggining;
	}

	public String getDateEnding() {
		return dateEnding;
	}

	public void setDateEnding(String dateEnding) {
		this.dateEnding = dateEnding;
	}

	public Long getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}
	
}
