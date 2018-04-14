package com.example.isa2017.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.converters.CinemaToCinemaDTO;
import com.example.isa2017.model.Cinema;
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
	
}
