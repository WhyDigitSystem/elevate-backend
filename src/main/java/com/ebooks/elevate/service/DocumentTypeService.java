package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebooks.elevate.dto.DocumentTypeDTO;
import com.ebooks.elevate.dto.DocumentTypeMappingDTO;
import com.ebooks.elevate.entity.DocumentTypeMappingVO;
import com.ebooks.elevate.entity.DocumentTypeVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface DocumentTypeService {
	
	// Document Type

	Map<String, Object> createUpdateDocumentType(DocumentTypeDTO documentTypeDTO) throws ApplicationException;

	DocumentTypeVO getDocumentTypeById(Long id) throws ApplicationException;

	List<DocumentTypeVO> getAllDocumentTypeByOrgId(Long orgId);
	
	// Document type mapping

	List<Map<String, Object>> getPendingDocumentTypeMapping(Long orgId, String branch, String branchCode,
			String finYear, String finYearIdentifier);

	Map<String, Object> createDocumentTypeMapping(DocumentTypeMappingDTO documentTypeMappingDTO)
			throws ApplicationException;

	List<DocumentTypeMappingVO> getAllDocumentTypeMapping(Long orgId);

	DocumentTypeMappingVO getDocumentTypeMappingById(Long id) throws ApplicationException;

}
