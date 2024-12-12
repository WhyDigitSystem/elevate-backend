package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class LedgerMappingDTO {

	private Long id;

	private String clientCoa;
	private String coa;
	private String createdBy;
	private boolean active;
	private String clientCode;
	
}
