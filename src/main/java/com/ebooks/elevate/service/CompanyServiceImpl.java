package com.ebooks.elevate.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.EltCompanyDTO;
import com.ebooks.elevate.entity.EltCompanyVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.EltCompanyRepo;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	EltCompanyRepo eltCompanyRepo;

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
		return eltCompanyVO;
	}

	@Override
	public Optional<EltCompanyVO> getEltCompanyById(Long id) {
		
		return eltCompanyRepo.findEltCompanyById(id);
	}

}
