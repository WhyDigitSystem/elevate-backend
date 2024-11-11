package com.ebooks.elevate.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TdsMaster2DTO {
	private Long Id;

	private LocalDate fromDate;
	private LocalDate toDate;
	private float tcsPercentage;
	private float surPercentage;
	private float edcessPercentage;

}
