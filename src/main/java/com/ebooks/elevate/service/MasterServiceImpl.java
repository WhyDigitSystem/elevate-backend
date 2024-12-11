package com.ebooks.elevate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.BranchDTO;
import com.ebooks.elevate.dto.EmployeeDTO;
import com.ebooks.elevate.dto.SacCodeDTO;
import com.ebooks.elevate.dto.SetTaxRateDTO;
import com.ebooks.elevate.dto.SubLedgerAccountDTO;
import com.ebooks.elevate.entity.BranchVO;
import com.ebooks.elevate.entity.EmployeeVO;
import com.ebooks.elevate.entity.SacCodeVO;
import com.ebooks.elevate.entity.SetTaxRateVO;
import com.ebooks.elevate.entity.SubLedgerAccountVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.BranchRepo;
import com.ebooks.elevate.repo.CoaRepo;
import com.ebooks.elevate.repo.DocumentTypeMappingDetailsRepo;
import com.ebooks.elevate.repo.EmployeeRepo;
import com.ebooks.elevate.repo.SacCodeRepo;
import com.ebooks.elevate.repo.SetTaxRateRepo;
import com.ebooks.elevate.repo.SubLedgerAccountRepo;

@Service
public class MasterServiceImpl implements MasterService {
	public static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	SetTaxRateRepo setTaxRateRepo;

	

	@Autowired
	CoaRepo coaRepo;

	@Autowired
	SacCodeRepo sacCodeRepo;

	@Autowired
	SubLedgerAccountRepo subLedgerAccountRepo;

	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
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
	public SetTaxRateVO updateCreateSetTaxRate(@Valid SetTaxRateDTO setTaxRateDTO) throws Exception {
		SetTaxRateVO setTaxRateVO = new SetTaxRateVO();
		boolean isUpdate = false;
		if (ObjectUtils.isNotEmpty(setTaxRateDTO.getId())) {
			isUpdate = true;
			setTaxRateVO = setTaxRateRepo.findById(setTaxRateDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid SetTaxRate details"));
			setTaxRateVO.setUpdatedBy(setTaxRateDTO.getCreatedBy());

		} else {
			if (setTaxRateRepo.existsByChapterAndOrgId(setTaxRateDTO.getChapter(), setTaxRateDTO.getOrgId())) {
				throw new ApplicationException("The given chapter already exists");
			}
			if (setTaxRateRepo.existsByHsnCodeAndOrgId(setTaxRateDTO.getHsnCode(), setTaxRateDTO.getOrgId())) {
				throw new ApplicationException("The given Hsn Code already exists.");
			}
			setTaxRateVO.setUpdatedBy(setTaxRateDTO.getCreatedBy());
			setTaxRateVO.setCreatedBy(setTaxRateDTO.getCreatedBy());
		}

		getSetTaxRateVOFromSetTaxRateDTO(setTaxRateDTO, setTaxRateVO);

		if (ObjectUtils.isNotEmpty(setTaxRateDTO.getId())) {
			SetTaxRateVO setTaxRate = setTaxRateRepo.findById(setTaxRateDTO.getId()).orElse(null);
			if (!setTaxRate.getChapter().equalsIgnoreCase(setTaxRateDTO.getChapter())) {
				if (setTaxRateRepo.existsByChapterAndOrgId(setTaxRateDTO.getChapter(), setTaxRateDTO.getOrgId())) {
					throw new ApplicationException("The given chapter already exists.");
				}
			}
			if (!setTaxRate.getHsnCode().equalsIgnoreCase(setTaxRateDTO.getHsnCode())) {
				if (setTaxRateRepo.existsByHsnCodeAndOrgId(setTaxRateDTO.getHsnCode(), setTaxRateDTO.getOrgId())) {
					throw new ApplicationException("The given Hsn Code already exists.");
				}
			}
		}
		return setTaxRateRepo.save(setTaxRateVO);
	}

	private void getSetTaxRateVOFromSetTaxRateDTO(@Valid SetTaxRateDTO setTaxRateDTO, SetTaxRateVO setTaxRateVO)
			throws Exception {
		setTaxRateVO.setOrgId(setTaxRateDTO.getOrgId());
		setTaxRateVO.setChapter(setTaxRateDTO.getChapter());
		setTaxRateVO.setSubChapter(setTaxRateDTO.getSubChapter());
		setTaxRateVO.setHsnCode(setTaxRateDTO.getHsnCode());
		setTaxRateVO.setBranch(setTaxRateDTO.getBranch());
		setTaxRateVO.setNewRate(setTaxRateDTO.getNewRate());
		setTaxRateVO.setExcepmted("e");
		setTaxRateVO.setActive(setTaxRateDTO.isActive());
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
	public SacCodeVO updateCreateSacCode(@Valid SacCodeDTO sacCodeDTO) throws ApplicationException {
		SacCodeVO sacCodeVO = new SacCodeVO();
		if (ObjectUtils.isNotEmpty(sacCodeDTO.getId())) {
			sacCodeVO = sacCodeRepo.findById(sacCodeDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid SacCode details"));
			if (!sacCodeVO.getServiceAccountCode().equals(sacCodeDTO.getServiceAccountCode())) {
				if (sacCodeRepo.existsByOrgIdAndServiceAccountCodeIgnoreCase(sacCodeDTO.getOrgId(),
						sacCodeDTO.getServiceAccountCode())) {
					throw new ApplicationException("This Service Account Code Already Exists.");
				}
			}
			if (!sacCodeVO.getSacDescription().equals(sacCodeDTO.getSacDescription())) {
				if (sacCodeRepo.existsByOrgIdAndSacDescriptionIgnoreCase(sacCodeDTO.getOrgId(),
						sacCodeDTO.getSacDescription())) {
					throw new ApplicationException("This SAC Descipition Already Exists.");
				}
			}
			sacCodeVO.setUpdatedBy(sacCodeDTO.getCreatedBy());
		} else {

			if (sacCodeRepo.existsByOrgIdAndServiceAccountCodeIgnoreCase(sacCodeDTO.getOrgId(),
					sacCodeDTO.getServiceAccountCode())) {
				throw new ApplicationException("This Service Account Code Already Exists.");
			}
			if (sacCodeRepo.existsByOrgIdAndSacDescriptionIgnoreCase(sacCodeDTO.getOrgId(),
					sacCodeDTO.getSacDescription())) {
				throw new ApplicationException("This SAC Descipition Already Exists.");
			}

			sacCodeVO.setUpdatedBy(sacCodeDTO.getCreatedBy());
			sacCodeVO.setCreatedBy(sacCodeDTO.getCreatedBy());
		}
		getSacCodeVOFromSacCodeDTO(sacCodeDTO, sacCodeVO);
		return sacCodeRepo.save(sacCodeVO);
	}

	private void getSacCodeVOFromSacCodeDTO(@Valid SacCodeDTO sacCodeDTO, SacCodeVO sacCodeVO) {
		sacCodeVO.setOrgId(sacCodeDTO.getOrgId());
		sacCodeVO.setServiceAccountCode(sacCodeDTO.getServiceAccountCode().toUpperCase());
		sacCodeVO.setSacDescription(sacCodeDTO.getSacDescription().toUpperCase());
		sacCodeVO.setActive(sacCodeDTO.isActive());
		sacCodeVO.setProduct(sacCodeDTO.getProduct().toUpperCase());
	}

//	@Override
//	public List<SacCodeVO> getSacCodeByActive() {
//		return sacCodeRepo.findsacCodeByActive();
//
//	}

	// SubLedgerAccount

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
	public SubLedgerAccountVO updateCreateSubLedgerAccount(@Valid SubLedgerAccountDTO subLedgerAccountDTO)
			throws ApplicationException {
		SubLedgerAccountVO subLedgerAccountVO = new SubLedgerAccountVO();
		boolean isUpdate = false;
		if (ObjectUtils.isNotEmpty(subLedgerAccountDTO.getId())) {
			isUpdate = true;
			subLedgerAccountVO = subLedgerAccountRepo.findById(subLedgerAccountDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid SubLedgerAccount details"));
			subLedgerAccountVO.setUpdatedBy(subLedgerAccountDTO.getCreatedBy());
		} else {
			if (subLedgerAccountRepo.existsByNewCodeAndOrgId(subLedgerAccountDTO.getNewCode(),
					subLedgerAccountDTO.getOrgId())) {
				throw new ApplicationException("The given code already exists.");
			}
			if (subLedgerAccountRepo.existsByOldCodeAndOrgId(subLedgerAccountDTO.getOldCode(),
					subLedgerAccountDTO.getOrgId())) {
				throw new ApplicationException("The given old code exists.");
			}
			if (subLedgerAccountRepo.existsBySubLedgerNameAndOrgId(subLedgerAccountDTO.getSubLedgerName(),
					subLedgerAccountDTO.getOrgId())) {
				throw new ApplicationException("The given sub ledger name exists.");
			}
			subLedgerAccountVO.setUpdatedBy(subLedgerAccountDTO.getCreatedBy());
			subLedgerAccountVO.setCreatedBy(subLedgerAccountDTO.getCreatedBy());
		}

		if (isUpdate) {
			SubLedgerAccountVO sub = subLedgerAccountRepo.findById(subLedgerAccountDTO.getId()).orElse(null);
			if (!sub.getNewCode().equalsIgnoreCase(subLedgerAccountDTO.getNewCode())) {
				if (subLedgerAccountRepo.existsByNewCodeAndOrgId(subLedgerAccountDTO.getNewCode(),
						subLedgerAccountDTO.getOrgId())) {
					throw new ApplicationException("The given new code already exists.");
				}
			}
			if (!sub.getOldCode().equalsIgnoreCase(subLedgerAccountDTO.getOldCode())) {
				if (subLedgerAccountRepo.existsByOldCodeAndOrgId(subLedgerAccountDTO.getOldCode(),
						subLedgerAccountDTO.getOrgId())) {
					throw new ApplicationException("The given old code exists.");
				}
			}
			if (!sub.getSubLedgerName().equalsIgnoreCase(subLedgerAccountDTO.getSubLedgerName())) {
				if (subLedgerAccountRepo.existsBySubLedgerNameAndOrgId(subLedgerAccountDTO.getSubLedgerName(),
						subLedgerAccountDTO.getOrgId())) {
					throw new ApplicationException("The given sub ledger name exists.");
				}
			}
		}
		getSubLedgerAccountVOFromSubLedgerAccountDTO(subLedgerAccountDTO, subLedgerAccountVO);
		return subLedgerAccountRepo.save(subLedgerAccountVO);
	}

	private void getSubLedgerAccountVOFromSubLedgerAccountDTO(@Valid SubLedgerAccountDTO subLedgerAccountDTO,
			SubLedgerAccountVO subLedgerAccountVO) {
		subLedgerAccountVO.setAccountsCategory(subLedgerAccountDTO.getAccountsCategory());
		subLedgerAccountVO.setSubLedgerType(subLedgerAccountDTO.getSubLedgerType());
		subLedgerAccountVO.setSubLedgerName(subLedgerAccountDTO.getSubLedgerName());
		subLedgerAccountVO.setNewCode(subLedgerAccountDTO.getNewCode());
		subLedgerAccountVO.setOldCode(subLedgerAccountDTO.getOldCode());
		subLedgerAccountVO.setControllAccount(subLedgerAccountDTO.getControllAccount());
		subLedgerAccountVO.setCurrency(subLedgerAccountDTO.getCurrency());
		subLedgerAccountVO.setCreditDays(subLedgerAccountDTO.getCreditDays());
		subLedgerAccountVO.setCreditLimit(subLedgerAccountDTO.getCreditLimit());
		subLedgerAccountVO.setVatno(subLedgerAccountDTO.getVatno());
		subLedgerAccountVO.setStateJutisiction(subLedgerAccountDTO.getStateJutisiction());
		subLedgerAccountVO.setInvoiceType(subLedgerAccountDTO.getInvoiceType());
		subLedgerAccountVO.setOrgId(subLedgerAccountDTO.getOrgId());
		subLedgerAccountVO.setActive(subLedgerAccountDTO.isActive());

	}

	@Override
	public List<SubLedgerAccountVO> getSubLedgerAccountByActive() {
		return subLedgerAccountRepo.findSubLedgerAccountByActive();

	}

}
