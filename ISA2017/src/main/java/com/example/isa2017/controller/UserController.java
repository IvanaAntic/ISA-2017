package com.example.isa2017.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.UserDTO;
import com.example.isa2017.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/register" , method=RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody UserDTO user){
		
		
		
		User user1 = userService.save(user);
		//kad je sacuvanp posalji verificaion email, napisati u serviceUserIml metodu
		return new ResponseEntity<> (user1,HttpStatus.OK);
	}
	
	
	
}
