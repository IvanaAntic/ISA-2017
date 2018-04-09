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

@Entity
public class UserItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private double startPrice;
	private double currentPrice;
	private Date endDate;
	private Time endTime;
	@OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
	private List<Bid> bids;
	@ManyToOne
	private User postedBy;
	@ManyToOne
	private User buyer;
	private boolean approved;
}
