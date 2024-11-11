package com.ebooks.elevate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IrnCreditDTO {
	private Long id;
	// Common fields
	private String branch;
	private String branchCode;
	private String createdBy;
	private String updatedBy;
	private boolean active;
	private boolean cancel;
	private String cancelRemarks;
	private String finYear;
	private Long orgId;
	// IRN fields
	private String vohNo;
	private LocalDate vohDate;
	private String partyName;
	private String partyCode;
	private String supRefNo;
	private LocalDate supRefDate;
	private String partyType;
	private String product;
	private int creditDays;
	private LocalDate dueDate;
	private String state;
	private String city;
	private String officeType;
	private String currency;
	private String status;
	private String originBill;
	private String remarks;
	private String address;
	private String shipRefNo;
	private int pincode;
	private String gstin;
	private String gstType;
	private String billingMonth;
	private String otherInfo;
	private String salesType;
	private String creditRemarks;
	private String charges;
	// SummaryFields
	private BigDecimal totChargesBillCurrAmt;
	private BigDecimal totChargesLCAmt;
	private BigDecimal totGrossBillAmt;
	private BigDecimal totGrossLCAmt;
	private BigDecimal netBillCurrAmt;
	private BigDecimal netLCAmt;
	private String amtInWords;
	private BigDecimal exRate;
	private BigDecimal totTaxAmt;
	private BigDecimal summaryExRate;
	private LocalDate currentDate;
	private Long currentDateValue;
	private Long roundOff;

	List<IrnCreditChargesDTO> irnCreditChargeDTO;

	List<IrnCreditGstDTO> irnCreditGstDTO;

}
