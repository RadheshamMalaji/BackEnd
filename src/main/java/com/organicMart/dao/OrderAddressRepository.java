package com.organicMart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.organicMart.pojos.OrderAddress;

public interface OrderAddressRepository extends JpaRepository<OrderAddress, Integer> {
	OrderAddress findByOrderId(String oId);
}
