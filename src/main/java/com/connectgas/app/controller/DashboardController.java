package com.connectgas.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectgas.app.model.common.Dashboard;
import com.connectgas.app.service.DashboardService;

@RestController
@RequestMapping("/v1/dashboard")
public class DashboardController {

	private DashboardService dashboardService;

	@GetMapping("/{userid}/{type}")
	public ResponseEntity<Dashboard> getDashboard(@PathVariable String userId, @PathVariable String type) {
		return ResponseEntity.ok(dashboardService.getDashboard(userId, type));
	}

}
