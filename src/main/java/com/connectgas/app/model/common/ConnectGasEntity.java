package com.connectgas.app.model.common;

import java.io.Serializable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ConnectGasEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -354026790629000246L;
	private String id;
	private String createdAt;
	private String createdBy;
	private String lastmodifiedAt;
	private String lastmodifiedBy;
	private State status;

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastmodifiedAt() {
		return lastmodifiedAt;
	}

	public void setLastmodifiedAt(String lastmodifiedAt) {
		this.lastmodifiedAt = lastmodifiedAt;
	}

	public String getLastmodifiedBy() {
		return lastmodifiedBy;
	}

	public void setLastmodifiedBy(String lastmodifiedBy) {
		this.lastmodifiedBy = lastmodifiedBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public State getStatus() {
		return status;
	}

	public void setStatus(State status) {
		this.status = status;
	}

}
