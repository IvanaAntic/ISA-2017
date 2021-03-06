package com.example.isa2017;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.Bid;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Friendship;
import com.example.isa2017.model.Hall;
import com.example.isa2017.model.Movie;
import com.example.isa2017.model.Play;
import com.example.isa2017.model.Projection;
import com.example.isa2017.model.Role;
import com.example.isa2017.model.Seat;
import com.example.isa2017.model.Theatre;
import com.example.isa2017.model.Ticket;
import com.example.isa2017.model.User;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.modelDTO.AuctionStatus;
import com.example.isa2017.repository.FriendshipRepository;
import com.example.isa2017.repository.UserRepository;
import com.example.isa2017.service.AdminItemService;
import com.example.isa2017.service.BidService;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.FriendshipService;
import com.example.isa2017.service.HallService;
import com.example.isa2017.service.MovieService;
import com.example.isa2017.service.PlayService;
import com.example.isa2017.service.ProjectionService;
import com.example.isa2017.service.SeatService;
import com.example.isa2017.service.TheatreService;
import com.example.isa2017.service.TicketService;
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
	@Autowired
	private AdminItemService adminItemService;
	@Autowired
	private BidService bidService;
	@Autowired
	private HallService hallService;
	@Autowired
	private SeatService seatService;
	@Autowired
	private ProjectionService projectionService;
	@Autowired
	private TicketService ticketService;
	@Autowired
	private FriendshipService friendshipService;
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	@PostConstruct
	private void init(){
		
		/*List<List<Movie>> generatedMovies = generateMovies();*/
		/*List<List<Play>> generatedPlays = generatePlays();*/
		/*
		Cinema cinema1 = new Cinema("arena cineplexx1", "adresa1", "opis1", 0, generatedMovies.get(0));
		cinemaService.save(cinema1);
		Cinema cinema2 = new Cinema("arena cineplexx2", "adresa2", "opis2", 2, generatedMovies.get(1));
		cinemaService.save(cinema2);
		Cinema cinema3 = new Cinema("arena cineplexx3", "adresa3", "opis3", 1, generatedMovies.get(2));
		cinemaService.save(cinema3);
		Cinema cinema4 = new Cinema("arena cineplexx4", "adresa4", "opis4", 3, generatedMovies.get(3));
		cinemaService.save(cinema4);
		Cinema cinema5 = new Cinema("arena cineplexx5", "adresa5", "opis5", 4, generatedMovies.get(4));
		cinemaService.save(cinema5);*/
		
		
		Cinema cinema1 = new Cinema();
		Cinema cinema2 = new Cinema();
		
		cinema1.setAddress("adresa1");
		cinema1.setDescription("opis");
		cinema1.setName("cinema1");
		
		cinema2.setAddress("adresa2");
		cinema2.setDescription("opis2");
		cinema2.setName("cinema2");
		
		
		cinemaService.save(cinema1);
		cinemaService.save(cinema2);
		

/*		Theatre theatre1 = new Theatre("Srpsko narodno pozorište", "Novi Sad, Pozorišni trg 1", "Srpsko narodno"
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
		theatreService.save(theatre5);*/
		//Obican korisnik
		User pera = new User("pera@pera", "pera", "Petar", "Petrovic", "064123123", "Petrovac", Role.USER, true,"0");
		//Administrator fan zone
		User gema = new User("gema@gema", "gema", "Gema", "Gema", "064123123", "Uzvece", Role.FANZONEADMIN, true,"0");
		//Obican user sa ispravnim mailom
		User sasa = new User("gemovics@gmail.com","gemovics", "Sasa", "Gemovic", "064123123", "Uzvece", Role.USER, true,"0");
		//Obican user sa ispravnim mailom
		User sasa2 = new User("gemin.mail.za.testiranje@gmail.com","gema", "Sasa2", "Gemovic2", "064123123", "Uzvece2", Role.USER, true,"0");
		//Admin fan zone
		User adminFZ = new User("adminFZ@adminFZ", "admin", "AdminFZ", "AdminFZ", "064123123", "Uzvece", Role.FANZONEADMIN, true,"0");
		User adminSYS = new User("adminSYS@adminSYS", "adminSYS", "AdminSYS", "AdminSYS", "064123123", "Uzvece", Role.SYSTEMADMIN, true,"0");
		
		User TCadmin = new User("tcadmin@tcadmin", "tcadmin", "Igor", "Zeljkovic", "066555548", "Novi Sad", Role.ADMIN, true,"0");
		User ivana = new User("iva@iva", "iva", "Ivana", "Antic", "066555548", "Novi Sad", Role.USER, true,"0");
		User marija = new User("marija@marija", "marija", "Marija", "Molnar", "066555548", "Novi Sad", Role.USER, true,"0");

		
		userRepository.save(adminSYS);//id=1 glavni predefinisani administrator
		userRepository.save(adminFZ);//id=2
		userRepository.save(gema);//id=3
		userRepository.save(pera);//id=4
		//userRepository.save(adminFZ);//id=5
		userRepository.save(sasa);//id=6
		userRepository.save(TCadmin);
		userRepository.save(sasa2);//id=6
		userRepository.save(ivana);
		userRepository.save(marija);
		
		Friendship f1=new Friendship();
		Friendship f2=new Friendship();
		Friendship f3=new Friendship();
		f1.setReciver(pera);
		f1.setSender(sasa);
		f1.setStatus("waiting");
		f1.setConfirm("notinvited");
		f2.setStatus("waiting");
		f2.setReciver(pera);
		f2.setConfirm("notinvited");
		f2.setSender(sasa2);
		
		f3.setStatus("accepted");
		f3.setConfirm("invited");
		f3.setReciver(ivana);
		f3.setSender(pera);
		
		friendshipRepository.save(f1);
		friendshipRepository.save(f2);
		friendshipRepository.save(f3);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date datum1 = null;
		Date datum2 = null;
		Date datum3 = null;
		Date datum4 = null;
		Date datum5=null;
		try {
			datum1 = dateFormat.parse("20/09/2018 19:40");
			datum2 = dateFormat.parse("20/09/2018 20:00");
			datum3 = dateFormat.parse("26/11/2018 20:00");
			datum4 = dateFormat.parse("16/09/2018 18:40");
			datum5 = dateFormat.parse("16/09/2018 19:40");
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		Theatre theatre1=new Theatre();
		theatre1.setName("Pozoriste1");
		theatre1.setAddress("Bulevar Oslobodjenja");
		theatre1.setAvgRating(3);
		theatreService.save(theatre1);
		
		Theatre theatre2=new Theatre();
		theatre2.setName("Narodno p NS");
		theatre2.setAddress("Narodnog Fronta");
		theatre2.setAvgRating(3);
		theatreService.save(theatre2);
		
		Theatre theatre3=new Theatre();
		theatre3.setName("Bosko Buha");
		theatre3.setAddress("Maksima Gorkog");
		theatre3.setAvgRating(3);
		theatreService.save(theatre3);
		
		List<String> actors=new ArrayList<>();
		actors.add("Sergej");
		actors.add("Mika");
		
		Play play1=new Play();
		play1.setActors(actors);
		play1.setPlayName("Na Drini cuprija");
		play1.setGenre("drama");
		play1.setDescription("radjeno po istoimenom romanu");
		play1.setDirector("Neko");
		playService.save(play1);
		
		Play play2=new Play();
		play2.setActors(actors);
		play2.setPlayName("Anikina vremena");
		play2.setGenre("drama");
		play1.setDescription("radjeno po istoimenom romanu");
		play1.setDirector("Neko drugi");
		playService.save(play2);
		
		Movie movie1 = new Movie();
		movie1.setCinema(cinema1);
		movie1.setMovieName("La La Land");
		movieService.save(movie1);
		
		Movie movie2 = new Movie();
		movie2.setCinema(cinema1);
		movie2.setMovieName("Harry Potter");
		movieService.save(movie2);
		
		Movie movie3 = new Movie();
		movie3.setCinema(cinema2);
		movie3.setMovieName("Notebook");
		movieService.save(movie3);
		
		Hall hall1 = new Hall();
		hall1.setCinema(cinema1);
		hall1.setHallName("Sala A2");
		hallService.save(hall1);
		
		Hall hall2 = new Hall();
		hall2.setCinema(cinema1);
		hall2.setHallName("Sala A3");
		hallService.save(hall2);
		
		Seat seat1 = new Seat();
		seat1.setColumnNumber(1);
		seat1.setRowNumber(1);
		seat1.setHall(hall1);
		seatService.save(seat1);
		
		Seat seat2 = new Seat();
		seat2.setColumnNumber(2);
		seat2.setRowNumber(1);
		seat2.setHall(hall1);
		seatService.save(seat2);
		
		Seat seat3 = new Seat();
		seat3.setColumnNumber(3);
		seat3.setRowNumber(1);
		seat3.setHall(hall1);
		seatService.save(seat3);
		
		Projection projection = new Projection();
		projection.setDate(datum1);
		projection.setEndDate(datum2);
		projection.setHall(hall1);
		projection.setMovie(movie1);
		projection.setPrice("250din");
		projectionService.save(projection);
		
		
		/*Projection projection2 = new Projection();
		projection2.setDate(datum4);
		projection2.setEndDate(datum5);
		projection2.setHall(hall1);
		projection2.setMovie(movie1);
		projection2.setPrice("250din");
		projectionService.save(projection2);*/
		
		
		Ticket ticket1 = new Ticket();
		ticket1.setDiscount(10);
		ticket1.setProjection(projection);
		ticket1.setSeat(seat1);
		ticket1.setUser(marija);
		ticketService.save(ticket1);
		
		
		//Oglas 1
		UserItem userItem1 = new UserItem();		
		userItem1.setName("Viking slem");
		userItem1.setDescription("Vikinski slem iz neke predstave...");
		userItem1.setStartPrice(1000);
		userItem1.setCurrentPrice(1000);
		userItem1.setEndDate(datum1);
		userItem1.setPostedBy(pera);
		userItem1.setApproved(true);
		userItem1.setApprovedBy(gema);
		userItem1.setStatus(AuctionStatus.Aktuelan);
		Date datumBid1 = null;
		Date datumBid2 = null;
		Date datumBid3 = null;
		Date datumBid4 = null;
		try {
			datumBid1 = dateFormat.parse("22/4/2018 12:00");
			datumBid2 = dateFormat.parse("22/4/2018 13:00");
			datumBid3 = dateFormat.parse("22/4/2018 13:30");
			datumBid4 = dateFormat.parse("22/4/2018 14:00");
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
		Bid bid1 = new Bid();
		bid1.setItem(userItem1);
		bid1.setPrice(1200);
		bid1.setDate(datumBid1);
		bid1.setBuyer(pera);
		//bidService.save(bid1);
		
		Bid bid2 = new Bid();
		bid2.setItem(userItem1);
		bid2.setPrice(1300);
		bid2.setDate(datumBid2);
		bid2.setBuyer(sasa);
		//bidService.save(bid2);
		
		Bid bid3 = new Bid();
		bid3.setItem(userItem1);
		bid3.setPrice(1350);
		bid3.setDate(datumBid3);
		bid3.setBuyer(pera);
		//bidService.save(bid3);
		
		List<Bid> bids = new ArrayList<Bid>();
		bids.add(bid1);
		bids.add(bid2);
		bids.add(bid3);
		
		userItem1.setBids(bids);
		userItem1.setCurrentPrice(bid3.getPrice());;
		userItemService.save(userItem1);
		//userItemService.addNewItem(userItem1);
		
		
		//Oglas 2
		UserItem userItem2 = new UserItem();
		userItem2.setName("Spajdermen maska");
		userItem2.setDescription("Spajdermenova maska, ima tragova koriscenja...");
		userItem2.setStartPrice(200);
		userItem1.setCurrentPrice(200);
		userItem2.setEndDate(datum2);
		userItem2.setPostedBy(sasa);
		userItem2.setStatus(AuctionStatus.Ceka_odobrenje);
		userItemService.save(userItem2);
		//Oglas 3
		UserItem userItem3 = new UserItem();		
		userItem3.setName("Torov cekic");
		userItem3.setDescription("Cekic kao nov, nije koriscen. Stoji i skuplja prasinu.");
		userItem3.setStartPrice(5000);
		userItem3.setCurrentPrice(5000);
		userItem3.setEndDate(datum3);
		userItem3.setPostedBy(pera);
		userItem3.setStatus(AuctionStatus.Ceka_odobrenje);
		userItemService.save(userItem3);
/*		//Oglas 4
		UserItem userItem4 = new UserItem();		
		userItem4.setName("Prsten moci iz filma gospodar prstenova-NOVO");
		userItem4.setDescription("Nova varijanta prstena iz filma Gospodar prstenova. Napravljen od titanijuma \n" + 
				"Pozlaćeni Prsten Moći Crni i Srebrni ! ! ! !  \n" + 
				"Materijal: titanijum sa pozlatom od žutog zlata \n" + 
				"Dimenzije prstena: raspoložive dimenzije:16mm,17mm,18mm,20mm,21mm unutrašnji prečnik.  \n" + 
				"Tekst je laserski ugraviran tako da ga je ne moguce skinuti ili izbrisati.  \n" + 
				"Ukoliko zelite i lancic(54cm) za prsten, on je 100din \n" + 
				"Pre kupovine samo pitajte za velicinu da bih proverio da li imam na stanju.");
		userItem4.setStartPrice(399);
		userItem4.setEndDate(datum4);
		userItem4.setPostedBy(sasa);
		userItemService.addNewItem(userItem4);
*/
		
		//Proizvod iz prodavnice 1
		AdminItem adminItem1 = new AdminItem();
		adminItem1.setName("Hulk igracka");
		adminItem1.setDescription("Neverovatni Hulk, deo sage filmova Avengers(Osvetnici).  \n" + 
				"Omiljeni superheroj!  \n" + 
				"Verno prikazan kroz igračku.  \n" + 
				"Na grudima ima taster za aktivaciju zvuka i efekata.");
		adminItem1.setPostedBy(adminFZ);
		adminItem1.setPostedDate(datum1);
		adminItem1.setCinema(cinema1);
		adminItem1.setPrice(1000);
		adminItem1.setReserved(true);
		adminItem1.setBuyer(pera);
		adminItem1.setReservationDate(datum2);
		adminItemService.save(adminItem1);
		//Proizvod iz prodavnice 2
		AdminItem adminItem2 = new AdminItem();
		adminItem2.setName("Privezak ogrlica iz filma LOTR");
		adminItem2.setDescription("Lanac sa priveskom iz filma Gospodar prstenova.");
		adminItem2.setPostedBy(gema);
		adminItem2.setPostedDate(datum3);
		adminItem2.setCinema(cinema2);
		adminItem2.setPrice(500);
		adminItem2.setReserved(false);
		adminItemService.save(adminItem2);
		
				
		
		
	}
	
/*	public List<List<Movie>> generateMovies(){

		List<Movie> movies1 = new ArrayList<Movie>();
		List<Movie> movies2 = new ArrayList<Movie>();		
		List<Movie> movies3 = new ArrayList<Movie>();		
		List<Movie> movies4 = new ArrayList<Movie>();
		List<Movie> movies5 = new ArrayList<Movie>();
		
		for(int i = 0; i < 5; i++){
			
			List<String> actors = new ArrayList<String>();
			actors.add("glumac1");
			actors.add("glumac2");
			actors.add("glumac3");
			
			Movie movie1 = new Movie("Bladerunner", actors,  "Sci-Fi",
					 "Ridley Scott",  "1h 57min", 5,
					 "Film prikazuje distopijski Los Angeles"
					 + " 2019. u kojemu su replikanti, organski roboti stvoreni genetskim inženjeringom, potpuno slični običnim ljudima."
					 + " Njihova upotreba je zabranjena na Zemlji te se replikanti koriste za opasne i riskantne poslove u svemirskom"
					 + " istraživanju. Radnja se odvija oko skupine nekoliko tih replikanata koji su pobjegli te se skrivaju"
					 + " u Los Angelesu, te ih posebna policija zvana 'Blade Runner' mora uloviti, a među njima je i iskusni "
					 + "Rick Deckard (Harrison Ford).");
			
			
			// 			ZA UBACIVANJE SLIKE
			
			File file = new File("C:\\Users\\Gema\\Desktop\\slika.png");
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
					 + " solace and eventual redemption through acts of common decency.");
			movieService.save(movie2);
			
			Movie movie3 = new Movie("Mračni vitez", actors,  "Action",
					 "Christopher Nolan",  "2h 32min", 5,
					 "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the"
					 + " people of Gotham, the Dark Knight must accept one of the greatest psychological and physical tests of"
					 + " his ability to fight injustice.");
			movieService.save(movie3);
			
			switch (i) {
			case 0:
				movies1.add(movie1);
				movies1.add(movie2);
				movies1.add(movie3);
				break;
			case 1:
				movies2.add(movie1);
				movies2.add(movie2);
				movies2.add(movie3);
				break;
			case 2:
				movies3.add(movie1);
				movies3.add(movie2);
				movies3.add(movie3);
				break;
			case 3:
				movies4.add(movie1);
				movies4.add(movie2);
				movies4.add(movie3);
				break;
			case 4:
				movies5.add(movie1);
				movies5.add(movie2);
				movies5.add(movie3);
				break;
			}
			
		}
		
		List<List<Movie>> generatedMovies = new ArrayList<List<Movie>>();
		generatedMovies.add(movies1);
		generatedMovies.add(movies2);
		generatedMovies.add(movies3);
		generatedMovies.add(movies4);
		generatedMovies.add(movies5);
		
		return generatedMovies;
		
	}*/
	
/*	public List<List<Play>> generatePlays(){

		
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
		
	}*/
}
