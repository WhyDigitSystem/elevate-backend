package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptOtherAccountDTO {
	private Long id;
	private String accountName;
	private String subLedgerName;
	private String credit;
	private String remarks;
}
