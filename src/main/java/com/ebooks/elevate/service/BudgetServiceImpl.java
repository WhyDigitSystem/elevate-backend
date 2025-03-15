package com.ebooks.elevate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks.elevate.repo.GroupLedgersRepo;
import com.ebooks.elevate.repo.GroupMappingRepo;
import com.ebooks.elevate.repo.SubGroupDetailsRepo;

@Service
public class BudgetServiceImpl implements BudgetService {
	public static final Logger LOGGER = LoggerFactory.getLogger(BudgetServiceImpl.class);
	
	@Autowired
	GroupMappingRepo groupMappingRepo;
	
	@Autowired
	SubGroupDetailsRepo subGroupDetailsRepo;
	
	@Autowired
	GroupLedgersRepo groupLedgersRepo;

	@Override
	public List<Map<String, Object>> getSubGroupDetails(Long orgId, String mainGroup) {
		
		Set<Object[]>subGroupDetails=groupMappingRepo.getSubGroupDetails(orgId,mainGroup);
		return getSubGroupDetails(subGroupDetails);
	}

	private List<Map<String, Object>> getSubGroupDetails(Set<Object[]> subGroupDetails) {
		List<Map<String,Object>> subgroup= new ArrayList<>();
		for(Object[]sub:subGroupDetails)
		{
			Map<String,Object> mp= new HashMap<>();
			mp.put("subGroupName", sub[0] != null ? sub[0].toString() : "");
			mp.put("subGroupCode", sub[1] != null ? sub[1].toString() : "");
			subgroup.add(mp);
		}
		return subgroup;
	}
	
	@Override
	public List<Map<String, Object>> getGroupLedgersDetails(Long orgId, String mainGroup,String subGroupCode) {
		
		Set<Object[]>subGroupDetails=groupMappingRepo.getGroupLedgersDetails(orgId, mainGroup, subGroupCode);
		return getGroupLedgerDetails(subGroupDetails);
	}

	private List<Map<String, Object>> getGroupLedgerDetails(Set<Object[]> subGroupDetails) {
		List<Map<String,Object>> subgroup= new ArrayList<>();
		for(Object[]sub:subGroupDetails)
		{
			Map<String,Object> mp= new HashMap<>();
			mp.put("subGroupName", sub[0] != null ? sub[0].toString() : "");
			mp.put("subGroupCode", sub[1] != null ? sub[1].toString() : "");
			mp.put("natureOfAccount", sub[2] != null ? sub[2].toString() : "");
			subgroup.add(mp);
		}
		return subgroup;
	}

}
