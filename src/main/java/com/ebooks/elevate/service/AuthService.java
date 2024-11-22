package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.ChangePasswordFormDTO;
import com.ebooks.elevate.dto.LoginFormDTO;
import com.ebooks.elevate.dto.RefreshTokenDTO;
import com.ebooks.elevate.dto.ResetPasswordFormDTO;
import com.ebooks.elevate.dto.ResponsibilityDTO;
import com.ebooks.elevate.dto.RolesDTO;
import com.ebooks.elevate.dto.SignUpFormDTO;
import com.ebooks.elevate.dto.UserResponseDTO;
import com.ebooks.elevate.entity.ResponsibilityVO;
import com.ebooks.elevate.entity.RolesVO;
import com.ebooks.elevate.entity.UserVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface AuthService {

	public void signup(SignUpFormDTO signUpRequest);

	public UserResponseDTO login(LoginFormDTO loginRequest, HttpServletRequest request) throws ApplicationException;

	public void logout(String userName);

	public void changePassword(ChangePasswordFormDTO changePasswordRequest);

	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest);

	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException;
	
	//Responsibility
	
	List<Map<String, Object>> getResponsibilityForRolesByOrgId();
	
	public List<ResponsibilityVO> getAllResponsibility();

	public List<ResponsibilityVO> getAllActiveResponsibility();
	
	ResponsibilityVO getResponsibilityById(Long id) throws ApplicationException;
	
	Map<String, Object> createUpdateResponsibilities(ResponsibilityDTO responsibilityDTO) throws ApplicationException;
	
	//ROLES
	
	Map<String, Object> createUpdateRoles(RolesDTO rolesDTO) throws ApplicationException;
	
	public List<RolesVO> getAllRoles();

	public List<RolesVO> getAllActiveRoles();
	
	RolesVO getRolesById(Long id) throws ApplicationException;
	
	//USERS
	
	List<UserVO>getAllUsersByOrgId(Long orgId);
	
	public UserVO getUserById(Long userId);

	public UserVO getUserByUserName(String userName);

	

}
