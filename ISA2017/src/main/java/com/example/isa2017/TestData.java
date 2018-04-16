package com.example.isa2017;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Bid;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Movie;
import com.example.isa2017.model.Play;
import com.example.isa2017.model.Role;
import com.example.isa2017.model.Theatre;
import com.example.isa2017.model.User;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.modelDTO.AuctionStatus;
import com.example.isa2017.repository.UserRepository;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.MovieService;
import com.example.isa2017.service.PlayService;
import com.example.isa2017.service.TheatreService;
import com.example.isa2017.service.UserItemService;



@Component
public class TestData {

	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private TheatreService theatreService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private PlayService playService;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserItemService userItemService;
	@PostConstruct
	private void init(){
		
		List<List<Movie>> generatedMovies = generateMovies();
		List<List<Play>> generatedPlays = generatePlays();
		
		Cinema cinema1 = new Cinema("arena cineplexx1", "adresa1", "opis1", 3, generatedMovies.get(0));
		cinemaService.save(cinema1);
		Cinema cinema2 = new Cinema("arena cineplexx2", "adresa2", "opis2", 2, generatedMovies.get(1));
		cinemaService.save(cinema2);
		Cinema cinema3 = new Cinema("arena cineplexx3", "adresa3", "opis3", 1, generatedMovies.get(2));
		cinemaService.save(cinema3);
		Cinema cinema4 = new Cinema("arena cineplexx4", "adresa4", "opis4", 3, generatedMovies.get(3));
		cinemaService.save(cinema4);
		Cinema cinema5 = new Cinema("arena cineplexx5", "adresa5", "opis5", 4, generatedMovies.get(4));
		cinemaService.save(cinema5);
		
		

		Theatre theatre1 = new Theatre("Srpsko narodno pozorište", "Novi Sad, Pozorišni trg 1", "Srpsko narodno"
				+ " pozorište je najstariji profesionalni teatar u Srba. Osnovano je 1861. godine u Novom Sadu i u "
				+ "okviru njega funkcionišu umetničke jedinice Opere, Baleta i Drame", 5, generatedPlays.get(0));
		theatreService.save(theatre1);
		
		Theatre theatre2 = new Theatre("Narodno pozorište", "Beograd, Trg republike", "U okviru njega funkcionišu"
				+ " umetničke jedinice Opera, Balet i Drama, a predstave se odigravaju na Velikoj sceni i Sceni Raša"
				+ " Plaović. Danas predstavlja jednu od najreprezentativnijih i najznačajnijih kulturnih institucija Srbije.", 5, generatedPlays.get(1));
		theatreService.save(theatre2);
		
		Theatre theatre3 = new Theatre("Opera i teatar Madlenianum", "Beograd, Glavna ulica 32, Zemun", "Privatno pozorište, prva"
				+ " privatna opera i pozorišna kompanija, kako u Srbiji, tako i u jugoistočnoj Evropi.", 5, generatedPlays.get(2));
		theatreService.save(theatre3);
		
		Theatre theatre4 = new Theatre("Knjaževsko-srpski teatar", "Kragujevac, Daničićeva 3", "Najstarije aktivno pozorište u Srbiji.", 5, generatedPlays.get(3));
		theatreService.save(theatre4);
		
		Theatre theatre5 = new Theatre("Narodno pozorište „Toša Jovanović“", "Zrenjanin, Trg slobode 7", "Najstarija pozorišna zgrada u današnjoj Srbiji.", 4, generatedPlays.get(4));
		theatreService.save(theatre5);
		User pera = new User("pera@pera", "pera", "Petar", "Petrovic", "064123123", "Petrovac", Role.USER, true);
		
		User gema = new User("gema@gema", "gema", "Sasa", "Gemovic", "064123123", "Uzvece", Role.FANZONEADMIN, true);
		userRepository.save(gema);
		userRepository.save(pera);
		User adminFZ = new User("adminFZ@adminFZ", "admin", "AdminFZ", "AdminFZ", "064123123", "Uzvece", Role.FANZONEADMIN, true);
		userRepository.save(gema);
		userRepository.save(adminFZ);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date datum;
		try {
			datum = dateFormat.parse("20/4/2018 20:00");
			UserItem userItem = new UserItem();
			UserItem userItem2 = new UserItem();
			userItem.setName("Viking slem");
			userItem.setDescription("Vikinski slem iz neke predstave...");
			userItem.setStartPrice(1000);
			userItem.setEndDate(datum);
			userItem.setPostedBy(pera);
			userItemService.addNewItem(userItem);
			userItem2.setName("Viking slem22");
			userItem2.setDescription("Sablja iz neke predstave...");
			userItem2.setStartPrice(1000);
			userItem2.setEndDate(datum);
			userItem2.setPostedBy(pera);
			userItemService.addNewItem(userItem2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<List<Movie>> generateMovies(){

		
		List<String> actors = new ArrayList<String>();
		actors.add("glumac1");
		actors.add("glumac2");
		actors.add("glumac3");
		
		List<String> projectionTimes = new ArrayList<String>();
		projectionTimes.add("17:30");
		projectionTimes.add("20:30");
		projectionTimes.add("22:30");
		
		Movie movie1 = new Movie("Bladerunner", actors,  "Sci-Fi",
				 "Ridley Scott",  "1h 57min", 5,
				 "Film prikazuje distopijski Los Angeles"
				 + " 2019. u kojemu su replikanti, organski roboti stvoreni genetskim inženjeringom, potpuno slični običnim ljudima."
				 + " Njihova upotreba je zabranjena na Zemlji te se replikanti koriste za opasne i riskantne poslove u svemirskom"
				 + " istraživanju. Radnja se odvija oko skupine nekoliko tih replikanata koji su pobjegli te se skrivaju"
				 + " u Los Angelesu, te ih posebna policija zvana 'Blade Runner' mora uloviti, a među njima je i iskusni "
				 + "Rick Deckard (Harrison Ford).", projectionTimes, 750);
		
		
		File file = new File("C:\\Users\\igor\\Downloads\\vivify.png");
        byte[] bFile = new byte[(int) file.length()];
 
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
        movie1.setImage(bFile);
		
		movieService.save(movie1);
		
		Movie movie2 = new Movie("Bekstvo iz Šošenka", actors,  "Drama",
				 "Frank Darabont",  "2h 22min", 5,
				 "Two imprisoned men bond over a number of years, finding"
				 + " solace and eventual redemption through acts of common decency.", projectionTimes, 250);
		movieService.save(movie2);
		
		Movie movie3 = new Movie("Mračni vitez", actors,  "Action",
				 "Christopher Nolan",  "2h 32min", 5,
				 "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the"
				 + " people of Gotham, the Dark Knight must accept one of the greatest psychological and physical tests of"
				 + " his ability to fight injustice.", projectionTimes, 350);
		movieService.save(movie3);
		
		List<Movie> movies1 = new ArrayList<Movie>();
		
		movies1.add(movie1);
		movies1.add(movie2);
		movies1.add(movie3);
		
		List<Movie> movies2 = new ArrayList<Movie>();
		
		movies2.add(movie1);
		movies2.add(movie2);
		movies2.add(movie3);
		
		List<Movie> movies3 = new ArrayList<Movie>();
		
		movies3.add(movie1);
		movies3.add(movie2);
		movies3.add(movie3);
		
		List<Movie> movies4 = new ArrayList<Movie>();
		
		movies4.add(movie1);
		movies4.add(movie2);
		movies4.add(movie3);
		
		List<Movie> movies5 = new ArrayList<Movie>();
		
		movies5.add(movie1);
		movies5.add(movie2);
		movies5.add(movie3);
		
		List<List<Movie>> generatedMovies = new ArrayList<List<Movie>>();
		generatedMovies.add(movies1);
		generatedMovies.add(movies2);
		generatedMovies.add(movies3);
		generatedMovies.add(movies4);
		generatedMovies.add(movies5);
		
		return generatedMovies;
		
	}
	
	public List<List<Play>> generatePlays(){

		
		List<String> actors = new ArrayList<String>();
		actors.add("glumac1");
		actors.add("glumac2");
		actors.add("glumac3");
		
		List<String> projectionTimes = new ArrayList<String>();
		projectionTimes.add("17:30");
		projectionTimes.add("20:30");
		projectionTimes.add("22:30");
		
		Play play1 = new Play("Bladerunner", actors,  "Sci-Fi",
				 "Ridley Scott",  "1h 57min", 5,
				 "Film prikazuje distopijski Los Angeles"
				 + " 2019. u kojemu su replikanti, organski roboti stvoreni genetskim inženjeringom, potpuno slični običnim ljudima."
				 + " Njihova upotreba je zabranjena na Zemlji te se replikanti koriste za opasne i riskantne poslove u svemirskom"
				 + " istraživanju. Radnja se odvija oko skupine nekoliko tih replikanata koji su pobjegli te se skrivaju"
				 + " u Los Angelesu, te ih posebna policija zvana 'Blade Runner' mora uloviti, a među njima je i iskusni "
				 + "Rick Deckard (Harrison Ford).", projectionTimes, 750);
		
		File file = new File("C:\\Users\\igor\\Downloads\\vivify.png");
        byte[] bFile = new byte[(int) file.length()];
 
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        play1.setImage(bFile);
		
		playService.save(play1);
		
		Play play2 = new Play("Bekstvo iz Šošenka", actors,  "Drama",
				 "Frank Darabont",  "2h 22min", 5,
				 "Two imprisoned men bond over a number of years, finding"
				 + " solace and eventual redemption through acts of common decency.", projectionTimes, 250);
		playService.save(play2);
		
		Play play3 = new Play("Mračni vitez", actors,  "Action",
				 "Christopher Nolan",  "2h 32min", 5,
				 "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the"
				 + " people of Gotham, the Dark Knight must accept one of the greatest psychological and physical tests of"
				 + " his ability to fight injustice.", projectionTimes, 350);
		playService.save(play3);
		
		List<Play> movies1 = new ArrayList<Play>();
		
		movies1.add(play1);
		movies1.add(play2);
		movies1.add(play3);
		
		List<Play> movies2 = new ArrayList<Play>();
		
		movies2.add(play1);
		movies2.add(play3);
		
		List<Play> movies3 = new ArrayList<Play>();
		
		movies3.add(play1);
		movies3.add(play2);
		
		List<Play> movies4 = new ArrayList<Play>();
		
		movies4.add(play1);
		movies4.add(play3);
		
		List<Play> movies5 = new ArrayList<Play>();
		
		movies5.add(play1);
		movies5.add(play2);
		movies5.add(play3);
		
		List<List<Play>> generatedMovies = new ArrayList<List<Play>>();
		generatedMovies.add(movies1);
		generatedMovies.add(movies2);
		generatedMovies.add(movies3);
		generatedMovies.add(movies4);
		generatedMovies.add(movies5);
		
		return generatedMovies;
		
	}
}
