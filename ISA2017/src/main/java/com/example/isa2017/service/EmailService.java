package com.example.isa2017.service;

import java.util.List;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.Message;
import com.example.isa2017.model.User;
import com.example.isa2017.model.UserItem;


public interface EmailService {

	Message save(Message message);
	Message findOne(Long id);
	List<Message> findAll();
	Message delete(Long id);
	
	Message sendApproved(User user, UserItem item) throws MailException, InterruptedException;
	Message sendDispproved(User user, UserItem item) throws MailException, InterruptedException;
	Message sendLicitationEnd(UserItem item) throws MailException, InterruptedException;
	Message sendBidAccepted(UserItem item) throws MailException, InterruptedException;
	Message sendBidRejected(User user, UserItem item) throws MailException, InterruptedException;
	Message sendReservSucc(User user, AdminItem item) throws MailException, InterruptedException;
}
