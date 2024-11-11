package com.ebooks.elevate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrsOpeningDTO {

	private Long id;
	private String billNo;
	private LocalDate billDate;
	private String chqNo;
	private LocalDate chqDate;
	private String bank;
	private String currency;
	private BigDecimal exRate;
	private BigDecimal receiptAmount;
	private BigDecimal paymentAmount;
	private boolean reconcile;
	//Common Fields
	private Long orgId;
	private String branch;
	private String branchCode;
	private String createdBy;
	private String updatedBy;
	private boolean active;
	private boolean cancel;
	private String cancelRemarks;
	private String finYear;
}
