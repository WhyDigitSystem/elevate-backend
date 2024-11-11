package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.ArBillBalanceDTO;
import com.ebooks.elevate.dto.ReceiptDTO;
import com.ebooks.elevate.entity.ArBillBalanceVO;
import com.ebooks.elevate.entity.ReceiptVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface ARService {

	// Receipt
	List<ReceiptVO> getAllReceiptReceivableByOrgId(Long orgId);

	Map<String, Object> updateCreateReceiptReceivable(@Valid ReceiptDTO receiptReceivableDTO)
			throws ApplicationException;

	List<ReceiptVO> getAllReceiptReceivableById(Long id);

	List<ReceiptVO> getReceiptReceivableByActive();

	List<Map<String, Object>> getCustomerNameAndCodeForReceipt(Long orgId);

	String getReceiptDocId(Long orgId, String finYear, String branch, String branchCode);

//	ARBillBalance
	List<ArBillBalanceVO> getAllArBillBalanceByOrgId(Long orgId);

	List<ArBillBalanceVO> getAllArBillBalanceById(Long id);

	Map<String, Object> updateCreateArBillBalance(@Valid ArBillBalanceDTO arBillBalanceDTO) throws ApplicationException;

	List<ArBillBalanceVO> getArBillBalanceByActive();

	String getArBillBalanceDocId(Long orgId, String finYear, String branch, String branchCode);
	
	List<Map<String, Object>> getPartyNameAndCodeForArBillBalance(Long orgId);


// 	ReceiptRegister
	List<Map<String, Object>> getAllReceiptRegister(Long orgId,
			String fromDate, String toDate, String subLedgerName);
}
