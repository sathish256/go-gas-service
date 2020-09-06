package com.connectgas.app.notification;

public class Notification {

	private String message;

	private String timeStamp;

	public Notification(String content) {
		this.message = content;
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
