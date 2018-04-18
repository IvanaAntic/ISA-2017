package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.Movie;

public interface MovieService {

	/**
	 *  
	 * @return List of all existing movies.
	 */
	List<Movie> findAll();
	
	/**
	 * Persists a movie. If movie's id is null,
	 * a new id will be assigned automatically.
	 * @param movie
	 * @return movie state after persisting. 
	 */
	Movie save(Movie movie);
	
	Movie findOne(Long id);
	
}
