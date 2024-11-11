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
public class ParticularsJournalDTO {
	
	private String accountsName;
	private String subledgerName;
	private String subLedgerCode;
	private BigDecimal debitAmount;
	private BigDecimal creditAmount;
	private String narration;
	
	

}
