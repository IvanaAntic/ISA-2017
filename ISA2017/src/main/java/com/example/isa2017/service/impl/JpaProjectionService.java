package com.example.isa2017.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.isa2017.model.Projection;
import com.example.isa2017.model.Ticket;
import com.example.isa2017.repository.ProjectionRepository;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.ProjectionService;
import com.example.isa2017.service.TicketService;

@Service
public class JpaProjectionService implements ProjectionService {

	@Autowired
	private ProjectionRepository projectionRepository;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private CinemaService cinemaService;
	
	@Override
	public Projection findOne(Long id) {

		return projectionRepository.findOne(id);
	}

	@Override
	public List<Projection> findAll() {

		return projectionRepository.findAll();
	}

	@Override
	public Projection save(Projection projection) {

		return projectionRepository.save(projection);
	}

	@Override
	public Projection delete(Long id) {
		Projection projection = projectionRepository.findOne(id);
		if (projection == null) {
			throw new IllegalArgumentException("Ne postoji.");
		}
		projectionRepository.delete(projection);
		return projection;
	}

	@Override
	public List<Projection> throwOutExpired(List<Projection> projections) {
		
		List<Ticket> tickets = ticketService.findAll();
		List<Projection> nonExpired = new ArrayList<>();
		List<Projection> expired = new ArrayList<>();
		Date today = new Date();
		today = Calendar.getInstance().getTime();
		
		System.out.println("provera da li je neka projekcija istekla...");
		
		for(Projection projection : projections){
			
			if(today.after(projection.getDate())){
				System.out.println("Projekcija " + projection.getId() + " je istekla!");
				expired.add(projection);
			}else{
				nonExpired.add(projection);
			}
		}
		
		System.out.println("provera zavrsena...");
		
		System.out.println("ubacivanje filmova i bioskopa u liste za ocenjivanje...");
		/*Ako se istekla projekcija i projekcija iz karte poklapaju onda se film i bioskop
		ubacuju u listu za ocenjivanje korisnika koji je rezervisao tu kartu
		Takodje se ta karta i obrise jer je istekla*/
		for(Ticket ticket : tickets){
			for(Projection projection : expired){
				if(projection.getId() == ticket.getProjection().getId()){
					ticket.getUser().getMoviesToRate().add(projection.getMovie());
					//ticket.getUser().getCinemasToRate().add(cinemaService.findOne(ticket.getCinemaId()));
					ticketService.delete(ticket.getId());
				}
			}
		}
		System.out.println("ubaceno!");
		
		System.out.println("brisanje isteklih projekcija i karata...");
		for(Projection p : expired){
			projectionRepository.delete(p);
		}
		System.out.println("uspesno obrisano!");
		
		return nonExpired;
	}

}
