package com.example.isa2017.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.example.isa2017.converters.DateConverter;
import com.example.isa2017.converters.ProjectionToProjectionDTO;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Hall;
import com.example.isa2017.model.Movie;
import com.example.isa2017.model.Play;
import com.example.isa2017.model.Projection;
import com.example.isa2017.model.Theatre;
import com.example.isa2017.model.Ticket;
import com.example.isa2017.modelDTO.ProjectionDTO;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.HallService;
import com.example.isa2017.service.MovieService;
import com.example.isa2017.service.PlayService;
import com.example.isa2017.service.ProjectionService;
import com.example.isa2017.service.TheatreService;

@RestController
@RequestMapping(value = "/projections")
public class ProjectionController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private PlayService playService;
	
	@Autowired
	private ProjectionService projectionService;
	
	@Autowired
	private HallService hallService;

	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private TheatreService theatreService;

	@Autowired
	private ProjectionToProjectionDTO toProjectionDTO;
	
	@Autowired
	private DateConverter dateConverter;

	@RequestMapping(value = "addProjection", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<ProjectionDTO> addProjection(HttpServletRequest request, @RequestBody ProjectionDTO projDTO) throws ParseException{
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		Date today = new Date();
		today = Calendar.getInstance().getTime();
		
		Movie movie = movieService.findOne(projDTO.getMovieId());
		Hall hall = hallService.findOne(projDTO.getHallId());
		
		Projection proj = new Projection();
		proj.setPrice(projDTO.getPrice());
		proj.setHall(hall);
		proj.setMovie(movie);
		proj.setDate(dateConverter.stringToDate(projDTO.getDate()));
		proj.setEndDate(dateConverter.addRuntime(dateConverter.stringToDate(projDTO.getDate()), movie.getRuntime()));
		
		/*	ne moze se dodati projekcija u proslost*/
		if(today.after(proj.getDate()))
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		
		/*	ne moze se dodati projekcija koja se nalazi u istoj sali
			i u istom vremenskom periodu kad i druga projekcija*/
		for(Projection p : movie.getProjections()){
			if(p.getDate().before(proj.getDate()) && p.getEndDate().after(proj.getDate()) && p.getHall().getId() == projDTO.getHallId())
				return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		projectionService.save(proj);
		
		return new ResponseEntity<>(toProjectionDTO.convert(proj), HttpStatus.OK);
	}
	
	@RequestMapping(value = "addProjectionPlay", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<ProjectionDTO> addProjectionPlay(HttpServletRequest request, @RequestBody ProjectionDTO projDTO) throws ParseException{
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		Date today = new Date();
		today = Calendar.getInstance().getTime();
		
		Play play = playService.findOne(projDTO.getPlayId());
		Hall hall = hallService.findOne(projDTO.getHallId());
		
		Projection proj = new Projection();
		proj.setPrice(projDTO.getPrice());
		proj.setHall(hall);
		proj.setPlay(play);
		proj.setDate(dateConverter.stringToDate(projDTO.getDate()));
		proj.setEndDate(dateConverter.addRuntime(dateConverter.stringToDate(projDTO.getDate()), play.getRuntime()));
		
		/*	ne moze se dodati projekcija u proslost*/
		if(today.after(proj.getDate()))
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		
		/*	ne moze se dodati projekcija koja se nalazi u istoj sali
			i u istom vremenskom periodu kad i druga projekcija*/
		for(Projection p : play.getProjections()){
			if(p.getDate().before(proj.getDate()) && p.getEndDate().after(proj.getDate()) && p.getHall().getId() == projDTO.getHallId())
				return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		projectionService.save(proj);
		
		return new ResponseEntity<>(toProjectionDTO.convert(proj), HttpStatus.OK);
	}
	
	@RequestMapping(value = "delete/{projectionId}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteProjection(HttpServletRequest request, @PathVariable Long projectionId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		Projection proj = projectionService.findOne(projectionId);
		
		/*	da li u ovoj projekciji postoji karta koju je rezervisao neki user*/
		for(Ticket t : proj.getTickets()){
			if(t.getUser() != null)
				return ResponseEntity
			            .status(HttpStatus.CONFLICT)
			            .body("Error Message");
		}
		
		projectionService.delete(projectionId);
		
	 return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{cinemaId}", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectionDTO>> getAll(@PathVariable Long cinemaId){
		
		List<Projection> projections = new ArrayList<>();
		
		/*	pronadji sve projekcije u ovom bioskopu	*/
		Cinema cinema = cinemaService.findOne(cinemaId);
		for(Movie movie : cinema.getMovies()){
			for(Projection proj : movie.getProjections()){
				projections.add(proj);
			}
		}
		
		return new ResponseEntity<>(toProjectionDTO.convert(projectionService.throwOutExpired(projections)), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "theatre/{theatreId}", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectionDTO>> getAllTheatre(@PathVariable Long theatreId){
		
		List<Projection> projections = new ArrayList<>();
		
		/*	pronadji sve projekcije u ovom bioskopu	*/
		Theatre theatre = theatreService.findOne(theatreId);
		for(Play movie : theatre.getPlays()){
			for(Projection proj : movie.getProjections()){
				projections.add(proj);
			}
		}
		
		return new ResponseEntity<>(toProjectionDTO.convert(projectionService.throwOutExpired(projections)), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/movie/{movieId}", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectionDTO>> getAllMovie(@PathVariable Long movieId){
		
		List<Projection> projections = movieService.findOne(movieId).getProjections();
		
		return new ResponseEntity<>(toProjectionDTO.convert(projectionService.throwOutExpired(projections)), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/play/{playId}", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectionDTO>> getAllPlay(@PathVariable Long playId){
		
		List<Projection> projections = playService.findOne(playId).getProjections();
		
		return new ResponseEntity<>(toProjectionDTO.convert(projectionService.throwOutExpired(projections)), HttpStatus.OK);
	}
	
}
