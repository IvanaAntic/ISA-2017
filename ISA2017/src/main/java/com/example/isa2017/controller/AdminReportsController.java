package com.example.isa2017.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.converters.DateConverter;
import com.example.isa2017.converters.TicketToTicketDTO;
import com.example.isa2017.model.Ticket;
import com.example.isa2017.modelDTO.AdminReportDTO;
import com.example.isa2017.modelDTO.TicketDTO;
import com.example.isa2017.service.TicketService;

@RestController
@RequestMapping(value="/reports")
public class AdminReportsController {

	@Autowired
	private DateConverter dateConverter;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TicketToTicketDTO toTicketDTO;
	
	@RequestMapping(value="byDay", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<List<TicketDTO>> getByDay(@RequestBody TicketDTO day) throws ParseException{
		
		Date dayDate = dateConverter.stringToDate(day.getDate());
		
		List<Ticket> tickets = new ArrayList<>();
		
		List<Ticket> onlyMovies = new ArrayList<>();
		for(Ticket ticket : ticketService.findAll()){
			if(ticket.getProjection().getMovie() != null)
				onlyMovies.add(ticket);
		}
		
		
		for(Ticket ticket : onlyMovies){
			if(ticket.getProjection().getMovie().getCinema().getId() == day.getProjectionMovieCinemaId()){
				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(dayDate);
				
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(ticket.getProjection().getDate());
				
				if(calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH))
					if(ticket.getUser() != null)
						tickets.add(ticket);
			}
		}
		
		return new ResponseEntity<>(toTicketDTO.convert(tickets), HttpStatus.OK);
	}
	
	@RequestMapping(value="byPeriod", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<List<TicketDTO>> getByPeriod(@RequestBody AdminReportDTO report) throws ParseException{
		
		Date dateBeginning = dateConverter.stringToDate(report.getDateBeggining());
		Calendar calendarBeginning = Calendar.getInstance();
		calendarBeginning.setTime(dateBeginning);
		
		Date dateEnding = dateConverter.stringToDate(report.getDateEnding());
		Calendar calendarEnding = Calendar.getInstance();
		calendarEnding.setTime(dateEnding);
		
		List<Ticket> tickets = new ArrayList<>();
		
		List<Ticket> onlyMovies = new ArrayList<>();
		for(Ticket ticket : ticketService.findAll()){
			if(ticket.getProjection().getMovie() != null)
				onlyMovies.add(ticket);
		}
		
		for(Ticket ticket : onlyMovies){
			if(ticket.getProjection().getMovie().getCinema().getId() == report.getCinemaId()){
				if(dateBeginning.before(ticket.getProjection().getDate()) && dateEnding.after(ticket.getProjection().getDate()))
					if(ticket.getUser() != null)
						tickets.add(ticket);
			}
		}
		
		return new ResponseEntity<>(toTicketDTO.convert(tickets), HttpStatus.OK);
	}
	
	@RequestMapping(value="byWeek", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<List<TicketDTO>> getByWeek(@RequestBody AdminReportDTO report) throws ParseException{
		
		Date dateBeginning = dateConverter.stringToDate(report.getDateBeggining());
		Date dateEnding = dateConverter.getWeek(dateBeginning);
		
		List<Ticket> tickets = new ArrayList<>();
		
		List<Ticket> onlyMovies = new ArrayList<>();
		for(Ticket ticket : ticketService.findAll()){
			if(ticket.getProjection().getMovie() != null)
				onlyMovies.add(ticket);
		}
		
		for(Ticket ticket : onlyMovies){
			if(ticket.getProjection().getMovie().getCinema().getId() == report.getCinemaId()){
				if(dateBeginning.before(ticket.getProjection().getDate()) && dateEnding.after(ticket.getProjection().getDate()))
					if(ticket.getUser() != null)
						tickets.add(ticket);
			}
		}
		
		return new ResponseEntity<>(toTicketDTO.convert(tickets), HttpStatus.OK);
	}
	
	@RequestMapping(value="byMonth", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<List<TicketDTO>> getByMonth(@RequestBody AdminReportDTO report) throws ParseException{
		
		Date dateBeginning = dateConverter.stringToDate(report.getDateBeggining());
		Date dateEnding = dateConverter.getMonth(dateBeginning);
		
		System.out.println("Krajnji datum: " + dateEnding);
		
		List<Ticket> tickets = new ArrayList<>();
		
		List<Ticket> onlyMovies = new ArrayList<>();
		for(Ticket ticket : ticketService.findAll()){
			if(ticket.getProjection().getMovie() != null)
				onlyMovies.add(ticket);
		}
		
		for(Ticket ticket : onlyMovies){
			if(ticket.getProjection().getMovie().getCinema().getId() == report.getCinemaId()){
				if(dateBeginning.before(ticket.getProjection().getDate()) && dateEnding.after(ticket.getProjection().getDate()))
					if(ticket.getUser() != null)
						tickets.add(ticket);
			}
		}
		
		return new ResponseEntity<>(toTicketDTO.convert(tickets), HttpStatus.OK);
	}
	
	/*
										POZORISTA		
	 																					*/
	
	
	@RequestMapping(value="byDayTheatre", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<List<TicketDTO>> getByDayTheatre(@RequestBody TicketDTO day) throws ParseException{
		
		Date dayDate = dateConverter.stringToDate(day.getDate());
		
		List<Ticket> tickets = new ArrayList<>();
		
		List<Ticket> onlyPlays = new ArrayList<>();
		for(Ticket ticket : ticketService.findAll()){
			if(ticket.getProjection().getPlay() != null)
				onlyPlays.add(ticket);
		}
		
		for(Ticket ticket : onlyPlays){
			if(ticket.getProjection().getPlay().getTheatre().getId() == day.getProjectionMovieCinemaId()){
				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(dayDate);
				
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(ticket.getProjection().getDate());
				
				if(calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH))
					if(ticket.getUser() != null)
						tickets.add(ticket);
			}
		}
		
		return new ResponseEntity<>(toTicketDTO.convert(tickets), HttpStatus.OK);
	}
	
	@RequestMapping(value="byPeriodTheatre", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<List<TicketDTO>> getByPeriodTheatre(@RequestBody AdminReportDTO report) throws ParseException{
		
		Date dateBeginning = dateConverter.stringToDate(report.getDateBeggining());
		Calendar calendarBeginning = Calendar.getInstance();
		calendarBeginning.setTime(dateBeginning);
		
		Date dateEnding = dateConverter.stringToDate(report.getDateEnding());
		Calendar calendarEnding = Calendar.getInstance();
		calendarEnding.setTime(dateEnding);
		
		List<Ticket> tickets = new ArrayList<>();
		
		List<Ticket> onlyPlays = new ArrayList<>();
		for(Ticket ticket : ticketService.findAll()){
			if(ticket.getProjection().getPlay() != null)
				onlyPlays.add(ticket);
		}
		
		for(Ticket ticket : onlyPlays){
			if(ticket.getProjection().getPlay().getTheatre().getId() == report.getCinemaId()){
				if(dateBeginning.before(ticket.getProjection().getDate()) && dateEnding.after(ticket.getProjection().getDate()))
					if(ticket.getUser() != null)
						tickets.add(ticket);
			}
		}
		
		return new ResponseEntity<>(toTicketDTO.convert(tickets), HttpStatus.OK);
	}
	
	@RequestMapping(value="byWeekTheatre", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<List<TicketDTO>> getByWeekTheatre(@RequestBody AdminReportDTO report) throws ParseException{
		
		Date dateBeginning = dateConverter.stringToDate(report.getDateBeggining());
		Date dateEnding = dateConverter.getWeek(dateBeginning);
		
		List<Ticket> tickets = new ArrayList<>();
		
		List<Ticket> onlyPlays = new ArrayList<>();
		for(Ticket ticket : ticketService.findAll()){
			if(ticket.getProjection().getPlay() != null)
				onlyPlays.add(ticket);
		}
		
		for(Ticket ticket : onlyPlays){
			if(ticket.getProjection().getPlay().getTheatre().getId() == report.getCinemaId()){
				if(dateBeginning.before(ticket.getProjection().getDate()) && dateEnding.after(ticket.getProjection().getDate()))
					if(ticket.getUser() != null)
						tickets.add(ticket);
			}
		}
		
		return new ResponseEntity<>(toTicketDTO.convert(tickets), HttpStatus.OK);
	}
	
	@RequestMapping(value="byMonthTheatre", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<List<TicketDTO>> getByMonthTheatre(@RequestBody AdminReportDTO report) throws ParseException{
		
		Date dateBeginning = dateConverter.stringToDate(report.getDateBeggining());
		Date dateEnding = dateConverter.getMonth(dateBeginning);
		
		System.out.println("Krajnji datum: " + dateEnding);
		
		List<Ticket> tickets = new ArrayList<>();
		
		List<Ticket> onlyPlays = new ArrayList<>();
		for(Ticket ticket : ticketService.findAll()){
			if(ticket.getProjection().getPlay() != null)
				onlyPlays.add(ticket);
		}
		
		for(Ticket ticket : onlyPlays){
			if(ticket.getProjection().getPlay().getTheatre().getId() == report.getCinemaId()){
				if(dateBeginning.before(ticket.getProjection().getDate()) && dateEnding.after(ticket.getProjection().getDate()))
					if(ticket.getUser() != null)
						tickets.add(ticket);
			}
		}
		
		return new ResponseEntity<>(toTicketDTO.convert(tickets), HttpStatus.OK);
	}
	
}
