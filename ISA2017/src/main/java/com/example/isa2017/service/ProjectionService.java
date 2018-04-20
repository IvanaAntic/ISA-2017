package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.Projection;

public interface ProjectionService {

	Projection findOne(Long id);
	List<Projection> findAll();
	Projection save(Projection projection);
	Projection delete(Long id);
}
