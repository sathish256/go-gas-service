package com.connectgas.app.model.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.connectgas.app.model.order.OrderStatus;

public class Dashboard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -968263020046623199L;

	private Map<OrderStatus, Long> todayOrders;
	private Map<OrderStatus, Long> monthlyOrders;
	private Double todayAmount;
	private Double monthlyAmount;
	private Double totalDuesFromCustomers;
	private Double totolDuesFromDealers;

	private List<ChartDataPoint> chartDataPoints;

	public Map<OrderStatus, Long> getTodayOrders() {
		return todayOrders;
	}

	public void setTodayOrders(Map<OrderStatus, Long> todayOrders) {
		this.todayOrders = todayOrders;
	}

	public Map<OrderStatus, Long> getMonthlyOrders() {
		return monthlyOrders;
	}

	public void setMonthlyOrders(Map<OrderStatus, Long> monthlyOrders) {
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
