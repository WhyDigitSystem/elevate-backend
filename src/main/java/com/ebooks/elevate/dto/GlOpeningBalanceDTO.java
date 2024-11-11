package com.ebooks.elevate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlOpeningBalanceDTO {
	private Long id;
	private String branch;
	private String currency;
	private BigDecimal exRate;
	private String refNo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate refDate;
	private String suppRefNo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate suppRefDate;
	private String remarks;
	private Long orgId;
	private String createdBy;
	private String finYear;
	private String branchCode;
	List<ParticularsGlOpeningBalanceDTO>particularsGlOpeningBalanceDTO;
}
