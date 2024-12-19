package com.ebooks.elevate.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CCoaDTO {

	private Long id;

	private String accountName;

	private String accountCode;

	private String createdBy;

	private String currency;

	private Long orgId;

	private boolean active;

	private String clientCode;

	private String clientId;

	public String clientName;

}
