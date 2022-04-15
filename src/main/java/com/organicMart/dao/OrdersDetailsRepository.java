package com.organicMart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organicMart.pojos.OrderDetails;
import com.organicMart.pojos.Orders;

@Repository
public interface OrdersDetailsRepository extends JpaRepository<OrderDetails, Integer> {
	List<OrderDetails> findBySelectedOrder(Orders odr);
	
	
}
