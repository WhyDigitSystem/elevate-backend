package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostInvSummaryDTO {

	private Long id;
	private BigDecimal totChargesBillCurrAmt;
	private BigDecimal totChargesLcAmt;
	private BigDecimal actBillCurrAmt;
	private BigDecimal actBillLcAmt;
	private BigDecimal netBillCurrAmt;
	private BigDecimal netBillLcAmt;
	private BigDecimal roundOff;
	private BigDecimal gstInputLcAmt;
	
	
	
}
