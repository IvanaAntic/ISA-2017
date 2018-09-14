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

import com.example.isa2017.converters.TheatreToTheatreDTO;
import com.example.isa2017.model.Theatre;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.TheatreDTO;
import com.example.isa2017.service.TheatreService;

@RestController
@RequestMapping(value = "/theatres")
public class TheatreController {

	@Autowired
	private TheatreService theatreService;
	
	@Autowired
	private TheatreToTheatreDTO toTheatreDTO;
	
	/*
	 *          za neregistrovane korisnike
	 * */
	
	@RequestMapping(value="getTheatres", method = RequestMethod.GET)
	public ResponseEntity<List<TheatreDTO>> getTheatres() {
		
		List<Theatre> theatres = theatreService.findAll();
		 
		return new ResponseEntity<>(toTheatreDTO.convert(theatres), HttpStatus.OK);
	}
	
	/*
	 *          za TCadmine
	 * */
	
	@RequestMapping(value="getTCadminTheatres", method = RequestMethod.GET)
	public ResponseEntity<List<TheatreDTO>> getTCadminTheatres(HttpServletRequest request) {
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		
		List<Theatre> theatres = theatreService.findAll();
		 
		return new ResponseEntity<>(toTheatreDTO.convert(theatres), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "editTheatre/{theatreId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<TheatreDTO> editPlay(HttpServletRequest request, @RequestBody TheatreDTO theatreDTO, @PathVariable Long theatreId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		Theatre theatre = theatreService.findOne(theatreId);
		theatre.setAddress(theatreDTO.getAddress());
		theatre.setDescription(theatreDTO.getDescription());
		theatre.setName(theatreDTO.getName());
		theatreService.save(theatre);
		
	 return new ResponseEntity<>(toTheatreDTO.convert(theatre), HttpStatus.OK);
	}
	
	@RequestMapping(value = "rateTheatre/{theatreId}", method=RequestMethod.POST, consumes=MediaType.ALL_VALUE)
	public ResponseEntity<TheatreDTO> rateTheatre(HttpServletRequest request, @PathVariable Long theatreId, @RequestBody Theatre rating){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		Theatre theatre = theatreService.findOne(theatreId);
		
		theatre.getRatingList().put(logged.getId(), rating.getRating());
		
		List<Integer> list = new ArrayList<>(theatre.getRatingList().values());
		
		theatre.setAvgRating(theatre.calculateAverage(list));
		
		theatreService.save(theatre);
		
		return new ResponseEntity<>(toTheatreDTO.convert(theatre), HttpStatus.OK);
	}
}
