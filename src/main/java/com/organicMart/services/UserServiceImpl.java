package com.organicMart.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.organicMart.dao.AddressRepository;
import com.organicMart.dao.CategoryRepository;
import com.organicMart.dao.OrdersRepository;
import com.organicMart.dao.SupplierRepository;
import com.organicMart.dao.UserRepository;
import com.organicMart.dto.LoginRequest;
import com.organicMart.dto.UserDTO;
import com.organicMart.pojos.Address;
import com.organicMart.pojos.Category;
import com.organicMart.pojos.Orders;
import com.organicMart.pojos.Role;
import com.organicMart.pojos.Supplier;
import com.organicMart.pojos.User;


@Service
@Transactional
public class UserServiceImpl implements IUserServices {
	@Value("${spring.mail.username}")
	private String  hostmail;
	

	
	@Autowired
	EmailSenderService sender;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AddressRepository addressrepo;
	
	@Autowired
	private CategoryRepository cateRepo;
	
	@Autowired
	private SupplierRepository supRepo;
	
	@Autowired
	private OrdersRepository orderRepo;
	
	@Override
	public User authenticateUser(LoginRequest loginRequest) {
		User u=userRepo.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
		if(u!=null) {
			sender.sendEmailForLogin(u.getEmail());
		}
		return u;
	}
	
	@Override
	public String createAccount(User user) {
		User u = userRepo.save(user);
		Address add = new Address();
		add.setCity("Pune");
		add.setState("Maharashtra");
		add.setCurrentUser(u);
		addressrepo.save(add);
		sender.sendEmailForNewRegistration(user.getEmail());
		return "SignUp successfully";
	}
	
	@Override
	public User editProfile(int userId, UserDTO userDTO) {
		User user = userRepo.findById(userId).get();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setPhone(userDTO.getPhone());
		return user;
	}
	
	@Override
	public String changePassword(int userId, String pwd) {
		User u = userRepo.findById(userId).get();
		u.setPassword(pwd);
		return "Password Changed successfully";
	}
	
	@Override
	public Address getAddress(int userId) {
		return addressrepo.findById(userId).get();
	}
	
	@Override
	public String editAddress(int userId, Address address) {
		Address add = addressrepo.findById(userId).get();
		System.out.println("address : "+add);
		if(add != null) {
		add.setArea(address.getArea());
		add.setCity(address.getCity());
		add.setFlatNo(address.getFlatNo());
		add.setPinCode(address.getPinCode());
		add.setSocietyName(address.getSocietyName());
		add.setState(address.getState());
		}
		return "Address Changed successfully";
	}
	
	@Override
	public List<User> getAllSupplier() {
		return userRepo.findByRole(Role.SUPPLIER);
	}
	
	@Override
	public List<User> getAllDeliveryBoy() {
		return userRepo.findByRole(Role.DELIVERY_BOY);
	}
	
	@Override
	public int addSupplierAccount(String categoryName, User user) {
		System.out.println("############: 	"+ categoryName);
		User u = userRepo.save(user);
		Address add = new Address();
		add.setCity("Pune");
		add.setState("Maharashtra");
		add.setCurrentUser(u);
		addressrepo.save(add);
		
		Category c = new Category();
		c.setCategoryName(categoryName);
		//Category cat = cateRepo.findByCategoryName(categoryName);
		Category cat =cateRepo.save(c);
		Supplier supp = new Supplier();
		supp.setCurrentUser(u);
		supp.setSupplierCategory(cat);
		supRepo.save(supp);
		return supp.getCurrentUser().getId();
	}
	
	@Override
	public Address getAddressDetails(int orderId) {
		Orders od = orderRepo.findById(orderId).get();
		User u = od.getSelectedCustomer();		
		return addressrepo.findById(u.getId()).get();
	}
	
	@Override
	public User getUserDetails(int oId) {
		Orders o = orderRepo.findById(oId).get();
		System.out.println("User : "+o.getSelectedCustomer());
		return o.getSelectedCustomer();
	}
	

	public int validateEmailAndGenearateOtp(String email) {
		int randomNumber;
		System.out.println("in serv");
		if (userRepo.findByemailid(email)!=null) {
			randomNumber = (int) (Math.random() * 999999);
			if (randomNumber <= 1000) {
				randomNumber = randomNumber + 1000;
			}
			sender.sendMail(email,"Organic Mart Offical website OTP Verification",String.valueOf(randomNumber));
			return randomNumber;
		}
		else {
			return -1;
		}
	}
	
	@Override
	public boolean changePassword(String email, String password) {
		User u=userRepo.findByemailid(email);
		u.setPassword(password);
		if(userRepo.save(u)!=null)
			return true;
		return false;
	}
	
}
