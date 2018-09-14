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

import com.example.isa2017.converters.MovieToMovieDTO;
import com.example.isa2017.model.Movie;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.MovieDTO;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {


	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private MovieToMovieDTO toMovieDTO;
	
	@RequestMapping(value="getTCadminMovies", method = RequestMethod.GET)
	public ResponseEntity<List<MovieDTO>> getTCadminMovies(HttpServletRequest request) {
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		List<Movie> movies = movieService.findAll();
		 
		return new ResponseEntity<>(toMovieDTO.convert(movies), HttpStatus.OK);
	}
	
	@RequestMapping(value = "deleteMovie/{movieId}", method=RequestMethod.DELETE)
	public ResponseEntity<MovieDTO> deleteMovieInCinema(HttpServletRequest request, @PathVariable Long movieId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		
		/*Cinema cinema = cinemaService.findOne(cinemaId);
		
		for(int i = 0; i < cinema.getMovies().size(); i++){
			if(cinema.getMovies().get(i).getId() == movieId)
				cinema.getMovies().remove(cinema.getMovies().get(i));
		}
		
		cinemaService.save(cinema);*/
		
		movieService.delete(movieId);
		
	 return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "addMovieToCinema/{cinemaId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<MovieDTO> addMovieToCinema(HttpServletRequest request, @RequestBody Movie movie, @PathVariable Long cinemaId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		if(movie.getImage() != null){
			String s = new String(movie.getImage());
			
			String[] parts = s.split(",");
			String firstPart = parts[1];
			movie.setImage(Base64.getDecoder().decode(firstPart));
		}
		
		movie.setCinema(cinemaService.findOne(cinemaId));
		movieService.save(movie);
		
	 return new ResponseEntity<>(toMovieDTO.convert(movie), HttpStatus.OK);
	}
	
	@RequestMapping(value = "editMovie/{movieId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<MovieDTO> editMovie(HttpServletRequest request, @RequestBody Movie movieDTO, @PathVariable Long movieId){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		Movie movie = movieService.findOne(movieId);
		
		if(movieDTO.getImage() != null){
			String s = new String(movieDTO.getImage());
			
			String[] parts = s.split(",");
			String firstPart = parts[1];
			movie.setImage(Base64.getDecoder().decode(firstPart));
		}/*else{
			movie.setImage(movieService.findOne(movieId).getImage());
		}*/
		
		movie.setActors(movieDTO.getActors());
		movie.setDescription(movieDTO.getDescription());
		movie.setDirector(movieDTO.getDirector());
		movie.setGenre(movieDTO.getGenre());
		movie.setMovieName(movieDTO.getMovieName());
		movie.setRuntime(movieDTO.getRuntime());
		
		movieService.save(movie);
		
	 return new ResponseEntity<>(toMovieDTO.convert(movie), HttpStatus.OK);
	}
	
	@RequestMapping(value = "rateMovie/{movieId}", method=RequestMethod.POST, consumes=MediaType.ALL_VALUE)
	public ResponseEntity<MovieDTO> rateMovie(@PathVariable Long movieId, @RequestBody MovieDTO rating){
		
		Movie movie = movieService.findOne(movieId);
		
		movie.getRatingList().add(rating.getRating());
		
		System.out.println("integers in the rating list: ");
		for(int i : movie.getRatingList()){
			System.out.println(i + ", ");
		}
		
		movie.setAvgRating(movie.calculateAverage(movie.getRatingList()));
		
		movieService.save(movie);
		
		return new ResponseEntity<>(toMovieDTO.convert(movie), HttpStatus.OK);
	}
	
/*	@RequestMapping(value = "moviesToRate", method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> getMoviesToRate(HttpServletRequest request){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>(logged.getMoviesToRate(), HttpStatus.OK);
	}*/

}
