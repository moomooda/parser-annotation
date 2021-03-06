package com.javatechie.spring.ajax.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatechie.spring.ajax.api.dto.BatchInfo;

public interface BatchInfoDao extends JpaRepository<BatchInfo, Integer> {

	BatchInfo findOneByIdAndIsShow(Integer id, Boolean isShow);

}
