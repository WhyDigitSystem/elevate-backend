package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticularsDebitNoteDTO {
	private Long id;
	private String tds;
	private String tdsPercent;
	private String section;
	private String totalTdsAmount;
	
}
