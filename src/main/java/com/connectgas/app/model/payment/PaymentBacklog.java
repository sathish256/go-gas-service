package com.connectgas.app.model.payment;

import com.connectgas.app.model.common.ConnectGasEntity;

public class PaymentBacklog extends ConnectGasEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6751828146216032141L;

	private AccountHolderType accountHolderType;

	private Double backlogAmount;
	
	private Double securityDeposit;
	
	private String auditLogMsg;

	public AccountHolderType getAccountHolderType() {
		return accountHolderType;
	}

	public void setAccountHolderType(AccountHolderType accountHolderType) {
		this.accountHolderType = accountHolderType;
	}

	public Double getBacklogAmount() {
		return backlogAmount;
	}

	public void setBacklogAmount(Double backlogAmount) {
		this.backlogAmount = backlogAmount;
	}

	public Double getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(Double securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public String getAuditLogMsg() {
		return auditLogMsg;
	}

	public void setAuditLogMsg(String auditLogMsg) {
		this.auditLogMsg = auditLogMsg;
	}

}
