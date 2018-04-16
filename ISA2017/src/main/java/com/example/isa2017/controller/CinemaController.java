package com.example.isa2017.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.converters.CinemaToCinemaDTO;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Movie;
import com.example.isa2017.modelDTO.CinemaDTO;
import com.example.isa2017.service.CinemaService;

@RestController
@RequestMapping(value = "/cinemas")
public class CinemaController {

	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private CinemaToCinemaDTO toCinemaDTO;
	
	@RequestMapping(value="getCinemas", method = RequestMethod.GET)
	public ResponseEntity<List<CinemaDTO>> getCinemas() {
		
		List<Cinema> cinemas = cinemaService.findAll();
		 
		return new ResponseEntity<>(toCinemaDTO.convert(cinemas), HttpStatus.OK);
	}
	
	@RequestMapping(value="getTCadminCinemas", method = RequestMethod.GET)
	public ResponseEntity<List<Cinema>> getTCadminCinemas() {
		
		List<Cinema> cinemas = cinemaService.findAll();
		 
		return new ResponseEntity<>(cinemas, HttpStatus.OK);
	}
	
	@RequestMapping(value = "deleteMovieInCinema/{cinemaId}/{movieId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Cinema> deleteMovieInCinema(@PathVariable Long cinemaId, @PathVariable Long movieId){
		
		//String movieToDelete = Long.toString(cinemaId);
		
		Cinema cinema = cinemaService.findOne(cinemaId);
		
		for(int i = 0; i < cinema.getMovies().size(); i++){
			if(cinema.getMovies().get(i).getId() == movieId)
				cinema.getMovies().remove(cinema.getMovies().get(i));
		}
		
		Cinema cinemaToDeleteMovieFrom = cinemaService.save(cinema);
		
	 return new ResponseEntity<>(cinemaToDeleteMovieFrom, HttpStatus.OK);
	}
	
}
