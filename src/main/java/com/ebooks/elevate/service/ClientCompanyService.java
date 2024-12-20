package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.ebooks.elevate.dto.ClientCompanyDTO;
import com.ebooks.elevate.entity.ClientCompanyVO;
import com.ebooks.elevate.exception.ApplicationException;

public interface ClientCompanyService {

	List<ClientCompanyVO> getClientCompanyByOrgId(Long orgId);

	Optional<ClientCompanyVO> getClientCompanyById(Long id);

	Map<String, Object> updateCreateClientCompany(@Valid ClientCompanyDTO clientCompanyDTO) throws ApplicationException, Exception;

}
