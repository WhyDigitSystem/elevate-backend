package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetTaxRateDTO {

	private Long Id;
	private Long orgId;
	private String chapter;
	private String subChapter;
	private String hsnCode;
	private String branch;
	private float newRate;
	private String excepmted;
	private boolean active;
	private String createdBy;
}
