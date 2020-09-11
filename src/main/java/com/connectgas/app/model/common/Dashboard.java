package com.connectgas.app.model.common;

import java.io.Serializable;
import java.util.List;

public class Dashboard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -968263020046623199L;

	private Integer todayOrders;
	private Integer monthlyOrders;
	private Double todayAmount;
	private Double monthlyAmount;
	private Double totalDuesFromCustomers;
	private Double totolDuesFromDealers;

	private List<ChartDataPoint> chartDataPoints;

	public Integer getTodayOrders() {
		return todayOrders;
	}

	public void setTodayOrders(Integer todayOrders) {
		this.todayOrders = todayOrders;
	}

	public Integer getMonthlyOrders() {
		return monthlyOrders;
	}

	public void setMonthlyOrders(Integer monthlyOrders) {
		this.monthlyOrders = monthlyOrders;
	}

	public Double getTodayAmount() {
		return todayAmount;
	}

	public void setTodayAmount(Double todayAmount) {
		this.todayAmount = todayAmount;
	}

	public Double getMonthlyAmount() {
		return monthlyAmount;
	}

	public void setMonthlyAmount(Double monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}

	public Double getTotalDuesFromCustomers() {
		return totalDuesFromCustomers;
	}

	public void setTotalDuesFromCustomers(Double totalDuesFromCustomers) {
		this.totalDuesFromCustomers = totalDuesFromCustomers;
	}

	public Double getTotolDuesFromDealers() {
		return totolDuesFromDealers;
	}

	public void setTotolDuesFromDealers(Double totolDuesFromDealers) {
		this.totolDuesFromDealers = totolDuesFromDealers;
	}

	public List<ChartDataPoint> getChartDataPoints() {
		return chartDataPoints;
	}

	public void setChartDataPoints(List<ChartDataPoint> chartDataPoints) {
		this.chartDataPoints = chartDataPoints;
	}

}
