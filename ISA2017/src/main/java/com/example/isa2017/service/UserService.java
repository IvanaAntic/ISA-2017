package com.example.isa2017.service;

import org.springframework.stereotype.Service;

import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.UserDTO;


@Service
public interface UserService {
	

	User save(UserDTO user);
	User signIn(UserDTO user);
	
	void sendVerificationMail(User user);
	User verifyEmail(Long id);
	
	
}
