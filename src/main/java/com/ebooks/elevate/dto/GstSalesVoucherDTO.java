package com.ebooks.elevate.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GstSalesVoucherDTO {
	private Long id;
	private String docId;
	private String referenceNo;
	private String currency;
	private LocalDateTime docDate;
	private LocalDateTime referenceDate;
	private BigDecimal exRate;
	private String remarks;
	private String createdBy;
	private BigDecimal totalDebitAmount;
	private BigDecimal totalCreditAmount;
	private BigDecimal stTaxAmount;
	private BigDecimal basAmount;
	private BigDecimal bssAmount;
	private BigDecimal chaAmount;

	List<ParticularsGstVoucherDTO> particularsGstVoucherDTO;

}
