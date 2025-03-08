package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyProcessDetailsDTO {
	
	private String elglCode;
	private String elglLedger;
	private String natureOfAccount;
	private String segment;
	private BigDecimal closingBalanceCurrentMonth;
	private BigDecimal closingBalancePreviousMonth;
	private BigDecimal actual;
	private BigDecimal budget;
	private BigDecimal pyActual;
	private String mismatch;
	private boolean approveStatus;
	private BigDecimal provision;
	private BigDecimal actualCurrentMonth;

}
