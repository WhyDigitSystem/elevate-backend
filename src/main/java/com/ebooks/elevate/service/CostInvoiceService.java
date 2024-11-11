package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.CostInvoiceDTO;
import com.ebooks.elevate.entity.CostInvoiceVO;
import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface CostInvoiceService {

	// CostInvoice
	
	List<CostInvoiceVO> getAllCostInvoiceByOrgId(Long orgId);

	Map<String, Object> updateCreateCostInvoice(@Valid CostInvoiceDTO costInvoiceDTO) throws ApplicationException;

	List<CostInvoiceVO> getAllCostInvoiceById(Long id);

	List<CostInvoiceVO> getCostInvoiceByActive();

	CostInvoiceVO getCostInvoiceByDocId(Long orgId, String docId);

	String getCostInvoiceDocId(Long orgId, String finYear, String branch, String branchCode);

	List<Map<String, Object>> getChargeType(Long orgId);

	List<Map<String, Object>> getChargeCodeByChargeType(Long orgId, String chargeType);

	List<Map<String, Object>> getCurrencyAndExrates(Long orgId);

	List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType);

	List<Map<String, Object>> getPartyStateCodeDetails(Long orgId, Long id);

	List<Map<String, Object>> getPartyAddressDetails(Long orgId, Long id, String stateCode, String placeOfSupply);

	List<Map<String, Object>> getGstTypeDetails(Long orgId, String branchCode, String stateCode);

	List<Map<String, Object>> getPlaceOfSupplyDetails(Long orgId, Long id, String stateCode);

}
