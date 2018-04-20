package com.example.isa2017.controller;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.CinemaDTO;
import com.example.isa2017.repository.CinemaRepository;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.MovieService;

@RestController
@RequestMapping(value = "/cinemas")
public class CinemaController {

	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private CinemaToCinemaDTO toCinemaDTO;
	
	/*
	 *          za neregistrovane korisnike
	 * */
	
	@RequestMapping(value="getCinemas", method = RequestMethod.GET)
	public ResponseEntity<List<CinemaDTO>> getCinemas() {
		
		List<Cinema> cinemas = cinemaService.findAll();
		 
		return new ResponseEntity<>(toCinemaDTO.convert(cinemas), HttpStatus.OK);
	}
	
	/*
	 *          za TCadmine
	 * */
	
	@RequestMapping(value="getTCadminCinemas", method = RequestMethod.GET)
	public ResponseEntity<List<Cinema>> getTCadminCinemas(HttpServletRequest request) {
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		
		List<Cinema> cinemas = cinemaService.findAll();
		 
		return new ResponseEntity<>(cinemas, HttpStatus.OK);
	}
	
	@RequestMapping(value="getTCadminMovies", method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> getTCadminMovies(HttpServletRequest request) {
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		
		List<Movie> movies = movieService.findAll();
		 
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}
	
	@RequestMapping(value = "deleteMovieInCinema/{cinemaId}/{movieId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Cinema> deleteMovieInCinema(HttpServletRequest request, @PathVariable Long cinemaId, @PathVariable Long movieId){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		
		Cinema cinema = cinemaService.findOne(cinemaId);
		
		for(int i = 0; i < cinema.getMovies().size(); i++){
			if(cinema.getMovies().get(i).getId() == movieId)
				cinema.getMovies().remove(cinema.getMovies().get(i));
		}
		
		cinemaService.save(cinema);
		
	 return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "addMovieToCinema/{cinemaId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Movie> addMovieToCinema(HttpServletRequest request, @RequestBody Movie movie, @PathVariable Long cinemaId){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		
		Cinema cinema = cinemaService.findOne(cinemaId);
		
		
		if(movie.getImage() != null){
			String s = new String(movie.getImage());
			
			String[] parts = s.split(",");
			String firstPart = parts[1];
			movie.setImage(Base64.getDecoder().decode(firstPart));
		}
		
		Movie addedMovie = movieService.save(movie);
		cinema.getMovies().add(addedMovie);
		cinemaService.save(cinema);
		
	 return new ResponseEntity<>(addedMovie, HttpStatus.OK);
	}
	
	@RequestMapping(value = "editMovie/{movieId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Movie> editMovie(HttpServletRequest request, @RequestBody Movie movie, @PathVariable Long movieId){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		
		movie.setId(movieId);
		
		if(movie.getImage() != null){
			String s = new String(movie.getImage());
			
			String[] parts = s.split(",");
			String firstPart = parts[1];
			movie.setImage(Base64.getDecoder().decode(firstPart));
		}else{
			movie.setImage(movieService.findOne(movieId).getImage());
		}
		
		Movie editedMovie = movieService.save(movie);
		
	 return new ResponseEntity<>(editedMovie, HttpStatus.OK);
	}
	
	@RequestMapping(value = "editCinema/{cinemaId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Cinema> editMovie(HttpServletRequest request, @RequestBody Cinema cinema, @PathVariable Long cinemaId){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		
		cinema.setId(cinemaId);
		cinema.setMovies(cinemaService.findOne(cinemaId).getMovies());
		Cinema editedCinema = cinemaService.save(cinema);
		
	 return new ResponseEntity<>(editedCinema, HttpStatus.OK);
	}
	
}
