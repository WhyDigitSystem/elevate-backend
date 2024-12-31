package com.ebooks.elevate.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginClientAccessDTO {

	private Long ID;
	private String clientCode;
    private String clientName;
    
}
