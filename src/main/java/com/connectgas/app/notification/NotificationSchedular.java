package com.connectgas.app.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class NotificationSchedular {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	int notificationCount;

	@Scheduled(fixedDelay = 6000)
	public void publishUpdates() {
		if (notificationCount < 20) {
			String userPhone = "9886333900";
			notificationCount++;
			Notification notification = new Notification("New Notification No " + notificationCount);
			messagingTemplate.convertAndSendToUser(userPhone, "/queue/notify", notification);
		}
	}

}
