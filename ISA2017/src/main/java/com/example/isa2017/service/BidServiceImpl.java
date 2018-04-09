package com.example.isa2017.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.Bid;
import com.example.isa2017.repository.BidRepository;

@Transactional
@Service
public class BidServiceImpl implements BidService {

	@Autowired
	private BidRepository bidRepository;
	
	//Prijavljeni korisnici mogu da licitiraju, ne sme biti licitirana manja cena od trenutne
	
	@Override
	public Bid findOne(Long id) {
		
		return bidRepository.findOne(id);
	}

	@Override
	public List<Bid> findAll() {
		
		return bidRepository.findAll();
	}

	@Override
	public Bid save(Bid bid) {
		
		return bidRepository.save(bid);
	}

}
