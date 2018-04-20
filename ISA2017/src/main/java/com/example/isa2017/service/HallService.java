package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.Hall;

public interface HallService {

	Hall findOne(Long id);
	List<Hall> findAll();
	Hall save(Hall hall);
	Hall delete(Long id);
}
