package com.gogas.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product extends GoGasEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7541870822535152218L;
	@Id
	@Column(name = "id", unique = true)
	private String id;
	@Column(name = "name")
	private String name;
	@Column(name = "type")
	private String type;
	@Column(name = "description")
	private String description;
	@Column(name = "specification")
	private String specification;
	@Column(name = "candfprice")
	private BigDecimal candfprice;

	@OneToMany(cascade = CascadeType.ALL)
	private List<DealerPrice> dealerprice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public BigDecimal getCandfprice() {
		return candfprice;
	}

	public void setCandfprice(BigDecimal candfprice) {
		this.candfprice = candfprice;
	}

	public List<DealerPrice> getDealerprice() {
		return dealerprice;
	}

	public void setDealerprice(List<DealerPrice> dealerprice) {
		this.dealerprice = dealerprice;
	}

}