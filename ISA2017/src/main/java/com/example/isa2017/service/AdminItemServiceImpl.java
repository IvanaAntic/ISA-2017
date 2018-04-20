package com.example.isa2017.service;

import static org.assertj.core.api.Assertions.registerCustomDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Role;
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
		adminItem.setPostedDate(new Date());		
		adminItem.setReserved(false);
		
		return adminItemRepository.save(adminItem);
	}

	@Override
	public AdminItem makeReservation(Long buyerId, Long itemId) throws Exception {
		
		User buyer = userRepository.findById(buyerId);
		AdminItem item = findOne(itemId);
		if ( buyer.getRole() != Role.USER) {
			throw new IllegalAccessError("Nemate prava pristupa!");
		}
		if (item.isReserved()) {
			throw new IllegalAddException("Proizvod je vec rezervisan!");
		}
		item.setBuyer(buyer);
		item.setReserved(true);
		item.setReservationDate(new Date());
		AdminItem reserved = adminItemRepository.save(item);
		
		return reserved;
	}

	@Override
	public AdminItem cancelReservation(Long buyerId, Long itemId) {
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

	@Override
	public List<AdminItem> getMyAdminItems(Long id) {
		List<AdminItem> retVal = new ArrayList<>();
		List<AdminItem> items = adminItemRepository.findAll();
		for (AdminItem adminItem : items) {
			if (adminItem.getPostedBy().getId() == id) {
				retVal.add(adminItem);
			}
		}
		return retVal;
	}

	@Override
	public List<AdminItem> adminItemsFromDTO(List<AdminItemDTO> adminItemsDTOs) {
		for (AdminItemDTO adminItemDTO : adminItemsDTOs) {
			
		}
		return null;
	}

	@Override
	public List<AdminItemDTO> adminItemsToDTO(List<AdminItem> adminItems) {
		List<AdminItemDTO> retVal = new ArrayList<>();
		for (AdminItem adminItem : adminItems) {
			retVal.add(new AdminItemDTO(adminItem));
		}
		return retVal;
	}

	@Override
	public List<AdminItem> getByBuyer(Long id) {
		List<AdminItem> adminItems = adminItemRepository.findAll();
		List<AdminItem> retVal = new ArrayList<>();
	
		for (AdminItem adminItem : adminItems) {
			if (adminItem.getBuyer() != null && adminItem.getBuyer().getId() == id) {
				retVal.add(adminItem);
			}
		}
		return retVal;
	}

	@Override
	public List<AdminItem> getReserved() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
