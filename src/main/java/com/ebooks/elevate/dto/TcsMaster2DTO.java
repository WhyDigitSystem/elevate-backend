package com.ebooks.elevate.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TcsMaster2DTO {
	private Long Id;
	private long SerialNo;
	private LocalDate fromDate;
	private LocalDate toDate;
	private float tcsPercentage;
}
