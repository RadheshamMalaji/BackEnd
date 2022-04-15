package com.organicMart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organicMart.pojos.User;

@Repository
public interface CustomerRepository extends JpaRepository<User, Integer> {

}
