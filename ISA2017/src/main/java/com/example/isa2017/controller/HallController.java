package com.example.isa2017.controller;

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

import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Hall;
import com.example.isa2017.model.Seat;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.HallService;
import com.example.isa2017.service.SeatService;

@RestController
@RequestMapping(value = "/halls")
public class HallController {
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private SeatService seatService;
	
	@Autowired
	private CinemaService cinemaService;

	@RequestMapping(value = "addHall/{cinemaId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Hall> addHall(@RequestBody Hall hall, @PathVariable Long cinemaId){
		
		for(Seat seat : hall.getSeats()){
			seatService.save(seat);	
		}
		
		hallService.save(hall);
		
		Cinema cinema = cinemaService.findOne(cinemaId);
		cinema.getHalls().add(hall);
		cinemaService.save(cinema);
		
		return new ResponseEntity<>(hall, HttpStatus.OK);
	}
	
	@RequestMapping(value = "editHall/{hallId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Hall> editHall(HttpServletRequest request, @RequestBody Hall hall, @PathVariable Long hallId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		
		hall.setId(hallId);
		Hall editedHall = hallService.save(hall);
		
	 return new ResponseEntity<>(editedHall, HttpStatus.OK);
	}
	
	@RequestMapping(value = "deleteHall/{hallId}", method=RequestMethod.DELETE)
	public ResponseEntity<Hall> deleteHall(HttpServletRequest request, @PathVariable Long hallId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		List<Cinema> cinemas = cinemaService.findAll();
		
		for(Cinema cinema : cinemas){
			List<Hall> halls = cinema.getHalls();
			Hall tempHall = null;
			for(Hall hall : halls){
				if(hall.getId() == hallId){
					tempHall = hall;
				}
			}
			if(tempHall != null){
				halls.remove(tempHall);
				cinemaService.save(cinema);
			}
		}
		
		hallService.delete(hallId);
		
	 return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
