package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncrementalProfitDTO {
	

	private String year;
	private String month;
	private Long orgId;
	private String client;
	private String clientCode;
	private String createdBy;
	private String particulars;
	private BigDecimal amount;
	
	private String yearType;


}
