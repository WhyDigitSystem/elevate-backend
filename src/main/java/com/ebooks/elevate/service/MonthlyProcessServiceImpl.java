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
		if (ObjectUtils.isNotEmpty(monthlyProcessDTO.getId())) {
			monthlyProcessVO = monthlyProcessRepo.findById(monthlyProcessDTO.getId())
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
			        vo.setElglCode(dto.getElglCode());
			        vo.setElglLedger(dto.getElglLedger());
			        vo.setNatureOfAccount(dto.getNatureOfAccount());
			        vo.setSegment(dto.getSegment());
			        vo.setClosingBalanceCurrentMonth(dto.getClosingBalanceCurrentMonth());
			        vo.setClosingBalancePreviousMonth(dto.getClosingBalancePreviousMonth());
			        vo.setActualCurrentMonth(dto.getClosingBalanceCurrentMonth().subtract(dto.getClosingBalancePreviousMonth()));
			        vo.setBudget(dto.getBudget());
			        vo.setPyActual(dto.getPyActual());
			        vo.setMismatch(dto.getMismatch());
			        vo.setYear(monthlyProcessDTO.getYear());
			        vo.setMonth(monthlyProcessDTO.getMonth());
			        vo.setClient(monthlyProcessDTO.getClient());
			        vo.setClientCode(monthlyProcessDTO.getClientCode());
			        vo.setApproveStatus(dto.isApproveStatus());
			        vo.setProvision(dto.getProvision());
			        vo.setMonthlyProcessVO(monthlyProcessVO);
			        if(dto.isApproveStatus())
			        {
			        	vo.setFinalActual((dto.getClosingBalanceCurrentMonth().subtract(dto.getClosingBalancePreviousMonth())).add(dto.getProvision()));
			        }
			        else
			        {
			        	vo.setFinalActual(BigDecimal.ZERO);
			        }
			        return vo;
			    })
			    .collect(Collectors.toList());
		
		monthlyProcessVO.setMonthlyProcessDetailsVO(monthlyProcessDetailsVOs);
	}

	@Override
	public List<MonthlyProcessVO> getAllMonthlyProcessByClientCode(Long orgId, String clientCode) {
		
		List<MonthlyProcessVO> monthlyProcessVO= monthlyProcessRepo.findByOrgIdAndClientCode(orgId,clientCode);
		return monthlyProcessVO;
		
	}

	@Override
	public MonthlyProcessVO getAllMonthlyProcessById(Long id) {
		MonthlyProcessVO monthlyProcessVO= monthlyProcessRepo.findById(id).get();
		return monthlyProcessVO;
	}

}
