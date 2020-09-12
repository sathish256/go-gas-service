package com.connectgas.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Configuration
public class StompSubscriberConfig implements ApplicationListener<SessionSubscribeEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(StompSubscriberConfig.class);

	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {

		LOGGER.info("Received Event" + event.getTimestamp());
		Message<byte[]> message = event.getMessage();
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		StompCommand command = accessor.getCommand();
		if (command.equals(StompCommand.SUBSCRIBE)) {
			String sessionId = accessor.getSessionId();
			String stompSubscriptionId = accessor.getSubscriptionId();
			String destination = accessor.getDestination();
			// Handle subscription event here
			// e.g. send welcome message to *destination*

			LOGGER.info("User subscribed" + sessionId + "---" + stompSubscriptionId + "---" + destination);

		}

		if (command.equals(StompCommand.DISCONNECT)) {
			String sessionId = accessor.getSessionId();
			String stompSubscriptionId = accessor.getSubscriptionId();
			String destination = accessor.getDestination();
			// Handle subscription event here
			// e.g. send welcome message to *destination*
			LOGGER.info("User Disconnected" + sessionId + "---" + stompSubscriptionId + "---" + destination);
		}
	}

}
