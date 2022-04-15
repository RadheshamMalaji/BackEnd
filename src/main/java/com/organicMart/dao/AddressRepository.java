package com.organicMart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organicMart.pojos.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
