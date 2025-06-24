package com.ebooks.elevate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import com.ebooks.elevate.dto.BudgetRatioAnalysisDTO;
import com.ebooks.elevate.dto.BudgetUnitWiseDTO;
import com.ebooks.elevate.dto.IncrementalProfitDTO;
import com.ebooks.elevate.dto.OrderBookingDTO;
import com.ebooks.elevate.dto.PreviousYearDTO;
import com.ebooks.elevate.dto.PyAdvancePaymentReceiptDTO;
import com.ebooks.elevate.dto.PyHeadCountDTO;
import com.ebooks.elevate.entity.BudgetACPVO;
import com.ebooks.elevate.entity.BudgetHeadCountVO;
import com.ebooks.elevate.entity.BudgetIncrementalProfitVO;
import com.ebooks.elevate.entity.BudgetUnitWiseVO;
import com.ebooks.elevate.entity.BudgetVO;
import com.ebooks.elevate.entity.FinancialYearVO;
import com.ebooks.elevate.entity.GroupLedgersVO;
import com.ebooks.elevate.entity.OrderBookingVO;
import com.ebooks.elevate.entity.PYHeadCountVO;
import com.ebooks.elevate.entity.PreviousYearARAPVO;
import com.ebooks.elevate.entity.PreviousYearActualOBVO;
import com.ebooks.elevate.entity.PreviousYearActualVO;
import com.ebooks.elevate.entity.PreviousYearIncrementalProfitVO;
import com.ebooks.elevate.entity.PreviousYearUnitwiseVO;
import com.ebooks.elevate.entity.PyAdvancePaymentReceiptVO;
import com.ebooks.elevate.repo.BudgetACPRepo;
import com.ebooks.elevate.repo.BudgetHeadCountRepo;
import com.ebooks.elevate.repo.BudgetIncrementalProfitRepo;
import com.ebooks.elevate.repo.BudgetRepo;
import com.ebooks.elevate.repo.BudgetUnitWiseRepo;
import com.ebooks.elevate.repo.FinancialYearRepo;
import com.ebooks.elevate.repo.GroupLedgersRepo;
import com.ebooks.elevate.repo.GroupMappingRepo;
import com.ebooks.elevate.repo.OrderBookingRepo;
import com.ebooks.elevate.repo.PreviousYearARAPRepo;
import com.ebooks.elevate.repo.PreviousYearActualOBRepo;
import com.ebooks.elevate.repo.PreviousYearActualRepo;
import com.ebooks.elevate.repo.PreviousYearIncrementalProfitRepo;
import com.ebooks.elevate.repo.PreviousYearUnitwiseRepo;
import com.ebooks.elevate.repo.PyAdvancePaymentReceiptRepo;
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
	FinancialYearRepo financialYearRepo;

	@Autowired
	PreviousYearActualOBRepo previousYearActualOBRepo;

	@Autowired
	BudgetUnitWiseRepo budgetUnitWiseRepo;

	@Autowired
	PreviousYearUnitwiseRepo previousYearUnitwiseRepo;

	@Autowired
	BudgetACPRepo budgetACPRepo;

	@Autowired
	PreviousYearARAPRepo previousYearARAPRepo;

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
	
	@Autowired
	BudgetIncrementalProfitRepo budgetIncrementalProfitRepo;
	
	@Autowired
	PreviousYearIncrementalProfitRepo previousYearIncrementalProfitRepo;
	
	
	@Autowired
	PyAdvancePaymentReceiptRepo pyAdvancePaymentReceiptRepo;

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

			GroupLedgersVO groupLedgersVO = new GroupLedgersVO();

			try {
				groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(budgetDTO2.getOrgId(),
						budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
			} catch (Exception e) {
				System.out.println(budgetDTO2.getAccountName() + budgetDTO2.getMainGroup());
			}
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

				GroupLedgersVO gr = new GroupLedgersVO();

				try {
					gr = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(budgetDTO2.getOrgId(),
							budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
				} catch (Exception e) {
					System.out.println(budgetDTO2.getAccountName() + budgetDTO2.getMainGroup());
				}

				budgetVO.setSubGroupCode(budgetDTO2.getSubGroupCode());
				budgetVO.setSubGroup(gr.getGroupName());
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
		String maingroup = null;
		String subgroup = null;
		Long org = null;
		String clientcode = null;
		String yr = null;
		for (PreviousYearDTO budgetDTO2 : budgetDTO) {
			maingroup = budgetDTO2.getMainGroup();

			GroupLedgersVO groupLedgersVO = new GroupLedgersVO();

			try {
				groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(budgetDTO2.getOrgId(),
						budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
			} catch (Exception e) {
				System.out.println(budgetDTO2.getAccountName() + budgetDTO2.getMainGroup());
			}
			subgroup = groupLedgersVO.getGroupName();
			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();

			List<PreviousYearActualVO> vo = previousYearActualRepo.getClientBudgetDls(org, clientcode, yr, maingroup, subgroup);
			if (vo != null) {
				previousYearActualRepo.deleteAll(vo);
			}
		}

		for (PreviousYearDTO budgetDTO2 : budgetDTO) {

			if (!budgetDTO2.getAmount().equals(BigDecimal.ZERO)) {
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

				GroupLedgersVO gr = new GroupLedgersVO();

				try {
					gr = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(budgetDTO2.getOrgId(),
							budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
				} catch (Exception e) {
					System.out.println(budgetDTO2.getAccountName() + budgetDTO2.getMainGroup());
				}

				budgetVO.setSubGroupCode(budgetDTO2.getSubGroupCode());
				budgetVO.setSubGroup(gr.getGroupName());
				budgetVO.setActive(true);

				int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setQuater(String.valueOf(quater));

				int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setMonthsequence(monthseq);

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
			String mainGroup, String clientYear) {

		Set<Object[]> subGroupDetails = new HashSet<Object[]>();
		String previousYear = null;
		if (clientYear.equals("FY")) {
			FinancialYearVO financialYearVO = financialYearRepo.findByOrgIdAndFinYearIdentifierAndYearType(orgId, year,
					clientYear);
			int preYear = financialYearVO.getFinYear() - 1;
			FinancialYearVO financialYearVO2 = financialYearRepo.findByOrgIdAndFinYearAndYearType(orgId, preYear,
					clientYear);
			previousYear = financialYearVO2.getFinYearIdentifier();

		} else if (clientYear.equals("CY")) {
			FinancialYearVO financialYearVO = financialYearRepo.findByOrgIdAndFinYearIdentifierAndYearType(orgId, year,
					clientYear);
			int preYear = financialYearVO.getFinYear() - 1;
			FinancialYearVO financialYearVO2 = financialYearRepo.findByOrgIdAndFinYearAndYearType(orgId, preYear,
					clientYear);
			previousYear = financialYearVO2.getFinYearIdentifier();
		}
		if (mainGroup.equals("Profit And Loss")) {
			subGroupDetails = budgetRepo.getProfitAndLossBudgetDetailsAuto(orgId, year, clientCode, mainGroup,
					clientYear, previousYear);
		} else {
			subGroupDetails = budgetRepo.getBudgetDetailsAuto(orgId, year, clientCode, mainGroup);
		}

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
	public List<Map<String, Object>> getPYDetailsAutomatic(Long orgId, String year, String clientCode,
			String mainGroup) {

		Set<Object[]> subGroupDetails = budgetRepo.getPYDetailsAuto(orgId, year, clientCode, mainGroup);
		return getPYAuto(subGroupDetails);
	}

	private List<Map<String, Object>> getPYAuto(Set<Object[]> subGroupDetails) {
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
		String type = null;
		for (BudgetACPDTO budgetDTO2 : budgetACPDTO) {
			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();
			month = budgetDTO2.getMonth();
			type = budgetDTO2.getType();
		}
		List<BudgetACPVO> vo = budgetACPRepo.getClientBudgetDls(org, clientcode, yr, month, type);
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
			budgetVO.setSlab6(budgetDTO2.getSlab6());
			budgetVO.setType(budgetDTO2.getType());
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
	public List<Map<String, Object>> getBudgetACPDetails(Long orgId, String year, String month, String clientCode,
			String type) {
		Set<Object[]> Details = budgetACPRepo.getBudgetACPDetails(orgId, year, month, clientCode, type);
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
			mp.put("slab6", sub[11] != null ? new BigDecimal(sub[11].toString()) : BigDecimal.ZERO);
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
		String unit = null;
		for (BudgetUnitWiseDTO budgetDTO2 : budgetUnitWiseDTO) {
			maingroup = budgetDTO2.getMainGroup();

			GroupLedgersVO groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(
					budgetDTO2.getOrgId(), budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
			subgroup = groupLedgersVO.getGroupName();
			org = budgetDTO2.getOrgId();
			unit = budgetDTO2.getUnit();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();

		}

		List<BudgetUnitWiseVO> vo = budgetUnitWiseRepo.getBudgetDls(org, clientcode, yr, unit);
		if (vo != null) {
			budgetUnitWiseRepo.deleteAll(vo);
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
	public List<Map<String, Object>> getUnitDetails(Long orgId, String clientCode) {
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
	public List<Map<String, Object>> getSegmentDetails(Long orgId, String clientCode, String segmentType) {
		Set<Object[]> Details = budgetUnitWiseRepo.getSegmentDetails(orgId, clientCode, segmentType);
		return getSegmentDetails(Details);
	}

	private List<Map<String, Object>> getSegmentDetails(Set<Object[]> Details) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : Details) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("segmentDetails", sub[0] != null ? sub[0].toString() : "");
			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public List<Map<String, Object>> getUnitLedgerDetails(Long orgId, String year, String clientCode, String mainGroup,
			String accountCode, String unit) {
		Set<Object[]> Details = budgetUnitWiseRepo.getUnitWiseLedgersDetails(orgId, year, clientCode, mainGroup,
				accountCode, unit);
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

	@Override
	public List<Map<String, Object>> getGroupLedgersDetailsPYForHeadCount(Long orgId, String year, String clientCode) {
		Set<Object[]> Details = pyHeadCountRepo.getPYHCDetails(orgId, year, clientCode);
		return getDetails(Details);
	}

	@Override
	public Map<String, Object> createUpdatePYAccountPayable(List<BudgetACPDTO> budgetACPDTO) {

		PreviousYearARAPVO budgetVO = new PreviousYearARAPVO();
		Long org = null;
		String clientcode = null;
		String yr = null;
		String month = null;
		String type = null;
		for (BudgetACPDTO budgetDTO2 : budgetACPDTO) {
			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();
			month = budgetDTO2.getMonth();
			type = budgetDTO2.getType();
		}
		List<PreviousYearARAPVO> vo = previousYearARAPRepo.getClientPYDetails(org, clientcode, yr, month, type);
		if (vo != null) {
			previousYearARAPRepo.deleteAll(vo);
		}

		for (BudgetACPDTO budgetDTO2 : budgetACPDTO) {

			budgetVO = new PreviousYearARAPVO();
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
			budgetVO.setSlab6(budgetDTO2.getSlab6());
			budgetVO.setType(budgetDTO2.getType());
			budgetVO.setActive(true);

			int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
			budgetVO.setQuater(String.valueOf(quater));

			int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
			budgetVO.setMonthsequence(monthseq);

			previousYearARAPRepo.save(budgetVO);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}

	@Override
	public List<Map<String, Object>> getPYACPDetails(Long orgId, String year, String month, String clientCode,
			String type) {
		Set<Object[]> Details = previousYearARAPRepo.getPYARAPDetails(orgId, year, month, clientCode, type);
		return getACPDetails(Details);
	}

	@Override
	public Map<String, Object> createUpdatePYUnitWise(List<BudgetUnitWiseDTO> budgetUnitWiseDTO) {
		PreviousYearUnitwiseVO budgetVO = new PreviousYearUnitwiseVO();
		String maingroup = null;
		String subgroup = null;
		Long org = null;
		String clientcode = null;
		String yr = null;
		String unit = null;
		for (BudgetUnitWiseDTO budgetDTO2 : budgetUnitWiseDTO) {
			maingroup = budgetDTO2.getMainGroup();

			GroupLedgersVO groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(
					budgetDTO2.getOrgId(), budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
			subgroup = groupLedgersVO.getGroupName();
			org = budgetDTO2.getOrgId();
			unit = budgetDTO2.getUnit();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();

		}

		List<PreviousYearUnitwiseVO> vo = previousYearUnitwiseRepo.getPYUnitDetails(org, clientcode, yr, unit);
		if (vo != null) {
			previousYearUnitwiseRepo.deleteAll(vo);
		}

		for (BudgetUnitWiseDTO budgetDTO2 : budgetUnitWiseDTO) {

			if (!budgetDTO2.getAmount().equals(BigDecimal.ZERO)) {
				budgetVO = new PreviousYearUnitwiseVO();
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

				previousYearUnitwiseRepo.save(budgetVO);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}

	@Override
	public List<Map<String, Object>> getPYUnitLedgerDetails(Long orgId, String year, String clientCode,
			String mainGroup, String accountCode, String unit) {
		Set<Object[]> Details = previousYearUnitwiseRepo.getPYUnitWiseLedgersDetails(orgId, year, clientCode, mainGroup,
				accountCode, unit);
		return getUnitLedgerDetails(Details);
	}

	@Override
	public List<Map<String, Object>> getRatioAnalysisBudgetGroupLedgersDetails(Long orgId, String year,
			String clientCode, String mainGroup, String subGroupCode) {

		Set<Object[]> subGroupDetails = groupMappingRepo.getRatioAnalysisBudgetGroupLedgersDetails(orgId, year,
				clientCode, mainGroup, subGroupCode);
		return getRatioAnalysisGroupLedgerDetails(subGroupDetails);
	}

	private List<Map<String, Object>> getRatioAnalysisGroupLedgerDetails(Set<Object[]> subGroupDetails) {
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
	public Map<String, Object> createUpdateBudgetRatioAnalysis(List<BudgetRatioAnalysisDTO> budgetRatioAnalysisDTO) {
		BudgetVO budgetVO = new BudgetVO();
		String maingroup = null;
		String subgroup = null;
		Long org = null;
		String clientcode = null;
		String yr = null;
		for (BudgetRatioAnalysisDTO budgetDTO2 : budgetRatioAnalysisDTO) {
			maingroup = budgetDTO2.getMainGroup();
			subgroup = budgetDTO2.getSubGroup();
			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();

			List<BudgetVO> vo = budgetRepo.getClientBudgetDls(org, clientcode, yr, maingroup, subgroup);
			if (vo != null) {
				budgetRepo.deleteAll(vo);
			}
		}

		for (BudgetRatioAnalysisDTO budgetDTO2 : budgetRatioAnalysisDTO) {

			if (!budgetDTO2.getAmount().equals(BigDecimal.ZERO)) {
				budgetVO = new BudgetVO();
				budgetVO.setOrgId(budgetDTO2.getOrgId());
				budgetVO.setClient(budgetDTO2.getClient());
				budgetVO.setClientCode(budgetDTO2.getClientCode());
				budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setAccountName(budgetDTO2.getAccountName());
				budgetVO.setYear(budgetDTO2.getYear()); // Extract year
				budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
				budgetVO.setAmount(budgetDTO2.getAmount());
				budgetVO.setMainGroup(budgetDTO2.getMainGroup());

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
	public List<Map<String, Object>> getRatioAnalysisPYGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup, String subGroupCode) {
		Set<Object[]> subGroupDetails = groupMappingRepo.getRatioAnalysisPYGroupLedgersDetails(orgId, year, clientCode,
				mainGroup, subGroupCode);
		return getRatioAnalysisPYGroupLedgerDetails(subGroupDetails);
	}

	private List<Map<String, Object>> getRatioAnalysisPYGroupLedgerDetails(Set<Object[]> subGroupDetails) {
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
	public Map<String, Object> createUpdatePYRatioAnalysis(List<BudgetRatioAnalysisDTO> budgetRatioAnalysisDTO) {
		PreviousYearActualVO budgetVO = new PreviousYearActualVO();
		String maingroup = null;
		String subgroup = null;
		Long org = null;
		String clientcode = null;
		String yr = null;
		for (BudgetRatioAnalysisDTO budgetDTO2 : budgetRatioAnalysisDTO) {
			maingroup = budgetDTO2.getMainGroup();
			subgroup = budgetDTO2.getSubGroup();
			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();

			List<PreviousYearActualVO> vo = previousYearActualRepo.getClientBudgetDls(org, clientcode, yr, maingroup, subgroup);
			if (vo != null) {
				previousYearActualRepo.deleteAll(vo);
			}
		}

		for (BudgetRatioAnalysisDTO budgetDTO2 : budgetRatioAnalysisDTO) {

			if (!budgetDTO2.getAmount().equals(BigDecimal.ZERO)) {
				budgetVO = new PreviousYearActualVO();
				budgetVO.setOrgId(budgetDTO2.getOrgId());
				budgetVO.setClient(budgetDTO2.getClient());
				budgetVO.setClientCode(budgetDTO2.getClientCode());
				budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setAccountName(budgetDTO2.getAccountName());
				budgetVO.setYear(budgetDTO2.getYear()); // Extract year
				budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
				budgetVO.setAmount(budgetDTO2.getAmount());
				budgetVO.setMainGroup(budgetDTO2.getMainGroup());

				budgetVO.setSubGroup(budgetDTO2.getSubGroup());
				budgetVO.setActive(true);

				int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setQuater(String.valueOf(quater));

				int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setMonthsequence(monthseq);

				previousYearActualRepo.save(budgetVO);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}

	@Override
	public List<Map<String, Object>> getLedgerDetailsForPL(Long orgId, String mainGroupName) {
		Set<Object[]> subGroupDetails = groupMappingRepo.getLedgerDetailsForPL(orgId, mainGroupName);
		return getLedgerDetailsForPL(subGroupDetails);
	}

	private List<Map<String, Object>> getLedgerDetailsForPL(Set<Object[]> subGroupDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : subGroupDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("subGroupName", sub[1] != null ? sub[1].toString() : "");
			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public List<Map<String, Object>> getSubGroupDetailsForPL(Long orgId, String mainGroupName) {
		Set<Object[]> subGroupDetails = groupMappingRepo.getSubGroupDetailsForPL(orgId, mainGroupName);
		return getSubGroupDetailsForPL(subGroupDetails);
	}

	private List<Map<String, Object>> getSubGroupDetailsForPL(Set<Object[]> subGroupDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : subGroupDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("subGroupName", sub[0] != null ? sub[0].toString() : "");
			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public List<Map<String, Object>> getLedgerDetailsForSubGroupPL(Long orgId, String mainGroupName,
			String subGroupName) {
		Set<Object[]> subGroupDetails = groupMappingRepo.getLedgerDetailsForSubGroupPL(orgId, mainGroupName,
				subGroupName);
		return getLedgerDetailsForSubGroupPL(subGroupDetails);
	}

	private List<Map<String, Object>> getLedgerDetailsForSubGroupPL(Set<Object[]> subGroupDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : subGroupDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("accountName", sub[0] != null ? sub[0].toString() : "");
			mp.put("accountCode", sub[1] != null ? sub[1].toString() : "");
			mp.put("mainGroup", sub[2] != null ? sub[2].toString() : "");
			mp.put("subGroup", sub[3] != null ? sub[3].toString() : "");
			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public Map<String, Object> createUpdateIncrementalProfitBudget(List<IncrementalProfitDTO> budgetDTO) {
		BudgetIncrementalProfitVO budgetVO = new BudgetIncrementalProfitVO();
		Long org = null;
		String clientcode = null;
		String yr = null;
		String subGroup= null;
		for (IncrementalProfitDTO budgetDTO2 : budgetDTO) {
			
			GroupLedgersVO groupLedgersVO = new GroupLedgersVO();

			try {
				groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(budgetDTO2.getOrgId(),
						budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
			} catch (Exception e) {
				System.out.println(budgetDTO2.getAccountName() + budgetDTO2.getMainGroup());
			}
			subGroup = groupLedgersVO.getGroupName();

			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();

			List<BudgetIncrementalProfitVO> vo = budgetIncrementalProfitRepo.getClientBudgetDls(org, clientcode, yr,subGroup);
			if (vo != null) {
				budgetIncrementalProfitRepo.deleteAll(vo);
			}
		}

		for (IncrementalProfitDTO budgetDTO2 : budgetDTO) {

			if (!budgetDTO2.getAmount().equals(BigDecimal.ZERO)) {
				budgetVO = new BudgetIncrementalProfitVO();
				budgetVO.setOrgId(budgetDTO2.getOrgId());
				budgetVO.setClient(budgetDTO2.getClient());
				budgetVO.setClientCode(budgetDTO2.getClientCode());
				budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setAccountName(budgetDTO2.getAccountName());
				budgetVO.setAccountCode(budgetDTO2.getAccountCode());
				budgetVO.setYear(budgetDTO2.getYear()); // Extract year
				budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
				budgetVO.setAmount(budgetDTO2.getAmount());
				budgetVO.setActive(true);
				GroupLedgersVO gr = new GroupLedgersVO();

				try {
					gr = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(budgetDTO2.getOrgId(),
							budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
				} catch (Exception e) {
					System.out.println(budgetDTO2.getAccountName() + budgetDTO2.getMainGroup());
				}

				budgetVO.setSubGroup(gr.getGroupName());
				budgetIncrementalProfitRepo.save(budgetVO);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}
	
	@Override
	public Map<String, Object> createUpdateIncrementalProfitPY(List<IncrementalProfitDTO> budgetDTO) {
		PreviousYearIncrementalProfitVO budgetVO = new PreviousYearIncrementalProfitVO();
		Long org = null;
		String clientcode = null;
		String yr = null;
		String subGroup= null;
		for (IncrementalProfitDTO budgetDTO2 : budgetDTO) {
			
			GroupLedgersVO groupLedgersVO = new GroupLedgersVO();

			try {
				groupLedgersVO = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(budgetDTO2.getOrgId(),
						budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
			} catch (Exception e) {
				System.out.println(budgetDTO2.getAccountName() + budgetDTO2.getMainGroup());
			}
			subGroup = groupLedgersVO.getGroupName();

			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();

			List<PreviousYearIncrementalProfitVO> vo = previousYearIncrementalProfitRepo.getClientBudgetDls(org, clientcode, yr,subGroup);
			if (vo != null) {
				previousYearIncrementalProfitRepo.deleteAll(vo);
			}
		}

		for (IncrementalProfitDTO budgetDTO2 : budgetDTO) {

			if (!budgetDTO2.getAmount().equals(BigDecimal.ZERO)) {
				budgetVO = new PreviousYearIncrementalProfitVO();
				budgetVO.setOrgId(budgetDTO2.getOrgId());
				budgetVO.setClient(budgetDTO2.getClient());
				budgetVO.setClientCode(budgetDTO2.getClientCode());
				budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setAccountName(budgetDTO2.getAccountName());
				budgetVO.setAccountCode(budgetDTO2.getAccountCode());
				budgetVO.setYear(budgetDTO2.getYear()); // Extract year
				budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
				budgetVO.setAmount(budgetDTO2.getAmount());
				budgetVO.setActive(true);
				GroupLedgersVO gr = new GroupLedgersVO();

				try {
					gr = groupLedgersRepo.findByOrgIdAndAccountNameAndMainGroupName(budgetDTO2.getOrgId(),
							budgetDTO2.getAccountName(), budgetDTO2.getMainGroup());
				} catch (Exception e) {
					System.out.println(budgetDTO2.getAccountName() + budgetDTO2.getMainGroup());
				}

				budgetVO.setSubGroup(gr.getGroupName());
				previousYearIncrementalProfitRepo.save(budgetVO);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
	}
	
	@Override
	public List<Map<String, Object>> getBudgetIncrementalGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup,String subGroup) {

		Set<Object[]> particularDetails = groupMappingRepo.getBudgetIncrementalLedgersDetails(orgId, year, clientCode, mainGroup,subGroup);
		return getIncrementalProfitLedgerDetails(particularDetails);
	}

	private List<Map<String, Object>> getIncrementalProfitLedgerDetails(Set<Object[]> subGroupDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : subGroupDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("accountCode", sub[0] != null ? sub[0].toString() : null);
			mp.put("accountName", sub[1] != null ? sub[1].toString() : "");
			mp.put("amount", sub[2] != null ? new BigDecimal(sub[2].toString()) : BigDecimal.ZERO);
			subgroup.add(mp);
		}
		return subgroup;
	}
	
	@Override
	public List<Map<String, Object>> getPYIncrementalGroupLedgersDetails(Long orgId, String year, String clientCode,
			String mainGroup,String subGroup) {

		Set<Object[]> particularDetails = groupMappingRepo.getPYIncrementalLedgersDetails(orgId, year, clientCode, mainGroup,subGroup);
		return getIncrementalProfitLedgerDetails(particularDetails);
	}

	@Override
	public Map<String, Object> createUpdateAdvancePaymentPY(List<PyAdvancePaymentReceiptDTO> pyAdvancePaymentReceiptDTO) {
		PyAdvancePaymentReceiptVO budgetVO = new PyAdvancePaymentReceiptVO();
		Long org = null;
		String clientcode = null;
		String yr = null;
		String type= null;
		for (PyAdvancePaymentReceiptDTO budgetDTO2 : pyAdvancePaymentReceiptDTO) {
			org = budgetDTO2.getOrgId();
			clientcode = budgetDTO2.getClientCode();
			yr = budgetDTO2.getYear();
			type=budgetDTO2.getType();
			
			List<PyAdvancePaymentReceiptVO> vo = pyAdvancePaymentReceiptRepo.getClientBudgetDls(org, clientcode, yr, type);
			if (vo != null) {
				pyAdvancePaymentReceiptRepo.deleteAll(vo);
			}
		}

		for (PyAdvancePaymentReceiptDTO budgetDTO2 : pyAdvancePaymentReceiptDTO) {

			if (!budgetDTO2.getAmount().equals(BigDecimal.ZERO)) {
				budgetVO = new PyAdvancePaymentReceiptVO();
				budgetVO.setOrgId(budgetDTO2.getOrgId());
				budgetVO.setClient(budgetDTO2.getClient());
				budgetVO.setClientCode(budgetDTO2.getClientCode());
				budgetVO.setCreatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setUpdatedBy(budgetDTO2.getCreatedBy());
				budgetVO.setType(budgetDTO2.getType());
				budgetVO.setParty(budgetDTO2.getParty());;
				budgetVO.setYear(budgetDTO2.getYear()); // Extract year
				budgetVO.setMonth(budgetDTO2.getMonth()); // Extract month (first three letters)
				budgetVO.setAmount(budgetDTO2.getAmount());
				budgetVO.setActive(true);

				int quater = quaterMonthService.getQuaterMonthDetails(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setQuater(String.valueOf(quater));

				int monthseq = quaterMonthService.getMonthNumber(budgetDTO2.getYearType(), budgetDTO2.getMonth());
				budgetVO.setMonthsequence(monthseq);
				
				pyAdvancePaymentReceiptRepo.save(budgetVO);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Successfully Saved");
		return response;
		
	}
	
	
	@Override
	public List<Map<String, Object>> getAdvancePaymentReceiptDetails(Long orgId, String year, String clientCode,String type) {

		Set<Object[]> paymentReceiptDetails = pyAdvancePaymentReceiptRepo.getPaymentReceiptDetails(orgId, year, clientCode, type);
		return getPRDetails(paymentReceiptDetails);
	}

	private List<Map<String, Object>> getPRDetails(Set<Object[]> paymentReceiptDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : paymentReceiptDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("party", sub[0] != null ? sub[0].toString() : "");
			mp.put("month", sub[1] != null ? sub[1].toString() : "");
			mp.put("amount", sub[2] != null ? new BigDecimal(sub[2].toString()) : BigDecimal.ZERO);
			subgroup.add(mp);
		}
		return subgroup;
	}
}
