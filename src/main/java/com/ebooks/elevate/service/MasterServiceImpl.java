package com.ebooks.elevate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.BranchDTO;
import com.ebooks.elevate.dto.EmployeeDTO;
import com.ebooks.elevate.dto.GroupLedgersDTO;
import com.ebooks.elevate.dto.GroupMapping2DTO;
import com.ebooks.elevate.dto.GroupMapping2SubGroupDTO;
import com.ebooks.elevate.dto.GroupMappingDTO;
import com.ebooks.elevate.dto.ListOfValuesDTO;
import com.ebooks.elevate.dto.ListOfValuesDetailsDTO;
import com.ebooks.elevate.dto.SegmentMappingDTO;
import com.ebooks.elevate.dto.SegmentMappingDetailsDTO;
import com.ebooks.elevate.dto.SubGroupDetailsDTO;
import com.ebooks.elevate.entity.BranchVO;
import com.ebooks.elevate.entity.CoaVO;
import com.ebooks.elevate.entity.EmployeeVO;
import com.ebooks.elevate.entity.GroupLedgersVO;
import com.ebooks.elevate.entity.GroupMappingVO;
import com.ebooks.elevate.entity.ListOfValuesDetailsVO;
import com.ebooks.elevate.entity.ListOfValuesVO;
import com.ebooks.elevate.entity.SacCodeVO;
import com.ebooks.elevate.entity.SegmentMappingDetailsVO;
import com.ebooks.elevate.entity.SegmentMappingVO;
import com.ebooks.elevate.entity.SetTaxRateVO;
import com.ebooks.elevate.entity.SubGroupDetailsVO;
import com.ebooks.elevate.entity.SubLedgerAccountVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.BranchRepo;
import com.ebooks.elevate.repo.CoaRepo;
import com.ebooks.elevate.repo.DocumentTypeMappingDetailsRepo;
import com.ebooks.elevate.repo.EmployeeRepo;
import com.ebooks.elevate.repo.GroupLedgersRepo;
import com.ebooks.elevate.repo.GroupMappingRepo;
import com.ebooks.elevate.repo.ListOfValuesDetailsRepo;
import com.ebooks.elevate.repo.ListOfValuesRepo;
import com.ebooks.elevate.repo.SacCodeRepo;
import com.ebooks.elevate.repo.SegmentMappingDetailsRepo;
import com.ebooks.elevate.repo.SegmentMappingRepo;
import com.ebooks.elevate.repo.SetTaxRateRepo;
import com.ebooks.elevate.repo.SubGroupDetailsRepo;
import com.ebooks.elevate.repo.SubLedgerAccountRepo;

@Service
public class MasterServiceImpl implements MasterService {
	public static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	GroupMappingRepo groupMappingRepo;

	@Autowired
	SubGroupDetailsRepo subGroupDetailsRepo;

	@Autowired
	GroupLedgersRepo groupLedgersRepo;

	@Autowired
	SetTaxRateRepo setTaxRateRepo;

	@Autowired
	ListOfValuesRepo listOfValuesRepo;

	@Autowired
	ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	CoaRepo coaRepo;

	@Autowired
	SacCodeRepo sacCodeRepo;

	@Autowired
	SubLedgerAccountRepo subLedgerAccountRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	SegmentMappingRepo segmentMappingRepo;
	
	@Autowired
	SegmentMappingDetailsRepo segmentMappingDetailsRepo;
	// Branch

	@Override
	public List<BranchVO> getAllBranch(Long orgid) {
		return branchRepo.findAll(orgid);
	}

	@Override
	public Optional<BranchVO> getBranchById(Long branchid) {

		return branchRepo.findById(branchid);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws Exception {
		BranchVO branchVO;
		String message = null;

		if (ObjectUtils.isEmpty(branchDTO.getId())) {
			// Check if the branch already exists for creation
			if (branchRepo.existsByBranchAndOrgId(branchDTO.getBranch(), branchDTO.getOrgId())) {
				String errorMessage = String.format("This Branch: %s Already Exists in This Organization",
						branchDTO.getBranch());
				throw new ApplicationException(errorMessage);
			}

			if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
				String errorMessage = String.format("This BranchCode: %s Already Exists in This Organization",
						branchDTO.getBranchCode());
				throw new ApplicationException(errorMessage);
			}

			// Create new branch
			branchVO = new BranchVO();
			branchVO.setCreatedBy(branchDTO.getCreatedBy());
			branchVO.setUpdatedBy(branchDTO.getCreatedBy());
			message = "Branch Created Successfully";
		} else {
			// Update existing branch
			branchVO = branchRepo.findById(branchDTO.getId())
					.orElseThrow(() -> new ApplicationException("Branch not found with id: " + branchDTO.getId()));

			branchVO.setUpdatedBy(branchDTO.getCreatedBy());

			if (!branchVO.getBranch().equalsIgnoreCase(branchDTO.getBranch())) {
				if (branchRepo.existsByBranchAndOrgId(branchDTO.getBranch(), branchDTO.getOrgId())) {
					String errorMessage = String.format("This Branch: %s Already Exists in This Organization",
							branchDTO.getBranch());
					throw new ApplicationException(errorMessage);
				}
				branchVO.setBranch(branchDTO.getBranch().toUpperCase());
			}

			if (!branchVO.getBranchCode().equalsIgnoreCase(branchDTO.getBranchCode())) {
				if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
					String errorMessage = String.format("This BranchCode: %s Already Exists in This Organization",
							branchDTO.getBranchCode());
					throw new ApplicationException(errorMessage);
				}
				branchVO.setBranchCode(branchDTO.getBranchCode().toUpperCase());
			}

			message = "Branch Updated Successfully";
		}

		getBranchVOFromBranchDTO(branchVO, branchDTO);
		branchRepo.save(branchVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("branchVO", branchVO);
		return response;
	}

	private void getBranchVOFromBranchDTO(BranchVO branchVO, BranchDTO branchDTO) {
		branchVO.setBranch(branchDTO.getBranch().toUpperCase());
		branchVO.setBranchCode(branchDTO.getBranchCode().toUpperCase());
		branchVO.setOrgId(branchDTO.getOrgId());
		branchVO.setAddressLine1(branchDTO.getAddressLine1());
		branchVO.setAddressLine2(branchDTO.getAddressLine2());
		branchVO.setPan(branchDTO.getPan());
		branchVO.setGstIn(branchDTO.getGstIn());
		branchVO.setPhone(branchDTO.getPhone());
		branchVO.setState(branchDTO.getState().toUpperCase());
		branchVO.setCity(branchDTO.getCity().toUpperCase());
		branchVO.setPinCode(branchDTO.getPinCode());
		branchVO.setCountry(branchDTO.getCountry().toUpperCase());
		branchVO.setStateNo(branchDTO.getStateNo().toUpperCase());
		branchVO.setStateCode(branchDTO.getStateCode().toUpperCase());
		branchVO.setLccurrency(branchDTO.getLccurrency());
		branchVO.setCancelRemarks(branchDTO.getCancelRemarks());
		branchVO.setActive(branchDTO.isActive());
	}

	@Override
	public void deleteBranch(Long branchid) {
		branchRepo.deleteById(branchid);
	}

	// Employee

	@Override
	public List<EmployeeVO> getAllEmployeeByOrgId(Long orgId) {
		return employeeRepo.findAllEmployeeByOrgId(orgId);
	}

	@Override
	public List<EmployeeVO> getAllEmployee() {
		return employeeRepo.findAll();
	}

	@Override
	public Optional<EmployeeVO> getEmployeeById(Long employeeid) {
		return employeeRepo.findById(employeeid);
	}

	@Override
	public Map<String, Object> createEmployee(EmployeeDTO employeeDTO) throws ApplicationException {
		EmployeeVO employeeVO;
		String message = null;

		if (ObjectUtils.isEmpty(employeeDTO.getId())) {
			// Check for existing employee by employee code within the organization
			if (employeeRepo.existsByEmployeeCodeAndOrgId(employeeDTO.getEmployeeCode(), employeeDTO.getOrgId())) {
				String errorMessage = String.format("This EmployeeCode: %s Already Exists in This Organization",
						employeeDTO.getEmployeeCode());
				throw new ApplicationException(errorMessage);
			}
			// Create new employee
			employeeVO = new EmployeeVO();
			employeeVO.setCreatedBy(employeeDTO.getCreatedBy());
			employeeVO.setUpdatedBy(employeeDTO.getCreatedBy());
			message = "Employee Creation Successfully";
		} else {
			// Update existing employee
			employeeVO = employeeRepo.findById(employeeDTO.getId()).orElseThrow(
					() -> new ApplicationException("ID is Not Found Any Information: " + employeeDTO.getId()));

			employeeVO.setUpdatedBy(employeeDTO.getCreatedBy());

			if (!employeeVO.getEmployeeCode().equalsIgnoreCase(employeeDTO.getEmployeeCode())) {
				if (employeeRepo.existsByEmployeeCodeAndOrgId(employeeDTO.getEmployeeCode(), employeeDTO.getOrgId())) {
					String errorMessage = String.format("This EmployeeCode: %s Already Exists in This Organization",
							employeeDTO.getEmployeeCode());
					throw new ApplicationException(errorMessage);
				}
				employeeVO.setEmployeeCode(employeeDTO.getEmployeeCode());
			}
			message = "Employee Update Successfully";
		}

		// Map the remaining fields
		getEmployeeVOFromEmployeeDTO(employeeVO, employeeDTO);

		// Save the entity
		employeeRepo.save(employeeVO);

		// Prepare the response
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("employeeVO", employeeVO);

		return response;
	}

	private void getEmployeeVOFromEmployeeDTO(EmployeeVO employeeVO, EmployeeDTO employeeDTO) {
		employeeVO.setEmployeeCode(employeeDTO.getEmployeeCode());
		employeeVO.setEmployeeName(employeeDTO.getEmployeeName());
		employeeVO.setGender(employeeDTO.getGender());
		employeeVO.setEmail(employeeDTO.getEmail());
		employeeVO.setDepartment(employeeDTO.getDepartment());
		employeeVO.setDesignation(employeeDTO.getDesignation());
		employeeVO.setDateOfBirth(employeeDTO.getDateOfBirth());
		employeeVO.setJoiningDate(employeeDTO.getJoiningdate());
		employeeDTO.setEmail(employeeDTO.getEmail());
		employeeVO.setOrgId(employeeDTO.getOrgId());
		employeeVO.setCancel(employeeDTO.isCancel());
		employeeVO.setActive(employeeDTO.isActive());
		employeeVO.setCancelRemark(employeeDTO.getCancelRemark());
	}

	@Override
	public void deleteEmployee(Long employeeid) {
		employeeRepo.deleteById(employeeid);
	}

	// setTaxesRate
	@Override
	public List<SetTaxRateVO> getAllSetTaxRateByOrgId(Long orgId) {
		List<SetTaxRateVO> setTaxRateVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  SetTaxRateInformation BY OrgId : {}", orgId);
			setTaxRateVO = setTaxRateRepo.getAllSetTaxRateByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  SetTaxRateInformation For All OrgId.");
			setTaxRateVO = setTaxRateRepo.findAll();
		}
		return setTaxRateVO;
	}

	@Override
	public List<SetTaxRateVO> getAllSetTaxRateById(Long id) {
		List<SetTaxRateVO> setTaxRateVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  SetTaxRateInformation BY Id : {}", id);
			setTaxRateVO = setTaxRateRepo.getAllSetTaxRateById(id);
		} else {
			LOGGER.info("Successfully Received  SetTaxRateInformation For All Id.");
			setTaxRateVO = setTaxRateRepo.findAll();
		}
		return setTaxRateVO;
	}

	@Override
	public List<SetTaxRateVO> getSetTaxRateByActive() {
		return setTaxRateRepo.findSetTaxRateByActive();
	}

	// SacCode

	@Override
	public List<SacCodeVO> getAllSacCodeById(Long id) {
		List<SacCodeVO> sacCodeVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  sacCode Information BY Id : {}", id);
			sacCodeVO = sacCodeRepo.getAllSacCodeById(id);
		} else {
			LOGGER.info("Successfully Received  SacCode Information For All Id.");
			sacCodeVO = sacCodeRepo.findAll();
		}
		return sacCodeVO;
	}

	@Override
	public List<SacCodeVO> getAllActiveSacCodeByOrgId(Long orgId) {
		List<SacCodeVO> sacCodeVO = new ArrayList<>();
		sacCodeVO = sacCodeRepo.getAllActiveSacCodeByOrgId(orgId);

		return sacCodeVO;
	}

	@Override
	public List<SacCodeVO> getAllSacCodeByOrgId(Long orgId) {
		List<SacCodeVO> sacCodeVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  SacCode Information BY OrgId : {}", orgId);
			sacCodeVO = sacCodeRepo.getAllSacCodeByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  SacCode Information For All OrgId.");
			sacCodeVO = sacCodeRepo.findAll();
		}
		return sacCodeVO;
	}

	@Override
	public List<SubLedgerAccountVO> getAllSubLedgerAccountById(Long id) {
		List<SubLedgerAccountVO> subLedgerAccountVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  SubLedgerAccount Information BY Id : {}", id);
			subLedgerAccountVO = subLedgerAccountRepo.getAllSubLedgerAccountById(id);
		} else {
			LOGGER.info("Successfully Received  SubLedgerAccount Information For All Id.");
			subLedgerAccountVO = subLedgerAccountRepo.findAll();
		}
		return subLedgerAccountVO;
	}

	@Override
	public List<SubLedgerAccountVO> getAllSubLedgerAccountByOrgId(Long orgId) {
		List<SubLedgerAccountVO> subLedgerAccountVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  SubLedgerAccount Information BY OrgId : {}", orgId);
			subLedgerAccountVO = subLedgerAccountRepo.getAllSubLedgerAccountByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  SubLedgerAccount Information For All OrgId.");
			subLedgerAccountVO = subLedgerAccountRepo.findAll();
		}
		return subLedgerAccountVO;
	}

	@Override
	public List<SubLedgerAccountVO> getSubLedgerAccountByActive() {
		return subLedgerAccountRepo.findSubLedgerAccountByActive();

	}

	@Override
	public List<ListOfValuesVO> getAllListOfValuesByOrgId(Long orgId) {
		return listOfValuesRepo.findAllByOrgId(orgId);
	}

	@Override
	public List<ListOfValuesVO> getListOfValuesById(Long id) {
		List<ListOfValuesVO> listOfValuesVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  List Of Values Information BY Id : {}", id);
			listOfValuesVO = listOfValuesRepo.getAllById(id);
		}
		return listOfValuesVO;
	}

	@Override
	public ListOfValuesVO updateCreateListOfValues(@Valid ListOfValuesDTO listOfValuesDTO) throws ApplicationException {
		ListOfValuesVO listOfValuesVO = new ListOfValuesVO();
		boolean isUpdate = false;
		if (ObjectUtils.isNotEmpty(listOfValuesDTO.getId())) {
			isUpdate = true;
			listOfValuesVO = listOfValuesRepo.findById(listOfValuesDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid ListOfValues details"));
			listOfValuesVO.setUpdatedBy(listOfValuesDTO.getCreatedBy());
		}

		else {
			if (listOfValuesRepo.existsByNameAndOrgId(listOfValuesDTO.getName(), listOfValuesDTO.getOrgId())) {
				throw new ApplicationException("List Name already Exists");
			}
			listOfValuesVO.setUpdatedBy(listOfValuesDTO.getCreatedBy());
			listOfValuesVO.setCreatedBy(listOfValuesDTO.getCreatedBy());
		}

		if (isUpdate) {
			ListOfValuesVO listOfValues = listOfValuesRepo.findById(listOfValuesDTO.getId()).orElse(null);
			if (!listOfValues.getName().equals(listOfValuesDTO.getName())) {
				if (listOfValuesRepo.existsByNameAndOrgId(listOfValuesDTO.getName(), listOfValuesDTO.getOrgId())) {
					throw new ApplicationException("List Name already Exists");
				}
			}
		}
		getListOfValuesVOFromTypesOfValuesDTO(listOfValuesDTO, listOfValuesVO);
		listOfValuesVO = listOfValuesRepo.save(listOfValuesVO);

		List<ListOfValuesDetailsVO> listOfValues1VOList = listOfValuesDetailsRepo.findByListOfValuesVO(listOfValuesVO);
		listOfValuesDetailsRepo.deleteAll(listOfValues1VOList);

		List<ListOfValuesDetailsVO> listOfValues1VOs = new ArrayList<>();
		if (listOfValuesDTO.getListOfValuesDetailsDTO() != null) {
			for (ListOfValuesDetailsDTO listOfValues1DTO : listOfValuesDTO.getListOfValuesDetailsDTO()) {

				ListOfValuesDetailsVO listOfValues1VO = new ListOfValuesDetailsVO();
				listOfValues1VO.setValuesDescription(listOfValues1DTO.getValuesDescription());
				listOfValues1VO.setActive(listOfValues1DTO.isActive());
				listOfValues1VO.setListOfValuesVO(listOfValuesVO);
				listOfValues1VOs.add(listOfValues1VO);
			}
		}

		listOfValuesVO.setUpdatedBy(listOfValuesDTO.getCreatedBy());
		listOfValuesVO.setListOfValuesDetailsVO(listOfValues1VOs);
		return listOfValuesRepo.save(listOfValuesVO);

	}

	private void getListOfValuesVOFromTypesOfValuesDTO(@Valid ListOfValuesDTO listOfValuesDTO,
			ListOfValuesVO listOfValuesVO) {
		listOfValuesVO.setName(listOfValuesDTO.getName());
		listOfValuesVO.setOrgId(listOfValuesDTO.getOrgId());
		listOfValuesVO.setActive(true);

	}

	@Override
	public List<Map<String, Object>> getBudgetGroup(Long orgId, String name) throws ApplicationException {

		Set<Object[]> obj = listOfValuesRepo.getListValuesDetailsForBudget(orgId, name);
		return ListofValue(obj);
	}

	private List<Map<String, Object>> ListofValue(Set<Object[]> obj) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] det : obj) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("valuesDescription", det[0] != null ? det[0].toString() : "");
			details.add(mp);
		}
		return details;
	}

	@Override
	public List<CoaVO> getSubGroup(Long orgId) {

		return coaRepo.getOrgIdAndSubGroupName(orgId);
	}

	@Override
	public List<CoaVO> getLegders(Long orgId, List<String> accountCode) {

		return coaRepo.getOrgIdAndSubGroupName(orgId, accountCode);
	}

	@Override
	public Map<String, Object> createUpdateGroupMapping(GroupMappingDTO groupMappingDTO) throws ApplicationException {

		GroupMappingVO groupMappingVO = new GroupMappingVO();

		String message = null;

		if (ObjectUtils.isEmpty(groupMappingDTO.getId())) {

			if (groupMappingRepo.existsByOrgIdAndGroupNameIgnoreCase(groupMappingDTO.getOrgId(),
					groupMappingDTO.getGroupName())) {
				throw new ApplicationException("Group Name already Exists");
			}
			groupMappingVO.setCreatedBy(groupMappingDTO.getCreatedBy());
			groupMappingVO.setUpdatedBy(groupMappingDTO.getCreatedBy());
			groupMappingVO.setGroupName(groupMappingDTO.getGroupName());

			message = "Group Mapping Creation SuccessFully";

		} else {

			groupMappingVO = groupMappingRepo.findById(groupMappingDTO.getId()).orElseThrow(
					() -> new ApplicationException("Group DTO not found with id: " + groupMappingDTO.getId()));
			if (!groupMappingVO.getGroupName().equals(groupMappingDTO.getGroupName())) {
				if (groupMappingRepo.existsByOrgIdAndGroupNameIgnoreCase(groupMappingDTO.getOrgId(),
						groupMappingDTO.getGroupName())) {
					throw new ApplicationException("Group Name already Exists");
				}
				groupMappingVO.setGroupName(groupMappingDTO.getGroupName());
			}
			groupMappingVO.setUpdatedBy(groupMappingDTO.getCreatedBy());

			message = "Group Mapping Updation SuccessFully";
		}

		groupMappingVO = getGroupMappingVOFromGroupMappingDTO(groupMappingVO, groupMappingDTO);
		groupMappingRepo.save(groupMappingVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("groupMappingVO", groupMappingVO); // Return the list of saved records
		return response;
	}

	private GroupMappingVO getGroupMappingVOFromGroupMappingDTO(GroupMappingVO groupMappingVO,
			GroupMappingDTO groupMappingDTO) {

		groupMappingVO.setActive(true);

		groupMappingVO.setOrgId(groupMappingDTO.getOrgId());

		if (groupMappingDTO.getId() != null) {
			List<SubGroupDetailsVO> detailsVOs = subGroupDetailsRepo.findByGroupMappingVO(groupMappingVO);
			subGroupDetailsRepo.deleteAll(detailsVOs);

			List<GroupLedgersVO> ledgersVOs = groupLedgersRepo.findByGroupMappingVO(groupMappingVO);
			groupLedgersRepo.deleteAll(ledgersVOs);
		}

		List<SubGroupDetailsVO> subGroupDetailsVO = new ArrayList<>();
		List<SubGroupDetailsDTO> subGroupDetailsDTOs = groupMappingDTO.getSubGroupDetailsVO();
		if (subGroupDetailsDTOs != null && !subGroupDetailsDTOs.isEmpty()) {
			for (SubGroupDetailsDTO subGroupDetailsDTO : subGroupDetailsDTOs) {
				SubGroupDetailsVO detailsVO = new SubGroupDetailsVO();
				detailsVO.setAccountCode(subGroupDetailsDTO.getAccountCode());
				detailsVO.setAccountName(subGroupDetailsDTO.getAccountName());
				detailsVO.setActive(subGroupDetailsDTO.isActive());
				detailsVO.setDisplaySeq(subGroupDetailsDTO.getDisplaySeq());
				detailsVO.setGroupMappingVO(groupMappingVO);
				subGroupDetailsVO.add(detailsVO);
			}
			groupMappingVO.setSubGroupDetailsVO(subGroupDetailsVO);
		}

		List<GroupLedgersVO> groupLedgersVO = new ArrayList<>();
		List<GroupLedgersDTO> groupLedgersDTOs = groupMappingDTO.getGroupLedgersDTO();
		if (groupLedgersDTOs != null && !groupLedgersDTOs.isEmpty()) {
			for (GroupLedgersDTO groupLedgersDTO : groupLedgersDTOs) {
				GroupLedgersVO ledgersVO = new GroupLedgersVO();
				ledgersVO.setParentCode(groupLedgersDTO.getParentCode());
				ledgersVO.setGroupName(groupLedgersDTO.getGroupName());
				ledgersVO.setOrgId(groupMappingDTO.getOrgId());
				ledgersVO.setDisplaySeq(groupLedgersDTO.getDisplaySeq());
				ledgersVO.setMainGroupName(groupMappingDTO.getGroupName());
				ledgersVO.setAccountCode(groupLedgersDTO.getAccountCode());
				ledgersVO.setAccountName(groupLedgersDTO.getAccountName());
				ledgersVO.setActive(groupLedgersDTO.isActive());
				ledgersVO.setGroupMappingVO(groupMappingVO);
				groupLedgersVO.add(ledgersVO);
			}
			groupMappingVO.setGroupLedgresVOs(groupLedgersVO);
		}
		return groupMappingVO;
	}

	@Override
	public List<GroupMappingVO> getGroupMappingAll(Long orgId) {
		return groupMappingRepo.getGroupMappingAll(orgId);
	}

	@Override
	public Optional<GroupMappingVO> getGroupMappingById(Long id) {
		return groupMappingRepo.findById(id);
	}

	@Override
	public Map<String, Object> createUpdateGroupMapping2(GroupMapping2DTO groupMapping2DTO)
			throws ApplicationException {

		if (ObjectUtils.isEmpty(groupMapping2DTO)) {
			throw new ApplicationException("GroupMapping2DTO is null or empty");
		}

		GroupMappingVO groupMappingVO;
		String message;

		if (groupMapping2DTO.getId() == null) {
			groupMappingVO = new GroupMappingVO();
			groupMappingVO.setCreatedBy(groupMapping2DTO.getCreatedBy());
			groupMappingVO.setUpdatedBy(groupMapping2DTO.getCreatedBy());
			message = "GroupMapping2 Created Successfully";
		} else {
			// Update flow

			groupMappingVO = groupMappingRepo.findById(groupMapping2DTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Groupmapping Details"));

			groupMappingVO.setGroupName(groupMapping2DTO.getSegment());

			groupMappingVO.setUpdatedBy(groupMapping2DTO.getCreatedBy());
			message = "GroupMapping2 Updated Successfully";
		}

		groupMappingVO = mapDTOToVO(groupMappingVO, groupMapping2DTO);
		groupMappingRepo.save(groupMappingVO);

		Map<String, Object> response = new HashMap<>();
		response.put("groupMappingVO", groupMappingVO);
		response.put("message", message);
		return response;
	}

	private GroupMappingVO mapDTOToVO(GroupMappingVO groupMappingVO, GroupMapping2DTO groupMapping2DTO) {
		groupMappingVO.setGroupName(groupMapping2DTO.getSegment());
		groupMappingVO.setSubheading(groupMapping2DTO.getHeader());
		groupMappingVO.setActive(groupMapping2DTO.isActive());
		groupMappingVO.setOrgId(groupMapping2DTO.getOrgId());
		if (groupMapping2DTO.getId() != null) {
			List<GroupLedgersVO> existingLedgers = groupLedgersRepo.findByGroupMappingVO(groupMappingVO);
			groupLedgersRepo.deleteAll(existingLedgers);
		}

		List<GroupLedgersVO> newLedgers = new ArrayList<>();
		if (groupMapping2DTO.getGroupMapping2SubGroupDTO() != null) {
			for (GroupMapping2SubGroupDTO dto : groupMapping2DTO.getGroupMapping2SubGroupDTO()) {
				GroupLedgersVO ledger = new GroupLedgersVO();
				ledger.setMainGroupName(groupMapping2DTO.getSegment());
				ledger.setDisplaySeq(dto.getDisplaySeq());
				ledger.setGroupName(groupMapping2DTO.getHeader());
				ledger.setAccountCode(dto.getSubGroupCode());
				ledger.setAccountName(dto.getSubGroup());
				ledger.setActive(groupMapping2DTO.isActive());
				ledger.setOrgId(groupMapping2DTO.getOrgId());
				ledger.setGroupMappingVO(groupMappingVO);
				newLedgers.add(ledger);
			}
		}

		groupMappingVO.setGroupLedgresVOs(newLedgers);
		return groupMappingVO;
	}

	@Override
	public List<Map<String, Object>> getLedgersDetailsForGroupMapping(Long orgId, String segment) {
		
		Set<Object []> ledgerDetails=groupLedgersRepo.ledgersDetails(orgId, segment);
		return ledgDetails(ledgerDetails);
	}

	private List<Map<String, Object>> ledgDetails(Set<Object[]> ledgerDetails) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] det : ledgerDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("ledger", det[0] != null ? det[0].toString() : "");
			mp.put("subGroupCode", det[1] != null ? det[1].toString() : null);
			details.add(mp);
		}
		return details;
	}

	@Override
	public Map<String, Object> createUpdateSegmentMapping(SegmentMappingDTO segmentMappingDTO)
			throws ApplicationException {

		SegmentMappingVO segMappingVO = new SegmentMappingVO();

		String message = null;

		if (ObjectUtils.isEmpty(segmentMappingDTO.getId())) {

			
			segMappingVO.setCreatedBy(segmentMappingDTO.getCreatedBy());
			segMappingVO.setUpdatedBy(segmentMappingDTO.getCreatedBy());
			message = "Segment Mapping Creation SuccessFully";

		} else {

			segMappingVO = segmentMappingRepo.findById(segmentMappingDTO.getId()).orElseThrow(
					() -> new ApplicationException("Segment DTO not found with id: " + segmentMappingDTO.getId()));
			segMappingVO.setUpdatedBy(segmentMappingDTO.getCreatedBy());

			message = "Segment Mapping Updation SuccessFully";
		}

		segMappingVO = getSegmentMappingVOFromSegmentMappingDTO(segMappingVO, segmentMappingDTO);
		segmentMappingRepo.save(segMappingVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("segmentMappingVO", segMappingVO); // Return the list of saved records
		return response;
	}

	private SegmentMappingVO getSegmentMappingVOFromSegmentMappingDTO(SegmentMappingVO segmentMappingVO,
			SegmentMappingDTO segmentMappingDTO) {

		segmentMappingVO.setActive(segmentMappingDTO.isActive());
		segmentMappingVO.setClientName(segmentMappingDTO.getClientName());
		segmentMappingVO.setClientCode(segmentMappingDTO.getClientCode());
		segmentMappingVO.setSegmentType(segmentMappingDTO.getSegmentType());
		segmentMappingVO.setOrgId(segmentMappingDTO.getOrgId());
		

		if (segmentMappingDTO.getId() != null) {
			List<SegmentMappingDetailsVO> detailsVOs = segmentMappingDetailsRepo.findBySegmentMappingVO(segmentMappingVO);
			segmentMappingDetailsRepo.deleteAll(detailsVOs);
		}

		List<SegmentMappingDetailsVO> segmentMappingDetailsVO = new ArrayList<>();
		List<SegmentMappingDetailsDTO> segmentMappingDetailsDTO = segmentMappingDTO.getSegmentMappingDetailsDTO();
		if (segmentMappingDetailsDTO != null && !segmentMappingDetailsDTO.isEmpty()) {
			for (SegmentMappingDetailsDTO segmentMappingDetailsDTOs : segmentMappingDetailsDTO) {
				SegmentMappingDetailsVO detailsVO = new SegmentMappingDetailsVO();
				detailsVO.setActive(segmentMappingDetailsDTOs.isActive());
				detailsVO.setValue(segmentMappingDetailsDTOs.getValue());
				detailsVO.setActive(segmentMappingDetailsDTOs.isActive());
				detailsVO.setSegmentMappingVO(segmentMappingVO);
				segmentMappingDetailsVO.add(detailsVO);
			}
			segmentMappingVO.setSegmentMappingDetailsVO(segmentMappingDetailsVO);
		}
		return segmentMappingVO;
	}

	@Override
	public List<SegmentMappingVO> getAllSegmentMapping(Long orgId) {
		
		return segmentMappingRepo.findByOrgId(orgId);
	}

	@Override
	public Optional<SegmentMappingVO> getSegmentMappingById(Long id) {
		// TODO Auto-generated method stub
		return segmentMappingRepo.findById(id);
	}
	
	
	@Override
	public List<Map<String, Object>> getSegmentDetailsByClient(Long orgId) {
		
		Set<Object []> segmentDetails=segmentMappingRepo.getSegmentDetailsByClient(orgId);
		return segDetails(segmentDetails);
	}

	private List<Map<String, Object>> segDetails(Set<Object[]> ledgerDetails) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] det : ledgerDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("values", det[0] != null ? det[0].toString() : "");
			details.add(mp);
		}
		return details;
	}

}
