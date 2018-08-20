package com.example.isa2017.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="seat")
public class Seat {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@Column
	private int seatRow;
	
	@Column
	private int seatColumn;
	//@ManyToOne
	//private Hall hall;

	@Column
	private boolean isReserved;
	
	public Seat() {}

	public Seat(int seatRow, int seatColumn, boolean isReserved) {
		super();
		this.seatRow = seatRow;
		this.seatColumn = seatColumn;
		this.isReserved = isReserved;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public int getseatRow() {
		return seatRow;
	}

	public void setseatRow(int seatRow) {
		this.seatRow = seatRow;
	}

	public int getseatColumn() {
		return seatColumn;
	}

	public void setseatColumn(int seatColumn) {
		this.seatColumn = seatColumn;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
	
	
	
}
