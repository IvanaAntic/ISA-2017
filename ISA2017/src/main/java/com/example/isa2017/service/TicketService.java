package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.Ticket;

public interface TicketService {

	Ticket findOne(Long id);
	List<Ticket> findAll();
	Ticket save(Ticket ticket);
	Ticket delete(Long id);
	
}
