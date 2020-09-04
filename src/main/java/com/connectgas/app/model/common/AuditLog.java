package com.connectgas.app.model.common;

import java.io.Serializable;

public class AuditLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -418881350587540504L;
	private String id;
	private String firstName;
	private String lastName;

	public AuditLog() {

	}

	public AuditLog(String string, String string2) {
		this.firstName = string;
		this.lastName = string2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}