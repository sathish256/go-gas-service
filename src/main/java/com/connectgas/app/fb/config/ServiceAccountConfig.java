package com.connectgas.app.fb.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

@Configuration
@ConfigurationProperties(prefix = "google.fb")
public class ServiceAccountConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -966635146054306512L;

	@JsonProperty("type")
	private String type;
	@JsonProperty("project_id")
	private String projectId;
	@JsonProperty("private_key_id")
	private String privateKeyId;
	@JsonProperty("private_key")
	private char[] privateKey;
	@JsonProperty("client_email")
	private String clientEmail;
	@JsonProperty("client_id")
	private String clientId;
	@JsonProperty("auth_uri")
	private String authUri;
	@JsonProperty("token_uri")
	private String tokenUri;
	@JsonProperty("auth_provider_x509_cert_url")
	private String authProviderX509CertUrl;
	@JsonProperty("client_x509_cert_url")
	private String clientX509CertUrl;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getPrivateKeyId() {
		return privateKeyId;
	}

	public void setPrivateKeyId(String privateKeyId) {
		this.privateKeyId = privateKeyId;
	}

	public  char[] getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(char[] privateKey) {
		this.privateKey = privateKey;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAuthUri() {
		return authUri;
	}

	public void setAuthUri(String authUri) {
		this.authUri = authUri;
	}

	public String getTokenUri() {
		return tokenUri;
	}

	public void setTokenUri(String tokenUri) {
		this.tokenUri = tokenUri;
	}

	public String getAuthProviderX509CertUrl() {
		return authProviderX509CertUrl;
	}

	public void setAuthProviderX509CertUrl(String authProviderX509CertUrl) {
		this.authProviderX509CertUrl = authProviderX509CertUrl;
	}

	public String getClientX509CertUrl() {
		return clientX509CertUrl;
	}

	public void setClientX509CertUrl(String clientX509CertUrl) {
		this.clientX509CertUrl = clientX509CertUrl;
	}

}
