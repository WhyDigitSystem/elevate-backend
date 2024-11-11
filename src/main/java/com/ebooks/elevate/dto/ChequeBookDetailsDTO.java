package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChequeBookDetailsDTO {

	private Long id;
	private Long chequeNo;
	private String status;
	private String cancelled;
	
}
