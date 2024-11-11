package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartCostCenterDTO {
	private Long id;
	private String costCenterCode;
	private String costCenterName;
	private BigDecimal credit;
	private BigDecimal debit;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String createdBy;
	private boolean active;
	private boolean cancel;
	private String cancelRemarks;
	private String finYear;

}
