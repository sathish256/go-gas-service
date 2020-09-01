package com.connectgas.app.model.product;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "candfPrice")
public class CAndFPrice {

	@Id
	@GeneratedValue(generator = "cndp-uuid")
	@GenericGenerator(name = "cndp-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true)
	private String id;

	@Column(name = "candfId")
	private String candfId;

	@Column(name = "price")
	private BigDecimal price;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCandfId() {
		return candfId;
	}

	public void setCandfId(String candfId) {
		this.candfId = candfId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
