package com.ebooks.elevate.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCompanyDTO {

	private Long id;
	private Long orgId;
	private String clientName;
	private String clientCode;
	private String phone;
	private String email;
	private String webSite;
	private String createdBy;
	private boolean active;
	private String bussinessType;
	private String turnOver;
	private String levelOfService;
	private String repPerson;
	
	private String userName;
	private String password;

	
}
