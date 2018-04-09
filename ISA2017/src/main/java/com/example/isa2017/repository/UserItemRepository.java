package com.example.isa2017.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.isa2017.model.UserItem;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {

}
