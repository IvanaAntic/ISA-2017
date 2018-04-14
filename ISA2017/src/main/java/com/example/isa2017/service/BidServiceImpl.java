package com.example.isa2017.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.Bid;
import com.example.isa2017.modelDTO.BidDTO;
import com.example.isa2017.repository.BidRepository;

@Transactional
@Service
public class BidServiceImpl implements BidService {

	@Autowired
	private BidRepository bidRepository;
	@Autowired
	private UserItemService userItemService;
	@Autowired
	private UserService userService;
	
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

	@Override
	public Bid bidFromDTO(BidDTO bidDTO) {
		Bid bid = new Bid();
		bid.setId(bidDTO.getId());
		bid.setPrice(Integer.parseInt(bidDTO.getPrice()));
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			Date date = dateFormat.parse(bidDTO.getDate());
			System.out.println(date);
			bid.setDate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		bid.setItem(userItemService.findOne(bidDTO.getItemId()));
		bid.setBuyer(userService.findById(bidDTO.getBuyerId()));
		
		return bid;
	}

}
