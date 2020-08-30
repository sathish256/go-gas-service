package com.connectgas.app.model.order;

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

import com.connectgas.app.model.common.GoGasEntity;

@Entity
@Table(name = "cg_quote")
public class ConnectGasQuote extends GoGasEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7541870822535152218L;
	@Id
	@GeneratedValue(generator = "quote-uuid")
	@GenericGenerator(name = "quote-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true)
	private String id;
	@Column(name = "customerId")
	private String customerId;
	@Column(name = "dealerId")
	private String dealerId;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuoteProduct> quoteProducts;

	@Column(name = "quoteStatus")
	private QuoteStatus quoteStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public List<QuoteProduct> getQuoteProducts() {
		return quoteProducts;
	}

	public void setQuoteProducts(List<QuoteProduct> quoteProducts) {
		this.quoteProducts = quoteProducts;
	}

	public QuoteStatus getQuoteStatus() {
		return quoteStatus;
	}

	public void setQuoteStatus(QuoteStatus quoteStatus) {
		this.quoteStatus = quoteStatus;
	}

}