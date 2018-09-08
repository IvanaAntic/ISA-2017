package com.example.isa2017.controller;

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
import com.example.isa2017.service.UserService;

@RestController
@RequestMapping(value = "/tickets")
public class TicketController {
	
	@Autowired
	private ProjectionService projectionService;
	
	@Autowired
	private SeatService seatService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TicketToTicketDTO toTicketDTO;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "createQuick", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<TicketDTO> createQuick(HttpServletRequest request, @RequestBody TicketDTO ticketDTO){
		
		// da li je ulogovan i da li je admin
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		if(logged.getRole()!=Role.ADMIN)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);*/
		
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
		
		quickTicket.setProjection(projection);
		quickTicket.setSeat(seat);
		quickTicket.setDiscount(ticketDTO.getDiscount());
		
		ticketService.save(quickTicket);
		
		
		return new ResponseEntity<>(toTicketDTO.convert(quickTicket), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "reserveQuick/{ticketId}", method = RequestMethod.PUT)
	public ResponseEntity<TicketDTO> reserveQuick(HttpServletRequest request, @PathVariable Long ticketId){
		
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
		
		return new ResponseEntity<>(toTicketDTO.convert(ticket), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{cinemaId}", method = RequestMethod.GET)
	public ResponseEntity<List<TicketDTO>> getQuick(@PathVariable Long cinemaId){
		
		List<Ticket> allTickets = ticketService.findAll();
		List<Ticket> tickets = new ArrayList<Ticket>();
		
		for(Ticket ticket : allTickets){
			if(ticket.getProjection().getMovie().getCinema().getId() == cinemaId && ticket.getUser() == null){
				if(!ticket.getProjection().getExpired()){
					tickets.add(ticket);
				}
			}
		}
		
		return new ResponseEntity<>(toTicketDTO.convert(tickets), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{ticketId}", method = RequestMethod.DELETE)
	public ResponseEntity<TicketDTO> delete(@PathVariable Long ticketId){
		
		Ticket ticket = ticketService.delete(ticketId);
		Seat seat = ticket.getSeat();
		seat.setReserved(false);
		seatService.save(seat);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "getHistory", method = RequestMethod.GET)
	public ResponseEntity<List<TicketDTO>> getHistory(HttpServletRequest request){
		// da li je ulogovan i da li je user
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		if(logged.getRole()!=Role.USER)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		Date today = new Date();
		today = Calendar.getInstance().getTime();
		
		List<Ticket> tickets = userService.findById(logged.getId()).getTickets();
		List<Ticket> expired = new ArrayList<>();
		
		for(Ticket ticket : tickets){
			if(today.after(ticket.getProjection().getDate())){
				Projection p = ticket.getProjection();
				p.setExpired(true);
				projectionService.save(p);
			}
		}
		
		for(Ticket ticket : tickets){
			if(ticket.getProjection().getExpired())
				expired.add(ticket);
		}
				
		return new ResponseEntity<>(toTicketDTO.convert(expired), HttpStatus.OK);
	}
	
}
