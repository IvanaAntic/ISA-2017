package com.example.isa2017.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.isa2017.model.Bid;
import com.example.isa2017.model.Message;
import com.example.isa2017.model.MessageType;
import com.example.isa2017.model.Role;
import com.example.isa2017.model.User;
import com.example.isa2017.model.UserItem;
import com.example.isa2017.modelDTO.AuctionStatus;
import com.example.isa2017.modelDTO.BidDTO;
import com.example.isa2017.modelDTO.UserItemDTO;
import com.example.isa2017.repository.MessageRepository;
import com.example.isa2017.repository.UserItemRepository;
import com.example.isa2017.repository.UserRepository;


@Service
public class UserItemServiceImpl implements UserItemService {

	@Autowired
	private UserItemRepository userItemRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private BidService bidService;
	@Autowired
	private UserRepository ur;
	@Autowired
	private EmailService emailService;
	@Autowired
	private MessageRepository messageRepository;
	@Override
	public UserItem findOne(Long id) {
		
		return userItemRepository.findOne(id);
	}

	@Override
	public List<UserItem> findAll() {
		
		return userItemRepository.findAll();
	}

	@Override
	public UserItem save(UserItem userItem) {
		
		return userItemRepository.save(userItem);
	}

	@Override
	public UserItem delete(Long id) {
		
		UserItem userItem = userItemRepository.findOne(id);
		if (userItem == null) {
			throw new IllegalArgumentException("Proizvod ne postoji.");
		}else
			userItemRepository.delete(userItem.getId());
			
		return userItem;
	}

	@Override
	public UserItem addNewItem(UserItem userItem) {
		userItem.setApproved(false);
		userItem.setStatus(AuctionStatus.Ceka_odobrenje);
		return save(userItem);
	}

	@Override
	public List<UserItem> getNotApproved() {
		List<UserItem> all = userItemRepository.findAll();
		List<UserItem> retVal = new ArrayList<>();
		for (UserItem userItem : all) {
			if (!userItem.isApproved()) {
				retVal.add(userItem);
			}
		}
		return retVal;
	}

	@Override
	public List<UserItem> getApproved() {
		List<UserItem> all = userItemRepository.findAll();
		List<UserItem> retVal = new ArrayList<>();
		for (UserItem userItem : all) {
			if (userItem.isApproved()) {
				retVal.add(userItem);
			}
		}
		return retVal;
	}

	@Override
	public List<UserItem> getApprovedBy(Long id) {
		List<UserItem> items = findAll();
		for (UserItem userItem : items) {
			if (userItem.getApprovedBy().getId() == id) {
				items.add(userItem);
			}
		}
		return items;
	}

	@Override
	public List<UserItem> getPostedBy(Long id) {
		List<UserItem> items = findAll();
		List<UserItem> retVal = new ArrayList<>();
		if (items == null) {
			throw new IllegalArgumentException("Proizvodi ne postoje.");
		}
		for (UserItem userItem : items) {
			if (userItem.getPostedBy().getId() == id) {
				retVal.add(userItem);
			}
		}
		return retVal;
	}

	@Override
	public List<UserItem> getBuyer(Long id) {
		List<UserItem> items = findAll();
		List<UserItem> retVal = new ArrayList<>();
		for (UserItem userItem : items) {
			if ( userItem.getBuyer() != null && userItem.getBuyer().getId() == id) {
				retVal.add(userItem);
			}
		}
		return retVal;
	}
	

	@Override
	public UserItem convertFromDTO(UserItemDTO userItemDTO) {
		UserItem userItem = new UserItem();
		userItem.setId(userItemDTO.getId());
		userItem.setName(userItemDTO.getName());
		userItem.setDescription(userItemDTO.getDescription());
		userItem.setStartPrice(Integer.parseInt(userItemDTO.getStartPrice()));
		userItem.setCurrentPrice(Integer.parseInt(userItemDTO.getCurrentPrice()));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//2018-04-20 20:00:00
		//ovakav datum saljem "2018-4-20 20:00:00"
		List<BidDTO> bidsDTO = userItemDTO.getBids();
		List<Bid> bids = new ArrayList<>();
		if (bidsDTO != null) {
			for (BidDTO bidDTO : bidsDTO) {
				bids.add(bidService.bidFromDTO(bidDTO));
			}
		}
		
		try {
			System.out.println("Za parsianje "+ userItemDTO.getEndDate());
			Date endDate = dateFormat.parse(userItemDTO.getEndDate());
			System.out.println("Nakon parsiranja "+ endDate);
			System.out.println("Nakon parsiranja i formatiran "+ dateFormat.format(endDate));
			userItem.setEndDate(endDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		//TODO  informacije od Useru koji je postavio, odobrio, kupio, 
		if (userItemDTO.getBuyerId() != null) {
			userItem.setBuyer(userService.findById(userItemDTO.getBuyerId()));
		}
		if (userItemDTO.isApproved()) {
			userItem.setApprovedBy(userService.findById(userItemDTO.getApprovedById()));
		}
		
		userItem.setPostedBy(userService.findById(userItemDTO.getPostedById()));
		userItem.setStatus(userItemDTO.getStatus());
		userItem.setApproved(userItemDTO.isApproved());
		
		return userItem;
	}

	@Override
	public UserItemDTO convertToDTO(UserItem userItem) {
		// TODO Auto-generated method stub
		UserItemDTO userItemDTO = new UserItemDTO();
		userItemDTO.setId(userItem.getId());
		userItemDTO.setName(userItem.getName());
		userItemDTO.setDescription(userItem.getDescription());
		userItemDTO.setStartPrice(Integer.toString(userItem.getStartPrice()));
		userItemDTO.setCurrentPrice(Integer.toString(userItem.getCurrentPrice()));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		userItemDTO.setEndDate(dateFormat.format(userItem.getEndDate()));
		
		List<Bid> bids = userItem.getBids();
		if (bids != null) {
			List<BidDTO> bidsDTO = new ArrayList<>();
			for (Bid bid : bids) {
				bidsDTO.add(bidService.bidToDTO(bid));
			}
			userItemDTO.setBids(bidsDTO);
		}
		
		//podaci o USERU
		if (userItem.isApproved()) {
			userItemDTO.setApprovedById(userItem.getApprovedBy().getId());
			userItemDTO.setApprovedByName(userItem.getApprovedBy().getEmail().split("@")[0]);
		}
		if (userItem.getBuyer() != null) {
			userItemDTO.setBuyerId(userItem.getBuyer().getId());
			userItemDTO.setBuyerName(userItem.getBuyer().getEmail().split("@")[0]);
		}
		
		userItemDTO.setPostedById(userItem.getPostedBy().getId());
		userItemDTO.setPostedByName(userItem.getPostedBy().getEmail().split("@")[0]);
		userItemDTO.setStatus(userItem.getStatus());
		userItemDTO.setApproved(userItem.isApproved());
		return userItemDTO;
	}

	@Override
	public List<UserItem> convertFromDTOs(List<UserItemDTO> userItemDTOs) {

		List<UserItem> userItems = new ArrayList<>();
		for (UserItemDTO userItemDTO : userItemDTOs) {
			userItems.add(convertFromDTO(userItemDTO));
		}
		return userItems;
	}

	@Override
	public List<UserItemDTO> convertToDTOs(List<UserItem> userItems) {

		List<UserItemDTO> userItemsDTO = new ArrayList<>();
		for (UserItem userItem : userItems) {
			userItemsDTO.add(convertToDTO(userItem));
		}
		return userItemsDTO;
	}

	@Override
	public UserItem approve(Long itemId, Long adminId) {
		UserItem userItem = findOne(itemId);
		User admin = userService.findById(adminId);
		if (admin.getRole() != Role.FANZONEADMIN) {
			throw new IllegalArgumentException("Morate biti administrator fan zone.");
		}
		userItem.setApprovedBy(admin);
		userItem.setStatus(AuctionStatus.Aktuelan);
		userItem.setApproved(true);
		userItemRepository.save(userItem);
		return userItem;
	}

	@Override
	public UserItem disapprove(Long itemId, Long adminId) {
		UserItem userItem = findOne(itemId);
		User admin = userService.findById(adminId);
		if (admin.getRole() != Role.FANZONEADMIN) {
			throw new IllegalArgumentException("Morate biti administrator fan zone.");
		}
		if (userItem.getApprovedBy() != admin) {
			throw new IllegalArgumentException("Niste odobrili ovaj proizvod.");
		}
		userItem.setApprovedBy(null);
		userItem.setApproved(false);
		userItem.setStatus(AuctionStatus.Odbijen);
		userItemRepository.save(userItem);
		return userItem;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation=Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public UserItem createBid(User user, Bid bid)throws Exception  {
		UserItem userItem = userItemRepository.findOne(bid.getItem().getId());
		/*System.out.println("USER  "+user.getName());
		user.setName("PROBA");
		ur.save(user);
		System.out.println("USER  "+user.getName());
*/		Bid newBid;
		//da li je licitacija istekla
		
		if (bid.getDate().compareTo(userItem.getEndDate()) > 0) 
			throw new IllegalArgumentException("Licitacija je istekla.");
		
			
			if (userItem.getBids() == null) {
				ArrayList<Bid> bids = new ArrayList<>();
				bids.add(bid);
				userItem.setBids(bids);
				userItem.setCurrentPrice(bid.getPrice());
				
			}else {
				
				List<Bid> bids = userItem.getBids();
				for (Bid bid2 : bids) {
					if (bid2.getPrice() >= bid.getPrice()) {
						
						throw new IllegalArgumentException("Ponuda mora biti veca od trenutne ponude.");
					}				
				}
				userItem.getBids().add(bid);
				userItem.setCurrentPrice(bid.getPrice());
			}
			
			UserItem newUserItem = userItemRepository.save(userItem);
		
	
		
		return newUserItem;
	}
	
	@Override
	public UserItem rejectLicitation(User owner, Long id) {
		
		UserItem userItem = userItemRepository.findOne(id);
		if(owner.getId() != userItem.getPostedBy().getId()) {
			throw new IllegalArgumentException("Nemate prava.");
		}
		userItem.setStatus(AuctionStatus.Odbijen);
		List<Bid> bids = userItem.getBids();
		List<Bid> forDelete = new ArrayList<>();
		
		/*for (Bid bid : bids) {
			forDelete.add(bid);
			bidService.delete(bid.getId());
		}
		userItem.getBids().removeAll(forDelete);*/
		
		UserItem rejected =  userItemRepository.save(userItem);
		
		return rejected;
	}

	@Override
	public UserItem acceptBid(User owner, BidDTO bidDTO) {
		
		Bid acceptedBid = bidService.findOne(bidDTO.getId());
		UserItem userItem = findOne(bidDTO.getItemId());
		User buyer = acceptedBid.getBuyer();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		try {
			date = dateFormat.parse(bidDTO.getDate());
			if (date.compareTo(userItem.getEndDate()) > 0 || userItem.getBuyer() != null) {
				throw new IllegalArgumentException("Licitacija je zavrsena "+userItem.getEndDate()+".");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(owner.getId() != userItem.getPostedBy().getId()) {
			throw new IllegalArgumentException("Nemate prava.");
		}
		userItem.setBuyer(buyer);
		userItem.setStatus(AuctionStatus.Zavrsen);
		
		return userItemRepository.save(userItem);
	}

	@Override
	public List<User> getBidders(UserItem userItem) {
		Set<User> users = new HashSet<>();
		
		List<Bid> bids = userItem.getBids();
		for (Bid bid : bids) {
			users.add(bid.getBuyer());
		}
		return new ArrayList<>(users);
	}

	@Override
	@Scheduled(cron = "0 */15 * * * *" )
	public void checkStatus() {
		
		System.out.println("Proveri da li ima licitacija koje uskoro isticu!");
		List<UserItem> approvedItems = getApproved();
		List<Message> messages = messageRepository.findAll();
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		boolean notified = false;
		for (UserItem userItem : approvedItems) {		
			
			cal.setTime(userItem.getEndDate());
			cal.add(Calendar.HOUR, -1);
			date = cal.getTime();
			System.out.println("Now " +nowDate);
			System.out.println("date " +date);
			System.out.println(nowDate.compareTo(date));
			System.out.println(date.compareTo(nowDate));
			int res = nowDate.compareTo(date);
			System.out.println("res "+ res);
			if (res > 0 ) {
				for (Message message : messages) {
					System.out.println("Da li postoji poruka? "+ message.getItemId()+"=="+userItem.getId());
					System.out.println("Da li postoji poruka? "+message.getType()+"=="+MessageType.USKORO_ISTICE);
					if (message.getItemId() == userItem.getId() && (message.getType() == MessageType.USKORO_ISTICE)) {
						notified = true;
						break;
					}
				}
				if (!notified) {
					try {
						notified = false;
						emailService.sendLicitationEnd(userItem);
						
					} catch (MailException e) {

						e.printStackTrace();
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}	
			}
			
			
		}
	}
	


}
