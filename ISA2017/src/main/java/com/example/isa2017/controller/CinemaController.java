package com.example.isa2017.controller;

import java.util.ArrayList;
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
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.CinemaDTO;
import com.example.isa2017.service.CinemaService;

@RestController
@RequestMapping(value = "/cinemas")
public class CinemaController {

	@Autowired
	private CinemaService cinemaService;
	
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
	public ResponseEntity<List<CinemaDTO>> getTCadminCinemas(HttpServletRequest request) {
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		
		List<Cinema> cinemas = cinemaService.findAll();
		 
		return new ResponseEntity<>(toCinemaDTO.convert(cinemas), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "editCinema/{cinemaId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<CinemaDTO> editMovie(HttpServletRequest request, @RequestBody CinemaDTO cinemaDTO, @PathVariable Long cinemaId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		Cinema cinema = cinemaService.findOne(cinemaId);
		cinema.setAddress(cinemaDTO.getAddress());
		cinema.setDescription(cinemaDTO.getDescription());
		cinema.setName(cinemaDTO.getName());
		cinemaService.save(cinema);
		
	 return new ResponseEntity<>(toCinemaDTO.convert(cinema), HttpStatus.OK);
	}
	
	@RequestMapping(value = "rateCinema/{cinemaId}", method=RequestMethod.POST, consumes=MediaType.ALL_VALUE)
	public ResponseEntity<CinemaDTO> rateCinema(HttpServletRequest request, @PathVariable Long cinemaId, @RequestBody Cinema rating){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		Cinema cinema = cinemaService.findOne(cinemaId);
		
		cinema.getRatingList().put(logged.getId(), rating.getRating());
		
		List<Integer> list = new ArrayList<>(cinema.getRatingList().values());
		
		cinema.setAvgRating(cinema.calculateAverage(list));
		
		cinemaService.save(cinema);
		
		return new ResponseEntity<>(toCinemaDTO.convert(cinema), HttpStatus.OK);
	}
}
