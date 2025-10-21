package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.MonthlyProcessDTO;
import com.ebooks.elevate.dto.TbHistoryDTO;
import com.ebooks.elevate.entity.MonthlyProcessVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface MonthlyProcessService {

	Map<String, Object> createUpdateMonthlyProcess(MonthlyProcessDTO monthlyProcessDTO) throws ApplicationException;
	
	List<MonthlyProcessVO>getAllMonthlyProcessByClientCode(Long orgId,String clientCode,String mainGroup,String subGroupCode, String finYear);

	MonthlyProcessVO getAllMonthlyProcessById(Long id);
	

	String createTrialBalanceRemoveDetails(TbHistoryDTO tbHistoryDTO) throws ApplicationException;
}
