package com.example.isa2017.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.model.Theatre;
import com.example.isa2017.service.TheatreService;

@RestController
@RequestMapping(value = "/theatres")
public class TheatreController {

	@Autowired
	private TheatreService theatreService;
	
	@RequestMapping(value="getCinemas", method = RequestMethod.GET)
	public ResponseEntity<List<Theatre>> getCinemas() {
		 List<Theatre> theatres = theatreService.findAll();
		 return new ResponseEntity<>(theatres, HttpStatus.OK);
	}
}
