package com.api.redis.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class User implements Serializable{
	
	/**
	 * 
	 */
	private String userId;
	private String name;
	private String phone;
	private String email;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public User(String userId, String name, String phone, String email) {
		super();
		this.userId = userId;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	public User() {
		super();
	}

}
