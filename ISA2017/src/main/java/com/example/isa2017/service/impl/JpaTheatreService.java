package com.example.isa2017.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.isa2017.model.Theatre;
import com.example.isa2017.repository.TheatreRepository;
import com.example.isa2017.service.TheatreService;

@Transactional
@Service
public class JpaTheatreService implements TheatreService{
	
	@Autowired
	private TheatreRepository theatreRepo;

	@Override
	public List<Theatre> findAll() {
		// TODO Auto-generated method stub
		return theatreRepo.findAll();
	}

	@Override
	public Theatre save(Theatre theatre) {
		// TODO Auto-generated method stub
		return theatreRepo.save(theatre);
	}

	@Override
	public Theatre findOne(Long id) {
		// TODO Auto-generated method stub
		return theatreRepo.findOne(id);
	}

}
