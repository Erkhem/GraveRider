package com.example.alwaysinmem.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Human implements Serializable{

	private static final long serialVersionUID = 1350827597088088608L;

	private String name;
	
	private String lastname;
	
	private String login;
	
	private String city;
	
	private String email;

	private String password;
	
	private List<Grave> graves = Collections.emptyList();
	

	public String getName() {
		return name;
	}

	public List<Grave> getGraves() {
		return graves;
	}

	public void setGraves(List<Grave> graves) {
		this.graves = graves;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}