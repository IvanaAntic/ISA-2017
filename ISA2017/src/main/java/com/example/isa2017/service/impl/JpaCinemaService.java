package com.example.isa2017.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.isa2017.model.Cinema;
import com.example.isa2017.repository.CinemaRepository;
import com.example.isa2017.service.CinemaService;

@Transactional
@Service
public class JpaCinemaService implements CinemaService{
	
	@Autowired
	private CinemaRepository cinemaRepository;

	@Override
	public List<Cinema> findAll() {
		// TODO Auto-generated method stub
		return cinemaRepository.findAll();
	}

	@Override
	public Cinema save(Cinema cinema) {
		// TODO Auto-generated method stub
		return cinemaRepository.save(cinema);
	}

	@Override
	public Cinema findOne(Long id) {
		// TODO Auto-generated method stub
		return cinemaRepository.findOne(id);
	}

}
