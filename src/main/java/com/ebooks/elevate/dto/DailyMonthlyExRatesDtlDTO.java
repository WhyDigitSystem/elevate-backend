package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyMonthlyExRatesDtlDTO {
	private Long id;
	private String currency;
	private String currencyDescripition;
	private float sellingExRate;
	private float buyingExrate;

}
