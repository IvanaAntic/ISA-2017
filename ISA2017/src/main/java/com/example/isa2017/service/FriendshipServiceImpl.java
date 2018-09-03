package com.example.isa2017.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.isa2017.model.Friendship;
import com.example.isa2017.repository.FriendshipRepository;

public class FriendshipServiceImpl implements FriendshipService {
	
	@Autowired
	private FriendshipRepository friendshipRepository;

	@Override
	public Friendship save(Friendship friendship) {
		// TODO Auto-generated method stub
		return friendshipRepository.save(friendship);
	}
	
	


}
