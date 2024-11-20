package com.ebooks.elevate.service;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.EltCompanyDTO;
import com.ebooks.elevate.entity.EltCompanyVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface CompanyService {

	Map<String, Object> updateCreateCompany(@Valid EltCompanyDTO eltCompanyDTO) throws ApplicationException;

	Optional<EltCompanyVO> getEltCompanyById(Long id);

}
