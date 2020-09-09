package com.connectgas.app.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4385398552814981867L;
	@NotBlank
	@Size(min = 10, max = 10)
	@Pattern(regexp = "^\\d{10}$")
	private String phone;
	@NotBlank
	private String password;
	@NotBlank
	private AuthScope authScope;

	// need default constructor for JSON Parsing
	public AuthRequest() {

	}

	public AuthRequest(String phone, String password) {
		this.setPhone(phone);
		this.setPassword(password);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthScope getAuthScope() {
		return authScope;
	}

	public void setAuthScope(AuthScope authScope) {
		this.authScope = authScope;
	}

}
