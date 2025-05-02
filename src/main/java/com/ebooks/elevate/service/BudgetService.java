package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.BudgetDTO;
import com.ebooks.elevate.dto.BudgetHeadCountDTO;
import com.ebooks.elevate.dto.OrderBookingDTO;
import com.ebooks.elevate.dto.PreviousYearDTO;
import com.ebooks.elevate.dto.PyHeadCountDTO;
import com.ebooks.elevate.entity.PYHeadCountVO;

@Service
public interface BudgetService {
	
	List<Map<String,Object>>getSubGroupDetails(Long orgId,String mainGroup);

	
	Map<String,Object>createUpdateBudget(List<BudgetDTO> budgetDTO);
	
	Map<String,Object>createUpdateBudgetOB(List<OrderBookingDTO> orderBookingDTO);

	List<Map<String, Object>> getGroupLedgersDetails(Long orgId, String year, String clientCode, String mainGroup,
			String subGroupCode);


	Map<String, Object> createUpdatePreviousYear(List<PreviousYearDTO> budgetDTO);
	
	


	List<Map<String, Object>> getPreviousYearGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup, String subGroupCode);


	List<Map<String, Object>> getActualGroupLedgersDetails(Long orgId, String year, String clientCode, String mainGroup,
			String subGroupCode);


	List<Map<String, Object>> getOrderBookingBudgetDetail(Long orgId, String year, String clientCode, String type);


	Map<String, Object> createUpdatePYActulaOB(List<OrderBookingDTO> orderBookingDTO);


	List<Map<String, Object>> getPYActualOBDetails(Long orgId, String year, String clientCode, String type);


	List<Map<String, Object>> getBudgetDetailsAutomatic(Long orgId, String year, String clientCode, String mainGroup);
	
	

	
	Map<String,Object>createUpdateBudgetHeadCount(List<BudgetHeadCountDTO> budgetHeadCountDTO);
	

	List<Map<String, Object>> getGroupLedgersDetailsForHeadCount(Long orgId, String year, String clientCode);


	Map<String, Object> createUpdatePreviousYearHeadCount(List<PyHeadCountDTO> pyHeadCountDTO);

}
