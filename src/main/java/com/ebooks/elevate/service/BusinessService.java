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
import com.ebooks.elevate.dto.ExcelUploadResultDTO;
import com.ebooks.elevate.dto.LedgerMappingDTO;
import com.ebooks.elevate.entity.CCoaVO;
import com.ebooks.elevate.entity.CoaVO;
import com.ebooks.elevate.entity.LedgerMappingVO;
import com.ebooks.elevate.exception.ApplicationException;

@Service
public interface BusinessService {

	Map<String, Object> createUpdateCoa(CoaDTO coaDTO) throws ApplicationException;

	List<CoaVO> getAllCao(Long orgId);

	Optional<CoaVO> getCaoById(Long id);

	List<Map<String, Object>> getGroupName(Long orgId);
	
	void excelUploadForCoa(MultipartFile[] files, String createdBy,Long orgId) throws ApplicationException, EncryptedDocumentException, IOException;
	
	int getTotalRows();

	int getSuccessfulUploads();
	
	//CCoa

	Map<String, Object> createUpdateCCoa(CCoaDTO cCoaDTO) throws ApplicationException;

	List<CCoaVO> getAllCCao(Long orgId, String clientCode);

	Optional<CCoaVO> getCCaoById(Long id);

	List<Map<String, Object>> getGroupNameForCCoa();


	//LEDGER MAPPING
	
	Map<String, Object> createUpdateLedgerMapping(LedgerMappingDTO ledgerMappingDTO) throws ApplicationException;

	List<Map<String, Object>> getCOAForLedgerMapping(Long orgId);

	List<Map<String, Object>> getLedgerMap();

	List<Map<String, Object>> getFillGridForLedgerMapping(String clientCode,Long orgId);

	Optional<LedgerMappingVO> getLedgerMappingbyId(Long id);

	List<LedgerMappingVO> getAllLedgerMapping();

	ExcelUploadResultDTO excelUploadForCCoa(MultipartFile[] files, String createdBy, String clientCode,
			String clientName, Long orgId)
			throws EncryptedDocumentException, io.jsonwebtoken.io.IOException, ApplicationException, IOException;

	ExcelUploadResultDTO excelUploadForLedgerMapping(MultipartFile[] files, String createdBy, String clientCode,Long orgId,String clientName) throws EncryptedDocumentException, IOException;

	

	


	

}
