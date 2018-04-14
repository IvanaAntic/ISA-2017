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
	AdminItem makeReservation(Long id);
	AdminItem cancelReservation(Long id);
	List<AdminItemDTO> getNotReserved();
	AdminItemDTO deleteAdminItem(Long id);
	AdminItem delete(Long id);
	

}
