package com.example.isa2017.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.repository.AdminItemReposotory;

@Service
public class AdminItemServiceImpl implements AdminItemService {

	@Autowired
	private AdminItemReposotory adminItemRepository;
	
	//Admin treba da kreira nove tematske proizvode
	@Override
	public AdminItem save(AdminItem adminItem) {
		
		return adminItemRepository.save(adminItem);
	}

	@Override
	public AdminItem findOne(Long id) {
		
		return adminItemRepository.findOne(id);
	}

	@Override
	public List<AdminItem> findAll() {
		
		return adminItemRepository.findAll();
	}

}
