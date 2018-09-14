package com.example.isa2017.controller;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.converters.PlayToPlayDTO;
import com.example.isa2017.model.Play;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.PlayDTO;
import com.example.isa2017.service.PlayService;
import com.example.isa2017.service.TheatreService;

@RestController
@RequestMapping(value = "/plays")
public class PlayController {


	@Autowired
	private TheatreService theatreService;
	
	@Autowired
	private PlayService playService;
	
	@Autowired
	private PlayToPlayDTO toPlayDTO;
	
	@RequestMapping(value="getTCadminPlays", method = RequestMethod.GET)
	public ResponseEntity<List<PlayDTO>> getTCadminPlays(HttpServletRequest request) {
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		List<Play> plays = playService.findAll();
		 
		return new ResponseEntity<>(toPlayDTO.convert(plays), HttpStatus.OK);
	}
	
	@RequestMapping(value = "deletePlay/{playId}", method=RequestMethod.DELETE)
	public ResponseEntity<PlayDTO> deletePlayInTheatre(HttpServletRequest request, @PathVariable Long playId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		
		/*Theatre theatre = theatreService.findOne(theatreId);
		
		for(int i = 0; i < theatre.getPlays().size(); i++){
			if(theatre.getPlays().get(i).getId() == playId)
				theatre.getPlays().remove(theatre.getPlays().get(i));
		}
		
		theatreService.save(theatre);*/
		
		playService.delete(playId);
		
	 return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "addPlayToTheatre/{theatreId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<PlayDTO> addPlayToTheatre(HttpServletRequest request, @RequestBody Play play, @PathVariable Long theatreId){
		
		/*User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);*/
		
		if(play.getImage() != null){
			String s = new String(play.getImage());
			
			String[] parts = s.split(",");
			String firstPart = parts[1];
			play.setImage(Base64.getDecoder().decode(firstPart));
		}
		
		play.setTheatre(theatreService.findOne(theatreId));
		playService.save(play);
		
	 return new ResponseEntity<>(toPlayDTO.convert(play), HttpStatus.OK);
	}
	
	@RequestMapping(value = "editPlay/{playId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<PlayDTO> editPlay(HttpServletRequest request, @RequestBody Play playDTO, @PathVariable Long playId){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		Play play = playService.findOne(playId);
		
		if(playDTO.getImage() != null){
			String s = new String(playDTO.getImage());
			
			String[] parts = s.split(",");
			String firstPart = parts[1];
			play.setImage(Base64.getDecoder().decode(firstPart));
		}/*else{
			play.setImage(playService.findOne(playId).getImage());
		}*/
		
		play.setActors(playDTO.getActors());
		play.setDescription(playDTO.getDescription());
		play.setDirector(playDTO.getDirector());
		play.setGenre(playDTO.getGenre());
		play.setPlayName(playDTO.getPlayName());
		play.setRuntime(playDTO.getRuntime());
		
		playService.save(play);
		
	 return new ResponseEntity<>(toPlayDTO.convert(play), HttpStatus.OK);
	}
	
	@RequestMapping(value = "ratePlay/{playId}", method=RequestMethod.POST, consumes=MediaType.ALL_VALUE)
	public ResponseEntity<PlayDTO> ratePlay(@PathVariable Long playId, @RequestBody PlayDTO rating){
		
		Play play = playService.findOne(playId);
		
		play.getRatingList().add(rating.getRating());
		
		System.out.println("integers in the rating list: ");
		for(int i : play.getRatingList()){
			System.out.println(i + ", ");
		}
		
		play.setAvgRating(play.calculateAverage(play.getRatingList()));
		
		playService.save(play);
		
		return new ResponseEntity<>(toPlayDTO.convert(play), HttpStatus.OK);
	}
	
/*	@RequestMapping(value = "playsToRate", method = RequestMethod.GET)
	public ResponseEntity<List<Play>> getPlaysToRate(HttpServletRequest request){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>(logged.getPlaysToRate(), HttpStatus.OK);
	}*/

}
