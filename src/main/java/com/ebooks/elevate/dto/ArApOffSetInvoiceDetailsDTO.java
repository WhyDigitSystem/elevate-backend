package com.ebooks.elevate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArApOffSetInvoiceDetailsDTO {
	private Long id;
	private String invoiceNo;
	private LocalDate invoiceDate;
	private String refNo;
	private LocalDate refDate;
	private String curr;
	private BigDecimal exRate;
	private BigDecimal invAmount;
	private String outStanding;
	private String settled;
	private BigDecimal setExRate;
	private BigDecimal tnxSettled;
	private BigDecimal gainOrLoss;
	private String remarks;

}
