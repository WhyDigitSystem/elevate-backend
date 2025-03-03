package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCompanyReportAccessDTO {
	
	private String elCode;
	private String description;
	private boolean access;
}
