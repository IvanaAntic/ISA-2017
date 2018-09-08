package com.example.isa2017.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Movie;
import com.example.isa2017.modelDTO.MovieDTO;

@Component
public class MovieToMovieDTO implements Converter<Movie, MovieDTO>{

	@Override
	public MovieDTO convert(Movie source) {
		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		MovieDTO movieDTO = modelMapper.map(source, MovieDTO.class);
		return movieDTO;
	}
	
	
	public List<MovieDTO> convert(List<Movie> source){
		
		List<MovieDTO> moviesDTO = new ArrayList<MovieDTO>();
		for (Movie movie : source) {
			moviesDTO.add(convert(movie));
		}
		
		return moviesDTO;
	}

}
