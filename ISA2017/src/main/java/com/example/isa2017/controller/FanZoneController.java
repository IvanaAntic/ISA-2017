package com.example.isa2017.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.Bid;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Role;
import com.example.isa2017.model.Theatre;
import com.example.isa2017.model.User;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.modelDTO.AdminItemDTO;
import com.example.isa2017.modelDTO.UserItemDTO;
import com.example.isa2017.service.AdminItemService;
import com.example.isa2017.service.BidService;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.EmailService;
import com.example.isa2017.service.TheatreService;
import com.example.isa2017.service.UserItemService;
import com.example.isa2017.service.UserService;





/**
 * Kontroler fan zone. 
 * @author Gema
 *
 */
@RestController
@RequestMapping(value = "/FanZone")
public class FanZoneController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminItemService adminItemService;
	
	@Autowired
	private UserItemService userItemService;
	
	@Autowired
	private BidService bidService;
	@Autowired
	private TheatreService theatreService;
	@Autowired
	private CinemaService cinemaService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserService userService;
	//Za Zvanicnu prodavnicu
	
	@RequestMapping(
			value = "/addNewItem",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminItemDTO> addNewItem(@Validated @RequestBody AdminItemDTO adminItemDTO) {
		logger.info("addNewItem");
		
		AdminItem newItem = adminItemService.addNewItem(adminItemDTO);
		
		if (newItem == null) {
			logger.info(" addNewItem error");
			return new ResponseEntity<AdminItemDTO>(adminItemDTO,
					HttpStatus.FORBIDDEN);
		}
		
		AdminItemDTO newAdminItemDTO = new AdminItemDTO(newItem);
		
		logger.info("< addNewItem ok");
		
		return new ResponseEntity<AdminItemDTO>(newAdminItemDTO,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/editAdminItem",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminItemDTO> editAdminItem(@Validated @RequestBody AdminItemDTO adminItemDTO) {
		logger.info("> editAdminItem");
		
		AdminItem itemForEdit = adminItemService.findOne(adminItemDTO.getId());
		itemForEdit.setName(adminItemDTO.getName());
		itemForEdit.setDescription(adminItemDTO.getDescription());
		itemForEdit.setPrice(Integer.parseInt(adminItemDTO.getPrice()));
		if (adminItemDTO.getCinemaId() != null) {
			List<Cinema> cinemas = cinemaService.findAll();
			for (Cinema cinema : cinemas) {
				if (cinema.getId() == adminItemDTO.getCinemaId()) {
					itemForEdit.setCinema(cinema);
				}
			}			
		}else{
			List<Theatre> theatres = theatreService.findAll();
			for (Theatre theatre : theatres) {
				if (theatre.getId() == adminItemDTO.getTheatreId()) {
					itemForEdit.setTheatre(theatre);
				}
			}			
		}
		adminItemService.save(itemForEdit);
		logger.info("< editAdminItem ok");
		
		
		return new ResponseEntity<AdminItemDTO>(new AdminItemDTO(itemForEdit),
				HttpStatus.OK);
	}
	@RequestMapping(value = "/deleteAdminItem/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<AdminItemDTO> deleteAdminItem(@PathVariable Long id) {
	
		AdminItemDTO deleted = adminItemService.deleteAdminItem(id);
		return new ResponseEntity<AdminItemDTO>(deleted,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/adminItems",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AdminItemDTO>> getAdminItems() {
		logger.info("> getAdminItems");
	
		List<AdminItemDTO> adminItems = adminItemService.getAll();

		logger.info("< getAdminItems");
		return new ResponseEntity<List<AdminItemDTO>>(adminItems,
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/adminItemsForSale",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AdminItemDTO>> getItemsForSale() {
		logger.info("> getItemsForSale");
	
		List<AdminItemDTO> adminItems = adminItemService.getNotReserved();

		logger.info("< getItemsForSale");
		return new ResponseEntity<List<AdminItemDTO>>(adminItems,
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/adminItem/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminItemDTO> getAdminItem(@PathVariable Long id) {
		logger.info("> getAdminItem");
		AdminItem adminItem = adminItemService.findOne(id);
		logger.info("< getAdminItem");
		return new ResponseEntity<AdminItemDTO>(new AdminItemDTO(adminItem), HttpStatus.OK);
	}
	@RequestMapping(
			value = "/reservation/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminItemDTO> reservationAdminItem(@PathVariable Long id, HttpServletRequest request) throws Exception {
		logger.info("> reservation");
		
		System.out.println("Stiglo  je :" + request.getAttributeNames() );
		
		User user = (User) request.getSession().getAttribute("logged");
		
		
		if (user == null) {
		
			return new ResponseEntity<AdminItemDTO>( HttpStatus.BAD_REQUEST);
		
		}else if (user.getRole() != Role.USER) {
				
			return new ResponseEntity<AdminItemDTO>( HttpStatus.METHOD_NOT_ALLOWED);
		}
		AdminItem reserved = adminItemService.makeReservation(user.getId(), id);
		System.out.println("User koji bi da rezervise je :" + user.getEmail() );
		
		if (reserved == null) {
			return new ResponseEntity<AdminItemDTO>( HttpStatus.BAD_REQUEST);
		}
		try {
			emailService.sendReservSucc(user, reserved);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
		logger.info("< reservation");
		return new ResponseEntity<AdminItemDTO>(new AdminItemDTO(reserved), HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/adminItem",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminItem> addAdminItem(@RequestBody AdminItem adminItem) throws Exception {
		logger.info("> addAdminItem");
		AdminItem newAdminItem = adminItemService.save(adminItem);
		logger.info("< addAdminItem");
		return new ResponseEntity<AdminItem>(newAdminItem, HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/approve/{itemId}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserItemDTO> approveUserItem(@PathVariable Long itemId, HttpServletRequest request) {
		logger.info("> getUserItem");
		User user = (User) request.getSession().getAttribute("logged");
		UserItem userItem = userItemService.approve(itemId, user.getId());
		if (userItem == null) {
			return new ResponseEntity<UserItemDTO>( HttpStatus.BAD_REQUEST);
		}
		try {
			emailService.sendApproved(user, userItem);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
		logger.info("< getUserItem");
		return new ResponseEntity<UserItemDTO>(userItemService.convertToDTO(userItem), HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/disapprove/{itemId}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserItemDTO> disapproveUserItem(@PathVariable Long itemId, HttpServletRequest request) {
		logger.info("> getUserItem");
		User user = (User) request.getSession().getAttribute("logged");
		UserItem userItem = userItemService.disapprove(itemId, user.getId());
		
		if (userItem == null) {
			return new ResponseEntity<UserItemDTO>( HttpStatus.BAD_REQUEST);
		}
		try {
			emailService.sendDispproved(user, userItem);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
		logger.info("< getUserItem");
		return new ResponseEntity<UserItemDTO>(userItemService.convertToDTO(userItem), HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/userItems",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserItem>> getUserItems() {
		logger.info("> getUserItems");
	
		List<UserItem> userItems = userItemService.findAll();

		logger.info("< getUserItems");
		return new ResponseEntity<List<UserItem>>(userItems,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/approvedItems/{adminId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserItemDTO>> getApprovedItems(@PathVariable Long adminId) {
		logger.info("> getApprovedItems");
	
		List<UserItem> userItems = userItemService.getApprovedBy(adminId);

		logger.info("< getApprovedItems");
		return new ResponseEntity<List<UserItemDTO>>(userItemService.convertToDTOs(userItems),
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/reservedItems",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AdminItemDTO>> getReservedItems(HttpServletRequest request) {
		logger.info("> getApprovedItems");
		User user = (User) request.getSession().getAttribute("logged");
		if (user == null) {
			return new ResponseEntity<List<AdminItemDTO>>(HttpStatus.UNAUTHORIZED);
		}
		List<AdminItem> adminItems = adminItemService.getByBuyer(user.getId());

		logger.info("< getApprovedItems");
		return new ResponseEntity<List<AdminItemDTO>>(adminItemService.adminItemsToDTO(adminItems), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteUserItem/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<UserItemDTO> deleteUserItem(@PathVariable Long id) {
	
		UserItem deleted = userItemService.delete(id);
		return new ResponseEntity<UserItemDTO>(userItemService.convertToDTO(deleted),
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/userItem",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserItem> addUserItem(@RequestBody UserItem userItem) throws Exception {
		logger.info("> addUserItem");
		UserItem newUserItem = userItemService.save(userItem);
		logger.info("< addUserItem");
		return new ResponseEntity<UserItem>(newUserItem, HttpStatus.CREATED);
	}
	@RequestMapping(
			value = "/bid",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bid> addBid(@RequestBody Bid bid, HttpServletRequest request) throws Exception {
		logger.info("> addBid");
		User user = (User) request.getAttribute("logged");
		Bid newBid = bidService.save(bid);
		logger.info("< addBid");
		return new ResponseEntity<Bid>(newBid, HttpStatus.CREATED);
	}
}
