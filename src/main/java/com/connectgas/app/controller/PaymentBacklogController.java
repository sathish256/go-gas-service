package com.connectgas.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectgas.app.model.payment.PaymentBacklog;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@RestController
@RequestMapping("/v1/payment-bl")
public class PaymentBacklogController {

	@Autowired
	private SimpleFirestoreRepository<PaymentBacklog, String> paymentRepository;

	@GetMapping("/{ahType}/{id}")
	private ResponseEntity<Double> getCustomerBacklog(@PathVariable String ahType, @PathVariable String id) {

		Map<String, String> criteria = new HashMap<>();
		criteria.put("accountHolderType", ahType);
		criteria.put("id", id);
		List<PaymentBacklog> pb = paymentRepository.findByPathAndValue(criteria, PaymentBacklog.class);
		if (CollectionUtils.isEmpty(pb))
			return ResponseEntity.ok(0.0);
		return ResponseEntity.ok(pb.get(0).getBacklogAmount());

	}

	@PostMapping("/security-deposit")
	private ResponseEntity<PaymentBacklog> updateSecurityDeposit(@RequestBody PaymentBacklog paymentBacklog) {

		Map<String, String> criteria = new HashMap<>();
		criteria.put("accountHolderType", paymentBacklog.getAccountHolderType().toString());
		criteria.put("id", paymentBacklog.getId());
		List<PaymentBacklog> pbs = paymentRepository.findByPathAndValue(criteria, PaymentBacklog.class);
		PaymentBacklog pb = null;
		if (CollectionUtils.isEmpty(pbs)) {
			paymentBacklog.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			paymentBacklog.setLastmodifiedBy("SYSTEM");
			paymentBacklog.setCreatedBy("SYSTEM");
			paymentBacklog.setBacklogAmount(0.0);
		} else {
			pb = pbs.get(0);
			paymentBacklog.setCreatedAt(pb.getCreatedAt());
			paymentBacklog.setCreatedBy("SYSTEM");
			paymentBacklog.setBacklogAmount(pb.getBacklogAmount());
		}
		paymentBacklog.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		paymentBacklog.setAuditLogMsg("Updated Security Deposit");
		return ResponseEntity.ok(paymentRepository.save(paymentBacklog, PaymentBacklog.class));

	}

}
