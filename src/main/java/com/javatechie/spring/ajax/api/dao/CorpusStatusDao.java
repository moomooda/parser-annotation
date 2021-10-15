package com.javatechie.spring.ajax.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatechie.spring.ajax.api.dto.Status;

public interface CorpusStatusDao  extends JpaRepository<Status, Integer>{

}
