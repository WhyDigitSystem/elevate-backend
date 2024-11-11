package com.ebooks.elevate.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyTdsExemptedDTO {
	private String tdsExempCerti;
    private BigDecimal value;
    private String finYear;
}
