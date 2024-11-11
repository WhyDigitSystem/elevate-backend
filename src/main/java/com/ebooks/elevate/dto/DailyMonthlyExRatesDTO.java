package com.ebooks.elevate.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyMonthlyExRatesDTO {
	private Long id;
	private LocalDate date;
	private String month;
	private boolean active;
	private Long orgId;
	private String userId;	
	private String createdBy;

	List<DailyMonthlyExRatesDtlDTO> dailyMonthlyExRatesDtlDTO;
}
