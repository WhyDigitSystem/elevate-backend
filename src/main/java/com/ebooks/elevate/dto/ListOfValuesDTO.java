package com.ebooks.elevate.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListOfValuesDTO {
	
	private Long id;
	private String name;
	private String createdBy;
	private Long orgId;
	
	private List<ListOfValuesDetailsDTO> listOfValuesDetailsDTO;

}
