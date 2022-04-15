package com.organicMart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organicMart.pojos.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
