package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.BranchDTO;
import com.ebooks.elevate.dto.EmployeeDTO;
import com.ebooks.elevate.entity.BranchVO;
import com.ebooks.elevate.entity.EmployeeVO;
import com.ebooks.elevate.entity.SacCodeVO;
import com.ebooks.elevate.entity.SetTaxRateVO;
import com.ebooks.elevate.entity.SubLedgerAccountVO;
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


	List<SetTaxRateVO> getSetTaxRateByActive();


//	SacCode
	List<SacCodeVO> getAllSacCodeById(Long id);

	List<SacCodeVO> getAllSacCodeByOrgId(Long orgId);

	List<SacCodeVO> getAllActiveSacCodeByOrgId(Long orgId);


//	List<SacCodeVO> getSacCodeByActive();

//	SubLedgerAccount
	List<SubLedgerAccountVO> getAllSubLedgerAccountByOrgId(Long orgId);


	List<SubLedgerAccountVO> getAllSubLedgerAccountById(Long id);

	List<SubLedgerAccountVO> getSubLedgerAccountByActive();



}
