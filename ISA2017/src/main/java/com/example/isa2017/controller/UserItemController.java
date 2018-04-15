package com.example.isa2017.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.modelDTO.AdminItemDTO;
import com.example.isa2017.modelDTO.UserItemDTO;
import com.example.isa2017.service.AdminItemService;
import com.example.isa2017.service.BidService;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.TheatreService;
import com.example.isa2017.service.UserItemService;

@RestController
@RequestMapping(value = "/userItem")
public class UserItemController {

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
	
	@RequestMapping(
			value = "/userItems",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserItemDTO>> getUserItems() {
		logger.info("> getUserItems");
	
		List<UserItem> userItems = userItemService.findAll();
		List<UserItemDTO> userItemsDTO = userItemService.convertToDTOs(userItems);
		logger.info("< getUserItems");
		return new ResponseEntity<List<UserItemDTO>>(userItemsDTO,
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/userItem",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserItemDTO> addUserItem(@RequestBody UserItemDTO userItemDTO){
		logger.info("> addUserItem");
		UserItem newItem = userItemService.addNewItem(userItemService.convertFromDTO(userItemDTO));
		
		logger.info("< addUserItem");
		return new ResponseEntity<UserItemDTO>(userItemService.convertToDTO(newItem), HttpStatus.CREATED);
	}
	@RequestMapping(
			value = "/userItem/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserItemDTO> getUserItem(@PathVariable Long id) {
		logger.info("> getUserItem");
		UserItem userItem = userItemService.findOne(id);
		logger.info("< getUserItem");
		return new ResponseEntity<UserItemDTO>(userItemService.convertToDTO(userItem), HttpStatus.OK);
	}
}
