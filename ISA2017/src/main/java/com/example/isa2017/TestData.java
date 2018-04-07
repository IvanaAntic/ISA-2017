package com.example.isa2017;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Theatre;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.TheatreService;

@Component
public class TestData {

	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private TheatreService theatreService;
	
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

		Theatre theatre1 = new Theatre("Srpsko narodno pozorište", "Novi Sad, Pozorišni trg 1", "Srpsko narodno"
				+ " pozorište je najstariji profesionalni teatar u Srba. Osnovano je 1861. godine u Novom Sadu i u "
				+ "okviru njega funkcionišu umetničke jedinice Opere, Baleta i Drame", 5);
		theatreService.save(theatre1);
		
		Theatre theatre2 = new Theatre("Narodno pozorište", "Beograd, Trg republike", "U okviru njega funkcionišu"
				+ " umetničke jedinice Opera, Balet i Drama, a predstave se odigravaju na Velikoj sceni i Sceni Raša"
				+ " Plaović. Danas predstavlja jednu od najreprezentativnijih i najznačajnijih kulturnih institucija Srbije.", 5);
		theatreService.save(theatre2);
		
		Theatre theatre3 = new Theatre("Opera i teatar Madlenianum", "Beograd, Glavna ulica 32, Zemun", "Privatno pozorište, prva"
				+ " privatna opera i pozorišna kompanija, kako u Srbiji, tako i u jugoistočnoj Evropi.", 5);
		theatreService.save(theatre3);
		
		Theatre theatre4 = new Theatre("Knjaževsko-srpski teatar", "Kragujevac, Daničićeva 3", "Najstarije aktivno pozorište u Srbiji.", 5);
		theatreService.save(theatre4);
		
		Theatre theatre5 = new Theatre("Narodno pozorište „Toša Jovanović“", "Zrenjanin, Trg slobode 7", "Najstarija pozorišna zgrada u današnjoj Srbiji.", 4);
		theatreService.save(theatre5);
	}
}
