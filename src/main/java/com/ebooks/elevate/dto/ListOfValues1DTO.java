package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListOfValues1DTO {
	private Long id;
	private Long sNo;
	private String valueCode;
	private String valueDescription;
	private boolean active;
}
