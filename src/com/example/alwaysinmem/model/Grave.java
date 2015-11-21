package com.example.alwaysinmem.model;

import java.io.Serializable;

public class Grave implements Serializable {

	private static final long serialVersionUID = 1231232132131412L;

	private String firstname;

	private String lastname;

	private String lattitude;

	private String longtitude;
	

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLattitude() {
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

}
