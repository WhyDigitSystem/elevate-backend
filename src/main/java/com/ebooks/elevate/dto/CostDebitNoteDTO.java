package com.ebooks.elevate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostDebitNoteDTO {

	private Long id;
	private String branch;
	private String branchCode;
	private String createdBy;
	private String cancelRemarks;
	private String finYear;
	private String ipNo;
	private String latitude;
	private Long orgId;
	private String docNo;
	private String subType;
	private String product;
	private String vohNo;
	private LocalDate vohDate; 
	private String partyType;
	private String suppRefNo;
	private LocalDate suppDate;
	private String partyName;
	private String partyCode;
	private int creditDays;
	private LocalDate dueDate;
	private String address;
	private String currency;
	private BigDecimal exRate;
	private String otherInfo;
	private String remarks;
	private String shipRefNo;
	private String status;
	private String orginBill;
	private String gstType;
	private boolean taxExampt;
	private LocalDate currentDate;
	private BigDecimal currentDateValue;
	private String partyAddType;
	
	private List<CostDebitNoteSummaryDTO> costDebitNoteSummaryDTO;
	
	private List<CostDebitChargesDTO> costDebitChargesDTO;
	
	private List<CostDebitNoteGstDTO> costDebitNoteGstDTO;
	
	private List<CostDebitNoteTaxPrtculDTO> costDebitNoteTaxPrtculDTO;
	
	
}
