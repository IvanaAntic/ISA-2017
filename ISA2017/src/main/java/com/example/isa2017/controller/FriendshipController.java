package com.example.isa2017.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.converters.UserToUserDTO;
import com.example.isa2017.model.Friendship;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.FriendshipDTO;
import com.example.isa2017.modelDTO.UserDTO;
import com.example.isa2017.service.FriendshipService;

@RestController
@RequestMapping(value = "/friendship")
public class FriendshipController {
	
	@Autowired
	private FriendshipService friendshipService;
	@Autowired
	private UserToUserDTO toUserDTO;
	
	
	@RequestMapping( value = "/addFriend", method= RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Friendship> addFriend(HttpServletRequest request,@RequestBody FriendshipDTO friendDTO){
		//treba mi ulogovani user
		System.out.println("Usli u FRIENDSHIP ADDFRIEND");
		User logged = (User) request.getSession().getAttribute("logged");
		//Friendship f=friendshipService.getFriendship(logged,friendDTO,"waiting");
		//DODAJ U friendshipbazu
		
		
		//System.out.println("ADD FRIEND FREIENDSHIP PROVERA"+f.getId());
		if(logged!=null){
			//getFriendship(User logged,FriendshipDTO friendshipDTO,String status)
			
			friendshipService.addFriend(logged,friendDTO);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/displayFriendRequests", method= RequestMethod.GET)
	public ResponseEntity<List<UserDTO>>  displayRequests(HttpServletRequest request){
		//System.out.println("Usli u displayFriendRequests");
		User logged = (User) request.getSession().getAttribute("logged");
		List<User> friends=friendshipService.getFriendshipRequests(logged);
		
		//treba da vratimo listu requestova
		return new ResponseEntity<>(toUserDTO.convert(friends),HttpStatus.OK);
	}
	
	
	
	
	
	@RequestMapping( value = "/accept", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity acceptFriend(HttpServletRequest request,@RequestBody FriendshipDTO friendshipDTO){
		System.out.println("Usli u ACCEPT KONTROLEEEEER");
		User logged = (User) request.getSession().getAttribute("logged");
		System.out.println("ULogovan je"+logged.getId());
		System.out.println("Prijatelj je"+friendshipDTO.getId());
		
		friendshipService.acceptFriend(logged,friendshipDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
}
