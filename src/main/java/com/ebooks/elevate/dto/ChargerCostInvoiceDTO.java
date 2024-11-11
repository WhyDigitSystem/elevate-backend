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
public class ChargerCostInvoiceDTO {
	private Long id;
	private String houseNo;
	private String jobNo;
	private String subJobNo;
	private String chargeName;
	private String chargeCode;
	private String chargeLedger;
	private String gsac;
	private String contType;
	private String currency;
	private BigDecimal exRate;
	private String gst;
	private BigDecimal fcAmt;
	private BigDecimal lcAmt;
	private BigDecimal billAmt;
}




