package com.example.isa2017.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.isa2017.model.Projection;
import com.example.isa2017.model.Ticket;
import com.example.isa2017.repository.TicketRepository;
import com.example.isa2017.service.TicketService;

@Transactional
@Service
public class JpaTicketService implements TicketService{
	
	@Autowired
	TicketRepository ticketRepo;

	@Override
	public Ticket findOne(Long id) {
		return ticketRepo.findOne(id);
	}

	@Override
	public List<Ticket> findAll() {
		return ticketRepo.findAll();
	}

	@Override
	public Ticket save(Ticket ticket) {
		return ticketRepo.save(ticket);
	}

	@Override
	public Ticket delete(Long id) {
		Ticket ticket = ticketRepo.findOne(id);
		if (ticket == null) {
			throw new IllegalArgumentException("Ne postoji.");
		}
		ticketRepo.delete(ticket);
		return ticket;
	}

}
