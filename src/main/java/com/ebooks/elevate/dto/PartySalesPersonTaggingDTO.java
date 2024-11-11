package com.ebooks.elevate.dto;

import java.time.LocalDate;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartySalesPersonTaggingDTO {
	private String salesPerson;

	private String empCode;

	private String salesBranch;

	private LocalDate effectiveFrom;

	private LocalDate effectiveTill;
}


