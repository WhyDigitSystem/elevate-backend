package com.ebooks.elevate.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalParameterDTO {

	private Long userId;
	private String finYear;
	private String clientCode;
	private String clientName;
	private String month;
	public String clientYear;

}