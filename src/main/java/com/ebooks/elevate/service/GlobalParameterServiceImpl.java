package com.ebooks.elevate.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.GlobalParameterDTO;
import com.ebooks.elevate.entity.GlobalParameterVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.FinancialYearRepo;
import com.ebooks.elevate.repo.GlobalParameterRepo;
import com.ebooks.elevate.repo.UserBranchAccessRepo;
import com.ebooks.elevate.repo.UserRepo;

@Service
public class GlobalParameterServiceImpl implements GlobalParameterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(GlobalParameterServiceImpl.class);

	@Autowired
	GlobalParameterRepo globalParameterRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserBranchAccessRepo userBranchAccessRepo;

	@Autowired
	FinancialYearRepo financialRepo;

	// Global Parametre

	@Override
	public Set<Object[]> getWarehouseNameByOrgIdAndBranchAndClient(Long orgid, String branch, String client) {
		return globalParameterRepo.findWarehouseNameByOrgIdAndBranchAndClient(orgid, branch, client);
	}

	@Override
	public Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid, String username) {

		return globalParameterRepo.findGlobalParamByOrgIdAndUserName(orgid, username);
	}

	// get access Branch
	@Override
	public Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid, String userName) {

		return userBranchAccessRepo.findGlobalParametersBranchByUserName(orgid, userName);
	}

	@Override
	public Map<String, Object> updateCreateGlobalparam(@Valid GlobalParameterDTO globalParameterDTO)
			throws ApplicationException {

		GlobalParameterVO globalParameterVO;
		String message = null;

		if (ObjectUtils.isEmpty(globalParameterDTO.getId())) {

			globalParameterVO = new GlobalParameterVO();

			message = "GlobalParameter Creation SuccessFully";
		}

		else {
			globalParameterVO = globalParameterRepo.findById(globalParameterDTO.getId())
					.orElseThrow(() -> new ApplicationException(
							"Global Parameter Not Found with id: " + globalParameterDTO.getId()));

			message = "GlobalParameter Updation Successfully";
		}

		globalParameterVO = getGlobalParameterVOFromglobalParameterDTO(globalParameterVO, globalParameterDTO);

		globalParameterRepo.save(globalParameterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("globalParameterVO", globalParameterVO);
		return response;

	}

	private GlobalParameterVO getGlobalParameterVOFromglobalParameterDTO(GlobalParameterVO globalParameterVO,
			@Valid GlobalParameterDTO globalParameterDTO) {

		globalParameterVO.setBranch(globalParameterDTO.getBranch());
		globalParameterVO.setBranchcode(globalParameterDTO.getBranchCode());
		globalParameterVO.setFinYear(globalParameterDTO.getFinYear());
		globalParameterVO.setOrgId(globalParameterDTO.getOrgId());
		globalParameterVO.setClientId(globalParameterDTO.getClientId());
		globalParameterVO.setUserid(globalParameterDTO.getUserId());

		return globalParameterVO;
	}

}