package com.organicMart.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organicMart.pojos.Orders;
import com.organicMart.pojos.Product;
import com.organicMart.pojos.User;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	List<Orders> findByselectedCustomer(User user);
	
	List<Orders> findBySelectedDeliveryBoy(User user);

	Optional<Product> findAllById(String orderId);

}
