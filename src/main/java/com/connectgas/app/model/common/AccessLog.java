package com.connectgas.app.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.connectgas.app.model.dto.AuthScope;

public class AccessLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4196287064172212481L;

	private String phone;
	private String lastLoginTimeStamp;
	private AuthScope authScope;

	public AccessLog() {
		// DefaultConstructor
	}

	public AccessLog(String phone, AuthScope authScope) {
		this.phone = phone;
		this.lastLoginTimeStamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
		this.authScope = authScope;
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

	public AuthScope getAuthScope() {
		return authScope;
	}

	public void setAuthScope(AuthScope authScope) {
		this.authScope = authScope;
	}

}
