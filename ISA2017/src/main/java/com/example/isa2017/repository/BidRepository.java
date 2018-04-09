package com.example.isa2017.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.isa2017.model.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

}
