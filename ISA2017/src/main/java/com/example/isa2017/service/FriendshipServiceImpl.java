package com.example.isa2017.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.Friendship;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.FriendshipDTO;
import com.example.isa2017.modelDTO.UserDTO;
import com.example.isa2017.repository.FriendshipRepository;
import com.example.isa2017.repository.UserRepository;

@Transactional
@Service
public class FriendshipServiceImpl implements FriendshipService {
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	@Autowired 
	private UserRepository userRepository;

	@Override
	public Friendship save(Friendship friendship) {
		// TODO Auto-generated method stub
		return friendshipRepository.save(friendship);
	}

	@Override
	public void addFriend(User logged, FriendshipDTO friendDTO) {
		// TODO Auto-generated method stub
		//uzmi logovanog po mailu
		User user=userRepository.findByEmail(logged.getEmail());
		Long idFriend=friendDTO.getId();
		Friendship friendship=new Friendship();
		friendship.setStatus("waiting");
		friendship.setSender(userRepository.findById(user.getId()));
		friendship.setReciver(userRepository.findById(idFriend));
		friendshipRepository.save(friendship);
		
	}



	@Override
	public List<User> getFriendshipRequests(User logged) {
		// TODO Auto-generated method stub
		System.out.println("USLI U FRENDSHIPSERVICE");
		
		User user=userRepository.findByEmail(logged.getEmail());
		System.out.println("Logovan je:"+user.getId() );
		List<Friendship> allTableFrindsip=friendshipRepository.findAll();
		List<User> usersAll=userRepository.findAll();
		List<User> returnList=new ArrayList<User>();
		for(Friendship f:allTableFrindsip){
			if(f.getStatus().equals("waiting")){
				
				if(f.getReciver().getId().equals(user.getId())){
				//lista recivera
				returnList.add(f.getSender());
			System.out.println("oni koji su u tabeli"+f.getStatus());
				}
			}
		}
		for(User u:returnList){
			System.out.println("Oni koji su mi poslali zahtev"+u.getName());
		}
		
		return returnList;
		
	}
	
	


}
