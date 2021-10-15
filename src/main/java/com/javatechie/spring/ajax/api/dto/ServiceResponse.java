package com.javatechie.spring.ajax.api.dto;


public class ServiceResponse<T> {
	
	private String status;
	private T data;
	private Integer code;
	private Long count;
	
	public ServiceResponse(String status, T data) {
		super();
		this.status = status;
		this.data = data;
	}
	public ServiceResponse(String status, Integer code, Long count, T data) {
		super();
		this.code=code;
		this.status = status;
		this.data = data;
		this.count = count;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

}
