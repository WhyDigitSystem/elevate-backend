package com.ebooks.elevate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
	private Long id;
	private String client;
	private String clientCode;
	private String clientType;
	private String clientMail;
	private String phoneNo;
	private String contactPerson;
	private String userName;
	private String password;
	private boolean active;
	private boolean cancel;
	private Long orgId;
	private String createdBy;
	private Role role;
	private String clientAdminName;

}
