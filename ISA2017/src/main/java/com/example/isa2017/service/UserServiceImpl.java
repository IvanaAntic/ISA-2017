package com.example.isa2017.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.UserDTO;
import com.example.isa2017.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Environment  env;
	@Override
	public User save(UserDTO user)  {
		// TODO Auto-generated method stub
		if(emailExist(user.getEmail())){
			return null;
		}
		
		User user1 = new User();
		user1.setName(user.getName());
		user1.setSurname(user.getSurname());
		user1.setEmail(user.getEmail());
		//dodaj da ne moze admin da vidi password koji je prosledjen!!
		//mozes sa private BCryptPasswordEncoder passwordEncoder;
		user1.setPassword(user.getPassword());
		user1.setCity(user.getCity());
		user1.setPhoneNumber(user.getPhoneNumber());
		user1.setRole(user.getRole().USER);
		
		User user2 = userRepository.findByEmail(user1.getEmail());
		if(user2==null){
			return userRepository.save(user1);
		}
		//ukoliko posljamo sa register isti email vratice null kao response kada unesemo sve napametre u formu i nece dodati u bazu
		return null;
	}
	@Override
	public User signIn(UserDTO user) {
		// TODO Auto-generated method stub
		
		User user1=new User();
		//da li taj email vec NE postoji mozes da ga signIN-ujes
		if(emailExist(user.getEmail()))
		{
			
			user1.setEmail(user.getEmail());
			user1.setPassword(user.getPassword());
        	System.out.println("Mapira");

			return user1;
		
		}
    	System.out.println("Vraca null");

		return null;
		
		
	}
	
	private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
        	System.out.println("true");
            return true;
        }
        System.out.println("false");
        return false;
    }
	
	public void sendVerificationMail(User user)throws MailException{
		//send email
		System.out.println("Sending email...");

		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setFrom(env.getProperty("spring.mail.username"));
		

		message.setSubject("Email for verification");
		message.setText("Hi, " + user.getName() + "\nPlease click on this link:"+ ",\n\n http://localhost:8080/user/verify/"+user.getId()+"");
		javaMailSender.send(message);
		

	}
	
	public User verifyEmail(Long id){
		User user1=userRepository.findById(id);
		user1.setActive(true);
		userRepository.save(user1);
		
		return user1;
	}
	

}
