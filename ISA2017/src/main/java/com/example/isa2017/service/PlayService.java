package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.Play;

public interface PlayService {
	/**
	 *  
	 * @return List of all existing plays.
	 */
	List<Play> findAll();
	
	/**
	 * Persists a play. If play's id is null,
	 * a new id will be assigned automatically.
	 * @param play
	 * @return play state after persisting. 
	 */
	Play save(Play play);
}
