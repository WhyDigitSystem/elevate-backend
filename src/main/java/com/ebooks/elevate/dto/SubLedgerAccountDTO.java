package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubLedgerAccountDTO {

	private Long id;
	private String accountsCategory;
	private String subLedgerType;
	private String newCode;
	private String oldCode;
	private String subLedgerName;
	private String controllAccount;
	private String currency;
	private String creditDays;
	private String creditLimit;
	private String vatno;
	private String stateJutisiction;
	private String invoiceType;
	private Long orgId;
	private boolean active;
	private String createdBy;
}
