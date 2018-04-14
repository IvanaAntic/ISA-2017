package com.example.isa2017.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Theatre;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.AdminItemDTO;
import com.example.isa2017.repository.AdminItemReposotory;
import com.example.isa2017.repository.CinemaRepository;
import com.example.isa2017.repository.TheatreRepository;
import com.example.isa2017.repository.UserRepository;





@Service
public class AdminItemServiceImpl implements AdminItemService {

	@Autowired
	private AdminItemReposotory adminItemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private TheatreRepository theatreRepository;
	
	
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

	@Override
	public AdminItem addNewItem(AdminItemDTO adminItemDTO) {
		AdminItem adminItem = new AdminItem();
		//Ko je dodao
		User postedBy = userRepository.getOne(adminItemDTO.getPostedById());
		if (adminItemDTO.getCinemaId() != null) {
			Cinema cinema = cinemaRepository.getOne(adminItemDTO.getCinemaId());
			adminItem.setCinema(cinema);
		}
		if (adminItemDTO.getTheatreId() != null) {
			Theatre theatre = theatreRepository.getOne(adminItemDTO.getTheatreId());
			adminItem.setTheatre(theatre);
		}
		
		
		if (postedBy == null) {
			return null;
		}
		
		
		adminItem.setName(adminItemDTO.getName());
		adminItem.setDescription(adminItemDTO.getDescription());
		adminItem.setPrice(Integer.parseInt(adminItemDTO.getPrice()));
		adminItem.setPostedBy(postedBy);
		
		
		adminItem.setReserved(false);
		return adminItemRepository.save(adminItem);
	}

	@Override
	public AdminItem makeReservation(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdminItem cancelReservation(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AdminItemDTO> getNotReserved() {
		List<AdminItemDTO> retVal = new ArrayList<>();
		List<AdminItem> items = adminItemRepository.findAll();
		for (AdminItem adminItem : items) {
			if (!adminItem.isReserved()) {
				retVal.add(new AdminItemDTO(adminItem));
			}
		}
		return retVal;
	}

	@Override
	public List<AdminItemDTO> getAll() {
		List<AdminItemDTO> retVal = new ArrayList<>();
		List<AdminItem> items = adminItemRepository.findAll();
		for (AdminItem adminItem : items) {
			
				retVal.add(new AdminItemDTO(adminItem));
		}
		return retVal;
	}

	@Override
	public AdminItemDTO deleteAdminItem(Long id) {
		AdminItem adminItem = adminItemRepository.findOne(id);
		if (adminItem == null) {
			throw new IllegalArgumentException("Proizvod ne postoji.");
		}else
			adminItemRepository.delete(adminItem.getId());
			
		return new AdminItemDTO(adminItem);
	}

	@Override
	public AdminItem delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
