package com.example.isa2017.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.converters.CinemaToCinemaDTO;
import com.example.isa2017.converters.HallToHallDTO;
import com.example.isa2017.converters.TheatreToTheatreDTO;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Hall;
import com.example.isa2017.model.Role;
import com.example.isa2017.model.Theatre;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.AdminItemDTO;
import com.example.isa2017.modelDTO.CinemaDTO;
import com.example.isa2017.modelDTO.HallDTO;
import com.example.isa2017.modelDTO.TheatreDTO;
import com.example.isa2017.modelDTO.UserDTO;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.EmailService;
import com.example.isa2017.service.HallService;
import com.example.isa2017.service.TheatreService;
import com.example.isa2017.service.UserService;

@RestController
@RequestMapping(value = "/admin")
public class SystemAdminController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TheatreService theatreService;
	@Autowired
	private CinemaService cinemaService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserService userService;
	@Autowired
	private HallService hallService;
	@Autowired
	private CinemaToCinemaDTO cTocDTO;
	@Autowired
	private TheatreToTheatreDTO tTotDTO;
	@Autowired
	private HallToHallDTO hTohDTO;

	@RequestMapping(
			value = "/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> getUsers(HttpServletRequest request) {
		logger.info("> getUsers");
		User admin = (User) request.getSession().getAttribute("logged");
		
		if (admin == null || admin.getRole() != Role.SYSTEMADMIN ) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.FORBIDDEN);
		}
				
		List<User> users = userService.findAll();
		List<UserDTO> usersDTO = new ArrayList<>();
		for (User user : users) {
			UserDTO uDTO = userService.convertToDTO(user);
			uDTO.setPassword("");
			usersDTO.add(uDTO);
		}
		
		
		logger.info("< getUsers");
		return new ResponseEntity<List<UserDTO>>(usersDTO,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/cinemas",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CinemaDTO>> getCinemas(HttpServletRequest request) {
		logger.info("> getcinemas");
		User admin = (User) request.getSession().getAttribute("logged");
		
		if (admin == null || admin.getRole() != Role.SYSTEMADMIN ) {
			return new ResponseEntity<List<CinemaDTO>>(HttpStatus.FORBIDDEN);
		}
				
		List<Cinema> cinemas = cinemaService.findAll();
		List<CinemaDTO> cinemasDTO = cTocDTO.convert(cinemas); 	
		
		logger.info("< getCinemas");
		return new ResponseEntity<List<CinemaDTO>>(cinemasDTO,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/theatres",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TheatreDTO>> getTheatres(HttpServletRequest request) {
		logger.info("> gettheatres");
		User admin = (User) request.getSession().getAttribute("logged");
		
		if (admin == null || admin.getRole() != Role.SYSTEMADMIN ) {
			return new ResponseEntity<List<TheatreDTO>>(HttpStatus.FORBIDDEN);
		}
				
		List<Theatre> theatres = theatreService.findAll();
		List<TheatreDTO> theatresDTO = tTotDTO.convert(theatres); 	
		
		logger.info("< getTheatres");
		return new ResponseEntity<List<TheatreDTO>>(theatresDTO,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/halls",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HallDTO>> getHalls(HttpServletRequest request) {
		logger.info("> gethalls");
		User admin = (User) request.getSession().getAttribute("logged");
		
		if (admin == null || admin.getRole() != Role.SYSTEMADMIN ) {
			return new ResponseEntity<List<HallDTO>>(HttpStatus.FORBIDDEN);
		}
				
		List<Hall> halls = hallService.findAll();
		List<HallDTO> hallsDTO = hTohDTO.convert(halls); 	
		
		logger.info("< getHalls");
		return new ResponseEntity<List<HallDTO>>(hallsDTO,
				HttpStatus.OK);
	}
}

