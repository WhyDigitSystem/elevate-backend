package com.ebooks.elevate.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.dto.FirstDataDTO;
import com.ebooks.elevate.entity.FirstDataVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface ExcelFileUploadService {

	void ExcelUploadForSample(MultipartFile[] files, String createdBy) throws ApplicationException;

	int getTotalRows();

	int getSuccessfulUploads();

	List<FirstDataVO> getAllFirstData();

	Map<String, Object> updateCreateFirstData(@Valid FirstDataDTO firstDataDTO) throws ApplicationException;

	Optional<FirstDataVO> getFirstDataById(Long id);




}
