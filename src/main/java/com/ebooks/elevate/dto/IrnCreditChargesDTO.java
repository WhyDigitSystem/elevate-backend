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
public class IrnCreditChargesDTO {
	private Long id;
	private String jobNo;
	private String houseNo;
	private String chargeCode;
	private String gchargeCode;
	private String chargeName;
	private String applyOn;
	private String currency;
	private BigDecimal exRate;
	private BigDecimal rate;
	private boolean exampted;
	private BigDecimal fcAmt;
	private BigDecimal lcAmt;
	private BigDecimal tlcAmt;
	private BigDecimal billAmt;
	private int gstPercentage;
	private BigDecimal gst;
}
