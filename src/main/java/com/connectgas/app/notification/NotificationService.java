package com.connectgas.app.notification;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import com.connectgas.app.model.dto.ConnectGasResponse;

/**
 * Service class for sending notification messages.
 */
@Service
public class NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

	// The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private SimpUserRegistry simpUserRegistry;

	private Map<String, Queue<Notification>> notificationQueue = new LinkedHashMap<>();

	/**
	 * Send notification to users subscribed on channel "/user/queue/notify".
	 *
	 * The message will be sent only to the user with the given username.
	 * 
	 * @param notification The notification message.
	 * @param username     The userPhone for the user to send notification.
	 */
	public void notify(Notification notification, String user) {
		logger.info("NotificationService::notify::user::{}", user);
		Queue<Notification> queuedNotifications = notificationQueue.getOrDefault(user, new LinkedBlockingQueue<>());
		queuedNotifications.add(notification);

		logger.info("usercount in simpleUserRegistry count {}", simpUserRegistry.getUserCount());

		logger.info("usercount in simpleUserRegistry users {}", simpUserRegistry.getUsers().toString());

		logger.info("usercount in simpleUserRegistry user sessions {}", simpUserRegistry.getUser(user).getSessions());
		if (simpUserRegistry.getUser(user) != null && simpUserRegistry.getUser(user).hasSessions()) {
			logger.info("NotificationService::notify::User has live Session::{}", user);
			while (!queuedNotifications.isEmpty())
				messagingTemplate.convertAndSendToUser(user, "/queue/notify", queuedNotifications.poll());
		} else {
			logger.info("NotificationService::notify::User does not have live Session::Notification Added to Queue::{}",
					queuedNotifications.size());

			notificationQueue.put(user, queuedNotifications);
		}
	}

	public ConnectGasResponse triggerNotifications(String user) {

		Queue<Notification> queuedNotifications = notificationQueue.getOrDefault(user, new LinkedBlockingQueue<>());
		logger.info("NotificationService::notify::Notification Added to Queue::{}", queuedNotifications.size());
		while (!queuedNotifications.isEmpty())
			messagingTemplate.convertAndSendToUser(user, "/queue/notify", queuedNotifications.poll());

		return new ConnectGasResponse(HttpStatus.OK, "Processed Notifications");
	}

}