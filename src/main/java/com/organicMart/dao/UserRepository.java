package com.organicMart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.organicMart.pojos.Role;
import com.organicMart.pojos.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("select u from User u where u.email=:em and u.password=:pass")
	User authenticateUser(@Param("em")String em, @Param("pass")String pass);

	@Query("select u.id from User u where u.role=:rl")
	List<Integer> getAllDeliveryBoy(@Param("rl")Role rl);
	
	List<User> findByRole(Role role);
	
	@Query("select u from User u where u.email=:em")
	User findByemailid(@Param("em")String em);
}
