package com.example.isa2017;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Cinema;
import com.example.isa2017.service.CinemaService;

@Component
public class TestData {

	@Autowired
	private CinemaService cinemaService;
	
	@PostConstruct
	private void init(){
		
		Cinema cinema1 = new Cinema("arena cineplexx1", "adresa1", "opis1", 3);
		cinemaService.save(cinema1);
		Cinema cinema2 = new Cinema("arena cineplexx2", "adresa2", "opis2", 2);
		cinemaService.save(cinema2);
		Cinema cinema3 = new Cinema("arena cineplexx3", "adresa3", "opis3", 1);
		cinemaService.save(cinema3);
		Cinema cinema4 = new Cinema("arena cineplexx4", "adresa4", "opis4", 3);
		cinemaService.save(cinema4);
		Cinema cinema5 = new Cinema("arena cineplexx5", "adresa5", "opis5", 4);
		cinemaService.save(cinema5);
		 
	}
}
