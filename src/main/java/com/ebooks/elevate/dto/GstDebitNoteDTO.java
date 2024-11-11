package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GstDebitNoteDTO {
	private Long id;
	private String chargeAccount;
	private String subLedgerCode;
	private String dbBillAmount;
	private String crBillAmount;
	private String dblcAmt;
	private String crlcamt;
}
