package com.example.isa2017.service;

import org.springframework.stereotype.Service;

import com.example.isa2017.model.Friendship;



@Service
public interface FriendshipService {

	Friendship save(Friendship friendship);
}
