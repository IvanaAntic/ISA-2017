package com.example.isa2017.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.isa2017.model.Movie;
import com.example.isa2017.repository.MovieRepository;
import com.example.isa2017.service.MovieService;

@Transactional
@Service
public class JpaMovieService implements MovieService{

	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public List<Movie> findAll() {
		// TODO Auto-generated method stub
		return movieRepository.findAll();
	}

	@Override
	public Movie save(Movie movie) {
		// TODO Auto-generated method stub
		return movieRepository.save(movie);
	}

	@Override
	public Movie findOne(Long id) {
		// TODO Auto-generated method stub
		return movieRepository.findOne(id);
	}

	
}
