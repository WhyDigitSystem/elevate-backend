package com.ebooks.elevate.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChequeBookDTO {

	private Long id;
	private String branch;
	private String chequeId;
	private String bank;
	private String checkPrefix;
	private String checkStartNo;
	private String noOfChequeLeaves;
	private Long orgId;
	private boolean active;
	private String createdBy;
	
	List<ChequeBookDetailsDTO> chequeBookDetailsDTO;
}
 