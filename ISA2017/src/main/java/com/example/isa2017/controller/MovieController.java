package com.example.isa2017.controller;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {


	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private MovieService movieService;
	
	@RequestMapping(value="getTCadminMovies", method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> getTCadminMovies(HttpServletRequest request) {
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		
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
	
	@RequestMapping(value = "rateMovie/{movieId}", method=RequestMethod.POST, consumes=MediaType.ALL_VALUE)
	public ResponseEntity<Movie> rateMovie(@PathVariable Long movieId, @RequestBody Movie rating){
		
		Movie movie = movieService.findOne(movieId);
		
		movie.getRatingList().add(rating.getRating());
		
		System.out.println("integers in the rating list: ");
		for(int i : movie.getRatingList()){
			System.out.println(i + ", ");
		}
		
		movie.setAvgRating(movie.calculateAverage(movie.getRatingList()));
		
		movieService.save(movie);
		
		return new ResponseEntity<>(movie, HttpStatus.OK);
	}

}
