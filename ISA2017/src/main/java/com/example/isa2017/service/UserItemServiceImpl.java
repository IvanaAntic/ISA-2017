package com.example.isa2017.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.isa2017.model.Bid;
import com.example.isa2017.model.Role;
import com.example.isa2017.model.User;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.modelDTO.AuctionStatus;
import com.example.isa2017.modelDTO.BidDTO;
import com.example.isa2017.modelDTO.UserItemDTO;
import com.example.isa2017.repository.UserItemRepository;


@Service
public class UserItemServiceImpl implements UserItemService {

	@Autowired
	private UserItemRepository userItemRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private BidService bidService;
	
	@Override
	public UserItem findOne(Long id) {
		
		return userItemRepository.findOne(id);
	}

	@Override
	public List<UserItem> findAll() {
		
		return userItemRepository.findAll();
	}

	@Override
	public UserItem save(UserItem userItem) {
		
		return userItemRepository.save(userItem);
	}

	@Override
	public UserItem delete(Long id) {
		
		UserItem userItem = userItemRepository.findOne(id);
		if (userItem == null) {
			throw new IllegalArgumentException("Proizvod ne postoji.");
		}else
			userItemRepository.delete(userItem.getId());
			
		return userItem;
	}

	@Override
	public UserItem addNewItem(UserItem userItem) {
		userItem.setApproved(false);
		userItem.setStatus(AuctionStatus.Ceka_odobrenje);
		return save(userItem);
	}

	@Override
	public List<UserItem> getNotApproved() {
		List<UserItem> all = userItemRepository.findAll();
		List<UserItem> retVal = new ArrayList<>();
		for (UserItem userItem : all) {
			if (!userItem.isApproved()) {
				retVal.add(userItem);
			}
		}
		return retVal;
	}

	@Override
	public List<UserItem> getApproved() {
		List<UserItem> all = userItemRepository.findAll();
		List<UserItem> retVal = new ArrayList<>();
		for (UserItem userItem : all) {
			if (userItem.isApproved()) {
				retVal.add(userItem);
			}
		}
		return retVal;
	}

	@Override
	public List<UserItem> getApprovedBy(Long id) {
		List<UserItem> items = findAll();
		for (UserItem userItem : items) {
			if (userItem.getApprovedBy().getId() == id) {
				items.add(userItem);
			}
		}
		return items;
	}

	@Override
	public List<UserItem> getPostedBy(Long id) {
		List<UserItem> items = findAll();
		List<UserItem> retVal = new ArrayList<>();
		if (items == null) {
			throw new IllegalArgumentException("Proizvodi ne postoje.");
		}
		for (UserItem userItem : items) {
			if (userItem.getPostedBy().getId() == id) {
				retVal.add(userItem);
			}
		}
		return retVal;
	}

	@Override
	public List<UserItem> getBuyer(Long id) {
		List<UserItem> items = findAll();
		for (UserItem userItem : items) {
			if (userItem.getBuyer().getId() == id) {
				items.add(userItem);
			}
		}
		return items;
	}

	@Override
	public UserItem convertFromDTO(UserItemDTO userItemDTO) {
		UserItem userItem = new UserItem();
		userItem.setId(userItemDTO.getId());
		userItem.setName(userItemDTO.getName());
		userItem.setDescription(userItemDTO.getDescription());
		userItem.setStartPrice(Integer.parseInt(userItemDTO.getStartPrice()));
		userItem.setCurrentPrice(Integer.parseInt(userItemDTO.getCurrentPrice()));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//2018-04-20 20:00:00
		//ovakav datum saljem "2018-4-20 20:00:00"
		List<BidDTO> bidsDTO = userItemDTO.getBids();
		List<Bid> bids = new ArrayList<>();
		if (bidsDTO != null) {
			for (BidDTO bidDTO : bidsDTO) {
				bids.add(bidService.bidFromDTO(bidDTO));
			}
		}
		
		try {
			System.out.println("Za parsianje "+ userItemDTO.getEndDate());
			Date endDate = dateFormat.parse(userItemDTO.getEndDate());
			System.out.println("Nakon parsiranja "+ endDate);
			System.out.println("Nakon parsiranja i formatiran "+ dateFormat.format(endDate));
			userItem.setEndDate(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//TODO  informacije od Useru koji je postavio, odobrio, kupio, 
		if (userItemDTO.getBuyerId() != null) {
			userItem.setBuyer(userService.findById(userItemDTO.getBuyerId()));
		}
		if (userItemDTO.isApproved()) {
			userItem.setApprovedBy(userService.findById(userItemDTO.getApprovedById()));
		}
		
		userItem.setPostedBy(userService.findById(userItemDTO.getPostedById()));
		userItem.setStatus(userItemDTO.getStatus());
		userItem.setApproved(userItemDTO.isApproved());
		
		return userItem;
	}

	@Override
	public UserItemDTO convertToDTO(UserItem userItem) {
		// TODO Auto-generated method stub
		UserItemDTO userItemDTO = new UserItemDTO();
		userItemDTO.setId(userItem.getId());
		userItemDTO.setName(userItem.getName());
		userItemDTO.setDescription(userItem.getDescription());
		userItemDTO.setStartPrice(Integer.toString(userItem.getStartPrice()));
		userItemDTO.setCurrentPrice(Integer.toString(userItem.getCurrentPrice()));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		userItemDTO.setEndDate(dateFormat.format(userItem.getEndDate()));
		
		List<Bid> bids = userItem.getBids();
		if (bids != null) {
			List<BidDTO> bidsDTO = new ArrayList<>();
			for (Bid bid : bids) {
				bidsDTO.add(new BidDTO(bid));
			}
			userItemDTO.setBids(bidsDTO);
		}
		
		//podaci o USERU
		if (userItem.isApproved()) {
			userItemDTO.setApprovedById(userItem.getApprovedBy().getId());
			userItemDTO.setApprovedByName(userItem.getApprovedBy().getEmail().split("@")[0]);
		}
		if (userItem.getBuyer() != null) {
			userItemDTO.setBuyerId(userItem.getBuyer().getId());
			userItemDTO.setBuyerName(userItem.getBuyer().getEmail().split("@")[0]);
		}
		
		userItemDTO.setPostedById(userItem.getPostedBy().getId());
		userItemDTO.setPostedByName(userItem.getPostedBy().getEmail().split("@")[0]);
		userItemDTO.setStatus(userItem.getStatus());
		userItemDTO.setApproved(userItem.isApproved());
		return userItemDTO;
	}

	@Override
	public List<UserItem> convertFromDTOs(List<UserItemDTO> userItemDTOs) {

		List<UserItem> userItems = new ArrayList<>();
		for (UserItemDTO userItemDTO : userItemDTOs) {
			userItems.add(convertFromDTO(userItemDTO));
		}
		return userItems;
	}

	@Override
	public List<UserItemDTO> convertToDTOs(List<UserItem> userItems) {

		List<UserItemDTO> userItemsDTO = new ArrayList<>();
		for (UserItem userItem : userItems) {
			userItemsDTO.add(convertToDTO(userItem));
		}
		return userItemsDTO;
	}

	@Override
	public UserItem approve(Long itemId, Long adminId) {
		UserItem userItem = findOne(itemId);
		User admin = userService.findById(adminId);
		if (admin.getRole() != Role.FANZONEADMIN) {
			throw new IllegalArgumentException("Morate biti administrator fan zone.");
		}
		userItem.setApprovedBy(admin);
		userItem.setStatus(AuctionStatus.Aktuelan);
		userItem.setApproved(true);
		userItemRepository.save(userItem);
		return userItem;
	}

	@Override
	public UserItem disapprove(Long itemId, Long adminId) {
		UserItem userItem = findOne(itemId);
		User admin = userService.findById(adminId);
		if (admin.getRole() != Role.FANZONEADMIN) {
			throw new IllegalArgumentException("Morate biti administrator fan zone.");
		}
		if (userItem.getApprovedBy() != admin) {
			throw new IllegalArgumentException("Niste odobrili ovaj proizvod.");
		}
		userItem.setApprovedBy(null);
		userItem.setApproved(false);
		userItem.setStatus(AuctionStatus.Odbijen);
		userItemRepository.save(userItem);
		return userItem;
	}
	

}
