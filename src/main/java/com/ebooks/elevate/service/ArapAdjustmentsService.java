package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.ArapAdjustmentsDTO;
import com.ebooks.elevate.entity.ArapAdjustmentsVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface ArapAdjustmentsService {

//	ArapAdjustments
	List<ArapAdjustmentsVO> getAllArapAdjustmentsByOrgId(Long orgId);

	List<ArapAdjustmentsVO> getAllArapAdjustmentsById(Long id);

	List<ArapAdjustmentsVO> getArapAdjustmentsByActive();

	Map<String, Object> createUpdateArapAdjustments(@Valid ArapAdjustmentsDTO arapAdjustmentsDTO) throws ApplicationException;

	ArapAdjustmentsVO getArapAdjustmentsByDocId(Long orgId, String docId);

	String getArapAdjustmentsDocId(Long orgId, String finYear, String branch, String branchCode);
	
}
