package com.ebooks.elevate.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.MonthlyProcessDTO;
import com.ebooks.elevate.entity.MonthlyProcessDetailsVO;
import com.ebooks.elevate.entity.MonthlyProcessVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.MonthlyProcessDetailsRepo;
import com.ebooks.elevate.repo.MonthlyProcessRepo;

@Service
public class MonthlyProcessServiceImpl implements MonthlyProcessService{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MonthlyProcessServiceImpl.class);

	@Autowired
	MonthlyProcessRepo monthlyProcessRepo;
	
	@Autowired
	MonthlyProcessDetailsRepo monthlyProcessDetailsRepo;
	
	@Override
	public Map<String, Object> createUpdateMonthlyProcess(MonthlyProcessDTO monthlyProcessDTO)
			throws ApplicationException {
		MonthlyProcessVO monthlyProcessVO = new MonthlyProcessVO();
		String message;
		MonthlyProcessVO monthly = monthlyProcessRepo.getOrgIdAndClientCodeAndYearAndMonthAndMainGroupAndSubGroupCode(monthlyProcessDTO.getOrgId(),monthlyProcessDTO.getClientCode(),monthlyProcessDTO.getYear(),monthlyProcessDTO.getMonth(),monthlyProcessDTO.getMainGroup(),monthlyProcessDTO.getSubGroupCode());
		if (monthly!=null) {
			monthlyProcessVO = monthlyProcessRepo.findByOrgIdAndClientCodeAndYearAndMonthAndMainGroupAndSubGroupCode(monthlyProcessDTO.getOrgId(),monthlyProcessDTO.getClientCode(),monthlyProcessDTO.getYear(),monthlyProcessDTO.getMonth(),monthlyProcessDTO.getMainGroup(),monthlyProcessDTO.getSubGroupCode())
					.orElseThrow(() -> new ApplicationException("Monthly Process not found"));

			monthlyProcessVO.setUpdatedBy(monthlyProcessDTO.getCreatedBy());
			createUpdateMonthlyProcessVOByMonthlyProcessDTO(monthlyProcessDTO, monthlyProcessVO);
			message = "Monthly Process Updated Successfully";
		} else {
			monthlyProcessVO.setCreatedBy(monthlyProcessDTO.getCreatedBy());
			monthlyProcessVO.setUpdatedBy(monthlyProcessDTO.getCreatedBy());
			createUpdateMonthlyProcessVOByMonthlyProcessDTO(monthlyProcessDTO, monthlyProcessVO);
			message = "Monthly Process Created Successfully";
		}

		monthlyProcessRepo.save(monthlyProcessVO);
		Map<String, Object> response = new HashMap<>();
		response.put("monthlyProcessVO", monthlyProcessVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateMonthlyProcessVOByMonthlyProcessDTO(MonthlyProcessDTO monthlyProcessDTO,
			MonthlyProcessVO monthlyProcessVO) {
		
		monthlyProcessVO.setOrgId(monthlyProcessDTO.getOrgId());
		monthlyProcessVO.setMainGroup(monthlyProcessDTO.getMainGroup());
		monthlyProcessVO.setSubGroup(monthlyProcessDTO.getSubGroup());
		monthlyProcessVO.setSubGroupCode(monthlyProcessDTO.getSubGroupCode());
		monthlyProcessVO.setYear(monthlyProcessDTO.getYear());
		monthlyProcessVO.setMonth(monthlyProcessDTO.getMonth());
		monthlyProcessVO.setClient(monthlyProcessDTO.getClient());
		monthlyProcessVO.setClientCode(monthlyProcessDTO.getClientCode());
		
		if (ObjectUtils.isNotEmpty(monthlyProcessVO.getId())) {
			List<MonthlyProcessDetailsVO> monthlyProcessDetailsVOs1 = monthlyProcessDetailsRepo.findByMonthlyProcessVO(monthlyProcessVO);
			monthlyProcessDetailsRepo.deleteAll(monthlyProcessDetailsVOs1);
		}
		
		List<MonthlyProcessDetailsVO> monthlyProcessDetailsVOs = monthlyProcessDTO.getMonthlyProcessDetailsDTO().stream()
			    .map(dto -> {
			        MonthlyProcessDetailsVO vo = new MonthlyProcessDetailsVO();
			        vo.setElGlCode(dto.getElGlCode());
			        vo.setElGl(dto.getElGl());
			        vo.setNatureOfAccount(dto.getNatureOfAccount());
			        vo.setSegment(monthlyProcessDTO.getSubGroup());
			        vo.setSegmentCode(monthlyProcessDTO.getSubGroupCode());
			        vo.setMainGroup(monthlyProcessDTO.getMainGroup());
			        vo.setCurrentMonthDebit(dto.getCurrentMonthDebit());
			        vo.setOrgId(monthlyProcessDTO.getOrgId());
			        vo.setCurrentMonthCredit(dto.getCurrentMonthCredit());
			        vo.setCurrentMonthClosing(dto.getCurrentMonthDebit().subtract(dto.getCurrentMonthCredit()));
			        vo.setPrevioustMonthDebit(dto.getPrevioustMonthDebit());
			        vo.setPrevioustMonthCredit(dto.getPrevioustMonthCredit());
			        
			        BigDecimal currentMonthClosing=dto.getCurrentMonthDebit().subtract(dto.getCurrentMonthCredit());
			        BigDecimal preMonthClosing=dto.getPrevioustMonthDebit().subtract(dto.getPrevioustMonthCredit()); 
			        
			        vo.setPrevioustMonthClosing(dto.getPrevioustMonthDebit().subtract(dto.getPrevioustMonthCredit()));
			        vo.setForTheMonthActDebit(dto.getCurrentMonthDebit().subtract(dto.getPrevioustMonthDebit()));
			        vo.setForTheMonthActCredit(dto.getCurrentMonthCredit().subtract(dto.getPrevioustMonthCredit()));
			        BigDecimal forMonthActDebit=dto.getCurrentMonthDebit().subtract(dto.getPrevioustMonthDebit());
			        BigDecimal forMonthActCredit=dto.getCurrentMonthCredit().subtract(dto.getPrevioustMonthCredit());
			        vo.setForTheMonthActClosing(forMonthActDebit.subtract(forMonthActCredit));
			        
			        vo.setProvisionDebit(dto.getProvisionDebit());
			        vo.setProvisionCredit(dto.getProvisionCredit());
			        vo.setProvisionClosing(dto.getProvisionDebit().subtract(dto.getProvisionCredit()));
			        
			        vo.setForTheMonthDebit(forMonthActDebit.add(dto.getProvisionDebit()));
			        vo.setForTheMonthCredit(forMonthActCredit.add(dto.getProvisionCredit()));
			        BigDecimal forTheMonthDebit=forMonthActDebit.add(dto.getProvisionDebit());
			        BigDecimal forTheMonthCredit=forMonthActCredit.add(dto.getProvisionCredit());
			        vo.setForTheMonthClosing(forTheMonthDebit.subtract(forTheMonthCredit));
			        vo.setMismatch(dto.isMismatch());
			        vo.setProvisionRemarks(dto.getProvisionRemarks());
			        vo.setYear(monthlyProcessDTO.getYear());
			        vo.setMonth(monthlyProcessDTO.getMonth());
			        vo.setClient(monthlyProcessDTO.getClient());
			        vo.setClientCode(monthlyProcessDTO.getClientCode());
			        vo.setApproveStatus(dto.isApproveStatus());
			        vo.setMonthlyProcessVO(monthlyProcessVO);
			        if(dto.isApproveStatus())
			        {
			        	vo.setClosingBalance(forTheMonthDebit.subtract(forTheMonthCredit));
			        }
			        else
			        {
			        	vo.setClosingBalance(BigDecimal.ZERO);
			        }
			        return vo;
			    })
			    .collect(Collectors.toList());
		
		monthlyProcessVO.setMonthlyProcessDetailsVO(monthlyProcessDetailsVOs);
	}

	@Override
	public List<MonthlyProcessVO> getAllMonthlyProcessByClientCode(Long orgId, String clientCode,String mainGroup,String subGroupCode,String finYear) {
		
		List<MonthlyProcessVO> monthlyProcessVO= monthlyProcessRepo.findByOrgIdAndClientCodeAndMainGroupAndSubGroupCodeAndYear(orgId,clientCode,mainGroup,subGroupCode,finYear);
		return monthlyProcessVO;
		
	}

	@Override
	public MonthlyProcessVO getAllMonthlyProcessById(Long id) {
		MonthlyProcessVO monthlyProcessVO= monthlyProcessRepo.findById(id).get();
		return monthlyProcessVO;
	}

}
