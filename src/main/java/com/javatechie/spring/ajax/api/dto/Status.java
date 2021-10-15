package com.javatechie.spring.ajax.api.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "corpus_status")
public class Status {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 255)
    private String info;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
