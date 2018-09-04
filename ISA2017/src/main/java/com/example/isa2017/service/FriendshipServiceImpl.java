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
		//System.out.println("Logovan je:"+user.getId() );
		List<Friendship> allTableFrindsip=friendshipRepository.findAll();
		
		List<User> returnList=new ArrayList<User>();
		for(Friendship f:allTableFrindsip){
			if(f.getStatus().equals("waiting")){
				
				if(f.getReciver().getId().equals(user.getId())){
				//lista recivera
				returnList.add(f.getSender());
			//System.out.println("oni koji su u tabeli"+f.getStatus());
				}
			}
		}
		for(User u:returnList){
			//System.out.println("Oni koji su mi poslali zahtev"+u.getName());
		}
		
		return returnList;
		
	}

	@Override
	public void acceptFriend(User logged, FriendshipDTO friendshipDTO) {
		// TODO Auto-generated method stub
		
		Friendship f= getFriendship(logged,friendshipDTO,"waiting");
		System.out.println("ID PRIJATELJSTVA JE:" +f.getId());
		f.setStatus("accepted");
		friendshipRepository.save(f);
		
	}
	@Override
	public Friendship getFriendship(User logged,FriendshipDTO friendshipDTO,String status){
		System.out.println("DOBAVI PRIJATELJSTVO");
		User user=userRepository.findByEmail(logged.getEmail());
		Long id=friendshipDTO.getId();
		Friendship getFriendship=new Friendship();
		List<Friendship> allFriendship=friendshipRepository.findAll();
		for(Friendship f:allFriendship){
			System.out.println("ULAZIMO U FOR");
			System.out.println("f.getReciver()----"+f.getReciver());
			System.out.println("logged.getId()----"+logged.getId());
			System.out.println("RECIVER JE"+f.getReciver().equals(logged.getId()));
			if(f.getReciver().getId().equals(logged.getId()) && f.getSender().getId().equals(friendshipDTO.getId()) && f.getStatus().equals(status) ){
				getFriendship=friendshipRepository.findById(f.getId());
				return getFriendship;
			}
			
		}
		return null;
	}
	
	//da li je vec dodat da li vec ista kombinacija id status recivet pstoji
	public boolean isAdded(User logged,FriendshipDTO friendshipDTO,String status){
		Friendship f= getFriendship(logged,friendshipDTO,"waiting");
		Long ifFriendship=f.getId();
		List<Friendship> allFriendship=friendshipRepository.findAll();
		for(Friendship fs:allFriendship){
			if(fs.getId().equals(ifFriendship)){
				return true;
			}
		}
		return false;
		
	}
	
	


}
