package com.connectgas.app.notification;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class Notification {

	private String message;

	private String timeStamp;

	public Notification(String content) {
		this.message = content;
		this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
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
