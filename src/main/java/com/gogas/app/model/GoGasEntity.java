package com.gogas.app.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class GoGasEntity {

	@Column(name = "createdAt")
	private LocalDateTime createdAt = LocalDateTime.now();
	@Column(name = "createdBy")
	private String createdBy;
	@Column(name = "lastmodifiedAt")
	private LocalDateTime lastmodifiedAt = LocalDateTime.now();
	@Column(name = "lastmodifiedBy")
	private String lastmodifiedBy;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private State status;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getLastmodifiedAt() {
		return lastmodifiedAt;
	}

	public void setLastmodifiedAt(LocalDateTime lastmodifiedAt) {
		this.lastmodifiedAt = lastmodifiedAt;
	}

	public String getLastmodifiedBy() {
		return lastmodifiedBy;
	}

	public void setLastmodifiedBy(String lastmodifiedBy) {
		this.lastmodifiedBy = lastmodifiedBy;
	}

	public State getStatus() {
		return status;
	}

	public void setStatus(State status) {
		this.status = status;
	}

}
