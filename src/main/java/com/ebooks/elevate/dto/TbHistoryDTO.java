package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbHistoryDTO {
	
	
	private Long orgId;
	private String clientCode;
	private String client;
	private String month;
	private String year;
	private String createdBy;
	private String remarks;

}
