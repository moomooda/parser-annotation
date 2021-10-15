package com.javatechie.spring.ajax.api.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * BatchInfo 和 User的中间类
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "batch_user")
@EntityListeners(AuditingEntityListener.class)
public class BatchUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(length = 255)
	private Integer batchId;

	@Column(length = 255)
	private Integer userId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "BatchUser [id=" + id + ", batchId=" + batchId + ", userId=" + userId + "]";
	}
	
}
