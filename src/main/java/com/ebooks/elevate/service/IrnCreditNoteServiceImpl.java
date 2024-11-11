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

import com.ebooks.elevate.dto.IrnCreditChargesDTO;
import com.ebooks.elevate.dto.IrnCreditDTO;
import com.ebooks.elevate.dto.IrnCreditGstDTO;
import com.ebooks.elevate.entity.DocumentTypeMappingDetailsVO;
import com.ebooks.elevate.entity.IrnCreditChargesVO;
import com.ebooks.elevate.entity.IrnCreditGstVO;
import com.ebooks.elevate.entity.IrnCreditVO;
import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.DocumentTypeMappingDetailsRepo;
import com.ebooks.elevate.repo.IrnCreditChargesRepo;
import com.ebooks.elevate.repo.IrnCreditGstRepo;
import com.ebooks.elevate.repo.IrnCreditRepo;
import com.ebooks.elevate.repo.PartyMasterRepo;

@Service
public class IrnCreditNoteServiceImpl implements IrnCreditNoteService {
	public static final Logger LOGGER = LoggerFactory.getLogger(IrnCreditNoteServiceImpl.class);
	@Autowired
	IrnCreditRepo irnCreditRepo;

	@Autowired
	IrnCreditChargesRepo irnCreditChargesRepo;

	@Autowired
	IrnCreditGstRepo irnCreditGstRepo;
	
	@Autowired
	PartyMasterRepo partyMasterRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public List<IrnCreditVO> getAllIrnCreditByOrgId(Long orgId) {
		List<IrnCreditVO> irnCreditVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  IrnCredit BY OrgId : {}", orgId);
			irnCreditVO = irnCreditRepo.getAllIrnCreditByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  IrnCredit For All OrgId.");
			irnCreditVO = irnCreditRepo.findAll();
		}
		return irnCreditVO;
	}

	@Override
	public List<IrnCreditVO> getAllIrnCreditById(Long id) {
		List<IrnCreditVO> irnCreditVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  IrnCredit BY Id : {}", id);
			irnCreditVO = irnCreditRepo.getAllIrnCreditById(id);
		} else {
			LOGGER.info("Successfully Received  IrnCredit For All Id.");
			irnCreditVO = irnCreditRepo.findAll();
		}
		return irnCreditVO;
	}

	@Override
	public Map<String, Object> updateCreateIrnCredit(@Valid IrnCreditDTO irnCreditDTO) throws ApplicationException {
		String screenCode = "ICN";
		IrnCreditVO irnCreditVO = new IrnCreditVO();
		String message;
		if (ObjectUtils.isNotEmpty(irnCreditDTO.getId())) {
			irnCreditVO = irnCreditRepo.findById(irnCreditDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid IrnCredit details"));
			irnCreditVO.setUpdatedBy(irnCreditDTO.getCreatedBy());
			createUpdateIrnCreditVOByIrnCreditDTO(irnCreditDTO, irnCreditVO);
			message = "IRN Credit Updated Successfully";
		} else {
			// GETDOCID API
			String docId = irnCreditRepo.getIrnCreditDocId(irnCreditDTO.getOrgId(), irnCreditDTO.getFinYear(),
					irnCreditDTO.getBranchCode(), screenCode);
			irnCreditVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(irnCreditDTO.getOrgId(), irnCreditDTO.getFinYear(),
							irnCreditDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			irnCreditVO.setUpdatedBy(irnCreditDTO.getCreatedBy());
			irnCreditVO.setCreatedBy(irnCreditDTO.getCreatedBy());
			createUpdateIrnCreditVOByIrnCreditDTO(irnCreditDTO, irnCreditVO);
			message = "IRN Credit Created Successfully";
		}
		irnCreditRepo.save(irnCreditVO);
		Map<String, Object> response = new HashMap<>();
		response.put("irnCreditVO", irnCreditVO);
		response.put("message", message);
		return response;
	}

	private IrnCreditVO createUpdateIrnCreditVOByIrnCreditDTO(@Valid IrnCreditDTO irnCreditDTO,
			IrnCreditVO irnCreditVO) {
		// Common fields
		irnCreditVO.setBranch(irnCreditDTO.getBranch());
		irnCreditVO.setBranchCode(irnCreditDTO.getBranchCode());
		irnCreditVO.setCreatedBy(irnCreditDTO.getCreatedBy());
		irnCreditVO.setUpdatedBy(irnCreditDTO.getUpdatedBy());
		irnCreditVO.setActive(irnCreditDTO.isActive());
		irnCreditVO.setCancel(irnCreditDTO.isCancel());
		irnCreditVO.setCancelRemarks(irnCreditDTO.getCancelRemarks());
		irnCreditVO.setFinYear(irnCreditDTO.getFinYear());
		irnCreditVO.setOrgId(irnCreditDTO.getOrgId());

		// IRN fields
		irnCreditVO.setVohNo(irnCreditDTO.getVohNo());
		irnCreditVO.setVohDate(irnCreditDTO.getVohDate());
		irnCreditVO.setPartyName(irnCreditDTO.getPartyName());
		irnCreditVO.setPartyCode(irnCreditDTO.getPartyCode());
		irnCreditVO.setSupRefNo(irnCreditDTO.getSupRefNo());
		irnCreditVO.setSupRefDate(irnCreditDTO.getSupRefDate());
		irnCreditVO.setPartyType(irnCreditDTO.getPartyType());
		irnCreditVO.setProduct(irnCreditDTO.getProduct());
		irnCreditVO.setCreditDays(irnCreditDTO.getCreditDays());
		irnCreditVO.setDueDate(irnCreditDTO.getDueDate());
		irnCreditVO.setState(irnCreditDTO.getState());
		irnCreditVO.setCity(irnCreditDTO.getCity());
		irnCreditVO.setOfficeType(irnCreditDTO.getOfficeType());
		irnCreditVO.setCurrency(irnCreditDTO.getCurrency());
		irnCreditVO.setStatus(irnCreditDTO.getStatus());
		irnCreditVO.setOriginBill(irnCreditDTO.getOriginBill());
		irnCreditVO.setRemarks(irnCreditDTO.getRemarks());
		irnCreditVO.setAddress(irnCreditDTO.getAddress());
		irnCreditVO.setShipRefNo(irnCreditDTO.getShipRefNo());
		irnCreditVO.setPincode(irnCreditDTO.getPincode());
		irnCreditVO.setGstin(irnCreditDTO.getGstin());
		irnCreditVO.setGstType(irnCreditDTO.getGstType());
		irnCreditVO.setBillingMonth(irnCreditDTO.getBillingMonth());
		irnCreditVO.setOtherInfo(irnCreditDTO.getOtherInfo());
		irnCreditVO.setSalesType(irnCreditDTO.getSalesType());
		irnCreditVO.setCreditRemarks(irnCreditDTO.getCreditRemarks());
		irnCreditVO.setCharges(irnCreditDTO.getCharges());

		// Summary fields
		irnCreditVO.setTotChargesBillCurrAmt(irnCreditDTO.getTotChargesBillCurrAmt());
		irnCreditVO.setTotChargesLCAmt(irnCreditDTO.getTotChargesLCAmt());
		irnCreditVO.setTotGrossBillAmt(irnCreditDTO.getTotGrossBillAmt());
		irnCreditVO.setTotGrossLCAmt(irnCreditDTO.getTotGrossLCAmt());
		irnCreditVO.setNetBillCurrAmt(irnCreditDTO.getNetBillCurrAmt());
		irnCreditVO.setNetLCAmt(irnCreditDTO.getNetLCAmt());
		irnCreditVO.setAmtInWords(irnCreditDTO.getAmtInWords());
		irnCreditVO.setExRate(irnCreditDTO.getExRate());
		irnCreditVO.setTotTaxAmt(irnCreditDTO.getTotTaxAmt());
		irnCreditVO.setSummaryExRate(irnCreditDTO.getSummaryExRate());
		irnCreditVO.setCurrentDate(irnCreditDTO.getCurrentDate());
		irnCreditVO.setCurrentDateValue(irnCreditDTO.getCurrentDateValue());
		irnCreditVO.setRoundOff(irnCreditDTO.getRoundOff());

		if (ObjectUtils.isNotEmpty(irnCreditVO.getId())) {
			List<IrnCreditChargesVO> irnCreditChargesVO1 = irnCreditChargesRepo.findByIrnCreditVO(irnCreditVO);
			irnCreditChargesRepo.deleteAll(irnCreditChargesVO1);
		}

		List<IrnCreditChargesVO> irnCreditChargesVOs = new ArrayList<>();
		for (IrnCreditChargesDTO irnCreditChargesDTO : irnCreditDTO.getIrnCreditChargeDTO()) {
			IrnCreditChargesVO irnCreditChargesVO = new IrnCreditChargesVO();
			irnCreditChargesVO.setJobNo(irnCreditChargesDTO.getJobNo());
			irnCreditChargesVO.setHouseNo(irnCreditChargesDTO.getHouseNo());
			irnCreditChargesVO.setChargeCode(irnCreditChargesDTO.getChargeCode());
			irnCreditChargesVO.setGchargeCode(irnCreditChargesDTO.getGchargeCode());
			irnCreditChargesVO.setChargeName(irnCreditChargesDTO.getChargeName());
			irnCreditChargesVO.setApplyOn(irnCreditChargesDTO.getApplyOn());
			irnCreditChargesVO.setCurrency(irnCreditChargesDTO.getCurrency());
			irnCreditChargesVO.setExRate(irnCreditChargesDTO.getExRate());
			irnCreditChargesVO.setRate(irnCreditChargesDTO.getRate());
			irnCreditChargesVO.setExampted(irnCreditChargesDTO.isExampted());
			irnCreditChargesVO.setFcAmt(irnCreditChargesDTO.getFcAmt());
			irnCreditChargesVO.setLcAmt(irnCreditChargesDTO.getLcAmt());
			irnCreditChargesVO.setTlcAmt(irnCreditChargesDTO.getTlcAmt());
			irnCreditChargesVO.setBillAmt(irnCreditChargesDTO.getBillAmt());
			irnCreditChargesVO.setGstPercentage(irnCreditChargesDTO.getGstPercentage());
			irnCreditChargesVO.setGst(irnCreditChargesDTO.getGst());
			irnCreditChargesVO.setIrnCreditVO(irnCreditVO);
			irnCreditChargesVOs.add(irnCreditChargesVO);
		}

		if (ObjectUtils.isNotEmpty(irnCreditVO.getId())) {
			List<IrnCreditGstVO> irnCreditGstVO1 = irnCreditGstRepo.findByIrnCreditVO(irnCreditVO);
			irnCreditGstRepo.deleteAll(irnCreditGstVO1);
		}

		List<IrnCreditGstVO> irnCreditGstVOs = new ArrayList<>();
		for (IrnCreditGstDTO irnCreditGstDTO : irnCreditDTO.getIrnCreditGstDTO()) {
			IrnCreditGstVO irnCreditGstVO = new IrnCreditGstVO();
			irnCreditGstVO.setChargeAcc(irnCreditGstDTO.getChargeAcc());
			irnCreditGstVO.setSubLodgerCode(irnCreditGstDTO.getSubLodgerCode());
			irnCreditGstVO.setDBillAmt(irnCreditGstDTO.getDBillAmt());
			irnCreditGstVO.setCrBillAmt(irnCreditGstDTO.getCrBillAmt());
			irnCreditGstVO.setDBLCAmt(irnCreditGstDTO.getDBLCAmt());
			irnCreditGstVO.setCrLCAmt(irnCreditGstDTO.getCrLCAmt());
			irnCreditGstVO.setGstRemarks(irnCreditGstDTO.getGstRemarks());
			irnCreditGstVO.setIrnCreditVO(irnCreditVO);
			irnCreditGstVOs.add(irnCreditGstVO);
		}
		irnCreditVO.setIrnCreditChargesVO(irnCreditChargesVOs);
		irnCreditVO.setIrnCreditGstVO(irnCreditGstVOs);
		return irnCreditVO;
	}

	@Override
	public List<IrnCreditVO> getIrnCreditByActive() {
		return irnCreditRepo.findIrnCreditByActive();

	}

	

	private List<Map<String, Object>> getPartyNameForIrn(Set<Object[]> irn) {
		List<Map<String, Object>> irnCredit = new ArrayList<>();
		for (Object[] sup : irn) {
			Map<String, Object> data = new HashMap<>();
			data.put("partyName", sup[0] != null ? sup[0].toString() : "");
			data.put("partyCode", sup[1] != null ? sup[1].toString() : "");
			data.put("partyType", sup[2] != null ? sup[2].toString() : "");
			irnCredit.add(data);
		}

		return irnCredit;
	}

	@Override
	public String getIrnCreditNoteDocId(Long orgId, String finYear, String branch, String branchCode) {
		String ScreenCode = "ICN";
		String result = irnCreditRepo.getIrnCreditDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}
	
	@Override
	public List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType) {
		
		return partyMasterRepo.findByOrgIdAndPartyTypeIgnoreCase(orgId,partyType);

	}

}
