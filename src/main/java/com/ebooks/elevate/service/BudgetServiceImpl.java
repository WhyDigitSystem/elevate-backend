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

import com.ebooks.elevate.dto.BudgetACPDTO;
import com.ebooks.elevate.dto.BudgetDTO;
import com.ebooks.elevate.dto.BudgetHeadCountDTO;
import com.ebooks.elevate.dto.BudgetUnitWiseDTO;
import com.ebooks.elevate.dto.OrderBookingDTO;
import com.ebooks.elevate.dto.PreviousYearDTO;
import com.ebooks.elevate.dto.PyHeadCountDTO;
import com.ebooks.elevate.entity.BudgetACPVO;
import com.ebooks.elevate.entity.BudgetHeadCountVO;
import com.ebooks.elevate.entity.BudgetUnitWiseVO;
import com.ebooks.elevate.entity.BudgetVO;
import com.ebooks.elevate.entity.GroupLedgersVO;
import com.ebooks.elevate.entity.OrderBookingVO;
import com.ebooks.elevate.entity.PYHeadCountVO;
import com.ebooks.elevate.entity.PreviousYearActualOBVO;
import com.ebooks.elevate.entity.PreviousYearActualVO;
import com.ebooks.elevate.repo.BudgetACPRepo;
import com.ebooks.elevate.repo.BudgetHeadCountRepo;
import com.ebooks.elevate.repo.BudgetRepo;
import com.ebooks.elevate.repo.BudgetUnitWiseRepo;
import com.ebooks.elevate.repo.GroupLedgersRepo;
import com.ebooks.elevate.repo.GroupMappingRepo;
import com.ebooks.elevate.repo.OrderBookingRepo;
import com.ebooks.elevate.repo.PreviousYearActualOBRepo;
import com.ebooks.elevate.repo.PreviousYearActualRepo;
import com.ebooks.elevate.repo.PyHeadCountRepo;
import com.ebooks.elevate.repo.SubGroupDetailsRepo;

@Service
public class BudgetServiceImpl implements BudgetService {
	public static final Logger LOGGER = LoggerFactory.getLogger(BudgetServiceImpl.class);

	@Autowired
	GroupMappingRepo groupMappingRepo;

	@Autowired
	QuaterMonthService quaterMonthService;

	@Autowired
	PreviousYearActualOBRepo previousYearActualOBRepo;

	@Autowired
	BudgetUnitWiseRepo budgetUnitWiseRepo;
	
	@Autowired
	BudgetACPRepo budgetACPRepo;

	@Autowired
	SubGroupDetailsRepo subGroupDetailsRepo;

	@Autowired
	GroupLedgersRepo groupLedgersRepo;

	@Autowired
	BudgetHeadCountRepo budgetHeadCountRepo;

	@Autowired
	PyHeadCountRepo pyHeadCountRepo;

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
		String maingroup = null;
		String subgroup = null;
		Long org = null;
		String clientcode = null;
		String yr = null;
		for (BudgetDTO budgetDTO2 : budgetDTO) {
			maingroup = budgetDTO2.getMainGroup();
			
			GroupLedgersVO groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(
					budgetDTO2.getOrgId(), budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
			subgroup = groupLedgersVO.getGroupName();
			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();
			
			List<BudgetVO> vo = budgetRepo.getClientBudgetDls(org, clientcode, yr, maingroup, subgroup);
			if (vo != null) {
				budgetRepo.deleteAll(vo);
			}
		}
		

		for (BudgetDTO budgetDTO2 : budgetDTO) {

			if (!budgetDTO2.getAmount().equals(BigDecimal.ZERO)) {
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

				GroupLedgersVO groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(
						budgetDTO2.getOrgId(), budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());

				budgetVO.setSubGroupCode(budgetDTO2.getSubGroupCode());
				budgetVO.setSubGroup(groupLedgersVO.getGroupName());
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
				GroupLedgersVO groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(
						budgetDTO2.getOrgId(), budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());

				budgetVO.setSubGroupCode(budgetDTO2.getSubGroupCode());
				budgetVO.setSubGroup(groupLedgersVO.getGroupName());
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
				GroupLedgersVO groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(
						budgetDTO2.getOrgId(), budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());

				budgetVO.setSubGroupCode(budgetDTO2.getSubGroupCode());
				budgetVO.setSubGroup(groupLedgersVO.getGroupName());
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
					budgetDTO2.getClientCode(), budgetDTO2.getYear(), budgetDTO2.getType());
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

	@Override
	public Map<String, Object> createUpdatePYActulaOB(List<OrderBookingDTO> orderBookingDTO) {
		PreviousYearActualOBVO budgetVO = new PreviousYearActualOBVO();
		for (OrderBookingDTO budgetDTO2 : orderBookingDTO) {
			List<PreviousYearActualOBVO> ordDetails = previousYearActualOBRepo.getBudgetListDetails(
					budgetDTO2.getOrgId(), budgetDTO2.getClientCode(), budgetDTO2.getYear(), budgetDTO2.getType());
			if (ordDetails != null) {
				previousYearActualOBRepo.deleteAll(ordDetails);
			}
		}
		for (OrderBookingDTO budgetDTO2 : orderBookingDTO) {

			budgetVO = new PreviousYearActualOBVO();
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

			previousYearActualOBRepo.save(budgetVO);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}

	@Override
	public List<Map<String, Object>> getPYActualOBDetails(Long orgId, String year, String clientCode, String type) {
		Set<Object[]> subGroupDetails = previousYearActualOBRepo.getOrderBookingPYDetails(orgId, year, clientCode,
				type);
		return getOrderBookingPYDetails(subGroupDetails);
	}

	private List<Map<String, Object>> getOrderBookingPYDetails(Set<Object[]> subGroupDetails) {
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

	@Override
	public List<Map<String, Object>> getBudgetDetailsAutomatic(Long orgId, String year, String clientCode,
			String mainGroup) {

		Set<Object[]> subGroupDetails = budgetRepo.getBudgetDetailsAuto(orgId, year, clientCode, mainGroup);
		return getBudgetAuto(subGroupDetails);
	}

	private List<Map<String, Object>> getBudgetAuto(Set<Object[]> subGroupDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : subGroupDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("subGroupCode", sub[0] != null ? sub[0].toString() : "");
			mp.put("subGroupName", sub[1] != null ? sub[1].toString() : "");
			mp.put("natureOfAccount", null);
			mp.put("month", sub[2]);
			mp.put("amount", sub[3] != null ? new BigDecimal(sub[3].toString()) : BigDecimal.ZERO);
			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public Map<String, Object> createUpdateBudgetHeadCount(List<BudgetHeadCountDTO> budgetHeadCountDTO) {

		BudgetHeadCountVO budgetVO = new BudgetHeadCountVO();
		Long org = null;
		String clientcode = null;
		String yr = null;
		for (BudgetHeadCountDTO budgetDTO2 : budgetHeadCountDTO) {
			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();
		}
		List<BudgetHeadCountVO> vo = budgetHeadCountRepo.getClientBudgetDls(org, clientcode, yr);
		if (vo != null) {
			budgetHeadCountRepo.deleteAll(vo);
		}

		for (BudgetHeadCountDTO budgetDTO2 : budgetHeadCountDTO) {

			budgetVO = new BudgetHeadCountVO();
			budgetVO.setOrgId(budgetDTO2.getOrgId());
			budgetVO.setClient(budgetDTO2.getClient());
			budgetVO.setClientCode(budgetDTO2.getClientCode());
			budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
			budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
			budgetVO.setYear(budgetDTO2.getYear()); // Extract year
			budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
			budgetVO.setDepartment(budgetDTO2.getDepartment());
			budgetVO.setEmploymentType(budgetDTO2.getEmploymentType());
			budgetVO.setCategory(budgetDTO2.getCategory());
			budgetVO.setHeadcount(budgetDTO2.getHeadcount());
			budgetVO.setCtc(budgetDTO2.getCtc());
			budgetVO.setActive(true);

			int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
			budgetVO.setQuater(String.valueOf(quater));

			int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
			budgetVO.setMonthsequence(monthseq);

			budgetHeadCountRepo.save(budgetVO);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}

	@Override
	public List<Map<String, Object>> getGroupLedgersDetailsForHeadCount(Long orgId, String year, String clientCode) {
		Set<Object[]> Details = budgetHeadCountRepo.getDetails(orgId, year, clientCode);
		return getDetails(Details);
	}

	private List<Map<String, Object>> getDetails(Set<Object[]> Details) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : Details) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("department", sub[0] != null ? sub[0].toString() : "");
			mp.put("employmentType", sub[1] != null ? sub[1].toString() : "");
			mp.put("category", sub[2] != null ? sub[2].toString() : "");
			mp.put("month", sub[3] != null ? sub[3].toString() : "");
			mp.put("headCount", sub[4] != null ? Integer.parseInt(sub[4].toString()) : 0);
			mp.put("ctc", sub[5] != null ? new BigDecimal(sub[5].toString()) : BigDecimal.ZERO);
			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public Map<String, Object> createUpdatePreviousYearHeadCount(List<PyHeadCountDTO> pyHeadCountDTO) {

		PYHeadCountVO budgetVO = new PYHeadCountVO();
		Long org = null;
		String clientcode = null;
		String yr = null;
		for (PyHeadCountDTO budgetDTO2 : pyHeadCountDTO) {
			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();
		}
		List<PYHeadCountVO> vo = pyHeadCountRepo.getClientBudgetDls(org, clientcode, yr);

		if (vo != null) {
			pyHeadCountRepo.deleteAll(vo);
		}

		for (PyHeadCountDTO budgetDTO2 : pyHeadCountDTO) {

			budgetVO = new PYHeadCountVO();
			budgetVO.setOrgId(budgetDTO2.getOrgId());
			budgetVO.setClient(budgetDTO2.getClient());
			budgetVO.setClientCode(budgetDTO2.getClientCode());
			budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
			budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
			budgetVO.setYear(budgetDTO2.getYear()); // Extract year
			budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
			budgetVO.setDepartment(budgetDTO2.getDepartment());
			budgetVO.setEmploymentType(budgetDTO2.getEmploymentType());
			budgetVO.setCategory(budgetDTO2.getCategory());
			budgetVO.setHeadcount(budgetDTO2.getHeadcount());
			budgetVO.setCtc(budgetDTO2.getCtc());
			budgetVO.setActive(true);

			int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
			budgetVO.setQuater(String.valueOf(quater));

			int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
			budgetVO.setMonthsequence(monthseq);

			pyHeadCountRepo.save(budgetVO);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}

	@Override
	public Map<String, Object> createUpdateBudgetAccountPayable(List<BudgetACPDTO> budgetACPDTO) {

		BudgetACPVO budgetVO = new BudgetACPVO();
		Long org = null;
		String clientcode = null;
		String yr = null;
		String month = null;
		for (BudgetACPDTO budgetDTO2 : budgetACPDTO) {
			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();
			month = budgetDTO2.getMonth();
		}
		List<BudgetACPVO> vo = budgetACPRepo.getClientBudgetDls(org, clientcode, yr, month);
		if (vo != null) {
			budgetACPRepo.deleteAll(vo);
		}

		for (BudgetACPDTO budgetDTO2 : budgetACPDTO) {

			budgetVO = new BudgetACPVO();
			budgetVO.setOrgId(budgetDTO2.getOrgId());
			budgetVO.setClient(budgetDTO2.getClient());
			budgetVO.setClientCode(budgetDTO2.getClientCode());
			budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
			budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
			budgetVO.setYear(budgetDTO2.getYear()); // Extract year
			budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
			budgetVO.setSupplier(budgetDTO2.getSupplier());
			budgetVO.setPaymentTerms(budgetDTO2.getPaymentTerms());
			budgetVO.setGrossPurchase(budgetDTO2.getGrossPurchase());
			budgetVO.setNoOfMonthPurchase(budgetDTO2.getNoOfMonthPurchase());
			budgetVO.setOutStanding(budgetDTO2.getOutStanding());
			budgetVO.setPaymentPeriod(budgetDTO2.getPaymentPeriod());
			budgetVO.setSlab1(budgetDTO2.getSlab1());
			budgetVO.setSlab2(budgetDTO2.getSlab2());
			budgetVO.setSlab3(budgetDTO2.getSlab3());
			budgetVO.setSlab4(budgetDTO2.getSlab4());
			budgetVO.setSlab5(budgetDTO2.getSlab5());
			budgetVO.setActive(true);

			int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
			budgetVO.setQuater(String.valueOf(quater));

			int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
			budgetVO.setMonthsequence(monthseq);

			budgetACPRepo.save(budgetVO);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}
	
	@Override
	public List<Map<String, Object>> getBudgetACPDetails(Long orgId, String year,String month, String clientCode) {
		Set<Object[]> Details = budgetACPRepo.getBudgetACPDetails(orgId, year, month, clientCode);
		return getACPDetails(Details);
	}

	private List<Map<String, Object>> getACPDetails(Set<Object[]> Details) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : Details) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("supplier", sub[0] != null ? sub[0].toString() : "");
			mp.put("paymentTerms", sub[1] != null ? sub[1].toString() : "");
			mp.put("grossPurchase", sub[2] != null ? new BigDecimal(sub[2].toString()) : BigDecimal.ZERO);
			mp.put("noOfMonthPurhcase", sub[3] != null ? Integer.parseInt(sub[3].toString()) : 0);
			mp.put("outStanding", sub[4] != null ? new BigDecimal(sub[4].toString()) : BigDecimal.ZERO);
			mp.put("paymentPeriod", sub[5] != null ? Integer.parseInt(sub[5].toString()) : 0);
			mp.put("slab1", sub[6] != null ? new BigDecimal(sub[6].toString()) : BigDecimal.ZERO);
			mp.put("slab2", sub[7] != null ? new BigDecimal(sub[7].toString()) : BigDecimal.ZERO);
			mp.put("slab3", sub[8] != null ? new BigDecimal(sub[8].toString()) : BigDecimal.ZERO);
			mp.put("slab4", sub[9] != null ? new BigDecimal(sub[9].toString()) : BigDecimal.ZERO);
			mp.put("slab5", sub[10] != null ? new BigDecimal(sub[10].toString()) : BigDecimal.ZERO);
			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public Map<String, Object> createUpdateBudgetUnitWise(List<BudgetUnitWiseDTO> budgetUnitWiseDTO) {
		BudgetUnitWiseVO budgetVO = new BudgetUnitWiseVO();
		String maingroup = null;
		String subgroup = null;
		Long org = null;
		String clientcode = null;
		String yr = null;
		String unit=null;
		for (BudgetUnitWiseDTO budgetDTO2 : budgetUnitWiseDTO) {
			maingroup = budgetDTO2.getMainGroup();
			
			GroupLedgersVO groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(
					budgetDTO2.getOrgId(), budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
			subgroup = groupLedgersVO.getGroupName();
			org = budgetDTO2.getOrgId();
			unit=budgetDTO2.getUnit();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();
			
			
		}
		
		List<BudgetVO> vo = budgetUnitWiseRepo.getBudgetDls(org, clientcode, yr, unit);
		if (vo != null) {
			budgetRepo.deleteAll(vo);
		}
		

		for (BudgetUnitWiseDTO budgetDTO2 : budgetUnitWiseDTO) {

			if (!budgetDTO2.getAmount().equals(BigDecimal.ZERO)) {
				budgetVO = new BudgetUnitWiseVO();
				budgetVO.setOrgId(budgetDTO2.getOrgId());
				budgetVO.setClient(budgetDTO2.getClient());
				budgetVO.setClientCode(budgetDTO2.getClientCode());
				budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setAccountName(budgetDTO2.getAccountName());
				budgetVO.setAccountCode(budgetDTO2.getAccountCode());
				budgetVO.setUnit(unit);
				budgetVO.setNatureOfAccount(budgetDTO2.getNatureOfAccount());
				budgetVO.setYear(budgetDTO2.getYear()); // Extract year
				budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
				budgetVO.setAmount(budgetDTO2.getAmount());
				budgetVO.setMainGroup(budgetDTO2.getMainGroup());

				GroupLedgersVO groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(
						budgetDTO2.getOrgId(), budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());

				budgetVO.setSubGroupCode(budgetDTO2.getSubGroupCode());
				budgetVO.setSubGroup(groupLedgersVO.getGroupName());
				budgetVO.setActive(true);

				int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setQuater(String.valueOf(quater));

				int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setMonthsequence(monthseq);

				budgetUnitWiseRepo.save(budgetVO);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}
	
	@Override
	public List<Map<String, Object>> getUnitDetails(Long orgId,String clientCode) {
		Set<Object[]> Details = budgetACPRepo.getUnitDetails(orgId, clientCode);
		return getUnitDetails(Details);
	}

	private List<Map<String, Object>> getUnitDetails(Set<Object[]> Details) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : Details) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("supplier", sub[0] != null ? sub[0].toString() : "");
			subgroup.add(mp);
		}
		return subgroup;
	}
	
	@Override
	public List<Map<String, Object>> getUnitLedgerDetails(Long orgId,String year,String clientCode, String mainGroup,String accountCode,String unit) {
		Set<Object[]> Details = budgetUnitWiseRepo.getUnitWiseLedgersDetails(orgId, year, clientCode, mainGroup, accountCode, unit);
		return getUnitLedgerDetails(Details);
	}
	
	private List<Map<String, Object>> getUnitLedgerDetails(Set<Object[]> Details) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : Details) {
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

	

}
