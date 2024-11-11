package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.TaxInvoiceDTO;
import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.entity.TaxInvoiceVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface TaxInvoiceService {
	
//	TaxInvoice
	List<TaxInvoiceVO> getAllTaxInvoiceByFinYearAndOrgId(Long orgId, String finYear, String branchCode);

	Map<String, Object> updateCreateTaxInvoice(TaxInvoiceDTO taxInvoiceDTO) throws ApplicationException;

	TaxInvoiceVO getTaxInvoiceById(Long id);

	TaxInvoiceVO getTaxInvoiceByDocId(Long orgId, String docId);

	String getTaxInvoiceDocId(Long orgId, String finYear, String branch, String branchCode);

	List<Map<String, Object>> getChargeType(Long orgId);

	List<Map<String, Object>> getChargeCodeByChargeType(Long orgId, String chargeType);

	List<Map<String, Object>> getCurrencyAndExrates(Long orgId);

	List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType);

	List<Map<String, Object>> getPartyStateCodeDetails(Long orgId, Long id);

	List<Map<String, Object>> getPlaceOfSupplyDetails(Long orgId, Long id,String stateCode);

	List<Map<String, Object>> getPartyAddressDetails(Long orgId, Long id, String stateCode, String placeOfSupply);

	List<Map<String, Object>> getGstTypeDetails(Long orgId, String branchCode, String stateCode);

}
