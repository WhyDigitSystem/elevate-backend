package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.BranchDTO;
import com.ebooks.elevate.dto.EmployeeDTO;
import com.ebooks.elevate.dto.GroupMapping2DTO;
import com.ebooks.elevate.dto.GroupMappingDTO;
import com.ebooks.elevate.dto.ListOfValuesDTO;
import com.ebooks.elevate.dto.SegmentMappingDTO;
import com.ebooks.elevate.entity.BranchVO;
import com.ebooks.elevate.entity.CoaVO;
import com.ebooks.elevate.entity.EmployeeVO;
import com.ebooks.elevate.entity.GroupMappingVO;
import com.ebooks.elevate.entity.ListOfValuesVO;
import com.ebooks.elevate.entity.SacCodeVO;
import com.ebooks.elevate.entity.SegmentMappingVO;
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

	
	//List Of Values
	

	List<ListOfValuesVO> getAllListOfValuesByOrgId(Long orgId);

	List<ListOfValuesVO> getListOfValuesById(Long listOfValuesId);


	ListOfValuesVO updateCreateListOfValues(@Valid ListOfValuesDTO listOfValuesDTO) throws ApplicationException;

	
	
	
	// Group Mapping\
	List<Map<String, Object>> getBudgetGroup(Long orgId,String name) throws ApplicationException;
	
	List<CoaVO>getSubGroup(Long orgId);

	List<CoaVO> getLegders(Long orgId, List<String> accountCode);
	
	Map<String, Object> createUpdateGroupMapping(GroupMappingDTO groupMappingDTO) throws ApplicationException;

	List<GroupMappingVO> getGroupMappingAll(Long orgId);

	Optional<GroupMappingVO> getGroupMappingById(Long id);

	Map<String, Object> createUpdateGroupMapping2(GroupMapping2DTO groupMapping2DTO) throws ApplicationException;


	List<Map<String, Object>> getLedgersDetailsForGroupMapping(Long orgId, String segment);
	
	
	
	// Segment Mapping
	
	Map<String, Object> createUpdateSegmentMapping(SegmentMappingDTO segmentMappingDTO) throws ApplicationException;

	List<SegmentMappingVO> getAllSegmentMapping(Long orgId);

	Optional<SegmentMappingVO> getSegmentMappingById(Long id);

	List<Map<String, Object>> getSegmentDetailsByClient(Long orgId);
	
	
}


