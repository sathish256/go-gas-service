package com.connectgas.app.repository;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.connectgas.app.fb.config.FirebaseInitialize;
import com.connectgas.app.model.common.AccessLog;
import com.connectgas.app.model.common.InventoryLog;

@Repository
public class FirebaseRealtimeDatabase {

	@Autowired
	private FirebaseInitialize firebaseInitialize;

	@Autowired
	private RestTemplate restTemplate;

	private long expriationTime;

	private String token;

	public void save(AccessLog accessLog, Class<AccessLog> class1) {

		try {

			if (Instant.now().getEpochSecond() > expriationTime) {
				token = firebaseInitialize.getRefreshToken();
				expriationTime = Instant.now().plusSeconds(3000).getEpochSecond();
			}
			restTemplate.postForObject("https://connect-gas-in.firebaseio.com/accesslogs.json?access_token=" + token,
					accessLog, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void save(InventoryLog inventoryLog, Class<InventoryLog> class1) {

		try {

			if (Instant.now().getEpochSecond() > expriationTime) {
				token = firebaseInitialize.getRefreshToken();
				expriationTime = Instant.now().plusSeconds(3000).getEpochSecond();
			}
			restTemplate.postForObject("https://connect-gas-in.firebaseio.com/inventorylog.json?access_token=" + token,
					inventoryLog, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
