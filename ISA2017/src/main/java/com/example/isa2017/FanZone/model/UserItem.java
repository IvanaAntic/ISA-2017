package com.example.isa2017.FanZone.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.isa2017.model.User;

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
	private List<Bid> bids; 
}
