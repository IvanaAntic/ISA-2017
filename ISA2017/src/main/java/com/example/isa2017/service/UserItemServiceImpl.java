package com.example.isa2017.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.UserItem;
import com.example.isa2017.repository.UserItemRepository;

@Service
public class UserItemServiceImpl implements UserItemService {

	@Autowired
	private UserItemRepository userItemRepository;
	
	@Override
	public UserItem findOne(Long id) {
		
		return userItemRepository.findOne(id);
	}

	@Override
	public List<UserItem> findAll() {
		
		return userItemRepository.findAll();
	}

	@Override
	public UserItem save(UserItem userItem) {
		
		return userItemRepository.save(userItem);
	}

}
