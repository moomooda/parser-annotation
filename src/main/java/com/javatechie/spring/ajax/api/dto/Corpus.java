package com.javatechie.spring.ajax.api.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 语料实体类
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "corpus")
@EntityListeners(AuditingEntityListener.class)
public class Corpus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 800)
	private String originalCorpus;

	@Column(length = 800)
	private String segment;
	
	@Column(name = "`index`")
	private Integer index;

	@Column(length = 1000)
	private String relation;
	
	@Column(length = 1000)
	private String dependency;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date importDate;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@Column
	private Integer userId;

	@Column
	private Integer statusId;

	@Column(length = 32)
	private Integer batchId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOriginalCorpus() {
		return originalCorpus;
	}

	public void setOriginalCorpus(String originalCorpus) {
		this.originalCorpus = originalCorpus;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	@Override
	public String toString() {
		return "Corpus [id=" + id + ", originalCorpus=" + originalCorpus + ", index=" + index + ", relation=" + relation
				+ ", importDate=" + importDate + ", updateDate=" + updateDate + ", userId=" + userId + ", statusId="
				+ statusId + ", batchId=" + batchId  + "]";
	}

}
