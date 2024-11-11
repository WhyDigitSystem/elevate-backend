package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupLedgerDTO {

	private Long id;
	private Long orgId;
	private String groupName;
	private String gstTaxFlag;
	private String coaList;
	private String accountGroupName;
	private String type;
	private boolean interBranchAc;
	private boolean controllAc;
	private String category;
	private String currency;
	private String createdBy;
	private boolean active;
}
