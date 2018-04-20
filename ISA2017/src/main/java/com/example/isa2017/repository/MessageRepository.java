package com.example.isa2017.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.isa2017.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
