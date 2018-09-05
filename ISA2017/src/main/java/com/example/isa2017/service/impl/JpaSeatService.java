package com.example.isa2017.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.isa2017.model.Seat;
import com.example.isa2017.repository.SeatRepository;
import com.example.isa2017.service.SeatService;

@Transactional
@Service
public class JpaSeatService implements SeatService {

	@Autowired
	private SeatRepository seatRepository;
	
	@Override
	public Seat findOne(Long id) {
		
		return seatRepository.findOne(id);
	}

	@Override
	public List<Seat> findAll() {
	
		return seatRepository.findAll();
	}

	@Override
	public Seat save(Seat seat) {

		return seatRepository.save(seat);
	}

	@Override
	public Seat delete(Long id) {
		Seat seat = seatRepository.findOne(id);
		if (seat == null) {
			throw new IllegalArgumentException("Sediste ne postoji.");
		}
		seatRepository.delete(seat);
		return seat;
	}

}
