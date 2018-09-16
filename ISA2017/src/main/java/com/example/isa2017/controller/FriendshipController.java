package com.example.isa2017.controller;

import java.util.ArrayList;
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

import com.example.isa2017.converters.UserToUserDTO;
import com.example.isa2017.model.Friendship;
import com.example.isa2017.model.Projection;
import com.example.isa2017.model.Ticket;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.FriendshipDTO;
import com.example.isa2017.modelDTO.UserDTO;
import com.example.isa2017.service.FriendshipService;
import com.example.isa2017.service.ProjectionService;
import com.example.isa2017.service.TicketService;
import com.example.isa2017.service.UserService;

@RestController
@RequestMapping(value = "/friendship")
public class FriendshipController {
	
	@Autowired
	private FriendshipService friendshipService;
	@Autowired
	private ProjectionService projectionService;
	@Autowired
	private UserToUserDTO toUserDTO;
	//@Autowired
	//private FriendshipToFriendshipDTO toFriendshipDTO;
	@Autowired
	private UserService userService;

	@Autowired
	private TicketService ticketService;
	
	@RequestMapping( value = "/addFriend", method= RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Friendship> addFriend(HttpServletRequest request,@RequestBody FriendshipDTO friendDTO){
		//treba mi ulogovani user
		System.out.println("Usli u FRIENDSHIP ADDFRIEND");
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged!=null){
			friendshipService.addFriend(logged,friendDTO);
			//userService.getAllUsers(logged);
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
	//displayFriendInvitations
	@RequestMapping(value="/displayFriendInvitations", method= RequestMethod.GET)
	public ResponseEntity<List<UserDTO>>  displayFriendInvitations(HttpServletRequest request){
		
		User logged = (User) request.getSession().getAttribute("logged");
		
		List<User> invitations=friendshipService.getFriendshipInvitations(logged);
		if(invitations.isEmpty()){
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(toUserDTO.convert(invitations),HttpStatus.OK);
	}
	//myfriends
	@RequestMapping(value="/displayFriendAccepted", method= RequestMethod.GET)
	public ResponseEntity<List<UserDTO>>  displayRequestsAccepted(HttpServletRequest request){
		//System.out.println("Usli u displayFriendRequests");
		User logged = (User) request.getSession().getAttribute("logged");
		List<User> friends=friendshipService.getFriendshipAccepted(logged);
		
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
	
	
	@RequestMapping( value = "/acceptInvitation", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity acceptFriendInvitation(HttpServletRequest request,@RequestBody FriendshipDTO friendshipDTO){
		System.out.println("Usli u ACCEPT INVITATION");
		User logged = (User) request.getSession().getAttribute("logged");
		System.out.println("ULogovan je"+logged.getId());
		System.out.println("Prijatelj je"+friendshipDTO.getId());
		//Friendship f=friendshipService.getFriendship(logged, friendshipDTO, "accepted");
		//sad je karta od 8
		//prva
		//druga
	//	int one=0;
		int count=0;
		List<Ticket> allTickets=ticketService.findAll();
		//Ticket one=new Ticket();
		//Ticket two=new Ticket();
		for(Ticket t:allTickets){
			if(t.getUser().getId().equals(friendshipDTO)){
				count++;
				
				if(count==1){
					Ticket one=ticketService.findOne(t.getId());
					one.setUser(logged);
					ticketService.save(one);
				}
				
			}
		}
		/*if(ticketcount>1){
			
		}*/
		
		//friendshipService.acceptFriend(logged,friendshipDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@RequestMapping( value = "/reject", method= RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity rejectFriend(HttpServletRequest request,@RequestBody FriendshipDTO friendshipDTO){
		//System.out.println("Usli u Reject kontroler");
		User logged = (User) request.getSession().getAttribute("logged");
		//System.out.println("ULogovan je"+logged.getId());
		//System.out.println("Prijatelj je"+friendshipDTO.getId());
		
		friendshipService.rejectFriend(logged,friendshipDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@RequestMapping( value = "/rejectInvitation", method= RequestMethod.POST)
	public ResponseEntity rejectFriendInvitation(HttpServletRequest request,@RequestBody UserDTO friendshipDTO){
		//System.out.println("Usli u Reject kontroler");
		User logged = (User) request.getSession().getAttribute("logged");
		System.out.println("ULogovan je"+logged.getId());
		System.out.println("Prijatelj je"+friendshipDTO.getId());
		User friend=userService.findById(friendshipDTO.getId());
		Friendship f=friendshipService.areFriends(logged, friend.getId(), "accepted");
		f.setConfirm("notinvited");
		friendshipService.save(f);
		//friendshipService.rejectFriend(logged,friendshipDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
	
	/*@RequestMapping( value = "/rejectInvitation", method= RequestMethod.POST)
	public ResponseEntity rejectFriendInvitation(HttpServletRequest request,@RequestBody FriendshipDTO friendshipDTO){
		System.out.println("Usli u Reject kontroler");
		User logged = (User) request.getSession().getAttribute("logged");
		System.out.println("ULogovan je"+logged.getId());
		System.out.println("Prijatelj je"+friendshipDTO.getId());
		
		friendshipService.rejectFriend(logged,friendshipDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}*/
	
	@RequestMapping( value = "/delete", method= RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteFriend(HttpServletRequest request,@RequestBody FriendshipDTO friendshipDTO){
	
		User logged = (User) request.getSession().getAttribute("logged");
		//System.out.println("ULogovan je"+logged.getId());
		//System.out.println("Prijatelj je"+friendshipDTO.getId());
	
		friendshipService.deleteFriend(logged,friendshipDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
	//inviteFriend
	//proverimo koliko nas logovani ima karata ako ima pozovi prijatelja i promeni id karte u slucaju da on prihvat
	//ako odbije ostace kod nas
	@RequestMapping( value = "/invite/{friendId}/{projId}", method= RequestMethod.POST)
	public ResponseEntity sendFriendInvite(HttpServletRequest request, @PathVariable Long friendId, @PathVariable Long projId){
		System.out.println("USLI U FRIEND INVITE");
		
		Projection p=projectionService.findOne(projId);
		//projection id projId
		
		User logged = (User) request.getSession().getAttribute("logged");
		System.out.println("ULogovan je"+logged.getId());
		User userFriend=userService.findById(friendId);
		List<Ticket> tickets=ticketService.findAll();
		List<Ticket> ticketsUser=new ArrayList<>();
		int countticket=0;
		//proveri koliko LOOGED IMA KARATA ZA PREDSTAVU
		for(Ticket t: tickets){
			if(t.getUser().getId().equals(logged.getId())){
				//System.out.println("INVITE CONTROLER USER ID U KARTI"+t.getUser().getId());
				//System.out.println("INVITE CONTROLER USER ID U KARTI"+logged.getId());
				//AKO JE TO USER MOJ
				//DAJ NJEGOVE KARTE
				//System.out.println("INVITE CONTROLER USER ID U KARTI"+t.getProjection().getId().equals(projId));
				//prebroj karte za projId
				if(t.getProjection().getId().equals(projId)){
					countticket++;
					ticketsUser.add(t);
					//System.out.println("KARTE BROJ"+countticket);
				}
			}
		}
		if(countticket<1){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		userService.sendMailToFriend(userFriend,logged,p);
		//UPDEJTUJ PRIJATELJSTVO KAO INVITED
		Friendship f=friendshipService.areFriends(logged, friendId, "accepted");
		//System.out.println("Friendship je OVAJ:" + f.getId());
		f.setConfirm("invited");
		friendshipService.save(f);
		//System.out.println("Prijatelj je"+friendshipDTO.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//loadInvited
	
	
}
