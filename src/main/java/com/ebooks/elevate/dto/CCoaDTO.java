package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CCoaDTO {

	private Long id;

	private String type;

	private String groupName;

	private String accountGroupName;

	private String natureOfAccount;

	private String accountCode;

	private String createdBy;

	private boolean interBranchAc;

	private boolean controllAc;

	private String currency;

	private boolean active;

	private String clientName;

	private String clientId;

}
