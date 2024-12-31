package com.ebooks.elevate.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.entity.BrsExcelUploadVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface TransactionService {



	List<Map<String, Object>> getBranchForBrsOpening(Long orgId);

	void ExcelUploadForBrs(MultipartFile[] files, Long orgId, String createdBy, String branch, String branchCode)
			throws ApplicationException, EncryptedDocumentException, IOException;

	List<BrsExcelUploadVO> getAllBrsExcelByOrgId(Long orgId);

	int getTotalRows();

	int getSuccessfulUploads();

}