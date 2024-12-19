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

import com.ebooks.elevate.dto.ClientCompanyDTO;
import com.ebooks.elevate.entity.ClientCompanyVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.ClientCompanyRepo;

@Service
public class ClientCompanyServiceImpl implements ClientCompanyService{

	public static final Logger LOGGER = LoggerFactory.getLogger(ClientCompanyServiceImpl.class);

	@Autowired
	ClientCompanyRepo clientCompanyRepo;
	
	@Override
	public List<ClientCompanyVO> getClientCompanyByOrgId(Long orgId) {
		return clientCompanyRepo.findClientCompanyByOrgId(orgId);
	}


	@Override
	public Optional<ClientCompanyVO> getClientCompanyById(Long companyid) {
		return clientCompanyRepo.findByClientCompanyById(companyid);
	}
	
	@Override
	public Map<String, Object> updateCreateClientCompany(@Valid ClientCompanyDTO clientCompanyDTO) throws ApplicationException {

		ClientCompanyVO clientCompanyVO;

		String message = null;

		if (ObjectUtils.isEmpty(clientCompanyDTO.getId())) {

			clientCompanyVO = new ClientCompanyVO();

			if (clientCompanyRepo.existsByClientCodeAndOrgId(clientCompanyDTO.getClientCode(), clientCompanyDTO.getOrgId())) {
				String errorMessage = String.format("The ClientCode: %s  already exists This Organization.",
						clientCompanyDTO.getClientCode());
				throw new ApplicationException(errorMessage);
			}

			if (clientCompanyRepo.existsByClientNameAndOrgId(clientCompanyDTO.getClientName(), clientCompanyDTO.getOrgId())) {
				String errorMessage = String.format("The ClientName: %s  already exists This Organization.",
						clientCompanyDTO.getClientName());
				throw new ApplicationException(errorMessage);
			}

			if (clientCompanyRepo.existsByEmailAndOrgId(clientCompanyDTO.getEmail(), clientCompanyDTO.getOrgId())) {
				String errorMessage = String.format("The CompanyMail: %s  already exists This Organization.",
						clientCompanyDTO.getEmail());
				throw new ApplicationException(errorMessage);
			}

			if (clientCompanyRepo.existsByPhoneAndOrgId(clientCompanyDTO.getPhone(), clientCompanyDTO.getOrgId())) {
				String errorMessage = String.format("The PhoneNo: %s  already exists This Organization.",
						clientCompanyDTO.getPhone());
				throw new ApplicationException(errorMessage);
			}

			if (clientCompanyRepo.existsByWebSiteAndOrgId(clientCompanyDTO.getWebSite(), clientCompanyDTO.getOrgId())) {
				String errorMessage = String.format("The WebSite: %s  already exists This Organization.",
						clientCompanyDTO.getWebSite());
				throw new ApplicationException(errorMessage);
			}

			clientCompanyVO.setCreatedBy(clientCompanyDTO.getCreatedBy());

			clientCompanyVO.setUpdatedBy(clientCompanyDTO.getCreatedBy());

			message = "Client Company Successfully";
		} else {

			clientCompanyVO = clientCompanyRepo.findById(clientCompanyDTO.getId()).orElseThrow(
					() -> new ApplicationException("Elt CompanyVO  Not Found with id: " + clientCompanyDTO.getOrgId()));
			clientCompanyVO.setUpdatedBy(clientCompanyDTO.getCreatedBy());

			if (!clientCompanyVO.getClientCode().equalsIgnoreCase(clientCompanyDTO.getClientCode())) {
			if (clientCompanyRepo.existsByClientCodeAndOrgId(clientCompanyDTO.getClientCode(), clientCompanyDTO.getOrgId())) {
				String errorMessage = String.format("The ClientCode: %s  already exists This Organization.",
						clientCompanyDTO.getClientCode());
				throw new ApplicationException(errorMessage);
			}
			}
			if (!clientCompanyVO.getClientName().equalsIgnoreCase(clientCompanyDTO.getClientName())) {
			if (clientCompanyRepo.existsByClientNameAndOrgId(clientCompanyDTO.getClientName(), clientCompanyDTO.getOrgId())) {
				String errorMessage = String.format("The ClientName: %s  already exists This Organization.",
						clientCompanyDTO.getClientName());
				throw new ApplicationException(errorMessage);
			}}
			clientCompanyVO.setClientName(clientCompanyDTO.getClientName());

			if (!clientCompanyVO.getEmail().equalsIgnoreCase(clientCompanyDTO.getEmail())) {
			if (clientCompanyRepo.existsByEmailAndOrgId(clientCompanyDTO.getEmail(), clientCompanyDTO.getOrgId())) {
				String errorMessage = String.format("The CompanyMail: %s  already exists This Organization.",
						clientCompanyDTO.getEmail());
				throw new ApplicationException(errorMessage);
			}}

			clientCompanyVO.setEmail(clientCompanyDTO.getEmail());

			if (!clientCompanyVO.getPhone().equalsIgnoreCase(clientCompanyDTO.getPhone())) {
			if (clientCompanyRepo.existsByPhoneAndOrgId(clientCompanyDTO.getPhone(), clientCompanyDTO.getOrgId())) {
				String errorMessage = String.format("The PhoneNo: %s  already exists This Organization.",
						clientCompanyDTO.getPhone());
				throw new ApplicationException(errorMessage);
			}}

			clientCompanyVO.setPhone(clientCompanyDTO.getPhone());

			if (!clientCompanyVO.getWebSite().equalsIgnoreCase(clientCompanyDTO.getWebSite())) {
			if (clientCompanyRepo.existsByWebSiteAndOrgId(clientCompanyDTO.getWebSite(), clientCompanyDTO.getOrgId())) {
				String errorMessage = String.format("The WebSite: %s  already exists This Organization.",
						clientCompanyDTO.getWebSite());
				throw new ApplicationException(errorMessage);
			}}

			clientCompanyVO.setWebSite(clientCompanyDTO.getWebSite());

			message = "Client Company Updation Successfully";

		}
		clientCompanyVO = getClientCompanyVOFromClientCompanyDTO(clientCompanyVO, clientCompanyDTO);
		clientCompanyRepo.save(clientCompanyVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("clientCompanyVO", clientCompanyVO);
		return response;

	}

	private ClientCompanyVO getClientCompanyVOFromClientCompanyDTO(ClientCompanyVO clientCompanyVO,
			@Valid ClientCompanyDTO clientCompanyDTO) {

		clientCompanyVO.setClientCode(clientCompanyDTO.getClientCode());
		clientCompanyVO.setClientName(clientCompanyDTO.getClientName());
		clientCompanyVO.setEmail(clientCompanyDTO.getEmail());
		clientCompanyVO.setPhone(clientCompanyDTO.getPhone());
		clientCompanyVO.setWebSite(clientCompanyDTO.getWebSite());
		clientCompanyVO.setOrgId(clientCompanyDTO.getOrgId());
		clientCompanyVO.setActive(clientCompanyDTO.isActive());
		clientCompanyVO.setBussinessType(clientCompanyDTO.getBussinessType());
		clientCompanyVO.setTurnOver(clientCompanyDTO.getTurnOver());
		clientCompanyVO.setLevelOfService(clientCompanyDTO.getLevelOfService());
		clientCompanyVO.setRepPerson(clientCompanyDTO.getRepPerson());


		return clientCompanyVO;
	}

	
	
}
