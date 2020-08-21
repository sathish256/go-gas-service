package com.gogas.app.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class GoGasEntity {

	@Column(name = "createdat")
	private LocalDateTime createat = LocalDateTime.now();
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "lastmodifiedat")
	private LocalDateTime lastmodifiedat = LocalDateTime.now();
	@Column(name = "lastmodifiedby")
	private String lastmodifiedby;

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private State state;



	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public LocalDateTime getCreateat() {
		return createat;
	}

	public void setCreateat(LocalDateTime createat) {
		this.createat = createat;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public LocalDateTime getLastmodifiedat() {
		return lastmodifiedat;
	}

	public void setLastmodifiedat(LocalDateTime lastmodifiedat) {
		this.lastmodifiedat = lastmodifiedat;
	}

	public String getLastmodifiedby() {
		return lastmodifiedby;
	}

	public void setLastmodifiedby(String lastmodifiedby) {
		this.lastmodifiedby = lastmodifiedby;
	}

}
