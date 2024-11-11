package com.ebooks.elevate.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptInvoiceDTO {
	private Long id;
	private String invoiceNo;
	private LocalDateTime invoiceDt;
	private String refNo;
	private LocalDateTime refDate;
	private String masterRef;
	private String houseRef;
	private String curr;
	private BigDecimal exRate;
	private BigDecimal amount;
	private BigDecimal chargableAmount;
	private String outStanding;
	private String settled;
	private BigDecimal recExRate;
	private BigDecimal txnSettled;
	private BigDecimal gainOrLoss;
}
