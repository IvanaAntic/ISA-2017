package com.example.isa2017.controller;

import java.util.ArrayList;
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

import com.example.isa2017.converters.TicketToTicketDTO;
import com.example.isa2017.model.Projection;
import com.example.isa2017.model.Role;
import com.example.isa2017.model.Seat;
import com.example.isa2017.model.Ticket;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.TicketDTO;
import com.example.isa2017.service.ProjectionService;
import com.example.isa2017.service.SeatService;
import com.example.isa2017.service.TicketService;

@RestController
@RequestMapping(value = "/tickets")
public class TicketController {
	
	@Autowired
	ProjectionService projectionService;
	
	@Autowired
	SeatService seatService;
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	TicketToTicketDTO ticketConverter;

	@RequestMapping(value = "createQuick", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Ticket> createQuick(HttpServletRequest request, @RequestBody TicketDTO ticketDTO){
		
		// da li je ulogovan i da li je admin
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		if(logged.getRole()!=Role.ADMIN)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		// napravi novi ticket i snimi...ali nema usera povezanog i takodje staviti to sediste da bude rezervisano
		Ticket quickTicket = new Ticket();
		Projection projection = projectionService.findOne(ticketDTO.getProjectionId());
		Seat seat = seatService.findOne(ticketDTO.getSeatId());
		if(seat.isReserved()){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}else{
			seat.setReserved(true);
			seatService.save(seat);
		}
		
		quickTicket.setMovieName(ticketDTO.getMovieName());
		quickTicket.setProjection(projection);
		quickTicket.setSeat(seat);
		quickTicket.setDiscount(ticketDTO.getDiscount());
		quickTicket.setCinemaId(ticketDTO.getCinemaId());
		
		ticketService.save(quickTicket);
		
		return new ResponseEntity<>(quickTicket, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "reserveQuick/{ticketId}", method = RequestMethod.PUT)
	public ResponseEntity<Ticket> reserveQuick(HttpServletRequest request, @PathVariable Long ticketId){
		
		// da li je ulogovan i da li je user
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		if(logged.getRole()!=Role.USER)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		//povezati kartu sa userom i snimiti
		Ticket ticket = ticketService.findOne(ticketId);
		
		// da li je karta povezana sa userom
		if(ticket.getUser() != null)
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		
		ticket.setUser(logged);
		ticketService.save(ticket);
		
		return new ResponseEntity<>(ticket, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{cinemaId}", method = RequestMethod.GET)
	public ResponseEntity<List<TicketDTO>> getQuick(@PathVariable Long cinemaId){
		
		List<Ticket> allTickets = ticketService.findAll();
		List<Ticket> tickets = new ArrayList<Ticket>();
		
		for(Ticket ticket : allTickets){
			if(ticket.getCinemaId() == cinemaId && ticket.getUser() == null){
				tickets.add(ticket);
			}
		}
		
		return new ResponseEntity<>(ticketConverter.convert(tickets), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{ticketId}", method = RequestMethod.DELETE)
	public ResponseEntity<Ticket> delete(@PathVariable Long ticketId){
		
		Ticket ticket = ticketService.delete(ticketId);
		Seat seat = ticket.getSeat();
		seat.setReserved(false);
		seatService.save(seat);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
}
