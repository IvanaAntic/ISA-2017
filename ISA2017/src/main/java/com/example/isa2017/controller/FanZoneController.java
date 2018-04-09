package com.example.isa2017.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.Bid;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.service.AdminItemService;
import com.example.isa2017.service.BidService;
import com.example.isa2017.service.UserItemService;





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
	
	@RequestMapping(
			value = "/adminItems",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AdminItem>> getAdminItems() {
		logger.info("> getAdminItems");
	
		List<AdminItem> adminItems = adminItemService.findAll();

		logger.info("< getAdminItems");
		return new ResponseEntity<List<AdminItem>>(adminItems,
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/adminItem",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminItem> addAdminItem(
			@RequestBody AdminItem adminItem) throws Exception {
		logger.info("> addAdminItem");
		AdminItem newAdminItem = adminItemService.save(adminItem);
		logger.info("< addAdminItem");
		return new ResponseEntity<AdminItem>(newAdminItem, HttpStatus.CREATED);
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
			value = "/userItem",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserItem> addUserItem(
			@RequestBody UserItem userItem) throws Exception {
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
	public ResponseEntity<Bid> addBid(
			@RequestBody Bid bid) throws Exception {
		logger.info("> addBid");
		Bid newBid = bidService.save(bid);
		logger.info("< addBid");
		return new ResponseEntity<Bid>(newBid, HttpStatus.CREATED);
	}
}
