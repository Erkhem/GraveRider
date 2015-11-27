package com.example.alwaysinmem.model;

import com.j256.ormlite.field.DatabaseField;

public class GraveUser {
	@DatabaseField(generatedId = true, columnName = "")
	private int id;
	
	@DatabaseField(foreign = true, columnName = "user")
	Human user;
	
	@DatabaseField(foreign = true, columnName = "grave")
	Grave grave;

}
