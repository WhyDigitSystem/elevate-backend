package com.ebooks.elevate.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.dto.ExcelUploadResultDTO;
import com.ebooks.elevate.dto.TbHeaderDTO;
import com.ebooks.elevate.exception.ApplicationException;

@Repository
public interface TrailBalanceService {


	int getTotalRows();

	int getSuccessfulUploads();


	Map<String, Object> createUpdateTrailBalance(TbHeaderDTO tbHeaderDTO) throws ApplicationException;

	ExcelUploadResultDTO excelUploadForTb(MultipartFile[] files, String createdBy, String clientCode, String finYear, String month,
			String clientName, Long orgId) throws ApplicationException, IOException;

	String getTBDocId(Long orgId, String finYear,String clientCode);

	List<Map<String, Object>> getFillGridForTB(Long orgId, String finYear,String tbMonth, String client,String clientCode);
	
	

}
