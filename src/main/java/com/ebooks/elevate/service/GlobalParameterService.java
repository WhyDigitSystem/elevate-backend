package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.GlobalParameterDTO;
import com.ebooks.elevate.entity.GlobalParameterVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface GlobalParameterService {
	// Global Parameter
	//Set<Object[]> getWarehouseNameByOrgIdAndBranchAndClient(Long orgid, String branch, String client);

	//Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(String userId);

	// to getAcces Global Param Dteails

	//Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid, String userName);

	Map<String, Object> updateCreateGlobalparam(@Valid GlobalParameterDTO globalParameterDTO) throws ApplicationException;

	List<Map<String, Object>> getClientCodeForGlopalParam(String userName);

	Optional<GlobalParameterVO> getGlobalparamByUserId(Long userId);


}