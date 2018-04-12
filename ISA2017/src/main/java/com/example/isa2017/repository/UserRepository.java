package com.example.isa2017.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.isa2017.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
 
		User findByEmail(String email);
		//User findByIdEquals(Long id);
		User findById(Long id);
	
}
