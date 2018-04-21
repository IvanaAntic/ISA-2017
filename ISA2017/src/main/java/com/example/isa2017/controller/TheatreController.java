package com.example.isa2017.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.converters.TheatreToTheatreDTO;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Theatre;
import com.example.isa2017.modelDTO.TheatreDTO;
import com.example.isa2017.service.TheatreService;

@RestController
@RequestMapping(value = "/theatres")
public class TheatreController {

	@Autowired
	private TheatreService theatreService;
	
	@Autowired
	private TheatreToTheatreDTO toTheatreDTO;
	
	@RequestMapping(value="getTheatres", method = RequestMethod.GET)
	public ResponseEntity<List<Theatre>> getTheatres() {
		 List<Theatre> theatres = theatreService.findAll();
		 return new ResponseEntity<>(theatres, HttpStatus.OK);
	}
	
	@RequestMapping(value="getTCadminTheatres", method = RequestMethod.GET)
	public ResponseEntity<List<Theatre>> getTCadminTheatres() {
		 List<Theatre> theatres = theatreService.findAll();
		 return new ResponseEntity<>(theatres, HttpStatus.OK);
	}
	
	@RequestMapping(value = "deletePlayInTheatre/{theatreId}/{playId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Theatre> deletePlayInTheatre(@PathVariable Long theatreId, @PathVariable Long playId){
		
		Theatre theatre = theatreService.findOne(theatreId);
		
		for(int i = 0; i < theatre.getPlays().size(); i++){
			if(theatre.getPlays().get(i).getId() == playId)
				theatre.getPlays().remove(theatre.getPlays().get(i));
		}
		
		Theatre theatreToDeletePlayFrom = theatreService.save(theatre);
		
	 return new ResponseEntity<>(theatreToDeletePlayFrom, HttpStatus.OK);
	}
	
	/*@RequestMapping(value="searchTheatre/{name}/{address}",method=RequestMethod.POST)
	public ResponseEntity<List<Theatre>> searchTheatre(@PathVariable String name,@PathVariable String address){
		 List<Theatre> theatres =new ArrayList<Theatre>();
		 for(Theatre theathre:theatreService.searchTheaters(name, address)){
			 theatres.add(theathre);
		 }
		 return new ResponseEntity<>(theatres, HttpStatus.OK);
	}
	*/
	
}
