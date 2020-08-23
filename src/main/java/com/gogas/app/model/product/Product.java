package com.gogas.app.model.product;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.gogas.app.model.common.GoGasEntity;

@Entity
@Table(name = "product")
public class Product extends GoGasEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7541870822535152218L;
	@Id
	@GeneratedValue(generator = "product-uuid")
	@GenericGenerator(name = "product-uuid", strategy = "uuid2")
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

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CAndFPrice> candfPrice;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DealerPrice> dealerPrice;

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

	public List<CAndFPrice> getCandfPrice() {
		return candfPrice;
	}

	public void setCandfPrice(List<CAndFPrice> candfPrice) {
		this.candfPrice = candfPrice;
	}

	public List<DealerPrice> getDealerPrice() {
		return dealerPrice;
	}

	public void setDealerPrice(List<DealerPrice> dealerPrice) {
		this.dealerPrice = dealerPrice;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

}