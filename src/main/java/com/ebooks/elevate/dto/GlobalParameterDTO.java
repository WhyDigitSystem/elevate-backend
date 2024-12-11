package com.ebooks.elevate.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalParameterDTO {

	private Long id;
	private Long userId;
	private String finYear;
	private String branch;
	private String branchCode;
	private Long orgId;
	private Long clientId;

}