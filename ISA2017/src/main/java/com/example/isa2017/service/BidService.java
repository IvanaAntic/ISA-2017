package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.Bid;

public interface BidService {

	Bid save(Bid bid);
	Bid findOne(Long id);
	List<Bid> findAll();
}
