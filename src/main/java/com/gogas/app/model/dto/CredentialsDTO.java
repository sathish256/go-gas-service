package com.gogas.app.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CredentialsDTO {

	@NotNull
	@NotBlank
	@Size(min = 10, max = 10)
	@Pattern(regexp = "^\\d{10}$" )
	private String phone;

	@NotNull
	@NotBlank
	@Size(min = 8, max = 16)	
	private String currentPassword;

	@NotNull
	@NotBlank
	@Size(min = 8, max = 16)
	private String newPassword;


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
