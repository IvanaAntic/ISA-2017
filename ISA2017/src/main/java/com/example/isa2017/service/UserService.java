package com.example.isa2017.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.isa2017.model.Projection;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.ChangePassDTO;
import com.example.isa2017.modelDTO.UserDTO;


@Service
public interface UserService {
	
	//save User
	User save(UserDTO user);
	
	User signIn(UserDTO user);	
	
	void sendVerificationMail(User user);
	
	User verifyEmail(Long id);
	
	List<User> findAll();
	
	User findByEmail(String email);
	
	User findById(Long id);

	User convertFromDTO(UserDTO user);

	UserDTO convertToDTO(User user);

	User editUser(UserDTO user,User use1);
	
	void changePassword(ChangePassDTO frontUser, User loggedUser);

	List<User> getAllUsers(User user);

	List<User> sortByName(User logged);

	List<User> sortBySurname(User logged);

	void sendMailToFriend(User userFriend,User logged,Projection p);
	
}
