package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.Cinema;

public interface CinemaService {

	/**
	 *  
	 * @return List of all existing cinemas.
	 */
	List<Cinema> findAll();
	
	/**
	 * Returns a cinema with specified ID.
	 * @param id ID of the cinema
	 * @return Cinema, if cinema with such ID
	 * exists, {@code null} if cinema is not found.
	 */
	Cinema findOne(Long id);
	
	/**
	 * Persists a cinema. If cinema's id is null,
	 * a new id will be assigned automatically.
	 * @param cinema
	 * @return cinema state after persisting. 
	 */
	Cinema save(Cinema cinema);
	
}
