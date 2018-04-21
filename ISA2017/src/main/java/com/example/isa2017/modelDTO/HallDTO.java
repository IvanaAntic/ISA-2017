package com.example.isa2017.modelDTO;

import java.util.List;



import com.example.isa2017.model.Seat;

public class HallDTO {

	
	private Long Id;	
	private int rows;	
	private int columns;
	private String name;	
	private List<SeatDTO> seats;
	public HallDTO() {
		super();
	}
	public HallDTO(Long id, int rows, int columns, String name, List<SeatDTO> seats) {
		super();
		Id = id;
		this.rows = rows;
		this.columns = columns;
		this.name = name;
		this.seats = seats;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SeatDTO> getSeats() {
		return seats;
	}
	public void setSeats(List<SeatDTO> seats) {
		this.seats = seats;
	}
	
	
	
}
