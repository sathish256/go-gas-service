package com.connectgas.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectgas.app.model.common.Dashboard;
import com.connectgas.app.service.DashboardService;

@RestController
@RequestMapping("/v1/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/delivery")
	public ResponseEntity<Dashboard> getDashboard(
			@RequestHeader(value = "modifiedBy", required = true) String modifiedBy) {
		return ResponseEntity.ok(dashboardService.getDeliveryPersonDashboard(modifiedBy));
	}

	@GetMapping("/dealer")
	public ResponseEntity<Dashboard> getDashboardByDealerId(
			@RequestParam(value = "dealerId", required = false) String dealerId,
			@RequestHeader(value = "modifiedBy", required = true) String modifiedBy) {
		return ResponseEntity.ok(dashboardService.getDealerDashboard(dealerId, modifiedBy));
	}

	@GetMapping("/candf")
	public ResponseEntity<Dashboard> getDashboardByCandfId(
			@RequestHeader(value = "modifiedBy", required = true) String modifiedBy) {

		return ResponseEntity.ok(dashboardService.getCandfDashboard(modifiedBy));
	}

}
