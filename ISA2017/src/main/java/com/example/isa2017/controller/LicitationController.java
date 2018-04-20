package com.example.isa2017.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.model.Bid;
import com.example.isa2017.model.User;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.modelDTO.BidDTO;
import com.example.isa2017.modelDTO.UserItemDTO;
import com.example.isa2017.service.BidService;
import com.example.isa2017.service.EmailService;
import com.example.isa2017.service.UserItemService;

@RestController
@RequestMapping(value = "/bid")
public class LicitationController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserItemService userItemService;
	
	@Autowired
	private BidService bidService;
	@Autowired
	private EmailService emailService;

	@RequestMapping(
			value = "/addBid",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserItemDTO> addBid(@RequestBody BidDTO bidDTO, HttpServletRequest request) throws Exception{
		logger.info("> addBid");
		
		User user = (User) request.getSession().getAttribute("logged");
		Bid newBid = bidService.bidFromDTO(bidDTO);
		UserItem userItem = userItemService.createBid(user, newBid);
		
		if (userItem == null) {
			return new ResponseEntity<UserItemDTO>( HttpStatus.FORBIDDEN);
		}
				
		logger.info("> addBid");
		return new ResponseEntity<UserItemDTO>( userItemService.convertToDTO(userItem), HttpStatus.OK);
	}
	@RequestMapping(
			value = "/acceptBid",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserItemDTO> acceptBid(@RequestBody BidDTO bidDTO, HttpServletRequest request) throws Exception{
		logger.info("> addBid");
		
		User user = (User) request.getSession().getAttribute("logged");
		UserItem userItem = userItemService.acceptBid(user, bidDTO);
		
		if (userItem == null) {
			return new ResponseEntity<UserItemDTO>( HttpStatus.FORBIDDEN);
		}
		//Obavestavanje korsnika da je njegova ponuda prihvacena a ostale korisnike da je odbijena
		List<User> bidders = userItemService.getBidders(userItem);
		try {
			for (User bidder : bidders) {
				
				if (bidder.getId() == userItem.getBuyer().getId()) {
					emailService.sendBidAccepted(userItem);
				}else {
					emailService.sendBidRejected(bidder,userItem);
				}
				
			}			
			
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
		logger.info("> addBid");
		return new ResponseEntity<UserItemDTO>( userItemService.convertToDTO(userItem), HttpStatus.OK);
	}
	@RequestMapping(
			value = "/rejectBid/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserItemDTO> rejectBid(@PathVariable Long id, HttpServletRequest request) throws Exception{
		logger.info("> addBid");
		
		User user = (User) request.getSession().getAttribute("logged");
		UserItem userItem = userItemService.rejectLicitation(user, id);
		
		if (userItem == null) {
			return new ResponseEntity<UserItemDTO>( HttpStatus.FORBIDDEN);
		}
		
		//Obavestavanje korisnika da su njihove ponuda odbijena
		List<User> bidders = userItemService.getBidders(userItem);
		try {
			for (User bidder : bidders) {
				emailService.sendBidRejected(bidder,userItem);
			}
			
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
		
		logger.info("> addBid");
		return new ResponseEntity<UserItemDTO>( userItemService.convertToDTO(userItem), HttpStatus.OK);
	}
}
