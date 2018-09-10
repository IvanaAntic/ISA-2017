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
		//uzmi logovanog po mailu to mi je pera 
		
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

		User user=userRepository.findByEmail(logged.getEmail());
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
		
		return returnList;
		
	}
	
	@Override
	public List<User> getFriendshipAccepted(User logged) {
		// TODO Auto-generated method stub
		User user=userRepository.findByEmail(logged.getEmail());
		List<Friendship> allTableFriendship=friendshipRepository.findAll();
		List<User> returnList=new ArrayList<User>();
		for(Friendship f: allTableFriendship){
			if(f.getStatus().equals("accepted")){
				if(f.getReciver().getId().equals(user.getId())){
					returnList.add(f.getSender());
				}else if(f.getSender().getId().equals(user.getId())){
					returnList.add(f.getReciver());
				}
			}
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
	
	public List<User> getFriends(User user,String status){
		System.out.println("Dobavljamo prijatelje status nam je: "+status);
		User logged=userRepository.findByEmail(user.getEmail());
		System.out.println("SAD PROVERAVAM FINDRECIVERS STA VRACA: ");
		List<User> sender=findRecivers(logged.getId(),status);
		List<User> reciver=findSenders(logged.getId(),status);
		sender.addAll(reciver);
		System.out.println("SENDER RETURN----------------------------------------"+status);
		for(int i=0;i<sender.size();i++){
			System.out.println("sta ima u sender listi"+ sender.get(i).getName());
		}
		System.out.println("SENDER RETURN----------------------------------------"+status);
		return sender;
	}

	private List<User> findSenders(Long id, String status) {
		// TODO Auto-generated method stub
		System.out.println("Usli u findSenders sa statusom" +status);
		List<Friendship> allF=friendshipRepository.findAll();
		System.out.println("Izlistah allF" +status);
		for(Friendship f: allF){
			System.out.println("Izlistah allF" +f.getId()+"status"+f.getStatus());
		}
		List<User> returnList=new ArrayList<>(); 
		for(Friendship f:allF){
			System.out.println("Usli u for RECIVER ID za metodu findSenders"+f.getReciver().getId());
			System.out.println("Usli u for ID KOji je logovan u metodi findSenders"+id);
			System.out.println("u metodi findSenders da li je nas logovani jedrak sa reciver id"+f.getReciver().getId().equals(id));
			if(f.getReciver().getId().equals(id) && f.getStatus().equals(status)){
				System.out.println("Ovo dodajemo u return"+f.getSender().getId());
				returnList.add(f.getSender());
				for(User u:returnList){
					System.out.println("NAME U RETURN LISTI "+u.getName()+"ID:"+u.getId());
				}
				//return returnList;
			}
		}
		return returnList;
	}

	private List<User> findRecivers(Long id, String status) {
		// TODO Auto-generated method stub
		List<Friendship> allF=friendshipRepository.findAll();
		List<User> returnList=new ArrayList<>(); 
		System.out.println("Usli u findRecivers sa statusom" +status);
		for(Friendship f:allF){

			if(f.getSender().getId().equals(id)){
				System.out.println("find reciver"+f.getReciver().getId());
				returnList.add(f.getReciver());
				return returnList;
			}
		}
		return returnList;
	}

	@Override
	public void rejectFriend(User logged, FriendshipDTO friendshipDTO) {
		// TODO Auto-generated method stub
		 Friendship friendship = getFriendship(logged, friendshipDTO, "waiting");
		 System.out.println("ID PRIJATELJSTVA kojeg brisemo JE:" +friendship.getId());
	        if (friendship != null){
	            friendshipRepository.delete(friendship);
	        }
	}

	@Override
	public void deleteFriend(User logged, FriendshipDTO friendshipDTO) {
		// TODO Auto-generated method stub
		 Friendship friendship = getFriendship(logged, friendshipDTO, "accepted");
		 System.out.println("ID PRIJATELJSTVA DELETE JE:" +friendship.getId());
		  if (friendship != null){
	            friendshipRepository.delete(friendship);
	        }
	}

	

}
