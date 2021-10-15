package com.javatechie.spring.ajax.api.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 批次信息类
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "batch_info")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class BatchInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(length = 255)
	private String name;

	@Column
	private Boolean isShow = true;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date creaDate) {
		this.createDate = creaDate;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

}
