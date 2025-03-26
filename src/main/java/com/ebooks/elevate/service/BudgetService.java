package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.BudgetDTO;
import com.ebooks.elevate.dto.PreviousYearDTO;

@Service
public interface BudgetService {
	
	List<Map<String,Object>>getSubGroupDetails(Long orgId,String mainGroup);

	
	Map<String,Object>createUpdateBudget(List<BudgetDTO> budgetDTO);

	List<Map<String, Object>> getGroupLedgersDetails(Long orgId, String year, String clientCode, String mainGroup,
			String subGroupCode);


	Map<String, Object> createUpdatePreviousYear(List<PreviousYearDTO> budgetDTO);


	List<Map<String, Object>> getPreviousYearGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup, String subGroupCode);

}
