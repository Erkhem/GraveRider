package com.example.alwaysinmem;

import java.io.Serializable;

public class Grave implements Serializable {

	private static final long serialVersionUID = 1231232132131412L;

	private String firstname;

	private String lasename;

	private String lattitute;

	private String longtitute;
	

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLasename() {
		return lasename;
	}

	public void setLasename(String lasename) {
		this.lasename = lasename;
	}

	public String getLattitute() {
		return lattitute;
	}

	public void setLattitute(String lattitute) {
		this.lattitute = lattitute;
	}

	public String getLongtitute() {
		return longtitute;
	}

	public void setLongtitute(String longtitute) {
		this.longtitute = longtitute;
	}

}
