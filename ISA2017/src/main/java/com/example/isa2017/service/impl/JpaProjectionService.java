package com.example.isa2017.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.isa2017.model.Projection;
import com.example.isa2017.repository.ProjectionRepository;
import com.example.isa2017.service.ProjectionService;

@Transactional
@Service
public class JpaProjectionService implements ProjectionService {

	@Autowired
	private ProjectionRepository projectionRepository;
	
	@Override
	public Projection findOne(Long id) {

		return projectionRepository.findOne(id);
	}

	@Override
	public List<Projection> findAll() {

		return projectionRepository.findAll();
	}

	@Override
	public Projection save(Projection projection) {

		return projectionRepository.save(projection);
	}

	@Override
	public Projection delete(Long id) {
		Projection projection = projectionRepository.findOne(id);
		if (projection == null) {
			throw new IllegalArgumentException("Ne postoji.");
		}
		projectionRepository.delete(projection);
		return projection;
	}

}
