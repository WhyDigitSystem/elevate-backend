package com.ebooks.elevate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.GlobalParameterDTO;
import com.ebooks.elevate.entity.ClientCompanyVO;
import com.ebooks.elevate.entity.FinancialYearVO;
import com.ebooks.elevate.entity.GlobalParameterVO;
import com.ebooks.elevate.entity.MonthCloseVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.ClientCompanyRepo;
import com.ebooks.elevate.repo.ClientRepo;
import com.ebooks.elevate.repo.FinancialYearRepo;
import com.ebooks.elevate.repo.GlobalParameterRepo;
import com.ebooks.elevate.repo.MonthCloseRepo;
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
	FinancialYearRepo financialYearRepo;

	@Autowired
	UserBranchAccessRepo userBranchAccessRepo;

	@Autowired
	FinancialYearRepo financialRepo;

	@Autowired
	ClientRepo clientRepo;
	
	@Autowired
	ClientCompanyRepo clientCompanyRepo;
	
	@Autowired
	MonthCloseRepo monthCloseRepo;

	// Global Parametre

//	@Override
//	public Set<Object[]> getWarehouseNameByOrgIdAndBranchAndClient(Long orgid, String branch, String client) {
//		return globalParameterRepo.findWarehouseNameByOrgIdAndBranchAndClient(orgid, branch, client);
//	}

//	@Override
//	public Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(String username) {
//
//		return globalParameterRepo.findGlobalParamByUserName(username);
//	}

	// get access Branch
//	@Override
//	public Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid, String userName) {
//
//		return userBranchAccessRepo.findGlobalParametersBranchByUserName(orgid, userName);
//	}

	@Override
	public Map<String, Object> updateCreateGlobalparam(@Valid GlobalParameterDTO globalParameterDTO)
	        throws ApplicationException {

	    String message;
	    GlobalParameterVO globalParameterVO;
	    
	    

	    // Check if a record with the same UserId exists
	    GlobalParameterVO existingRecord = globalParameterRepo.findByUserid(globalParameterDTO.getUserId());
	    
	    ClientCompanyVO clientCompanyVO= clientCompanyRepo.findByClientCode(globalParameterDTO.getClientCode()); 
	    
	    String previousYear=null;
		if(clientCompanyVO.getClientYear().equals("FY"))
		{
			FinancialYearVO financialYearVO= financialYearRepo.findByOrgIdAndFinYearIdentifierAndYearType(clientCompanyVO.getOrgId(),globalParameterDTO.getFinYear(),clientCompanyVO.getClientYear());
			int preYear=financialYearVO.getFinYear()-1;
			FinancialYearVO financialYearVO2=financialYearRepo.findByOrgIdAndFinYearAndYearType(clientCompanyVO.getOrgId(),preYear,clientCompanyVO.getClientYear());
			previousYear=financialYearVO2.getFinYearIdentifier();
			
		}
		else if(clientCompanyVO.getClientYear().equals("CY"))
		{
			FinancialYearVO financialYearVO= financialYearRepo.findByOrgIdAndFinYearIdentifierAndYearType(clientCompanyVO.getOrgId(),globalParameterDTO.getFinYear(),clientCompanyVO.getClientYear());
			int preYear=financialYearVO.getFinYear()-1;
			FinancialYearVO financialYearVO2=financialYearRepo.findByOrgIdAndFinYearAndYearType(clientCompanyVO.getOrgId(),preYear,clientCompanyVO.getClientYear());
			previousYear=financialYearVO2.getFinYearIdentifier();
		}

	    if (existingRecord != null) {
	        // Update the existing record
	        existingRecord.setClientCode(globalParameterDTO.getClientCode());
	        existingRecord.setClientName(globalParameterDTO.getClientName());
	        existingRecord.setFinYear(globalParameterDTO.getFinYear());
	        existingRecord.setMonth(globalParameterDTO.getMonth());
	        existingRecord.setClientYear(clientCompanyVO.getClientYear());
	        existingRecord.setPy(previousYear);
	        Optional<MonthCloseVO> record = monthCloseRepo
	                .findByClientCodeAndFinYearAndMonth(globalParameterDTO.getClientCode(), globalParameterDTO.getFinYear(), globalParameterDTO.getMonth());
	        if (record.isPresent() && "CLOSED".equalsIgnoreCase(record.get().getStatus())) {
	        	existingRecord.setMonthClose("CLOSED");
	        }
	        else {
	        	existingRecord.setMonthClose("OPEN");
	        }
	        globalParameterVO = globalParameterRepo.save(existingRecord);
	        message = "GlobalParameter Updation Successfully";
	    } else {
	        // Create a new record
	        globalParameterVO = new GlobalParameterVO();
	        globalParameterVO.setFinYear(globalParameterDTO.getFinYear());
	        globalParameterVO.setClientCode(globalParameterDTO.getClientCode());
	        globalParameterVO.setUserid(globalParameterDTO.getUserId());
	        globalParameterVO.setMonth(globalParameterDTO.getMonth());
	        globalParameterVO.setClientYear(globalParameterDTO.getClientYear());
	        globalParameterVO.setClientName(globalParameterDTO.getClientName());
	        globalParameterVO.setPy(previousYear);
	        Optional<MonthCloseVO> record = monthCloseRepo
	                .findByClientCodeAndFinYearAndMonth(globalParameterDTO.getClientCode(), globalParameterDTO.getFinYear(), globalParameterDTO.getMonth());
	        if (record.isPresent() && "CLOSED".equalsIgnoreCase(record.get().getStatus())) {
	        	existingRecord.setMonthClose("CLOSED");
	        }
	        else {
	        	existingRecord.setMonthClose("OPEN");
	        }
	        globalParameterVO = globalParameterRepo.save(globalParameterVO);
	        message = "GlobalParameter Creation Successfully";
	    }

	    // Prepare the response
	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("globalParameterVO", globalParameterVO);

	    return response;
	}

   
	

	@Override
	public List<Map<String, Object>> getClientCodeForGlopalParam(String userName) {
		Set<Object[]> getClientDts = clientRepo.getClientCode(userName);
		return getClientCodeForGlopa(getClientDts);
	}

	private List<Map<String, Object>> getClientCodeForGlopa(Set<Object[]> getFullGrid) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getFullGrid) {
			Map<String, Object> map = new HashMap<>();
			map.put("clientCode", ch[0] != null ? ch[0].toString() : ""); // Map clientCode
			map.put("clientName", ch[1] != null ? ch[1].toString() : ""); // Map clientName
			List1.add(map); // Add the map to the list
		}
		return List1;
	}  
 
	@Override
	public Optional<GlobalParameterVO> getGlobalparamByUserId(Long userId) {

		return globalParameterRepo.getGlobalParam(userId);
	}  

}