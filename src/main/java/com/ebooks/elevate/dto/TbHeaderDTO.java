package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbHeaderDTO {

	private Long id;
	private String clientCode;
	private String month;
	private String finYear;
	private String createdBy;
	
}
