package com.connectgas.app.config;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

public class StompSubscriberConfig implements ApplicationListener<SessionSubscribeEvent> {

	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		Message<byte[]> message = event.getMessage();
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		StompCommand command = accessor.getCommand();
		if (command.equals(StompCommand.SUBSCRIBE)) {
			String sessionId = accessor.getSessionId();
			String stompSubscriptionId = accessor.getSubscriptionId();
			String destination = accessor.getDestination();
			// Handle subscription event here
			// e.g. send welcome message to *destination*

			System.out.println("User subscribed" + sessionId + "---" + stompSubscriptionId + "---" + destination);

		}

		if (command.equals(StompCommand.DISCONNECT)) {
			String sessionId = accessor.getSessionId();
			String stompSubscriptionId = accessor.getSubscriptionId();
			String destination = accessor.getDestination();
			// Handle subscription event here
			// e.g. send welcome message to *destination*
			System.out.println("User Disconnected" + sessionId + "---" + stompSubscriptionId + "---" + destination);
		}
	}

}
