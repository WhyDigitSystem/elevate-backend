package com.ebooks.elevate.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.entity.BrsExcelUploadVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.BranchRepo;
import com.ebooks.elevate.repo.BrsExcelUploadRepo;
import com.ebooks.elevate.repo.DocumentTypeMappingDetailsRepo;

@Service
public class TransactionServiceImpl implements TransactionService {
	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	BrsExcelUploadRepo brsExcelUploadRepo;

//	@Autowired
//	ReconciliationSummaryRepo reconciliationSummaryRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public List<Map<String, Object>> getBranchForBrsOpening(Long orgId) {
		Set<Object[]> result = branchRepo.findBranchForBrsOpening(orgId);
		return getBranch(result);
	}

	private List<Map<String, Object>> getBranch(Set<Object[]> result) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> object = new HashMap<>();
			object.put("branch", fs[0] != null ? fs[0].toString() : "");
		}
		return details;
	}

	@Override
	public void ExcelUploadForBrs(MultipartFile[] files, Long orgId, String createdBy, String branch, String branchCode)
			throws ApplicationException, EncryptedDocumentException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BrsExcelUploadVO> getAllBrsExcelByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSuccessfulUploads() {
		// TODO Auto-generated method stub
		return 0;
	}

}
