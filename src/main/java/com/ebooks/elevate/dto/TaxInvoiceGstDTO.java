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
public class TaxInvoiceGstDTO {
	
	private String gstChargeAcc;
	private String gstSubledgerCode;
	private BigDecimal gstDbBillAmount;
	private BigDecimal gstCrBillAmount;
	private BigDecimal gstDbLcAmount;
	private BigDecimal gstCrLcAmount;

}