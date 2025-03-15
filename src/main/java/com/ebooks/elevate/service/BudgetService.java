package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface BudgetService {
	
	List<Map<String,Object>>getSubGroupDetails(Long orgId,String mainGroup);

	List<Map<String, Object>> getGroupLedgersDetails(Long orgId, String mainGroup, String subGroupCode);

}
