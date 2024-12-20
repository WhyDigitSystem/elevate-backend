package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbDetailsDTO {
	
	private String coa;
	private String coaCode;
	private String clientAccountName;
	private String clientAccountCode;
	private BigDecimal credit;
	private BigDecimal debit;
	private String remarks;

}
