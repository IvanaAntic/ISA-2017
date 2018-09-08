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

import com.example.isa2017.converters.HallToHallDTO;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Hall;
import com.example.isa2017.model.Projection;
import com.example.isa2017.model.Seat;
import com.example.isa2017.modelDTO.HallDTO;
import com.example.isa2017.modelDTO.SeatDTO;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.HallService;
import com.example.isa2017.service.ProjectionService;
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
	
	@Autowired
	private ProjectionService projService;
	
	@Autowired
	private HallToHallDTO toHallDTO;

	@RequestMapping(value = "addHall/{cinemaId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<HallDTO> addHall(@RequestBody HallDTO hallDTO, @PathVariable Long cinemaId){
		
		Hall hall = new Hall();
		
		hall.setCinema(cinemaService.findOne(cinemaId));
		hall.setHallName(hallDTO.getHallName());
		hallService.save(hall);
		
		for(SeatDTO seat : hallDTO.getSeats()){
			Seat s = new Seat();
			s.setHall(hall);
			s.setReserved(seat.isReserved());
			s.setColumnNumber(seat.getColumnNumber());
			s.setRowNumber(seat.getRowNumber());
			seatService.save(s);	
		}
		
		Hall frontHall = hallService.findOne(hall.getId());
		
		return new ResponseEntity<>(toHallDTO.convert(frontHall), HttpStatus.OK);
	}
	
	@RequestMapping(value = "editHall/{hallId}", method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<HallDTO> editHall(HttpServletRequest request, @RequestBody HallDTO hallDTO, @PathVariable Long hallId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		Hall hall = hallService.findOne(hallId);
		hall.setHallName(hallDTO.getHallName());
		hallService.save(hall);
		
		for(Seat s : hall.getSeats()){
			seatService.delete(s.getId());
		}
		
		for(SeatDTO seat : hallDTO.getSeats()){
			Seat s = new Seat();
			s.setHall(hall);
			s.setReserved(seat.isReserved());
			s.setColumnNumber(seat.getColumnNumber());
			s.setRowNumber(seat.getRowNumber());
			seatService.save(s);	
		}
		
		hall = hallService.findOne(hallId);
		
	 return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "deleteHall/{hallId}", method=RequestMethod.DELETE)
	public ResponseEntity<Hall> deleteHall(HttpServletRequest request, @PathVariable Long hallId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		//List<Cinema> cinemas = cinemaService.findAll();
		/*List<Projection> projs = projService.findAll();
		
		for(Projection p : projs){									// da li postoji i dalje projekcija u sali?
			if(p.getHall() != null){
				if(p.getHall().getId() == hallId){
					return new ResponseEntity<>(HttpStatus.LOCKED);
				}
			}
		}*/
		
		for(Seat s : hallService.findOne(hallId).getSeats()){			// da li je neko mesto u sali rezervisano?
			if(s.isReserved())
				return new ResponseEntity<>(HttpStatus.LOCKED);
		}
		
		/*for(Cinema cinema : cinemas){				// uklanja se sala iz bioskopa
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
			
			cinema.getHalls().remove(hallService.findOne(hallId));
			
		}*/
		
		hallService.delete(hallId);
		
	 return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inProjection/{projId}", method = RequestMethod.GET)
	public ResponseEntity<HallDTO> getHall(@PathVariable Long projId){
		
		Projection proj = projService.findOne(projId);
		
		Hall hall = proj.getHall();
		
		return new ResponseEntity<>(toHallDTO.convert(hall), HttpStatus.OK);		// posle proveriti uz projekcije
		
	}
	
	@RequestMapping(value = "/hall/{hallId}", method = RequestMethod.GET)
	public ResponseEntity<HallDTO> getOneHall(@PathVariable Long hallId){
		
		return new ResponseEntity<>(toHallDTO.convert(hallService.findOne(hallId)), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{cinemaId}", method = RequestMethod.GET)
	public ResponseEntity<List<HallDTO>> getHalls(@PathVariable Long cinemaId){
		
		Cinema cinema = cinemaService.findOne(cinemaId);
		
		List<Hall> halls = cinema.getHalls();
		
		return new ResponseEntity<>(toHallDTO.convert(halls), HttpStatus.OK);
		
	}
	
}
