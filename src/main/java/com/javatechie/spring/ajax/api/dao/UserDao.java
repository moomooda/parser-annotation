package com.javatechie.spring.ajax.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javatechie.spring.ajax.api.dto.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	
	public User findByUserName(String username);

}
