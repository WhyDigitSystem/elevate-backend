package com.ebooks.elevate.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.dto.CCoaDTO;
import com.ebooks.elevate.dto.CoaDTO;
import com.ebooks.elevate.entity.CCoaVO;
import com.ebooks.elevate.entity.CoaVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface BusinessService {

	Map<String, Object> createUpdateCoa(CoaDTO coaDTO) throws ApplicationException;

	List<CoaVO> getAllCao();

	Optional<CoaVO> getCaoById(Long id);

	List<Map<String, Object>> getGroupName();
	
	void excelUploadForCoa(MultipartFile[] files, String createdBy) throws ApplicationException, EncryptedDocumentException, IOException;
	
	int getTotalRows();

	int getSuccessfulUploads();
	
	//CCoa

	Map<String, Object> createUpdateCCoa(CCoaDTO cCoaDTO) throws ApplicationException;

	List<CCoaVO> getAllCCao();

	Optional<CCoaVO> getCCaoById(Long id);

	List<Map<String, Object>> getGroupNameForCCoa();

	void excelUploadForCCoa(MultipartFile[] files, String createdBy, String clientCode) throws EncryptedDocumentException, IOException, ApplicationException;

	

}
