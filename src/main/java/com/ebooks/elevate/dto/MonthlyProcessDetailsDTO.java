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
	
	private String mainGroup;
	private String elGlCode;
	private String elGl;
	private String natureOfAccount;
	private String segment;
	private String segmentCode;
	private BigDecimal currentMonthDebit;
	private BigDecimal currentMonthCredit;
	private BigDecimal currentMonthClosing;
	private BigDecimal previoustMonthDebit;
	private BigDecimal previoustMonthCredit;
	private BigDecimal previoustMonthClosing;
	private BigDecimal forTheMonthActDebit;
	private BigDecimal forTheMonthActCredit;
	private BigDecimal forTheMonthActClosing;
	private BigDecimal provisionDebit;
	private BigDecimal provisionCredit;
	private BigDecimal provisionClosing;
	private BigDecimal forTheMonthDebit;
	private BigDecimal forTheMonthCredit;
	private BigDecimal forTheMonthClosing;
	private boolean mismatch;
	private boolean approveStatus;
	private String provisionRemarks;

}
