package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.Bid;
import com.example.isa2017.model.User;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.modelDTO.BidDTO;
import com.example.isa2017.modelDTO.UserItemDTO;


public interface UserItemService {

	UserItem save(UserItem userItem);
	UserItem findOne(Long id);
	UserItem delete(Long id);
	List<UserItem> findAll();
	UserItem addNewItem(UserItem userItem);
	UserItem approve(Long itemId, Long adminId);
	UserItem disapprove(Long itemId, Long adminId);
	List<UserItem> getNotApproved();
	List<UserItem> getApproved();
	List<UserItem> getApprovedBy(Long id);
	List<UserItem> getPostedBy(Long id);
	List<UserItem> getBuyer(Long id);
	UserItem convertFromDTO(UserItemDTO userItemDTO);
	UserItemDTO convertToDTO(UserItem userItem);
	List<UserItem> convertFromDTOs(List<UserItemDTO> userItemDTOs);
	List<UserItemDTO> convertToDTOs(List<UserItem> userItems);
	UserItem createBid(User user, Bid bid) throws Exception; 
	UserItem acceptBid(User owner,BidDTO bidDTO);
	UserItem rejectLicitation(User owner, Long itemId);
	List<User> getBidders(UserItem userItem);
	void checkStatus();
}
