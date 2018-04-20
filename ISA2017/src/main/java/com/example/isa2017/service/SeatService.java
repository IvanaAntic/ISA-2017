package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.Seat;

public interface SeatService {

	Seat findOne(Long id);
	List<Seat> findAll();
	Seat save(Seat seat);
	Seat delete(Long id);
}
