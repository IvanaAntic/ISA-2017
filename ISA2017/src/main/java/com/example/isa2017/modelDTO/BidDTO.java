package com.example.isa2017.modelDTO;

import java.sql.Time;
import java.util.Date;

import javax.persistence.ManyToOne;

import com.example.isa2017.model.Bid;
import com.example.isa2017.model.User;
import com.example.isa2017.model.UserItem;

public class BidDTO {

	private Long id;
	private String date;
	private Long buyerId;
	private String buyerName;
	private Long itemId;
	private String price;
	//String uname = email.substring(0,email.indexOf('@'));
	public BidDTO() {
		super();
	}
	public BidDTO(Bid bid) {

		this.id = bid.getId();
		this.date = bid.getDate().toString();
		this.buyerId = bid.getBuyer().getId();
		this.buyerName = bid.getBuyer().getEmail().split("@")[0];
		this.itemId = bid.getBuyer().getId();
		this.price = Integer.toString(bid.getPrice());
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
