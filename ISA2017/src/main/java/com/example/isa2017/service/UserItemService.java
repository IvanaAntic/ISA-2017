package com.example.isa2017.service;

import java.util.List;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.modelDTO.UserItemDTO;


public interface UserItemService {

	UserItem save(UserItem userItem);
	UserItem findOne(Long id);
	UserItem delete(Long id);
	List<UserItem> findAll();
	UserItem addNewItem(UserItem userItem);
	List<UserItem> getNotApproved();
	List<UserItem> getApproved();
	List<UserItem> getApprovedBy(Long id);
	List<UserItem> getPostedBy(Long id);
	List<UserItem> getBuyer(Long id);
	UserItem convertFromDTO(UserItemDTO userItemDTO);
	UserItemDTO convertToDTO(UserItem userItem);
	List<UserItem> convertFromDTOs(List<UserItemDTO> userItemDTOs);
	List<UserItemDTO> convertToDTOs(List<UserItem> userItems);

	
}
