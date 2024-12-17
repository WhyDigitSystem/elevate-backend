package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.dto.TbHeaderDTO;
import com.ebooks.elevate.exception.ApplicationException;

@Repository
public interface TrailBalanceService {

	void excelUploadForTb(MultipartFile[] files, String createdBy, String clientCode,String finYear,String month) throws ApplicationException;

	int getTotalRows();

	int getSuccessfulUploads();

	List<Map<String, Object>> getFillGridForTbExcelUpload(String finYear, String clientCode, String month);

	Map<String, Object> createUpdateTrailBalance(TbHeaderDTO tbHeaderDTO) throws ApplicationException;
	
	

}
