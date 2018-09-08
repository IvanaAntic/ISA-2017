package com.example.isa2017.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.isa2017.model.Role;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.ChangePassDTO;
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
	
	@Autowired
	private FriendshipService friendshipService;
	
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
		user1.setType("0");
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
		//if(emailExist(user.getEmail()))
		if(userExist(user.getEmail(),user.getPassword()))
		{
			
			user1.setEmail(user.getEmail());
			user1.setPassword(user.getPassword());
			
        	System.out.println("Mapira");
        	
        	//Ovo sam uradio da bi mi vratio id usera na front
        	user1.setId(findByEmail(user.getEmail()).getId());
        	user1.setCity(findByEmail(user.getEmail()).getCity());
        	user1.setName(findByEmail(user.getEmail()).getName());
         	user1.setSurname(findByEmail(user.getEmail()).getSurname());
         	user1.setPhoneNumber(findByEmail(user.getEmail()).getPhoneNumber());
         	user1.setRole(findByEmail(user.getEmail()).getRole());
         	user1.setFirstLogin(findByEmail(user.getEmail()).isFirstLogin());
         	user1.setType(findByEmail(user.getEmail()).getType());
			return user1;
		
		}
    	System.out.println("Vraca null");

		return null;
		
		
	}
	@Override
	public User convertFromDTO(UserDTO user){
		User user1 = new User();
		user1.setEmail(user.getEmail());
		user1.setPassword(user.getPassword());
		user1.setCity(user.getCity());
		user1.setPhoneNumber(user.getPhoneNumber());
		user1.setRole(user.getRole());
		user1.setType(user.getType());
		return user1;
	}
	@Override
	public UserDTO convertToDTO(User user){
		UserDTO user1DTO=new UserDTO();
		user1DTO.setId(user.getId());
		user1DTO.setEmail(user.getEmail());
		user1DTO.setName(user.getName());
		user1DTO.setPassword(user.getPassword());
		user1DTO.setSurname(user.getSurname());
		user1DTO.setCity(user.getCity());
		user1DTO.setPhoneNumber(user.getPhoneNumber());
		user1DTO.setRole(user.getRole());
		user1DTO.setType(user.getType());
		return user1DTO;
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
	private boolean userExist(String email,String password){
		User user=userRepository.findByEmailAndPassword(email, password);
		if(user!=null){
			System.out.println("Odgovaraju email i sifra");
            return true;
		}
		 System.out.println("Ne odgovaraju email i sifra");
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
	
	@Override
	public User editUser(UserDTO user,User user1){
		
		User userEdit=user1;
		//User user= userRepository.findByEmail(user1.getEmail());
		//System.out.println("user1edit"+user1.getEmail());
		userEdit.setName(user.getName());
		userEdit.setSurname(user.getSurname());
		userEdit.setCity(user.getCity());
		userEdit.setPhoneNumber(user.getPhoneNumber());
		System.out.println("ime je"+userEdit.getName());
		userRepository.save(userEdit);
			
		return userEdit;
		
	}
	
	public User verifyEmail(Long id){
		User user1=userRepository.findById(id);
		user1.setActive(true);
		userRepository.save(user1);
		
		return user1;
	}
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}
	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}
	@Override
	public void changePassword(ChangePassDTO front, User loggedUser) {
		
		//User user = loggedUser;
		
		if(loggedUser.getRole() == Role.ADMIN){
			
			loggedUser.setFirstLogin(false);
			
			if(front.getNewPass().equals(front.getNewConfirmed())){
				
				loggedUser.setPassword(front.getNewPass());
				userRepository.save(loggedUser);
				
			}
			
		}
	}
	
	//odnos ostalih korisnika u odnosu na logovanog
	@Override
	public List<User> getAllUsers(User user){
		User logged=userRepository.findByEmail(user.getEmail());//logovan je pera 
		System.out.println("sad cemo ici koji su peri prihvaceni prijatelji: ");
		List<User> friends=friendshipService.getFriends(logged,"accepted");
		System.out.println("sad cemo ici koji su peri waiting prijatelji: ");
		List<User> waitingFriends=friendshipService.getFriends(logged,"waiting");
		List<User> allUsres=userRepository.findAll();
		List<User> all=new ArrayList<>();
		for(User u:allUsres){
			if(friends.contains(u)){
				u.setType("2");
				all.add(u);
			}else if(waitingFriends.contains(u)){
				u.setType("1");
				all.add(u);
			}else if((u.getId()==logged.getId())){
				u.setType("3");
				all.add(u);
			}else{
				u.setType("0");
				all.add(u);
			}
		}
		
		return all;
	}

}
