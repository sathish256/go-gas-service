package com.connectgas.app.model.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AccessLog extends ConnectGasEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4196287064172212481L;

	private String phone;

	public AccessLog() {
		// DefaultConstructor
	}

	public AccessLog(String phone) {

		this.phone = phone;
		this.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		this.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		this.setId(UUID.randomUUID().toString());
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
