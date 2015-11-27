package com.example.alwaysinmem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;

public class Grave implements Serializable {

	private static final long serialVersionUID = 1231232132131412L;

	@DatabaseField(generatedId = true, columnName = "grave_id")
	private int graveId;
	
	@DatabaseField(canBeNull = false, columnName = "first_name")
	private String firstname;
	
	@DatabaseField(canBeNull = false, columnName = "last_name")
	private String lastname;

	@DatabaseField(columnName = "lattitude")
	private String lattitude;

	@DatabaseField(columnName = "longitude")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
//		result = prime * result + ((owners == null) ? 0 : owners.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((lattitude == null) ? 0 : lattitude.hashCode());
		result = prime * result + ((longtitude == null) ? 0 : longtitude.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		Grave toCompare = (Grave) o;
		return this.getFirstname().equals(toCompare.getFirstname())
				&& this.getLastname().equals(toCompare.getLastname());
	}

}
