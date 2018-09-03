package com.example.isa2017.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.isa2017.model.Friendship;


@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {


}
