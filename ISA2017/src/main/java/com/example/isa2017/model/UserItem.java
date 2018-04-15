package com.example.isa2017.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.isa2017.modelDTO.AuctionStatus;

@Entity
public class UserItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private int startPrice;
	private int currentPrice;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	@OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
	private List<Bid> bids;
	@ManyToOne
	private User postedBy;
	@ManyToOne
	private User approvedBy;
	@ManyToOne
	private User buyer;
	private AuctionStatus status;
	private boolean approved;
	public UserItem() {
		super();
	}
	public UserItem( String name, String description, int startPrice, int currentPrice, Date endDate,
			List<Bid> bids, User postedBy, User approvedBy, User buyer, AuctionStatus status,
			boolean approved) {

		this.name = name;
		this.description = description;
		this.startPrice = startPrice;
		this.currentPrice = currentPrice;
		this.endDate = endDate;
		this.bids = bids;
		this.postedBy = postedBy;
		this.approvedBy = approvedBy;
		this.buyer = buyer;
		this.status = status;
		this.approved = approved;
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
	public int getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}
	public int getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Bid> getBids() {
		return bids;
	}
	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}
	public User getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}
	public User getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public AuctionStatus getStatus() {
		return status;
	}
	public void setStatus(AuctionStatus status) {
		this.status = status;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
}
