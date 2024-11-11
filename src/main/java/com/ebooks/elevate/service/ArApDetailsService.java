package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.ArapDetailsDTO;
import com.ebooks.elevate.entity.ArapDetailsVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface ArApDetailsService {

//	ArapDetails
	List<ArapDetailsVO> getAllArapDetailsByOrgId(Long orgId);

	Map<String, Object> createupdateArapDetails(@Valid ArapDetailsDTO arapDetailsDTO) throws ApplicationException;

	List<ArapDetailsVO> getAllArapDetailsById(Long id);

	List<ArapDetailsVO> getArapDetailsByActive();

	ArapDetailsVO getArapDetailsByDocId(Long orgId, String docId);

	String getArapDetailsDocId(Long orgId, String finYear, String branch, String branchCode);

}
