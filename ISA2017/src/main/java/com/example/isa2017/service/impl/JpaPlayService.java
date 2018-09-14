package com.example.isa2017.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.isa2017.model.Play;
import com.example.isa2017.repository.PlayRepository;
import com.example.isa2017.service.PlayService;

@Transactional
@Service
public class JpaPlayService implements PlayService{
	
	@Autowired
	private PlayRepository playRepository;
	
	@Override
	public List<Play> findAll() {
		// TODO Auto-generated method stub
		return playRepository.findAll();
	}

	@Override
	public Play save(Play play) {
		// TODO Auto-generated method stub
		return playRepository.save(play);
	}

	@Override
	public Play findOne(Long id) {
		// TODO Auto-generated method stub
		return playRepository.findOne(id);
	}

	@Override
	public Play delete(Long id) {
		Play play = playRepository.findOne(id);
		if (play == null) {
			throw new IllegalArgumentException("Sediste ne postoji.");
		}
		playRepository.delete(play);
		return play;
	}
	
	
}
