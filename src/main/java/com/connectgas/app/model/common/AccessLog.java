package com.connectgas.app.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccessLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4196287064172212481L;

	private String phone;
	private String lastLoginTimeStamp;

	public AccessLog() {
		// DefaultConstructor
	}

	public AccessLog(String phone) {
		this.phone = phone;
		this.lastLoginTimeStamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLastLoginTimeStamp() {
		return lastLoginTimeStamp;
	}

	public void setLastLoginTimeStamp(String lastLoginTimeStamp) {
		this.lastLoginTimeStamp = lastLoginTimeStamp;
	}

}
