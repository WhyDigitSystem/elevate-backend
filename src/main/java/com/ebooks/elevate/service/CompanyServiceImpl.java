package com.ebooks.elevate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.CompanyEmployeeDTO;
import com.ebooks.elevate.dto.EltCompanyDTO;
import com.ebooks.elevate.entity.CompanyEmployeeVO;
import com.ebooks.elevate.entity.EltCompanyVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.CompanyEmployeeRepo;
import com.ebooks.elevate.repo.EltCompanyRepo;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	EltCompanyRepo eltCompanyRepo;

	@Autowired
	CompanyEmployeeRepo companyEmployeeRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Override
	public Map<String, Object> updateCreateCompany(@Valid EltCompanyDTO eltCompanyDTO) throws ApplicationException {

		EltCompanyVO eltCompanyVO;

		String message = null;

		if (ObjectUtils.isEmpty(eltCompanyDTO.getId())) {

			eltCompanyVO = new EltCompanyVO();

			if (eltCompanyRepo.existsByCompanyCodeAndId(eltCompanyDTO.getCompanyCode(), eltCompanyDTO.getId())) {
				String errorMessage = String.format("The CompanyCode: %s  already exists This Organization.",
						eltCompanyDTO.getCompanyCode());
				throw new ApplicationException(errorMessage);
			}

			if (eltCompanyRepo.existsByCompanyNameAndId(eltCompanyDTO.getCompanyName(), eltCompanyDTO.getId())) {
				String errorMessage = String.format("The CompanyName: %s  already exists This Organization.",
						eltCompanyDTO.getCompanyName());
				throw new ApplicationException(errorMessage);
			}

			if (eltCompanyRepo.existsByEmailAndId(eltCompanyDTO.getEmail(), eltCompanyDTO.getId())) {
				String errorMessage = String.format("The CompanyMail: %s  already exists This Organization.",
						eltCompanyDTO.getEmail());
				throw new ApplicationException(errorMessage);
			}

			if (eltCompanyRepo.existsByPhoneAndId(eltCompanyDTO.getPhone(), eltCompanyDTO.getId())) {
				String errorMessage = String.format("The PhoneNo: %s  already exists This Organization.",
						eltCompanyDTO.getPhone());
				throw new ApplicationException(errorMessage);
			}

			if (eltCompanyRepo.existsByWebSiteAndId(eltCompanyDTO.getWebSite(), eltCompanyDTO.getId())) {
				String errorMessage = String.format("The WebSite: %s  already exists This Organization.",
						eltCompanyDTO.getWebSite());
				throw new ApplicationException(errorMessage);
			}

			eltCompanyVO.setCreatedBy(eltCompanyDTO.getCreatedBy());

			eltCompanyVO.setUpdatedBy(eltCompanyDTO.getCreatedBy());

			message = "Elt Company Successfully";
		} else {

			eltCompanyVO = eltCompanyRepo.findById(eltCompanyDTO.getId()).orElseThrow(
					() -> new ApplicationException("Elt CompanyVO  Not Found with id: " + eltCompanyDTO.getId()));
			eltCompanyVO.setUpdatedBy(eltCompanyDTO.getCreatedBy());

			if (eltCompanyRepo.existsByCompanyCodeAndId(eltCompanyDTO.getCompanyCode(), eltCompanyDTO.getId())) {
				String errorMessage = String.format("The CompanyCode: %s  already exists This Organization.",
						eltCompanyDTO.getCompanyCode());
				throw new ApplicationException(errorMessage);
			}

			eltCompanyVO.setCompanyCode(eltCompanyDTO.getCompanyCode());

			if (eltCompanyRepo.existsByCompanyNameAndId(eltCompanyDTO.getCompanyName(), eltCompanyDTO.getId())) {
				String errorMessage = String.format("The CompanyName: %s  already exists This Organization.",
						eltCompanyDTO.getCompanyName());
				throw new ApplicationException(errorMessage);
			}
			eltCompanyVO.setCompanyName(eltCompanyDTO.getCompanyName());

			if (eltCompanyRepo.existsByEmailAndId(eltCompanyDTO.getEmail(), eltCompanyDTO.getId())) {
				String errorMessage = String.format("The CompanyMail: %s  already exists This Organization.",
						eltCompanyDTO.getEmail());
				throw new ApplicationException(errorMessage);
			}

			eltCompanyVO.setEmail(eltCompanyDTO.getEmail());

			if (eltCompanyRepo.existsByPhoneAndId(eltCompanyDTO.getPhone(), eltCompanyDTO.getId())) {
				String errorMessage = String.format("The PhoneNo: %s  already exists This Organization.",
						eltCompanyDTO.getPhone());
				throw new ApplicationException(errorMessage);
			}

			eltCompanyVO.setPhone(eltCompanyDTO.getPhone());

			if (eltCompanyRepo.existsByWebSiteAndId(eltCompanyDTO.getWebSite(), eltCompanyDTO.getId())) {
				String errorMessage = String.format("The WebSite: %s  already exists This Organization.",
						eltCompanyDTO.getWebSite());
				throw new ApplicationException(errorMessage);
			}

			eltCompanyVO.setWebSite(eltCompanyDTO.getWebSite());

			message = "Elt Company Updation Successfully";

		}
		eltCompanyVO = getEltCompanyVOFromEltCompanyDTO(eltCompanyVO, eltCompanyDTO);
		eltCompanyRepo.save(eltCompanyVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("eltCompanyVO", eltCompanyVO);
		return response;

	}

	private EltCompanyVO getEltCompanyVOFromEltCompanyDTO(EltCompanyVO eltCompanyVO,
			@Valid EltCompanyDTO eltCompanyDTO) {

		eltCompanyVO.setCompanyCode(eltCompanyDTO.getCompanyCode());
		eltCompanyVO.setCompanyName(eltCompanyDTO.getCompanyName());
		eltCompanyVO.setEmail(eltCompanyDTO.getEmail());
		eltCompanyVO.setPhone(eltCompanyDTO.getPhone());
		eltCompanyVO.setWebSite(eltCompanyDTO.getWebSite());
		eltCompanyVO.setActive(eltCompanyDTO.isActive());
		return eltCompanyVO;
	}

	@Override
	public Optional<EltCompanyVO> getEltCompanyById(Long id) {

		return eltCompanyRepo.findEltCompanyById(id);
	}

	@Override
	public List<EltCompanyVO> getAllEltCompany() {
		
		return eltCompanyRepo.findAll();
	}

//CompanyEmployee
	
	@Override
	public Map<String, Object> updateCreateCompanyEmployee(@Valid CompanyEmployeeDTO companyEmployeeDTO)
			throws ApplicationException {

		CompanyEmployeeVO companyEmployeeVO;

		String message = null;

		if (ObjectUtils.isEmpty(companyEmployeeDTO.getId())) {

			companyEmployeeVO = new CompanyEmployeeVO();

			if (companyEmployeeRepo.existsByEmployeeCodeAndOrgId(companyEmployeeDTO.getEmployeeCode(),
					companyEmployeeDTO.getOrgId())) {
				String errorMessage = String.format("The EmployeeCode: %s  already exists This Organization.",
						companyEmployeeDTO.getCompanyCode());
				throw new ApplicationException(errorMessage);
			}

			if (companyEmployeeRepo.existsByEmailAndOrgId(companyEmployeeDTO.getEmail(),
					companyEmployeeDTO.getOrgId())) {
				String errorMessage = String.format("The CompanyMail: %s  already exists This Organization.",
						companyEmployeeDTO.getEmail());
				throw new ApplicationException(errorMessage);
			}

			if (companyEmployeeRepo.existsByPhoneAndOrgId(companyEmployeeDTO.getPhone(),
					companyEmployeeDTO.getOrgId())) {
				String errorMessage = String.format("The PhoneNo: %s  already exists This Organization.",
						companyEmployeeDTO.getPhone());
				throw new ApplicationException(errorMessage);
			}

			if (companyEmployeeRepo.existsByWebSiteAndOrgId(companyEmployeeDTO.getWebSite(),
					companyEmployeeDTO.getOrgId())) {
				String errorMessage = String.format("The WebSite: %s  already exists This Organization.",
						companyEmployeeDTO.getWebSite());
				throw new ApplicationException(errorMessage);
			}

			companyEmployeeVO.setCreatedBy(companyEmployeeDTO.getCreatedBy());

			companyEmployeeVO.setUpdatedBy(companyEmployeeDTO.getCreatedBy());

			message = "Company Employee Successfully";
		} else {

			companyEmployeeVO = companyEmployeeRepo.findById(companyEmployeeDTO.getId()).orElseThrow(
					() -> new ApplicationException("Elt CompanyVO  Not Found with id: " + companyEmployeeDTO.getId()));
			companyEmployeeVO.setUpdatedBy(companyEmployeeDTO.getCreatedBy());

			if (companyEmployeeRepo.existsByEmployeeCodeAndOrgId(companyEmployeeDTO.getEmployeeCode(),
					companyEmployeeDTO.getOrgId())) {
				String errorMessage = String.format("The EmployeeCode: %s  already exists This Organization.",
						companyEmployeeDTO.getCompanyCode());
				throw new ApplicationException(errorMessage);
			}

			if (companyEmployeeRepo.existsByEmailAndOrgId(companyEmployeeDTO.getEmail(),
					companyEmployeeDTO.getOrgId())) {
				String errorMessage = String.format("The CompanyMail: %s  already exists This Organization.",
						companyEmployeeDTO.getEmail());
				throw new ApplicationException(errorMessage);
			}

			if (companyEmployeeRepo.existsByPhoneAndOrgId(companyEmployeeDTO.getPhone(),
					companyEmployeeDTO.getOrgId())) {
				String errorMessage = String.format("The PhoneNo: %s  already exists This Organization.",
						companyEmployeeDTO.getPhone());
				throw new ApplicationException(errorMessage);
			}

			if (companyEmployeeRepo.existsByWebSiteAndOrgId(companyEmployeeDTO.getWebSite(),
					companyEmployeeDTO.getOrgId())) {
				String errorMessage = String.format("The WebSite: %s  already exists This Organization.",
						companyEmployeeDTO.getWebSite());
				throw new ApplicationException(errorMessage);
			}

			message = "Company Employee Updation Successfully";

		}
		companyEmployeeVO = getCompanyEmployeeVOFromCompanyEmployeeDTO(companyEmployeeVO, companyEmployeeDTO);
		companyEmployeeRepo.save(companyEmployeeVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("companyEmployeeVO", companyEmployeeVO);
		return response;

	}

	private CompanyEmployeeVO getCompanyEmployeeVOFromCompanyEmployeeDTO(CompanyEmployeeVO companyEmployeeVO,
			@Valid CompanyEmployeeDTO companyEmployeeDTO) {
		{

			companyEmployeeVO.setEmployeeCode(companyEmployeeDTO.getEmployeeCode());
			companyEmployeeVO.setEmployeeName(companyEmployeeDTO.getEmployeeName());
			companyEmployeeVO.setEmail(companyEmployeeDTO.getEmail());
			companyEmployeeVO.setPhone(companyEmployeeDTO.getPhone());
			companyEmployeeVO.setOrgId(companyEmployeeDTO.getOrgId());
			companyEmployeeVO.setWebSite(companyEmployeeDTO.getWebSite());
			companyEmployeeVO.setActive(companyEmployeeDTO.isActive());
			return companyEmployeeVO;
		}

	}

	@Override
	public Optional<CompanyEmployeeVO> getCompanyEmployeeById(Long id) {
		return companyEmployeeRepo.findById(id);
	}

	@Override
	public List<CompanyEmployeeVO> getAllCompanyEmployeeByOrgId(Long orgId) {
		return companyEmployeeRepo.getAllCompanyEmployee(orgId);
	}

	
}
