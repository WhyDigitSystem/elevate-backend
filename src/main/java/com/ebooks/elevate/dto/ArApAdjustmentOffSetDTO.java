package com.ebooks.elevate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArApAdjustmentOffSetDTO {

	private Long id;
	private String docId;
	private LocalDate docDate;
	private String subLedgerType;
	private String subLedgerName;
	private String subLedgerCode;
	private String receiptPaymentDocId;
	private LocalDate receiptPaymentDocDate;
	private String currency;
	private BigDecimal exRate;
	private String amount;
	private String supplierRefNo;
	private BigDecimal forexGainOrLoss;
	private BigDecimal totalSettled;
	private BigDecimal roundOffAmount;
	private BigDecimal onAccount;
	private String narration;
	private Long orgId;
	private boolean active;
	private String createdBy;
	
	List<ArApOffSetInvoiceDetailsDTO> arApOffSetInvoiceDetailsDTO;
}
