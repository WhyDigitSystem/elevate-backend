package com.ebooks.elevate.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.dto.ElMfrDTO;
import com.ebooks.elevate.entity.ElMfrVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface ELReportService {

	Map<String, Object> createUpdateElMfr(ElMfrDTO elMfrDTO) throws ApplicationException;

	List<ElMfrVO> getAllElMfr(Long orgId);

	int getTotalRows();

	int getSuccessfulUploads();

	void excelUploadForElMfr(MultipartFile[] files, String createdBy, Long orgId) throws ApplicationException, IOException;

	List<Map<String, Object>> getMisMatchClientTb(Long orgId, String clientCode, String accountCode);

}
