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
public class IrnCreditGstDTO {
	private Long id;
	private String chargeAcc;
	private String subLodgerCode;
	private BigDecimal dBillAmt;
	private BigDecimal crBillAmt;
	private BigDecimal dBLCAmt;
	private BigDecimal crLCAmt;
	private String gstRemarks;
}
