package com.organicMart.services;

import java.util.List;

import com.organicMart.dto.LoginRequest;
import com.organicMart.dto.UserDTO;
import com.organicMart.pojos.Address;
import com.organicMart.pojos.User;

public interface IUserServices {
	User authenticateUser(LoginRequest loginRequest);
	
	String createAccount(User user);
	
	User editProfile(int userId, UserDTO userDTO);
	
	String changePassword(int userId, String pwd);
	
	Address getAddress(int userId);
	
	String editAddress(int userId, Address address);
	
	List<User> getAllSupplier();
	
	List<User> getAllDeliveryBoy();
	
	int addSupplierAccount(String categoryName, User user);
	
	Address getAddressDetails(int orderId);
	
	User getUserDetails(int cId);
	
	boolean changePassword(String email, String password);
}
