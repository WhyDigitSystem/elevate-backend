package com.ebooks.elevate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.ChargerCostInvoiceDTO;
import com.ebooks.elevate.dto.CostInvSummaryDTO;
import com.ebooks.elevate.dto.CostInvoiceDTO;
import com.ebooks.elevate.dto.TdsCostInvoiceDTO;
import com.ebooks.elevate.entity.ChargerCostInvoiceVO;
import com.ebooks.elevate.entity.CostInvSummaryVO;
import com.ebooks.elevate.entity.CostInvoiceVO;
import com.ebooks.elevate.entity.DocumentTypeMappingDetailsVO;
import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.entity.TdsCostInvoiceVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.ChargeTypeRequestRepo;
import com.ebooks.elevate.repo.ChargerCostInvoiceRepo;
import com.ebooks.elevate.repo.CostInvSummaryRepo;
import com.ebooks.elevate.repo.CostInvoiceRepo;
import com.ebooks.elevate.repo.DocumentTypeMappingDetailsRepo;
import com.ebooks.elevate.repo.TdsCostInvoiceRepo;

@Service
public class CostInvoiceServiceImpl implements CostInvoiceService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CostInvoiceServiceImpl.class);

	@Autowired
	CostInvoiceRepo costInvoiceRepo;

	@Autowired
	TdsCostInvoiceRepo tdsCostInvoiceRepo;

	@Autowired
	ChargerCostInvoiceRepo chargerCostInvoiceRepo;

	@Autowired
	CostInvSummaryRepo costInvSummaryRepo;
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	ChargeTypeRequestRepo chargeTypeRequestRepo;

	// costInvoice

	@Override
	public List<CostInvoiceVO> getAllCostInvoiceByOrgId(Long orgId) {
		List<CostInvoiceVO> costInvoiceVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  CostInvoice BY OrgId : {}", orgId);
			costInvoiceVO = costInvoiceRepo.getAllCostInvoiceByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  CostInvoice For All OrgId.");
			costInvoiceVO = costInvoiceRepo.findAll();
		}
		return costInvoiceVO;
	}

	@Override
	public List<CostInvoiceVO> getAllCostInvoiceById(Long id) {
		List<CostInvoiceVO> costInvoiceVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  CostInvoice BY Id : {}", id);
			costInvoiceVO = costInvoiceRepo.getAllCostInvoiceById(id);
		} else {
			LOGGER.info("Successfully Received CostInvoice For All Id.");
			costInvoiceVO = costInvoiceRepo.findAll();
		}
		return costInvoiceVO;
	}

	@Override
	public List<CostInvoiceVO> getCostInvoiceByActive() {
		return costInvoiceRepo.findCostInvoiceByActive();
	}

	@Override
	public Map<String, Object> updateCreateCostInvoice(@Valid CostInvoiceDTO costInvoiceDTO)
			throws ApplicationException {
		String screenCode = "cI";
		CostInvoiceVO costInvoiceVO;
		String message = null;

		if (ObjectUtils.isEmpty(costInvoiceDTO.getId())) {

			costInvoiceVO = new CostInvoiceVO();

			// GETDOCID API
			String docId = costInvoiceRepo.getCostInvoiceDocId(costInvoiceDTO.getOrgId(), costInvoiceDTO.getFinYear(),
					costInvoiceDTO.getBranchCode(), screenCode);
			costInvoiceVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(costInvoiceDTO.getOrgId(),
							costInvoiceDTO.getFinYear(), costInvoiceDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			costInvoiceVO.setCreatedBy(costInvoiceDTO.getCreatedBy());
			costInvoiceVO.setUpdatedBy(costInvoiceDTO.getCreatedBy());

			message = "CostInvoice Creation Successfully";
		}

		else {
			costInvoiceVO = costInvoiceRepo.findById(costInvoiceDTO.getId()).orElseThrow(
					() -> new ApplicationException("Cost Invoice Not Found with id: " + costInvoiceDTO.getId()));
			costInvoiceVO.setUpdatedBy(costInvoiceDTO.getCreatedBy());

			message = "CostInvoice Updation Successfully";
		}

		costInvoiceVO = getCostInvoiceVOFromCostInvoiceDTO(costInvoiceVO, costInvoiceDTO);
		costInvoiceRepo.save(costInvoiceVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("costInvoiceVO", costInvoiceVO);
		return response;

	}

	private CostInvoiceVO getCostInvoiceVOFromCostInvoiceDTO(CostInvoiceVO costInvoiceVO,
			@Valid CostInvoiceDTO costInvoiceDTO) {
 
		costInvoiceVO.setMode(costInvoiceDTO.getMode());
		costInvoiceVO.setProduct(costInvoiceDTO.getProduct());
		costInvoiceVO.setPurVoucherNo(costInvoiceDTO.getPurVoucherNo());
		costInvoiceVO.setPurVoucherDate(costInvoiceDTO.getPurVoucherDate());
		costInvoiceVO.setCostInvoiceNo(costInvoiceDTO.getCostInvoiceNo());
		costInvoiceVO.setCostInvoiceDate(costInvoiceDTO.getCostInvoiceDate());
		costInvoiceVO.setSupplierBillNo(costInvoiceDTO.getSupplierBillNo());
		costInvoiceVO.setSupplierType(costInvoiceDTO.getSupplierType());
		costInvoiceVO.setSupplierCode(costInvoiceDTO.getSupplierCode());
		costInvoiceVO.setCreditDays(costInvoiceDTO.getCreditDays());
		costInvoiceVO.setDueDate(costInvoiceDTO.getDueDate());
		costInvoiceVO.setSupplierName(costInvoiceDTO.getSupplierName());
		costInvoiceVO.setSupplierPlace(costInvoiceDTO.getSupplierPlace());
		costInvoiceVO.setCurrency(costInvoiceDTO.getCurrency());
		costInvoiceVO.setExRate(costInvoiceDTO.getExRate());
		costInvoiceVO.setSupplierGstIn(costInvoiceDTO.getSupplierGstIn());
		costInvoiceVO.setSupplierGstInCode(costInvoiceDTO.getSupplierGstInCode());
		costInvoiceVO.setRemarks(costInvoiceDTO.getRemarks());
		costInvoiceVO.setAddress(costInvoiceDTO.getAddress());
		costInvoiceVO.setOtherInfo(costInvoiceDTO.getOtherInfo());
		costInvoiceVO.setShipperRefNo(costInvoiceDTO.getShipperRefNo());
		costInvoiceVO.setGstType(costInvoiceDTO.getGstType());
		costInvoiceVO.setOrgId(costInvoiceDTO.getOrgId());
		costInvoiceVO.setCreatedBy(costInvoiceDTO.getCreatedBy());
		costInvoiceVO.setCancelRemarks(costInvoiceDTO.getCancelRemarks());
		costInvoiceVO.setBranch(costInvoiceDTO.getBranch());
		costInvoiceVO.setBranchCode(costInvoiceDTO.getBranchCode());
		costInvoiceVO.setCustomer(costInvoiceDTO.getCustomer());
		costInvoiceVO.setClient(costInvoiceDTO.getClient());
		costInvoiceVO.setFinYear(costInvoiceDTO.getFinYear());
		costInvoiceVO.setIpNo(costInvoiceDTO.getIpNo());
		costInvoiceVO.setLatitude(costInvoiceDTO.getLatitude());
		costInvoiceVO.setPayment(costInvoiceDTO.  getPayment());
		costInvoiceVO.setAccuralid(costInvoiceDTO.getAccuralid());
		costInvoiceVO.setUtrRef(costInvoiceDTO.getUtrRef());
		costInvoiceVO.setCostType(costInvoiceDTO.getCostType());

		if (costInvoiceDTO.getId() != null) {

			List<ChargerCostInvoiceVO> chargerCostInvoiceVOs = chargerCostInvoiceRepo
					.findByCostInvoiceVO(costInvoiceVO);
			chargerCostInvoiceRepo.deleteAll(chargerCostInvoiceVOs);

			List<TdsCostInvoiceVO> tdsCostInvoiceVOs = tdsCostInvoiceRepo.findByCostInvoiceVO(costInvoiceVO);
			tdsCostInvoiceRepo.deleteAll(tdsCostInvoiceVOs);

			List<CostInvSummaryVO> costInvSummaryVOs = costInvSummaryRepo.findByCostInvoiceVO(costInvoiceVO);
			costInvSummaryRepo.deleteAll(costInvSummaryVOs);
		}

		List<ChargerCostInvoiceVO> chargerCostInvoiceVOs = new ArrayList<>();
		for (ChargerCostInvoiceDTO chargerCostInvoiceDTO : costInvoiceDTO.getChargerCostInvoiceDTO()) {
			ChargerCostInvoiceVO chargerCostInvoiceVO = new ChargerCostInvoiceVO();

			chargerCostInvoiceVO.setHouseNo(chargerCostInvoiceDTO.getHouseNo());
			chargerCostInvoiceVO.setJobNo(chargerCostInvoiceDTO.getJobNo());
			chargerCostInvoiceVO.setSubJobNo(chargerCostInvoiceDTO.getSubJobNo());
			chargerCostInvoiceVO.setChargeName(chargerCostInvoiceDTO.getChargeName());
			chargerCostInvoiceVO.setChargeCode(chargerCostInvoiceDTO.getChargeCode());
			chargerCostInvoiceVO.setChargeLedger(chargerCostInvoiceDTO.getChargeLedger());
			chargerCostInvoiceVO.setGsac(chargerCostInvoiceDTO.getGsac());
			chargerCostInvoiceVO.setContType(chargerCostInvoiceDTO.getContType());
			chargerCostInvoiceVO.setCurrency(chargerCostInvoiceDTO.getCurrency());
			chargerCostInvoiceVO.setExRate(chargerCostInvoiceDTO.getExRate());
			chargerCostInvoiceVO.setGst(chargerCostInvoiceDTO.getGst());
			chargerCostInvoiceVO.setFcAmt(chargerCostInvoiceDTO.getFcAmt());
			chargerCostInvoiceVO.setLcAmt(chargerCostInvoiceDTO.getLcAmt());
			chargerCostInvoiceVO.setBillAmt(chargerCostInvoiceDTO.getBillAmt());

			chargerCostInvoiceVO.setCostInvoiceVO(costInvoiceVO);
			chargerCostInvoiceVOs.add(chargerCostInvoiceVO);
		}
		costInvoiceVO.setChargerCostInvoiceVO(chargerCostInvoiceVOs);

		List<TdsCostInvoiceVO> tdsCostInvoiceVOs = new ArrayList<>();
		for (TdsCostInvoiceDTO tdsCostInvoiceDTO : costInvoiceDTO.getTdsCostInvoiceDTO()) {
			TdsCostInvoiceVO tdsCostInvoiceVO = new TdsCostInvoiceVO();

			tdsCostInvoiceVO.setTdsWithHolding(tdsCostInvoiceDTO.getTdsWithHolding());
			tdsCostInvoiceVO.setTdsWithHoldingPer(tdsCostInvoiceDTO.getTdsWithHoldingPer());
			tdsCostInvoiceVO.setSection(tdsCostInvoiceDTO.getSection());
			tdsCostInvoiceVO.setTotTdsWhAmnt(tdsCostInvoiceDTO.getTotTdsWhAmnt());

			tdsCostInvoiceVO.setCostInvoiceVO(costInvoiceVO);
			tdsCostInvoiceVOs.add(tdsCostInvoiceVO);
		}
		costInvoiceVO.setTdsCostInvoiceVO(tdsCostInvoiceVOs);

		List<CostInvSummaryVO> costInvSummaryVOs = new ArrayList<>();
		for (CostInvSummaryDTO costInvSummaryDTO : costInvoiceDTO.getCostInvSummaryDTO()) {
			CostInvSummaryVO costInvSummaryVO = new CostInvSummaryVO();

			costInvSummaryVO.setTotChargesBillCurrAmt(costInvSummaryDTO.getTotChargesBillCurrAmt());
			costInvSummaryVO.setTotChargesLcAmt(costInvSummaryDTO.getTotChargesLcAmt());
			costInvSummaryVO.setActBillCurrAmt(costInvSummaryDTO.getActBillCurrAmt());
			costInvSummaryVO.setActBillLcAmt(costInvSummaryDTO.getActBillLcAmt());
			costInvSummaryVO.setNetBillCurrAmt(costInvSummaryDTO.getNetBillCurrAmt());
			costInvSummaryVO.setNetBillLcAmt(costInvSummaryDTO.getNetBillLcAmt());
			costInvSummaryVO.setRoundOff(costInvSummaryDTO.getRoundOff());
			costInvSummaryVO.setGstInputLcAmt(costInvSummaryDTO.getGstInputLcAmt());

			costInvSummaryVO.setCostInvoiceVO(costInvoiceVO);
			costInvSummaryVOs.add(costInvSummaryVO);
		}
		costInvoiceVO.setCostInvSummaryVO(costInvSummaryVOs);

		return costInvoiceVO; 

	}

	@Override
	public CostInvoiceVO getCostInvoiceByDocId(Long orgId, String docId) {
		return costInvoiceRepo.findAllCostInvoiceByDocId(orgId, docId);
	}

	@Override
	public String getCostInvoiceDocId(Long orgId, String finYear, String branch, String branchCode) {
		String ScreenCode = "CI";
		String result = costInvoiceRepo.getCostInvoiceDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getChargeType(Long orgId) {
		Set<Object[]> chType = costInvoiceRepo.getActiveChargType(orgId);
		return getChargeType(chType);
	}

	private List<Map<String, Object>> getChargeType(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("chargeType", ch[0].toString());
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getChargeCodeByChargeType(Long orgId, String chargeType) {
		Set<Object[]> chCode = costInvoiceRepo.getActiveChargCodeByOrgIdAndChargeTypeIgnoreCase(orgId,
				chargeType);
		return getChargeCode(chCode);
	}

	private List<Map<String, Object>> getChargeCode(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("chargeCode", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("govChargeCode", ch[1] != null ? ch[1].toString() : "");
			map.put("chargeName", ch[2] != null ? ch[2].toString() : "");
			map.put("taxable", ch[3] != null ? ch[3].toString() : "");
			map.put("ccFeeApplicable", ch[4] != null ? ch[4].toString() : "");
			map.put("exempted", ch[5] != null ? ch[5].toString() : "");
			map.put("sac", ch[6] != null ? ch[6].toString() : "");
			map.put("GSTPercent", ch[7] != null ? ch[7].toString() : ""); // Handle as string, empty if null
			map.put("ledger", ch[8] != null ? ch[8].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getCurrencyAndExrates(Long orgId) {
		Set<Object[]> currency = costInvoiceRepo.getCurrencyAndExrateDetails(orgId);
		return getCurrency(currency);
	}

	private List<Map<String, Object>> getCurrency(Set<Object[]> currency) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : currency) {
			Map<String, Object> map = new HashMap<>();
			map.put("currency", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("currencyDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("buyingExRate", ch[2] != null ? ch[2].toString() : "");
			map.put("sellingExRate", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType) {
		
		return costInvoiceRepo.findByOrgIdAndPartyTypeIgnoreCase(orgId,partyType);

	}

	@Override
	public List<Map<String, Object>> getPartyStateCodeDetails(Long orgId,Long id) {
		Set<Object[]> getStateDetails = costInvoiceRepo.getStateCodeDetails(orgId,id);
		return getState(getStateDetails);
	}

	private List<Map<String, Object>> getState(Set<Object[]> getStateDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getStateDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("stateCode", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("recipientGSTIN", ch[1] != null ? ch[1].toString() : "");
			map.put("stateNo", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartyAddressDetails(Long orgId,Long id,String stateCode,String placeOfSupply) {
		Set<Object[]> getAddressDetails = costInvoiceRepo.getAddressDetails(orgId, id, stateCode, placeOfSupply);
		return getAddress(getAddressDetails);
	}

	private List<Map<String, Object>> getAddress(Set<Object[]> getAddressDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getAddressDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("addressType", ch[0] != null ? ch[0].toString() : "");
			map.put("address", ch[1] != null ? ch[1].toString() : ""); // Empty string if null
			map.put("pinCode", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getGstTypeDetails(Long orgId,String branchCode,String stateCode) {
		Set<Object[]> getGSTTypeDetails = costInvoiceRepo.getGstType(orgId, branchCode, stateCode);
		return getGstType(getGSTTypeDetails);
	}

	private List<Map<String, Object>> getGstType(Set<Object[]> getGSTTypeDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getGSTTypeDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("gstType", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getPlaceOfSupplyDetails(Long orgId,Long id,String stateCode) {
		Set<Object[]> getPOSDetails = costInvoiceRepo.getPlaceOfSupplyDetails(orgId,id,stateCode);
		return getPOS(getPOSDetails);
	}

	private List<Map<String, Object>> getPOS(Set<Object[]> getPOSDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getPOSDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("placeOfSupply", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			List1.add(map);
		}
		return List1;
	}

}
