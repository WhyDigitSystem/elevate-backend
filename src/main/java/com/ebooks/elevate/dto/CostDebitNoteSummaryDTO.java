package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostDebitNoteSummaryDTO {

	private Long Id;

	private BigDecimal totChargesBillCurrAmt;

	private BigDecimal totChargesLCAmt;

	private BigDecimal totGrossBillAmt;

	private BigDecimal totGrossLCAmt;

	private BigDecimal netBillCurrAmt;

	private BigDecimal netLCAmt;

	private String amtInWords;
	
	private BigDecimal roundOff;

}
