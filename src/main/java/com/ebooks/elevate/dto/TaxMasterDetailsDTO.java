package com.ebooks.elevate.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxMasterDetailsDTO {

	private Long id;
	private String gst;
	private String gstType;
	private String percentage;
	private String taxType;
	private LocalDate fromDate;
	private String toDate;
	private String revenueLedger;
	private String costLedger;
	private boolean active;
}
