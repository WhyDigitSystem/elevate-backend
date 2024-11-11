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

import com.ebooks.elevate.dto.CostDebitChargesDTO;
import com.ebooks.elevate.dto.CostDebitNoteDTO;
import com.ebooks.elevate.dto.CostDebitNoteGstDTO;
import com.ebooks.elevate.dto.CostDebitNoteSummaryDTO;
import com.ebooks.elevate.dto.CostDebitNoteTaxPrtculDTO;
import com.ebooks.elevate.entity.CostDebitChargesVO;
import com.ebooks.elevate.entity.CostDebitNoteGstVO;
import com.ebooks.elevate.entity.CostDebitNoteSummaryVO;
import com.ebooks.elevate.entity.CostDebitNoteTaxPrtculVO;
import com.ebooks.elevate.entity.CostDebitNoteVO;
import com.ebooks.elevate.entity.DocumentTypeMappingDetailsVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.CostDebitChargesRepo;
import com.ebooks.elevate.repo.CostDebitNoteGstRepo;
import com.ebooks.elevate.repo.CostDebitNoteRepo;
import com.ebooks.elevate.repo.CostDebitNoteSummaryRepo;
import com.ebooks.elevate.repo.CostDebitNoteTaxPrtculRepo;
import com.ebooks.elevate.repo.DocumentTypeMappingDetailsRepo;

@Service
public class CostDebitNoteServiceImpl implements CostDebitNoteService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CostDebitNoteServiceImpl.class);

	@Autowired
	CostDebitNoteRepo costDebitNoteRepo;

	@Autowired
	CostDebitChargesRepo costDebitChargesRepo;

	@Autowired
	CostDebitNoteGstRepo costDebitNoteGstRepo;

	@Autowired
	CostDebitNoteTaxPrtculRepo costDebitNoteTaxPrtculRepo;

	@Autowired
	CostDebitNoteSummaryRepo costDebitNoteSummaryRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public Map<String, Object> updateCreateCostDebitNote(@Valid CostDebitNoteDTO costDebitNoteDTO)
			throws ApplicationException {

		String message = null;
		String screenCode = "CDN";
		CostDebitNoteVO costDebitNoteVO;

		if (ObjectUtils.isEmpty(costDebitNoteDTO.getId())) {
			costDebitNoteVO = new CostDebitNoteVO();

			// GETDOCID API
			String docId = costDebitNoteRepo.getCostDebitNoteDocId(costDebitNoteDTO.getOrgId(),
					costDebitNoteDTO.getFinYear(), costDebitNoteDTO.getBranchCode(), screenCode);
			costDebitNoteVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(costDebitNoteDTO.getOrgId(),
							costDebitNoteDTO.getFinYear(), costDebitNoteDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			costDebitNoteVO.setCreatedBy(costDebitNoteDTO.getCreatedBy());
			costDebitNoteVO.setModifyBy(costDebitNoteDTO.getCreatedBy());

			message = "CostDebitNote Creation Successfully";

		} else {
			costDebitNoteVO = costDebitNoteRepo.findById(costDebitNoteDTO.getId()).orElseThrow(
					() -> new ApplicationException("Cost DebitNote Not Found with id: " + costDebitNoteDTO.getId()));
			costDebitNoteVO.setModifyBy(costDebitNoteDTO.getCreatedBy());

			message = "CostDebitNote Updation Successfully";
		}

		costDebitNoteVO = getCostDebitNoteVOFromCostDebitNoteDTO(costDebitNoteVO, costDebitNoteDTO);
		costDebitNoteRepo.save(costDebitNoteVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("costDebitNoteVO", costDebitNoteVO);
		return response;
	}

	private CostDebitNoteVO getCostDebitNoteVOFromCostDebitNoteDTO(CostDebitNoteVO costDebitNoteVO,
			@Valid CostDebitNoteDTO costDebitNoteDTO) {

		// Set individual fields from DTO to VO
		costDebitNoteVO.setBranch(costDebitNoteDTO.getBranch());
		costDebitNoteVO.setBranchCode(costDebitNoteDTO.getBranchCode());
		costDebitNoteVO.setCreatedBy(costDebitNoteDTO.getCreatedBy());
		costDebitNoteVO.setCancelRemarks(costDebitNoteDTO.getCancelRemarks());
		costDebitNoteVO.setFinYear(costDebitNoteDTO.getFinYear());
		costDebitNoteVO.setTaxExampt(costDebitNoteDTO.isTaxExampt());
		costDebitNoteVO.setIpNo(costDebitNoteDTO.getIpNo());
		costDebitNoteVO.setLatitude(costDebitNoteDTO.getLatitude());
		costDebitNoteVO.setOrgId(costDebitNoteDTO.getOrgId());
		costDebitNoteVO.setDocNo(costDebitNoteDTO.getDocNo());
		costDebitNoteVO.setSubType(costDebitNoteDTO.getSubType());
		costDebitNoteVO.setProduct(costDebitNoteDTO.getProduct());
		costDebitNoteVO.setVohNo(costDebitNoteDTO.getVohNo());
		costDebitNoteVO.setVohDate(costDebitNoteDTO.getVohDate());
		costDebitNoteVO.setPartyType(costDebitNoteDTO.getPartyType());
		costDebitNoteVO.setSuppRefNo(costDebitNoteDTO.getSuppRefNo());
		costDebitNoteVO.setSuppDate(costDebitNoteDTO.getSuppDate());
		costDebitNoteVO.setPartyName(costDebitNoteDTO.getPartyName());
		costDebitNoteVO.setPartyCode(costDebitNoteDTO.getPartyCode());
		costDebitNoteVO.setCreditDays(costDebitNoteDTO.getCreditDays());
		costDebitNoteVO.setDueDate(costDebitNoteDTO.getDueDate());
		costDebitNoteVO.setAddress(costDebitNoteDTO.getAddress());
		costDebitNoteVO.setCurrency(costDebitNoteDTO.getCurrency());
		costDebitNoteVO.setExRate(costDebitNoteDTO.getExRate());
		costDebitNoteVO.setOtherInfo(costDebitNoteDTO.getOtherInfo());
		costDebitNoteVO.setRemarks(costDebitNoteDTO.getRemarks());
		costDebitNoteVO.setShipRefNo(costDebitNoteDTO.getShipRefNo());
		costDebitNoteVO.setStatus(costDebitNoteDTO.getStatus());
		costDebitNoteVO.setOrginBill(costDebitNoteDTO.getOrginBill());
		costDebitNoteVO.setGstType(costDebitNoteDTO.getGstType());
		costDebitNoteVO.setCurrentDate(costDebitNoteDTO.getCurrentDate());
		costDebitNoteVO.setCurrentDateValue(costDebitNoteDTO.getCurrentDateValue());

		// Deleting existing entries if updating
		if (costDebitNoteDTO.getId() != null) {
			List<CostDebitNoteGstVO> costDebitNoteGstVOs = costDebitNoteGstRepo.findByCostDebitNoteVO(costDebitNoteVO);
			costDebitNoteGstRepo.deleteAll(costDebitNoteGstVOs);

			List<CostDebitNoteTaxPrtculVO> costDebitNoteTaxPrtculVOs = costDebitNoteTaxPrtculRepo
					.findByCostDebitNoteVO(costDebitNoteVO);
			costDebitNoteTaxPrtculRepo.deleteAll(costDebitNoteTaxPrtculVOs);

			List<CostDebitNoteSummaryVO> costDebitNoteSummaryVOs = costDebitNoteSummaryRepo
					.findByCostDebitNoteVO(costDebitNoteVO);
			costDebitNoteSummaryRepo.deleteAll(costDebitNoteSummaryVOs);

			List<CostDebitChargesVO> costDebitChargesVOs = costDebitChargesRepo.findByCostDebitNoteVO(costDebitNoteVO);
			costDebitChargesRepo.deleteAll(costDebitChargesVOs);
		}

		// Adding Cost Debit Note GSTs
		List<CostDebitNoteGstVO> costDebitNoteGstVOs = new ArrayList<>();
		for (CostDebitNoteGstDTO costDebitNoteGstDTO : costDebitNoteDTO.getCostDebitNoteGstDTO()) {
			CostDebitNoteGstVO costDebitNoteGstVO = new CostDebitNoteGstVO();

			costDebitNoteGstVO.setChargeAcc(costDebitNoteGstDTO.getChargeAcc());
			costDebitNoteGstVO.setSubLodgerCode(costDebitNoteGstDTO.getSubLodgerCode());
			costDebitNoteGstVO.setDBillAmt(costDebitNoteGstDTO.getDBillAmt());
			costDebitNoteGstVO.setCrBillAmt(costDebitNoteGstDTO.getCrBillAmt());
			costDebitNoteGstVO.setDBLCAmt(costDebitNoteGstDTO.getDBLCAmt());
			costDebitNoteGstVO.setCrLCAmt(costDebitNoteGstDTO.getCrLCAmt());
			costDebitNoteGstVO.setRemarks(costDebitNoteGstDTO.getRemarks());
			

			costDebitNoteGstVO.setCostDebitNoteVO(costDebitNoteVO);
			costDebitNoteGstVOs.add(costDebitNoteGstVO);
		}
		costDebitNoteVO.setCostDebitNoteGstVO(costDebitNoteGstVOs);

		// Adding Cost Debit Note Tax Particulars
		List<CostDebitNoteTaxPrtculVO> costDebitNoteTaxPrtculVOs = new ArrayList<>();
		for (CostDebitNoteTaxPrtculDTO costDebitNoteTaxPrtculDTO : costDebitNoteDTO.getCostDebitNoteTaxPrtculDTO()) {
			CostDebitNoteTaxPrtculVO costDebitNoteTaxPrtculVO = new CostDebitNoteTaxPrtculVO();

			costDebitNoteTaxPrtculVO.setTds(costDebitNoteTaxPrtculDTO.getTds());
			costDebitNoteTaxPrtculVO.setTdsPercentage(costDebitNoteTaxPrtculDTO.getTdsPercentage());
			costDebitNoteTaxPrtculVO.setSection(costDebitNoteTaxPrtculDTO.getSection());
			costDebitNoteTaxPrtculVO.setTotTDSAmt(costDebitNoteTaxPrtculDTO.getTotTDSAmt());

			costDebitNoteTaxPrtculVO.setCostDebitNoteVO(costDebitNoteVO);
			costDebitNoteTaxPrtculVOs.add(costDebitNoteTaxPrtculVO);
		}
		costDebitNoteVO.setCostDebitNoteTaxPrtculVO(costDebitNoteTaxPrtculVOs);

		// Adding Cost Debit Note Summaries
		List<CostDebitNoteSummaryVO> costDebitNoteSummaryVOs = new ArrayList<>();
		for (CostDebitNoteSummaryDTO costDebitNoteSummaryDTO : costDebitNoteDTO.getCostDebitNoteSummaryDTO()) {
			CostDebitNoteSummaryVO costDebitNoteSummaryVO = new CostDebitNoteSummaryVO();

			costDebitNoteSummaryVO.setTotChargesBillCurrAmt(costDebitNoteSummaryDTO.getTotChargesBillCurrAmt());
			costDebitNoteSummaryVO.setTotChargesLCAmt(costDebitNoteSummaryDTO.getTotChargesLCAmt());
			costDebitNoteSummaryVO.setTotGrossBillAmt(costDebitNoteSummaryDTO.getTotGrossBillAmt());
			costDebitNoteSummaryVO.setTotGrossLCAmt(costDebitNoteSummaryDTO.getTotGrossLCAmt());
			costDebitNoteSummaryVO.setNetBillCurrAmt(costDebitNoteSummaryDTO.getNetBillCurrAmt());
			costDebitNoteSummaryVO.setNetLCAmt(costDebitNoteSummaryDTO.getNetLCAmt());
			costDebitNoteSummaryVO.setRoundOff(costDebitNoteSummaryDTO.getRoundOff());
			costDebitNoteSummaryVO.setAmtInWords(costDebitNoteSummaryDTO.getAmtInWords());

			costDebitNoteSummaryVO.setCostDebitNoteVO(costDebitNoteVO);
			costDebitNoteSummaryVOs.add(costDebitNoteSummaryVO);
		}
		costDebitNoteVO.setCostDebitNoteSummaryVO(costDebitNoteSummaryVOs);

		// Adding Cost Debit Charges
		List<CostDebitChargesVO> costDebitChargesVOs = new ArrayList<>();
		for (CostDebitChargesDTO costDebitChargesDTO : costDebitNoteDTO.getCostDebitChargesDTO()) {
			CostDebitChargesVO costDebitChargesVO = new CostDebitChargesVO();

			costDebitChargesVO.setJobType(costDebitChargesDTO.getJobType());
			costDebitChargesVO.setJobNo(costDebitChargesDTO.getJobNo());
			costDebitChargesVO.setSubJobNo(costDebitChargesDTO.getSubJobNo());
			costDebitChargesVO.setHouseNo(costDebitChargesDTO.getHouseNo());
			costDebitChargesVO.setChargeCode(costDebitChargesDTO.getChargeCode());
			costDebitChargesVO.setGChargeCode(costDebitChargesDTO.getGChargeCode());
			costDebitChargesVO.setGSAC(costDebitChargesDTO.getGSAC());
			costDebitChargesVO.setChargeName(costDebitChargesDTO.getChargeName());
			costDebitChargesVO.setApplyOn(costDebitChargesDTO.getApplyOn());
			costDebitChargesVO.setCurrency(costDebitChargesDTO.getCurrency());
			costDebitChargesVO.setExRate(costDebitChargesDTO.getExRate());
			costDebitChargesVO.setRate(costDebitChargesDTO.getRate());
			costDebitChargesVO.setFcAmt(costDebitChargesDTO.getFcAmt());
			costDebitChargesVO.setLcAmt(costDebitChargesDTO.getLcAmt());
			costDebitChargesVO.setTaxPercentage(costDebitChargesDTO.getTaxPercentage());
			costDebitChargesVO.setTlcAmt(costDebitChargesDTO.getTlcAmt());
			costDebitChargesVO.setBillAmt(costDebitChargesDTO.getBillAmt());
			costDebitChargesVO.setGstPercentage(costDebitChargesDTO.getGstPercentage());
			costDebitChargesVO.setGst(costDebitChargesDTO.getGst());
			costDebitChargesVO.setExampted(costDebitChargesDTO.isExampted());
			costDebitChargesVO.setTax(costDebitChargesDTO.isTax());

			costDebitChargesVO.setCostDebitNoteVO(costDebitNoteVO);
			costDebitChargesVOs.add(costDebitChargesVO);
		}
		costDebitNoteVO.setCostDebitChargesVO(costDebitChargesVOs);

		return costDebitNoteVO;
	}

	@Override
	public List<CostDebitNoteVO> getCostDebitNoteByOrgId(Long orgId) {

		return costDebitNoteRepo.getByCostDebitNoteByOrgId(orgId);
	}
 
	@Override
	public List<CostDebitNoteVO> getCostDebitNoteById(Long id) {
		return costDebitNoteRepo.getByCostDebitNoteById(id);
	}

	@Override
	public List<CostDebitNoteVO> getActiveCostDebitNote() {
		return costDebitNoteRepo.getActiveCostDebitNote();
	}

	@Override
	public CostDebitNoteVO getAllCostDebitNoteByDocId(Long orgId, String docId) {
		return costDebitNoteRepo.findAllCostDebitNoteByDocId(orgId, docId);
	}
  //8778426636
	@Override
	public String getCostDebitNoteDocId(Long orgId, String finYear, String branch, String branchCode) {
		String ScreenCode = "CDN";
		String result = costDebitNoteRepo.getCostDebitNoteDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	


	@Override
	public List<Map<String, Object>> chargeTypeDetailsForCostDebitNote(Long orgId) {
	    Set<Object[]> getChargeTypeDetails = costDebitNoteRepo.getChareDetails(orgId);
	    return getChargeType(getChargeTypeDetails);  // Call the correct method
	}

	private List<Map<String, Object>> getChargeType(Set<Object[]> getCharge) {
	    List<Map<String, Object>> gridDetails = new ArrayList<>();
	    for (Object[] grid : getCharge) {
	        Map<String, Object> details = new HashMap<>();
	        details.put("chargeType", grid[0] != null ? grid[0].toString() : "");
	        details.put("chargeCode", grid[1] != null ? grid[1].toString() : "");
	        details.put("govtSac", grid[2] != null ? grid[2].toString() : "");
	        details.put("serviceAccountCode", grid[3] != null ? grid[3].toString() : "");  // Updated to match "serviceAccountCode"
	        
	        gridDetails.add(details);
	    }
	    return gridDetails;
	}

	@Override
	public List<Map<String, Object>> partyDetailsForCostDebitNote(Long orgId, String branch, String finYear) {
	    // Fetch party details from the repository
	    Set<Object[]> getPartyTypeDetails = costDebitNoteRepo.getParty(orgId, branch, finYear);
	    // Return processed party details
	    return getPartyDetails(getPartyTypeDetails);
	}

	private List<Map<String, Object>> getPartyDetails(Set<Object[]> getPartyInformation) {
	    List<Map<String, Object>> gridDetails = new ArrayList<>();
	    // Iterate through the result set
	    for (Object[] grid : getPartyInformation) {
	       
	            Map<String, Object> details = new HashMap<>();
	            details.put("partyName", grid[0] != null ? grid[0].toString() : "");
	            details.put("partyCode", grid[1] != null ? grid[1].toString() : "");
	            details.put("partyType", grid[2] != null ? grid[2].toString() : "");
	            details.put("addressType", grid[3] != null ? grid[3].toString() : "");

	            gridDetails.add(details);
	       
	    }
	    return gridDetails;  // Return the list of party details
	}

	@Override
	public List<Map<String, Object>> getAllDocIdForCostInvoice(Long orgId) {
		  Set<Object[]> getAllDocId = costDebitNoteRepo.getDocIdForCI(orgId);
		    // Return processed party details
		    return getAllCIDocId(getAllDocId);
		}

		private List<Map<String, Object>> getAllCIDocId(Set<Object[]> getDocIdInformation) {
		    List<Map<String, Object>> gridDetails = new ArrayList<>();
		    // Iterate through the result set
		    for (Object[] grid : getDocIdInformation) {
		       
		            Map<String, Object> details = new HashMap<>();
		            details.put("docId", grid[0] != null ? grid[0].toString() : "");
		            gridDetails.add(details);
		       
		    }
		    return gridDetails;  // Return the list of party details
		}

		@Override
		public List<Map<String, Object>> getCurrencyAndExrates(Long orgId) {
			Set<Object[]> currency = costDebitNoteRepo.getCurrencyAndExrateDetails(orgId);
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
	

}
