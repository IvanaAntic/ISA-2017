package com.example.isa2017.model;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class FanZone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private User admin;
	private List<AdminItem> adminItems;
	private List<UserItem> 	userItems;
	
}
