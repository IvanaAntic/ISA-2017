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
	 * Returns a theatre with specified ID.
	 * @param id ID of the theatre
	 * @return Theatre, if theatre with such ID
	 * exists, {@code null} if theatre is not found.
	 */
	Theatre findOne(Long id);
	
	/**
	 * Persists a theatre. If theatre's id is null,
	 * a new id will be assigned automatically.
	 * @param theatre
	 * @return theatre state after persisting. 
	 */
	Theatre save(Theatre theatre);

	List<Theatre> searchTheaters(String name, String address);
	
}
