package com.example.isa2017.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.isa2017.model.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long>{
	List<Theatre> findByNameContaining(String name);
	List<Theatre> findByAddressContaining(String address);
	List<Theatre> findByNameContainingAndAddressContaining(String name,String address);
}
