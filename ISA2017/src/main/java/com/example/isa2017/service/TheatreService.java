package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.Theatre;

public interface TheatreService {

	/**
	 *  
	 * @return List of all existing theatres.
	 */
	List<Theatre> findAll();
	
	/**
	 * Persists a theatre. If theatre's id is null,
	 * a new id will be assigned automatically.
	 * @param theatre
	 * @return theatre state after persisting. 
	 */
	Theatre save(Theatre theatre);
	
}
