package com.gogas.app.model.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "auditlog")
public class AuditLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -418881350587540504L;
	@Id
	@GeneratedValue(generator="audit-uuid")
	@GenericGenerator(name="audit-uuid", strategy = "uuid")
	@Column(name = "id", unique = true)
	private String id;
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "lastname")
	private String lastName;
	
	public AuditLog() {

	}

	public AuditLog(String string, String string2) {
		this.firstName = string;
		this.lastName = string2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}