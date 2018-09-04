package com.example.isa2017.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.isa2017.model.Friendship;
import com.example.isa2017.modelDTO.UserDTO;


@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

	


}
