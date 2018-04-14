package com.example.isa2017.modelDTO;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Theatre;
import com.example.isa2017.model.User;

public class AdminItemDTO {

	private Long id;
	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private String price;
	private boolean isReserved;
	private Long theatreId;
	private String theatreName;
	private Long cinemaId;
	private String cinemaName;
	private Long buyerId;
	private String buyerName;
	private Long postedById;
	private String postedByName;
	
	
	public AdminItemDTO() {
		super();
	}


	public AdminItemDTO(AdminItem adminItem) {
		this.id = adminItem.getId();
		this.name = adminItem.getName();
		this.description = adminItem.getDescription();
		this.price = Integer.toString(adminItem.getPrice());
		this.isReserved = adminItem.isReserved();
		if (adminItem.getTheatre() != null) {
			this.theatreId = adminItem.getTheatre().getId();
			this.theatreName = adminItem.getTheatre().getName();
		}
		if (adminItem.getCinema() != null) {
			this.cinemaId = adminItem.getCinema().getId();
			this.cinemaName = adminItem.getCinema().getName();
		}
		if (adminItem.getBuyer() != null) {
			this.buyerId = adminItem.getBuyer().getId();
			this.buyerName = adminItem.getBuyer().getName();
		}
		
		this.postedById = adminItem.getPostedBy().getId();
		this.postedByName = adminItem.getPostedBy().getName();
	}


	public AdminItemDTO(Long id, String name, String description, String price, boolean isReserved, Long theatreId,
			Long cinemaId, Long buyerId, Long postedById) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.isReserved = isReserved;
		this.theatreId = theatreId;
		this.cinemaId = cinemaId;
		this.buyerId = buyerId;
		this.postedById = postedById;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public boolean isReserved() {
		return isReserved;
	}


	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}


	public Long getTheatreId() {
		return theatreId;
	}


	public void setTheatreId(Long theatreId) {
		this.theatreId = theatreId;
	}


	public Long getCinemaId() {
		return cinemaId;
	}


	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}


	public Long getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}


	public Long getPostedById() {
		return postedById;
	}


	public void setPostedById(Long postedById) {
		this.postedById = postedById;
	}


	public String getTheatreName() {
		return theatreName;
	}


	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}


	public String getCinemaName() {
		return cinemaName;
	}


	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}


	public String getBuyerName() {
		return buyerName;
	}


	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}


	public String getPostedByName() {
		return postedByName;
	}


	public void setPostedByName(String postedByName) {
		this.postedByName = postedByName;
	}
	
	
}
