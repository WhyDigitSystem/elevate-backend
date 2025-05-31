package com.ebooks.elevate.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientSegmentDTO {
	
	private String unit;
	private String segment;
	private boolean active;

}
