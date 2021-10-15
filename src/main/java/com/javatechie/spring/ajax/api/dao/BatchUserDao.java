package com.javatechie.spring.ajax.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatechie.spring.ajax.api.dto.BatchUser;

public interface BatchUserDao extends JpaRepository<BatchUser, Integer> {
	List<BatchUser> findAllByUserId(Integer userId);
}
