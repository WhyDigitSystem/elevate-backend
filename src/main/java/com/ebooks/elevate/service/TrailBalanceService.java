package com.ebooks.elevate.service;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.exception.ApplicationException;

@Repository
public interface TrailBalanceService {

	void excelUploadForTb(MultipartFile[] files, String createdBy, String clientCode,String finYear,String month) throws ApplicationException;

	int getTotalRows();

	int getSuccessfulUploads();
	
	

}
