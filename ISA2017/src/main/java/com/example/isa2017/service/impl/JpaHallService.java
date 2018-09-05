package com.example.isa2017.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.isa2017.model.Hall;
import com.example.isa2017.repository.HallRepository;
import com.example.isa2017.service.HallService;

@Transactional
@Service
public class JpaHallService implements HallService {

	@Autowired
	private HallRepository hallRepository;
	
	@Override
	public Hall findOne(Long id) {
		
		return hallRepository.findOne(id);
	}

	@Override
	public List<Hall> findAll() {
		
		return hallRepository.findAll();
	}

	@Override
	public Hall save(Hall hall) {
		
		return hallRepository.save(hall);
	}

	@Override
	public Hall delete(Long id) {
		
		Hall hall = hallRepository.findOne(id);
		if (hall == null) {
			throw new IllegalArgumentException("Ne postoji.");
		}
		hallRepository.delete(hall);
		return hall;
	}

}
