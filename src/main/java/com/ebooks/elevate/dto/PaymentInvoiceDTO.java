package com.ebooks.elevate.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInvoiceDTO {
	private Long id;
	private String invoiceNumber;
	private LocalDateTime invoiceDate;
	private String refNo;
	private LocalDateTime refDate;
	private String suppRefNo;
	private LocalDateTime suppRefDate;
	private String curr;
	private BigDecimal exRate;
	private String amount;
	private String outStanding;
	private String settled;
	private BigDecimal payExRate;
	private String txnSettled;
	private String gainOrLoss;
	private String remarks;
}
