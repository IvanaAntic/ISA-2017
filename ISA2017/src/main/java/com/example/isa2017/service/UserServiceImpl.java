package com.example.isa2017.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.UserDTO;
import com.example.isa2017.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public User save(UserDTO user) {
		// TODO Auto-generated method stub
		User user1 = new User();
		user1.setName(user.getName());
		user1.setSurname(user.getSurname());
		user1.setEmail(user.getEmail());
		//dodaj da ne moze admin da vidi password koji je prosledjen!!
		//mozes sa private BCryptPasswordEncoder passwordEncoder;
		user1.setPassword(user.getPassword());
		user1.setCity(user.getCity());
		user1.setPhoneNumber(user.getPhoneNumber());
		user1.setRole(user.getRole().USER);
		
		return userRepository.save(user1);
	}
	@Override
	public User signIn(UserDTO user) {
		// TODO Auto-generated method stub
		
		User user1=new User();
		//da li taj email vec NE postoji mozes da ga signIN-ujes
		
		if((userRepository.findByEmail(user.getEmail()))!=null){
			//jos ovde dodati potvrdu da li je verifikovan email
			
			user1.setEmail(user.getEmail());
			user1.setPassword(user.getPassword());
		}
		
		
		return user1;
	}
	
	

}
