package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SacCodeDTO {
	private Long id;
	private Long orgId;
	private String serviceAccountCode;
	private String sacDescription;
	private String product;
	private String createdBy;
	private String cancelRemarks;
	private boolean cancel;
	private boolean active;
}
