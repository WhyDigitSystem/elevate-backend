package com.ebooks.elevate.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {

	private Long id;
	private Long orgId;
	private String accountGroup;
	private String branchLocation;
	private String accountType;
	private String groupName;
	private String accountCode;
	private String accountName;
	private String currency;
	private String category;
	private boolean block;
	private boolean isItcApplicable;
	private boolean active;
	private String companyName;
	private String aType;
	private String aCategory;
	private String aCurrency;
	private String transId;
	private String aCatCode;
	private String bp;
	private String groupCode;
	private String gst;
	private String createdBy;
	

	private List<Account1DTO> account1DTO;

	private List<Account2DTO> account2DTO;

	private List<Account3DTO> account3DTO;

}
