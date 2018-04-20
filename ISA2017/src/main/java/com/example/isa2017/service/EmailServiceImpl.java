package com.example.isa2017.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.AdminItem;
import com.example.isa2017.model.Message;
import com.example.isa2017.model.MessageType;
import com.example.isa2017.model.User;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.repository.MessageRepository;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private UserItemService userItemService;
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Environment env;
	@Override
	public Message save(Message message) {
		
		return messageRepository.save(message);
	}

	@Override
	public Message findOne(Long id) {
		
		return messageRepository.findOne(id);
	}

	@Override
	public List<Message> findAll() {
		List<Message> messages = new ArrayList<>();
		messages = messageRepository.findAll();
		return messages;
	}

	@Override
	public Message delete(Long id) {
		Message message = messageRepository.findOne(id);
		message.setDeleted(true);
		messageRepository.save(message);
		return message;
	}

	@Override
	@Async
	public Message sendApproved(User user, UserItem item) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Rezervacija proizvoda");
		mail.setText("Postovani " + user.getName() + ",\n\nLicitacija vaseg proizvoda je odobrena!\r\n" + 
														"\r\n" + 
														"\r\n" + 
														"\r\n" + 
														"Vaš proizvod:\r\n" + 
														"\r\n" +
														"Naziv proizvoda: "+item.getName()+"\r\n" +
														"Datum zavrsetka licitacije: "+item.getEndDate().toLocaleString()+"\r\n" + 														
														"Pocetna cena: "+item.getStartPrice()+"\r\n" +  
														"\r\n" + 
														" \r\n" + 
														"\r\n");
		javaMailSender.send(mail);
		
		Message message = convertFromMail(mail ,user, item);
		message.setType(MessageType.ODOBREN_OGLAS);
		messageRepository.save(message);
		System.out.println("Email poslat!");
		
		return message;
	}

	@Override
	@Async
	public Message sendDispproved(User user, UserItem item) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Rezervacija proizvoda");
		mail.setText("Postovani " + user.getName() + ",\n\nLicitacija vaseg proizvoda je odbijena!\r\n" + 
														"\r\n" + 
														"\r\n" + 
														"\r\n" + 
														"Vaš proizvod:\r\n" + 
														"\r\n" +
														"Naziv proizvoda: "+item.getName()+"\r\n" +
														"Datum zavrsetka licitacije: "+item.getEndDate().toLocaleString()+"\r\n" + 														
														"Pocetna cena: "+item.getStartPrice()+"\r\n" +  
														"\r\n" + 
														" \r\n" + 
														"\r\n");
		javaMailSender.send(mail);
		
		Message message = convertFromMail(mail ,user, item);
		message.setType(MessageType.ODBIJEN_OGLAS);
		messageRepository.save(message);
		System.out.println("Email poslat!");
		
		return message;

	}

	@Override
	@Async
	public Message sendLicitationEnd(UserItem item) {
		SimpleMailMessage mail = new SimpleMailMessage();
		User user = item.getPostedBy();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Aukcija proizvoda");
		mail.setText("Postovani " + user.getName() + ",\n\nLicitacija vaseg proizvoda uskoro istice.\r\n" + 
														"\r\n" + 
														"\r\n" + 
														"\r\n" + 
														"Vaš proizvod:\r\n" + 
														"\r\n" +
														"Naziv proizvoda: "+item.getName()+"\r\n" +
														"Datum zavrsetka licitacije: "+item.getEndDate().toLocaleString()+"\r\n" + 														
														"Pocetna cena: "+item.getStartPrice()+"\r\n" +
														"Najbolja ponuda: "+item.getCurrentPrice()+"\r\n" +
														"\r\n" + 
														" \r\n" + 
														"\r\n");
		javaMailSender.send(mail);
		
		Message message = new Message();
		message.setUserId(user.getId());
		message.setDate(new Date());
		message.setDeleted(false);
		message.setSubject(mail.getSubject());
		message.setText(mail.getText());
		message.setItemId(item.getId());
		message.setType(MessageType.USKORO_ISTICE);
		messageRepository.save(message);
		System.out.println("Email poslat!");
		
		return message;
	}

	@Override
	@Async
	public Message sendBidAccepted(UserItem item) {
		User user = item.getBuyer();
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Aukcija proizvoda");
		mail.setText("Postovani " + user.getName() + ",\n\nVasa ponuda je prihvacena!\r\n" + 
														"\r\n" + 
														"\r\n" + 
														"\r\n" + 
														"Proizvod:\r\n" + 
														"\r\n" +
														"Naziv proizvoda: "+item.getName()+"\r\n" +
														"Datum zavrsetka licitacije: "+item.getEndDate().toLocaleString()+"\r\n" + 														
														"Pocetna cena: "+item.getStartPrice()+"\r\n" + 
														"Vasa ponuda: "+item.getCurrentPrice()+"\r\n" +
														"Prodavac: "+item.getPostedBy().getName()+" "+item.getPostedBy().getSurname()+ "\r\n" +
														"Broj telefona: " +item.getPostedBy().getPhoneNumber()+ "\r\n" +
														"\r\n" + 
														" \r\n" + 
														"\r\n");
		javaMailSender.send(mail);
		
		Message message = convertFromMail(mail ,user, item);
		message.setType(MessageType.PRIHVACENA_PONUDA);
		messageRepository.save(message);
		return message;
	}

	@Override
	@Async
	public Message sendBidRejected(User user, UserItem item) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Aukcija proizvoda");
		mail.setText("Postovani " + user.getName() + ",\n\nVasa ponuda je odbijena!\r\n" + 
														"\r\n" + 
														"\r\n" + 
														"\r\n" + 
														"Proizvod:\r\n" + 
														"\r\n" +
														"Naziv proizvoda: "+item.getName()+"\r\n" +
														"Datum zavrsetka licitacije: "+item.getEndDate().toLocaleString()+"\r\n" + 														
														"Pocetna cena: "+item.getStartPrice()+"\r\n" + 
														"Vasa ponuda: "+item.getCurrentPrice()+"\r\n" +
														"Prodavac: "+item.getPostedBy().getName()+" "+item.getPostedBy().getSurname()+ "\r\n" +
														"Broj telefona: " +item.getPostedBy().getPhoneNumber()+ "\r\n" +
														"\r\n" + 
														" \r\n" + 
														"\r\n");
		javaMailSender.send(mail);
		
		Message message = convertFromMail(mail ,user, item);
		message.setType(MessageType.ODBIJENA_PONUDA);
		messageRepository.save(message);
		return message;
	}

	@Override
	@Async
	public Message sendReservSucc(User user, AdminItem item) throws MailException, InterruptedException  {
		
		System.out.println("Slanje emaila...");
		String place;
		String placeAddress;
		if (item.getCinema() == null) {
			 place = item.getTheatre().getName();
			 placeAddress = item.getTheatre().getAddress();
		}else {
			place = item.getCinema().getName();
			placeAddress = item.getCinema().getAddress();
		}

		String reserDate = item.getReservationDate().toLocaleString();
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Rezervacija proizvoda");
		mail.setText("Postovani " + user.getName() + ",\n\nUspešno ste rezervisali proizvod na nasem sajtu!\r\n" + 
														"\r\n" + 
														" \r\n" + 
														"\r\n" + 
														"Vaša rezervacija:\r\n" + 
														"\r\n" +
														"Naziv proizvoda: "+item.getName()+"\r\n" +
														"Datum rezervacije: "+reserDate+"\r\n" + 														
														"Mesto preuzimanja: "+place+", "+placeAddress+"\r\n" +  
														"Ukupan iznos: "+item.getPrice()+"\r\n" + 
														"\r\n" + 
														" \r\n" + 
														"\r\n");
		javaMailSender.send(mail);
		
		Message message = new Message();
		message.setUserId(user.getId());
		message.setDate(new Date());
		message.setDeleted(false);
		message.setSubject(mail.getSubject());
		message.setText(mail.getText());
		message.setItemId(item.getId());
		message.setType(MessageType.USPESNA_REZERVACIJA_PROIZVODA);
		messageRepository.save(message);
		System.out.println("Email poslat!");
		
		return message;
	}
	private Message convertFromMail(SimpleMailMessage mail , User user, UserItem item) {
		Message message = new Message();
		message.setUserId(user.getId());
		message.setDate(new Date());
		message.setDeleted(false);
		message.setSubject(mail.getSubject());
		message.setText(mail.getText());
		message.setItemId(item.getId());
		return message;
	}

}
