package com.connectgas.app.model.common;

import java.io.Serializable;

public class ChartDataPoint implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4200219852202316677L;
	private String date;
	private Integer noOfOrders;

	public ChartDataPoint() {
		// Default Constructor
	}

	public ChartDataPoint(String date, Long noOfOrders) {
		this.date = date;
		this.noOfOrders = noOfOrders.intValue();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getNoOfOrders() {
		return noOfOrders;
	}

	public void setNoOfOrders(Integer noOfOrders) {
		this.noOfOrders = noOfOrders;
	}

}
