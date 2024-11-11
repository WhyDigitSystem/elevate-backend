package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CostDebitNoteTaxPrtculDTO {
	
	private Long Id;
	
	private String tds;

	private BigDecimal tdsPercentage;

	private String section;

	private BigDecimal totTDSAmt;

}
