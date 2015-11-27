package com.example.alwaysinmem.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;

public class Human implements Serializable{

	private static final long serialVersionUID = 1350827597088088608L;

	@DatabaseField(generatedId = true, columnName = "human_id")
	private int humanId;
	
	@DatabaseField(canBeNull = false, columnName = "name")
	private String name;
	
	@DatabaseField(canBeNull = false, columnName = "last_name")
	private String lastname;
	
	@DatabaseField(canBeNull = false, columnName = "login")
	private String login;
	
	@DatabaseField(canBeNull = true, columnName = "city")
	private String city;
	
	@DatabaseField(canBeNull = true, columnName = "email")
	private String email;
	
	@DatabaseField(canBeNull = false, columnName = "password")
	private String password;
	

	public String getName() {
		return name;
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