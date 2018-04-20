package com.example.isa2017.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.modelDTO.AdminItemDTO;


public interface AdminItemService {

	AdminItem save(AdminItem adminItem);
	AdminItem findOne(Long id);
	List<AdminItem> findAll();
	List<AdminItemDTO> getAll();
	AdminItem addNewItem(AdminItemDTO adminItemDTO);
	AdminItem makeReservation(Long buyerId, Long itemId) throws Exception;
	AdminItem cancelReservation(Long buyerId, Long itemId)throws Exception;
	List<AdminItemDTO> getNotReserved();
	AdminItemDTO deleteAdminItem(Long id);
	AdminItem delete(Long id);
	List<AdminItem> getMyAdminItems(Long id);
	List<AdminItem> adminItemsFromDTO(List<AdminItemDTO> adminItemDTO);
	List<AdminItemDTO> adminItemsToDTO(List<AdminItem> adminItem);
	List<AdminItem> getByBuyer(Long id);
	List<AdminItem> getReserved();


}
