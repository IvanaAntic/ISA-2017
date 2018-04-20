package com.example.isa2017.modelDTO;

import javax.persistence.Column;

public class SeatDTO {

	private Long Id;
	private int row;	
	private int column;
	private boolean isReserved;
	
	public SeatDTO() {
		super();
	}
	public SeatDTO(Long id, int row, int column, boolean isReserved) {
		super();
		Id = id;
		this.row = row;
		this.column = column;
		this.isReserved = isReserved;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public boolean isReserved() {
		return isReserved;
	}
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
	
	
}
