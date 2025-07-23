package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.BudgetACPDTO;
import com.ebooks.elevate.dto.BudgetDTO;
import com.ebooks.elevate.dto.BudgetHeadCountDTO;
import com.ebooks.elevate.dto.BudgetRatioAnalysisDTO;
import com.ebooks.elevate.dto.BudgetUnitWiseDTO;
import com.ebooks.elevate.dto.IncrementalProfitDTO;
import com.ebooks.elevate.dto.LoanOutstandingDTO;
import com.ebooks.elevate.dto.OrderBookingDTO;
import com.ebooks.elevate.dto.PreviousYearDTO;
import com.ebooks.elevate.dto.PyAdvancePaymentReceiptDTO;
import com.ebooks.elevate.dto.PyHeadCountDTO;
import com.ebooks.elevate.dto.SalesPurchaseDTO;
import com.ebooks.elevate.entity.BudgetLoansOutStandingVO;
import com.ebooks.elevate.entity.PyLoansOutStandingVO;

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


	List<Map<String, Object>> getBudgetDetailsAutomatic(Long orgId, String year, String clientCode, String mainGroup,String yearType);
	
	

	
	Map<String,Object>createUpdateBudgetHeadCount(List<BudgetHeadCountDTO> budgetHeadCountDTO);
	

	List<Map<String, Object>> getGroupLedgersDetailsForHeadCount(Long orgId, String year, String clientCode);


	Map<String, Object> createUpdatePreviousYearHeadCount(List<PyHeadCountDTO> pyHeadCountDTO);
	
	List<Map<String, Object>> getGroupLedgersDetailsPYForHeadCount(Long orgId, String year, String clientCode);
	
	Map<String,Object>createUpdateBudgetAccountPayable(List<BudgetACPDTO> budgetACPDTO);


	List<Map<String, Object>> getBudgetACPDetails(Long orgId, String year, String month, String clientCode,String type);
	
	Map<String,Object>createUpdatePYAccountPayable(List<BudgetACPDTO> budgetACPDTO);
	
	List<Map<String, Object>> getPYACPDetails(Long orgId, String year, String month, String clientCode,String type);
	
	
	Map<String,Object>createUpdateBudgetUnitWise(List<BudgetUnitWiseDTO> budgetUnitWiseDTO);


	List<Map<String, Object>> getUnitDetails(Long orgId, String clientCode);



	List<Map<String, Object>> getUnitLedgerDetails(Long orgId, String year, String clientCode, String mainGroup,
			String accountCode, String unit);

	List<Map<String, Object>> getSegmentDetails(Long orgId, String clientCode, String segmentType);
	
	
	Map<String,Object>createUpdatePYUnitWise(List<BudgetUnitWiseDTO> budgetUnitWiseDTO);
	
	List<Map<String, Object>> getPYUnitLedgerDetails(Long orgId, String year, String clientCode, String mainGroup,
			String accountCode, String unit);


	List<Map<String, Object>> getRatioAnalysisPYGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup, String subGroupCode);


	Map<String, Object> createUpdateBudgetRatioAnalysis(List<BudgetRatioAnalysisDTO> budgetRatioAnalysisDTO);


	List<Map<String, Object>> getRatioAnalysisBudgetGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup, String subGroupCode);


	Map<String, Object> createUpdatePYRatioAnalysis(List<BudgetRatioAnalysisDTO> budgetRatioAnalysisDTO);


	List<Map<String, Object>> getLedgerDetailsForPL(Long orgId, String mainGroupName);


	List<Map<String, Object>> getSubGroupDetailsForPL(Long orgId, String mainGroupName);



	List<Map<String, Object>> getLedgerDetailsForSubGroupPL(Long orgId, String mainGroupName, String subGroupName);


	List<Map<String, Object>> getPYDetailsAutomatic(Long orgId, String year, String clientCode, String mainGroup);


	Map<String, Object> createUpdateIncrementalProfitBudget(List<IncrementalProfitDTO> budgetDTO);


	Map<String, Object> createUpdateIncrementalProfitPY(List<IncrementalProfitDTO> budgetDTO);


	List<Map<String, Object>> getBudgetIncrementalGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup,String subGroup);


	List<Map<String, Object>> getPYIncrementalGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup,String subGroup);
	
	
	// Advance Payment
	
	Map<String, Object> createUpdateAdvancePaymentPY(List<PyAdvancePaymentReceiptDTO> advancePaymentReceiptDTO);


	List<Map<String, Object>> getAdvancePaymentReceiptDetails(Long orgId, String year, String clientCode, String type);

	List<BudgetLoansOutStandingVO> BudgetLoanOutStandingLedger(Long orgId, String year, String clientCode);

	Map<String, Object> createUpdateBudgetLoanOutStanding(List<LoanOutstandingDTO> loanOutstandingDTO);

	Map<String, Object> createUpdatePyLoanOutStanding(List<LoanOutstandingDTO> loanOutstandingDTO);

	List<PyLoansOutStandingVO> PyLoanOutStandingLedger(Long orgId, String year, String clientCode);
	
	
	Map<String, Object> createUpdateBudgetSalesPurchaseAnalysis(List<SalesPurchaseDTO> salesPurchaseDTO);


	Map<String, Object> createUpdatePySalesPurchaseAnalysis(List<SalesPurchaseDTO> salesPurchaseDTO);
	
	

}
