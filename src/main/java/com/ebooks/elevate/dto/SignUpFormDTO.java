/*
 * ========================================================================
 * This file is the intellectual property of GSM Outdoors.it
 * may not be copied in whole or in part without the express written
 * permission of GSM Outdoors.
 * ========================================================================
 * Copyrights(c) 2023 GSM Outdoors. All rights reserved.
 * ========================================================================
 */
package com.ebooks.elevate.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpFormDTO {

	private Long id;
	private String userName;
	private String password;
	private String employeeCode;
	private String employeeName;
	private String nickName;
	private String email;
	private Long orgId;
	private String mobileNo;
	private String userType;
	private boolean isActive;
	private boolean allIndiaAcces;
	private List<UserLoginRoleAccessDTO> roleAccessDTO;
	private List<UserLoginBranchAccessDTO> branchAccessDTOList;
	private List<UserLoginClientAccessDTO> clientAccessDTOList;

}
