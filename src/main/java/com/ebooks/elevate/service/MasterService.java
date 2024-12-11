package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.AccountDTO;
import com.ebooks.elevate.dto.BranchDTO;
import com.ebooks.elevate.dto.ChargeTypeRequestDTO;
import com.ebooks.elevate.dto.ChequeBookDTO;
import com.ebooks.elevate.dto.CostCenterDTO;
import com.ebooks.elevate.dto.EmployeeDTO;
import com.ebooks.elevate.dto.CoaDTO;
import com.ebooks.elevate.dto.ListOfValuesDTO;
import com.ebooks.elevate.dto.PartyMasterDTO;
import com.ebooks.elevate.dto.SacCodeDTO;
import com.ebooks.elevate.dto.SetTaxRateDTO;
import com.ebooks.elevate.dto.SubLedgerAccountDTO;
import com.ebooks.elevate.dto.TaxMasterDTO;
import com.ebooks.elevate.dto.TcsMasterDTO;
import com.ebooks.elevate.dto.TdsMasterDTO;
import com.ebooks.elevate.entity.AccountVO;
import com.ebooks.elevate.entity.BranchVO;
import com.ebooks.elevate.entity.ChargeTypeRequestVO;
import com.ebooks.elevate.entity.ChequeBookVO;
import com.ebooks.elevate.entity.CostCenterVO;
import com.ebooks.elevate.entity.EmployeeVO;
import com.ebooks.elevate.entity.CoaVO;
import com.ebooks.elevate.entity.ListOfValuesVO;
import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.entity.SacCodeVO;
import com.ebooks.elevate.entity.SetTaxRateVO;
import com.ebooks.elevate.entity.SubLedgerAccountVO;
import com.ebooks.elevate.entity.TaxMasterVO;
import com.ebooks.elevate.entity.TcsMasterVO;
import com.ebooks.elevate.entity.TdsMasterVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface MasterService {

	// Branch

	List<BranchVO> getAllBranch(Long orgid);

	Optional<BranchVO> getBranchById(Long branchid);

	Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws Exception;

	void deleteBranch(Long branchid);

	// employee

	List<EmployeeVO> getAllEmployee();

	List<EmployeeVO> getAllEmployeeByOrgId(Long orgId);

	Optional<EmployeeVO> getEmployeeById(Long employeeid);

	Map<String, Object> createEmployee(EmployeeDTO employeeDTO) throws ApplicationException;

	void deleteEmployee(Long employeeid);

//	SetTaxRateVO
	List<SetTaxRateVO> getAllSetTaxRateByOrgId(Long orgId);

	List<SetTaxRateVO> getAllSetTaxRateById(Long id);

	SetTaxRateVO updateCreateSetTaxRate(@Valid SetTaxRateDTO setTaxRateDTO) throws Exception;

	List<SetTaxRateVO> getSetTaxRateByActive();

//	TaxMasterVO
	TaxMasterVO updateCreateTaxMaster(TaxMasterDTO taxMasterDTO) throws ApplicationException;

	List<TaxMasterVO> getAllTaxMasterByOrgId(Long orgId);

	List<TaxMasterVO> getAllTaxMasterById(Long id);

	List<TaxMasterVO> getTaxMasterByActive();

//	TcsMasterVO 
	List<TcsMasterVO> getAllTcsMasterByOrgId(Long orgId);

	List<TcsMasterVO> getAllTcsMasterById(Long id);

	TcsMasterVO updateCreateTcsMaster(@Valid TcsMasterDTO tcsMasterDTO) throws ApplicationException;

	List<TcsMasterVO> getTcsMasterByActive();

//	TdsMasterVO
	List<TdsMasterVO> getAllTdsMasterByOrgId(Long orgId);

	List<TdsMasterVO> getAllTdsMasterById(Long id);

	TdsMasterVO updateCreateTdsMaster(@Valid TdsMasterDTO tdsMasterDTO) throws ApplicationException;

	List<TdsMasterVO> getTdsMasterByActive();

//	AccountVO
	List<AccountVO> getAllAccountByOrgId(Long orgId);

	AccountVO updateCreateAccount(@Valid AccountDTO accountDTO) throws ApplicationException;

	List<AccountVO> getAllAccountById(Long id);

	List<AccountVO> getAccountByActive();


//	SacCode
	List<SacCodeVO> getAllSacCodeById(Long id);

	List<SacCodeVO> getAllSacCodeByOrgId(Long orgId);

	List<SacCodeVO> getAllActiveSacCodeByOrgId(Long orgId);

	SacCodeVO updateCreateSacCode(@Valid SacCodeDTO sacCodeDTO) throws ApplicationException;

//	List<SacCodeVO> getSacCodeByActive();

//	SubLedgerAccount
	List<SubLedgerAccountVO> getAllSubLedgerAccountByOrgId(Long orgId);

	SubLedgerAccountVO updateCreateSubLedgerAccount(@Valid SubLedgerAccountDTO subLedgerAccountDTO)
			throws ApplicationException;

	List<SubLedgerAccountVO> getAllSubLedgerAccountById(Long id);

	List<SubLedgerAccountVO> getSubLedgerAccountByActive();

//	CostCenterVO
	List<CostCenterVO> getAllCostCenterByOrgId(Long orgId);

	CostCenterVO updateCreateCostCenter(@Valid CostCenterDTO costCenterDTO) throws ApplicationException;

	List<CostCenterVO> getAllCostCenterById(Long id);

	List<CostCenterVO> getCostCenterByActive();

//	ChequeBook
	List<ChequeBookVO> getAllChequeBookByOrgId(Long orgId);

	ChequeBookVO updateCreateChequeBook(@Valid ChequeBookDTO chequeBookDTO) throws ApplicationException;

	List<ChequeBookVO> getAllChequeBookById(Long id);

	List<ChequeBookVO> getChequeBookByActive();

//	ChargeTypeRequest
	List<ChargeTypeRequestVO> getAllChargeTypeRequestByOrgId(Long orgId);

	List<Map<String, Object>> getChargeType(Long orgId);

	ChargeTypeRequestVO updateCreateChargeTypeRequest(@Valid ChargeTypeRequestDTO chargeTypeRequestDTO)
			throws ApplicationException;

	List<ChargeTypeRequestVO> getAllChargeTypeRequestById(Long id);

	List<ChargeTypeRequestVO> getChargeTypeRequestByActive();

	List<Map<String, Object>> getSalesAccountFromGroup(Long orgId);

	List<Map<String, Object>> getPaymentAccountFromGroup(Long orgId);
//	ListOfValues

	List<ListOfValuesVO> getListOfValuesById(Long id);

	List<ListOfValuesVO> getListOfValuesByOrgId(Long orgid);

	ListOfValuesVO updateCreateListOfValues(@Valid ListOfValuesDTO listOfValuesDTO) throws ApplicationException;

	// PartyMaster

	List<PartyMasterVO> getPartyMasterByOrgId(Long orgid);

	List<PartyMasterVO> getPartyMasterById(Long id);

	PartyMasterVO updateCreatePartyMaster(@Valid PartyMasterDTO partyMasterDTO) throws ApplicationException;

	String getPartyMasterDocId(Long orgId, String finYear, String branch, String branchCode);

}
