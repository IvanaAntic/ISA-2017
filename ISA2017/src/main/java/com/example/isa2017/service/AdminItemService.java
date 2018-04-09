package com.example.isa2017.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.isa2017.model.AdminItem;


public interface AdminItemService {

	AdminItem save(AdminItem adminItem);
	AdminItem findOne(Long id);
	List<AdminItem> findAll();
}
