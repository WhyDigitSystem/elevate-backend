package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconcileCashDTO {
	private Long id;
	private String cashAccount;
	private BigDecimal balanceAsPerBooks;
	private int dn1;
	private int dn2;
	private int dn3;
	private int dn4;
	private int dn5;
	private int dn6;
	private int dn7;
	private int dn8;
	private String remarks;

	private Long orgId;
	private String branch;
	private boolean active;
	private String branchCode;
	private String createdBy;
	private String finYear;
	private String ipNo;
	private String latitude;
}
