package com.connectgas.app.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.threeten.bp.LocalDateTime;

@Configuration
@EnableScheduling
@Controller
public class NotificationSchedular {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	int notificationCount;

	@Scheduled(fixedDelay = 60000)
	public void publishUpdates() {
		String userPhone = "9886333900";
		Notification notification = new Notification("New Notification No " + notificationCount);
		notification.setTimeStamp(LocalDateTime.now().toString());
		messagingTemplate.convertAndSendToUser(userPhone, "/queue/notify", notification);
	}

	@Scheduled(fixedDelay = 60000)
	public void publishUpdatesToNaseer() {
		String userPhone = "9738521186";
		Notification notification = new Notification("New Notification No " + notificationCount);
		notification.setTimeStamp(LocalDateTime.now().toString());
		messagingTemplate.convertAndSendToUser(userPhone, "/queue/notify", notification);
	}
}
