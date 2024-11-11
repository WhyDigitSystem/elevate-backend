package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticularsGstVoucherDTO {
	private Long id;
	private String accountName;
	private String subLedgerCode;
	private String subLedgerName;
	private BigDecimal debit;
	private BigDecimal baseDbAmount;
	private BigDecimal credit;
	private BigDecimal baseCrAmount;
	private String narration;
}
