package com.ebooks.elevate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.BudgetDTO;
import com.ebooks.elevate.dto.OrderBookingDTO;
import com.ebooks.elevate.dto.PreviousYearDTO;
import com.ebooks.elevate.entity.BudgetVO;
import com.ebooks.elevate.entity.OrderBookingVO;
import com.ebooks.elevate.entity.PreviousYearActualVO;
import com.ebooks.elevate.repo.BudgetRepo;
import com.ebooks.elevate.repo.GroupLedgersRepo;
import com.ebooks.elevate.repo.GroupMappingRepo;
import com.ebooks.elevate.repo.OrderBookingRepo;
import com.ebooks.elevate.repo.PreviousYearActualRepo;
import com.ebooks.elevate.repo.SubGroupDetailsRepo;

@Service
public class BudgetServiceImpl implements BudgetService {
	public static final Logger LOGGER = LoggerFactory.getLogger(BudgetServiceImpl.class);

	@Autowired
	GroupMappingRepo groupMappingRepo;

	@Autowired
	QuaterMonthService quaterMonthService;

	@Autowired
	SubGroupDetailsRepo subGroupDetailsRepo;

	@Autowired
	GroupLedgersRepo groupLedgersRepo;

	@Autowired
	OrderBookingRepo orderBookingRepo;

	@Autowired
	BudgetRepo budgetRepo;

	@Autowired
	PreviousYearActualRepo previousYearActualRepo;

	@Override
	public List<Map<String, Object>> getSubGroupDetails(Long orgId, String mainGroup) {

		Set<Object[]> subGroupDetails = groupMappingRepo.getSubGroupDetails(orgId, mainGroup);
		return getSubGroupDetails(subGroupDetails);
	}

	private List<Map<String, Object>> getSubGroupDetails(Set<Object[]> subGroupDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : subGroupDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("subGroupName", sub[0] != null ? sub[0].toString() : "");
			mp.put("subGroupCode", sub[1] != null ? sub[1].toString() : null);
			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public List<Map<String, Object>> getGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup, String subGroupCode) {

		Set<Object[]> subGroupDetails = groupMappingRepo.getGroupLedgersDetails(orgId, year, clientCode, mainGroup,
				subGroupCode);
		return getGroupLedgerDetails(subGroupDetails);
	}

	private List<Map<String, Object>> getGroupLedgerDetails(Set<Object[]> subGroupDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : subGroupDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("subGroupName", sub[0] != null ? sub[0].toString() : "");
			mp.put("subGroupCode", sub[1] != null ? sub[1].toString() : "");
			mp.put("natureOfAccount", sub[2] != null ? sub[2].toString() : "");
			mp.put("month", sub[3] != null ? sub[3].toString() : "");
			mp.put("amount", sub[4] != null ? new BigDecimal(sub[4].toString()) : BigDecimal.ZERO);
			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public Map<String, Object> createUpdateBudget(List<BudgetDTO> budgetDTO) {
		BudgetVO budgetVO = new BudgetVO();
		for (BudgetDTO budgetDTO2 : budgetDTO) {
			budgetVO = budgetRepo.getBudgetDetails(budgetDTO2.getOrgId(), budgetDTO2.getClientCode(),
					budgetDTO2.getYear(), budgetDTO2.getMonth(), budgetDTO2.getMainGroup(),
					budgetDTO2.getSubGroupCode(), budgetDTO2.getAccountCode());
			if (budgetVO == null) {

				budgetVO = new BudgetVO();
				budgetVO.setOrgId(budgetDTO2.getOrgId());
				budgetVO.setClient(budgetDTO2.getClient());
				budgetVO.setClientCode(budgetDTO2.getClientCode());
				budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setAccountName(budgetDTO2.getAccountName());
				budgetVO.setAccountCode(budgetDTO2.getAccountCode());
				budgetVO.setNatureOfAccount(budgetDTO2.getNatureOfAccount());
				budgetVO.setYear(budgetDTO2.getYear()); // Extract year
				budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
				budgetVO.setAmount(budgetDTO2.getAmount());
				budgetVO.setMainGroup(budgetDTO2.getMainGroup());
				budgetVO.setSubGroupCode(budgetDTO2.getSubGroupCode());
				budgetVO.setSubGroup(budgetDTO2.getSubGroup());
				budgetVO.setActive(true);

				int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setQuater(String.valueOf(quater));

				int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setMonthsequence(monthseq);

				budgetRepo.save(budgetVO);
			} else {
				budgetVO = budgetRepo.getBudgetDetails(budgetDTO2.getOrgId(), budgetDTO2.getClientCode(),
						budgetDTO2.getYear(), budgetDTO2.getMonth(), budgetDTO2.getMainGroup(),
						budgetDTO2.getSubGroupCode(), budgetDTO2.getAccountCode());
				budgetVO.setOrgId(budgetDTO2.getOrgId());
				budgetVO.setClient(budgetDTO2.getClient());
				budgetVO.setClientCode(budgetDTO2.getClientCode());
				budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setAccountName(budgetDTO2.getAccountName());
				budgetVO.setAccountCode(budgetDTO2.getAccountCode());
				budgetVO.setNatureOfAccount(budgetDTO2.getNatureOfAccount());
				budgetVO.setYear(budgetDTO2.getYear()); // Extract year
				budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
				budgetVO.setAmount(budgetDTO2.getAmount());
				budgetVO.setMainGroup(budgetDTO2.getMainGroup());
				budgetVO.setSubGroupCode(budgetDTO2.getSubGroupCode());
				budgetVO.setSubGroup(budgetDTO2.getSubGroup());
				budgetVO.setActive(true);
				int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setQuater(String.valueOf(quater));
				int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setMonthsequence(monthseq);
				budgetRepo.save(budgetVO);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}

	@Override
	public Map<String, Object> createUpdatePreviousYear(List<PreviousYearDTO> budgetDTO) {
		PreviousYearActualVO budgetVO = new PreviousYearActualVO();
		for (PreviousYearDTO budgetDTO2 : budgetDTO) {
			budgetVO = previousYearActualRepo.getPreviousYearDetails(budgetDTO2.getOrgId(), budgetDTO2.getClientCode(),
					budgetDTO2.getYear(), budgetDTO2.getMonth(), budgetDTO2.getMainGroup(),
					budgetDTO2.getSubGroupCode(), budgetDTO2.getAccountCode());
			if (budgetVO == null) {
				budgetVO = new PreviousYearActualVO();
				budgetVO.setOrgId(budgetDTO2.getOrgId());
				budgetVO.setClient(budgetDTO2.getClient());
				budgetVO.setClientCode(budgetDTO2.getClientCode());
				budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setAccountName(budgetDTO2.getAccountName());
				budgetVO.setAccountCode(budgetDTO2.getAccountCode());
				budgetVO.setNatureOfAccount(budgetDTO2.getNatureOfAccount());
				budgetVO.setYear(budgetDTO2.getYear()); // Extract year
				budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
				budgetVO.setAmount(budgetDTO2.getAmount());
				budgetVO.setMainGroup(budgetDTO2.getMainGroup());
				budgetVO.setSubGroupCode(budgetDTO2.getSubGroupCode());
				budgetVO.setSubGroup(budgetDTO2.getSubGroup());
				budgetVO.setActive(true);
				int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setMonthsequence(monthseq);
				budgetVO.setQuater(String.valueOf(quater));

				previousYearActualRepo.save(budgetVO);
			} else {
				budgetVO = previousYearActualRepo.getPreviousYearDetails(budgetDTO2.getOrgId(),
						budgetDTO2.getClientCode(), budgetDTO2.getYear(), budgetDTO2.getMonth(),
						budgetDTO2.getMainGroup(), budgetDTO2.getSubGroupCode(), budgetDTO2.getAccountCode());
				budgetVO.setOrgId(budgetDTO2.getOrgId());
				budgetVO.setClient(budgetDTO2.getClient());
				budgetVO.setClientCode(budgetDTO2.getClientCode());
				budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setAccountName(budgetDTO2.getAccountName());
				budgetVO.setAccountCode(budgetDTO2.getAccountCode());
				budgetVO.setNatureOfAccount(budgetDTO2.getNatureOfAccount());
				budgetVO.setYear(budgetDTO2.getYear()); // Extract year
				budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
				budgetVO.setAmount(budgetDTO2.getAmount());
				budgetVO.setMainGroup(budgetDTO2.getMainGroup());
				budgetVO.setSubGroupCode(budgetDTO2.getSubGroupCode());
				budgetVO.setSubGroup(budgetDTO2.getSubGroup());
				budgetVO.setActive(true);
				int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setMonthsequence(monthseq);
				budgetVO.setQuater(String.valueOf(quater));

				previousYearActualRepo.save(budgetVO);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}

	@Override
	public List<Map<String, Object>> getPreviousYearGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup, String subGroupCode) {

		Set<Object[]> subGroupDetails = groupMappingRepo.PreviousYearGroupLedgersDetails(orgId, year, clientCode,
				mainGroup, subGroupCode);
		return getGroupLedgerDetails(subGroupDetails);
	}

	@Override
	public List<Map<String, Object>> getActualGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup, String subGroupCode) {

		Set<Object[]> subGroupDetails = groupMappingRepo.ActualGroupLedgersDetails(orgId, year, clientCode, mainGroup,
				subGroupCode);
		return getGroupLedgerDetails(subGroupDetails);
	}

	@Override
	public Map<String, Object> createUpdateBudgetOB(List<OrderBookingDTO> orderBookingDTO) {
		OrderBookingVO budgetVO = new OrderBookingVO();
		for (OrderBookingDTO budgetDTO2 : orderBookingDTO) {
			List<OrderBookingVO> ordDetails = orderBookingRepo.getBudgetListDetails(budgetDTO2.getOrgId(),
					budgetDTO2.getClientCode(), budgetDTO2.getYear(),budgetDTO2.getType());
			orderBookingRepo.deleteAll(ordDetails);
		}
		for (OrderBookingDTO budgetDTO2 : orderBookingDTO) {


			budgetVO = new OrderBookingVO();
			budgetVO.setOrgId(budgetDTO2.getOrgId());
			budgetVO.setClient(budgetDTO2.getClient());
			budgetVO.setClientCode(budgetDTO2.getClientCode());
			budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
			budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
			budgetVO.setYear(budgetDTO2.getYear()); // Extract year
			budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
			budgetVO.setAmount(budgetDTO2.getAmount());
			budgetVO.setBalanceOrderValue(budgetDTO2.getBalanceOrderValue());
			budgetVO.setClassification(budgetDTO2.getClassification());
			budgetVO.setCustomerName(budgetDTO2.getCustomerName());
			budgetVO.setExistingOrderPlan(budgetDTO2.getExistingOrderPlan());
			budgetVO.setOrderValue(budgetDTO2.getOrderValue());
			budgetVO.setPaymentReceived(budgetDTO2.getPaymentReceived());
			budgetVO.setSegment(budgetDTO2.getSegment());
			budgetVO.setType(budgetDTO2.getType());
			budgetVO.setYetToReceive(budgetDTO2.getYetToReceive());

			budgetVO.setActive(true);

			int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
			budgetVO.setQuater(String.valueOf(quater));

			int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
			budgetVO.setMonthsequence(monthseq);

			orderBookingRepo.save(budgetVO);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}

	@Override
	public List<Map<String, Object>> getOrderBookingBudgetDetail(Long orgId, String year, String clientCode,
			String type) {

		Set<Object[]> subGroupDetails = orderBookingRepo.getOrderBookingBudDetails(orgId, year, clientCode, type);
		return getOrderBookingBudgetDetails(subGroupDetails);
	}

	private List<Map<String, Object>> getOrderBookingBudgetDetails(Set<Object[]> subGroupDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : subGroupDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("segment", sub[0] != null ? sub[0].toString() : "");
			mp.put("customerName", sub[1] != null ? sub[1].toString() : "");
			mp.put("orderValue", sub[2] != null ? new BigDecimal(sub[2].toString()) : BigDecimal.ZERO);
			mp.put("balanceOrderValue", sub[3] != null ? new BigDecimal(sub[3].toString()) : BigDecimal.ZERO);
			mp.put("classification", sub[4] != null ? sub[4].toString() : "");
			mp.put("existingOrderPlan", sub[5] != null ? new BigDecimal(sub[5].toString()) : BigDecimal.ZERO);
			mp.put("paymentReceived", sub[6] != null ? new BigDecimal(sub[6].toString()) : BigDecimal.ZERO);
			mp.put("yetToReceived", sub[7] != null ? new BigDecimal(sub[7].toString()) : BigDecimal.ZERO);
			mp.put("month", sub[8] != null ? sub[8].toString() : "");
			mp.put("amount", sub[9] != null ? new BigDecimal(sub[9].toString()) : BigDecimal.ZERO);
			subgroup.add(mp);
		}
		return subgroup;
	}
}
