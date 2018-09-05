package com.example.isa2017.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.converters.CinemaToCinemaDTO;
import com.example.isa2017.converters.UserToUserDTO;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Role;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.ChangePassDTO;
import com.example.isa2017.modelDTO.CinemaDTO;
import com.example.isa2017.modelDTO.UserDTO;
import com.example.isa2017.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserToUserDTO toUserDTO;
	
	@RequestMapping(value="/register" , method=RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody UserDTO user){
		
		User user1 = userService.save(user);
		if(user1!=null){
			try{
				
				userService.sendVerificationMail(user1);
				return new ResponseEntity<>(user1,HttpStatus.OK);
				
			}catch(MailException e)
			{
				log.info("Error sending mail"+e.getMessage());	
			}
			
		}
		//kad je sacuvanp posalji verificaion email, napisati u serviceUserIml metodu
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value="/verify/{id}")
	public ResponseEntity<String> verifyUser(@PathVariable Long id){
		User user =  userService.verifyEmail(id);
		if(user==null){
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		//userService.verifyEmail(id);
		
		return new ResponseEntity<String>("verifikovan",HttpStatus.ACCEPTED);
	}
	@RequestMapping(value="/role/{id}")
	public ResponseEntity<Role> getUserRole(@PathVariable Long id){
		
		User user =  userService.findById(id);
		if (user == null) {
			return new ResponseEntity<Role>(user.getRole(),HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Role>(user.getRole(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<User> loginUser(@RequestBody UserDTO user,HttpSession session,HttpServletRequest request){
		User logged=userService.signIn(user);
		
		if(logged!=null ){
			
			HttpSession newSession = request.getSession();
		    newSession.setAttribute("logged", logged);
			System.out.println("u:"+logged.getEmail());
			return new ResponseEntity<>(logged,HttpStatus.OK);
			
		}
		
		return new ResponseEntity<>(logged,HttpStatus.NOT_FOUND);
	}
	
	
	@RequestMapping(value="/displayUser", method=RequestMethod.GET)
	public ResponseEntity<UserDTO> displayUser(HttpServletRequest request){
		User logged = (User) request.getSession().getAttribute("logged");
		System.out.println("user display User:"+logged.getEmail());
		if(logged!=null) {
			UserDTO log=userService.convertToDTO(logged);
			return new ResponseEntity<UserDTO>(log,HttpStatus.OK);
		}
		UserDTO log=null;
		return new ResponseEntity<UserDTO>(log,HttpStatus.NOT_FOUND);
		
	}
	
	@RequestMapping(value="/loggoutUser", method=RequestMethod.GET)
	public ResponseEntity<String> logoutUser(HttpSession session,HttpServletRequest request){
			request.getSession().invalidate();
	
			return new ResponseEntity<String>("User logged out",HttpStatus.OK);	
	}
	
	@RequestMapping(value="/editUser",method=RequestMethod.POST)
	public ResponseEntity<User> editUser(@RequestBody UserDTO user,HttpServletRequest request){
		User user1 = (User)request.getSession().getAttribute("logged");
		System.out.println("Email"+user1.getEmail());
		
			if(user1!=null){
				
				userService.editUser(user,user1);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	public ResponseEntity<User> changePassword(@RequestBody ChangePassDTO front,HttpServletRequest request){
		User loggedUser = (User)request.getSession().getAttribute("logged");
		
		if(loggedUser!=null && loggedUser.getPassword().equals(front.getOld())){
			userService.changePassword(front,loggedUser);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}

	/*@RequestMapping(value="/displayUsers", method=RequestMethod.GET)
	public ResponseEntity<UserDTO> displayUsers(HttpServletRequest request){
		User logged = (User) request.getSession().getAttribute("logged");
		System.out.println("user display User:"+logged.getEmail());
		if(logged!=null) {
			UserDTO log=userService.convertToDTO(logged);
			return new ResponseEntity<UserDTO>(log,HttpStatus.OK);
		}
		UserDTO log=null;
		return new ResponseEntity<UserDTO>(log,HttpStatus.NOT_FOUND);
		
	}*/
	@RequestMapping(value="/displayUsers", method=RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> dispayList(HttpServletRequest request){
		System.out.println("OK");
		User logged = (User) request.getSession().getAttribute("logged");
		List<User> users = userService.findAll();
		List<User> returnList = new ArrayList<>();
		//System.out.println("U listi koji treba da se ispisu korisnici");
		for(User u:users){
			//System.out.println("Display user"+u.getName());
			if(u.getRole()==Role.USER){
				if(!u.getId().equals(logged.getId())){
				returnList.add(u);
				System.out.println("Oni koji su obicni korisnici"+u.getName());
				}
			}
		}
			//return new ResponseEntity<>(toCinemaDTO.convert(cinemas), HttpStatus.OK);
			return new ResponseEntity<List<UserDTO>>(toUserDTO.convert(returnList), HttpStatus.OK);
		
	}
}
