package com.connectgas.app.notification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Notification {

	private String message;

	private String timeStamp;

	public Notification(String content) {
		this.message = content;
		this.timeStamp = LocalDateTime.now(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern("HH:mm"));
	}

	public String getContent() {
		return message;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
