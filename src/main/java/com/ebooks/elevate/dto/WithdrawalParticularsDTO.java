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
public class WithdrawalParticularsDTO {

	private String accountsName;
	private BigDecimal debit;
	private BigDecimal credit;
	private String narration;
}
