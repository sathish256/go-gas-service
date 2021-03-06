package com.connectgas.app.fb.config;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitialize {

	Logger logger = LoggerFactory.getLogger(FirebaseInitialize.class);

	@Autowired
	private ServiceAccountConfig serviceAccountConfig;

	@PostConstruct
	public void initialize() {

		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(getServiceAccount()))
					.setDatabaseUrl("https://fir-demo-8920f.firebaseio.com").build();
			FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private InputStream getServiceAccount() throws JsonProcessingException {

		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("\"type\": \"").append(serviceAccountConfig.getType()).append("\",\n");
		sb.append("\"project_id\": \"").append(serviceAccountConfig.getProjectId()).append("\",\n");
		sb.append("\"private_key_id\": \"").append(serviceAccountConfig.getPrivateKeyId()).append("\",\n");
		sb.append("\"private_key\": \"").append(serviceAccountConfig.getPrivateKey()).append("\",\n");
		sb.append("\"client_email\": \"").append(serviceAccountConfig.getClientEmail()).append("\",\n");
		sb.append("\"client_id\": \"").append(serviceAccountConfig.getClientId()).append("\",\n");
		sb.append("\"auth_uri\": \"").append(serviceAccountConfig.getAuthUri()).append("\",\n");
		sb.append("\"token_uri\": \"").append(serviceAccountConfig.getTokenUri()).append("\",\n");
		sb.append("\"auth_provider_x509_cert_url\": \"").append(serviceAccountConfig.getAuthProviderX509CertUrl())
				.append("\",\n");
		sb.append("\"client_x509_cert_url\": \"").append(serviceAccountConfig.getClientX509CertUrl()).append("\"");
		sb.append("\n}");

		/*
		 * ObjectMapper objectMapper = new ObjectMapper();
		 * objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		 * objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		 * objectMapper.configure(
		 * JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true );
		 */
		return new ByteArrayInputStream(sb.toString().getBytes());
	}

}
