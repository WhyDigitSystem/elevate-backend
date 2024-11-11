package com.ebooks.elevate.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentSummaryDTO {

	private Long id;
	@Column(name ="totaldebitamount",precision =10,scale =2)
	private float totalDebitAmount;
	@Column(name ="totalcreditamount",precision =10,scale =2)
	private float totalCreditAmount;
	
	
}
