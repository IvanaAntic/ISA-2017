package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.UserItem;

public interface UserItemService {

	UserItem save(UserItem userItem);
	UserItem findOne(Long id);
	List<UserItem> findAll();
	
}
