package com.ebooks.elevate.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.dto.ExcelUploadResultDTO;
import com.ebooks.elevate.dto.TbDetailsDTO;
import com.ebooks.elevate.dto.TbHeaderDTO;
import com.ebooks.elevate.entity.DocumentTypeMappingDetailsVO;
import com.ebooks.elevate.entity.TbDetailsVO;
import com.ebooks.elevate.entity.TbHeaderVO;
import com.ebooks.elevate.entity.TrialBalanceVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.DocumentTypeMappingDetailsRepo;
import com.ebooks.elevate.repo.TbHeaderRepo;
import com.ebooks.elevate.repo.TrialBalanceRepo;

import io.jsonwebtoken.io.IOException;

@Service
public class TrailBalanceServiceImpl implements TrailBalanceService {

	@Autowired
	TrialBalanceRepo trialBalanceRepo;

	@Autowired
	TbHeaderRepo tbHeaderRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

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

	
	@Override
	public ExcelUploadResultDTO excelUploadForTb(MultipartFile[] files, String createdBy, String clientCode, 
	        String finYear, String month, String clientName, Long orgId) throws ApplicationException, java.io.IOException {

	    ExcelUploadResultDTO result = new ExcelUploadResultDTO(); // Result object
	    List<TrialBalanceVO> dataToSave = new ArrayList<>();
	    result.setTotalExcelRows(0);
	    result.setSuccessfulUploads(0);
	    result.setUnsuccessfulUploads(0);

	    for (MultipartFile file : files) {
	        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	            Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
	            Row headerRow = sheet.getRow(0);

	            // Validate header
	            for (Row row : sheet) {
	            	
	            	if (row.getRowNum() == 0) {
	                    continue; // Skip this iteration
	                }
	
	                
	                result.setTotalExcelRows(result.getTotalExcelRows() + 1); // Increment total rows

	                try {
	                    // Parse cell values
	                	String accountCode = getStringCellValue(row.getCell(0)); // Account Code is in column 0
	                    String accountName = getStringCellValue(row.getCell(1)); // Account Name is in column 1
	                    BigDecimal opBalance = parseBigDecimal(getStringCellValue(row.getCell(2))); 
	                    BigDecimal debit = parseBigDecimal(getStringCellValue(row.getCell(3))); // Debit Amount in column 3
	                    BigDecimal credit = parseBigDecimal(getStringCellValue(row.getCell(4))); // Credit in column 4
	                    BigDecimal clBalance = parseBigDecimal(getStringCellValue(row.getCell(5)));
	                    // Debit in column 3

	                   


	                    // Create and populate TrailBalanceVO object
	                    TrialBalanceVO dataVO = new TrialBalanceVO();
	                    dataVO.setAccountName(accountName);
	                    dataVO.setAccountCode(accountCode);
	                    dataVO.setClientCode(clientCode);	 
	                    dataVO.setOpBalance(opBalance);
	                    dataVO.setCredit(credit);
	                    dataVO.setDebit(debit);
	                    dataVO.setClBalance(clBalance);
	                    dataVO.setFinYear(finYear);
	                    dataVO.setMonth(month);
	                    dataVO.setOrgId(orgId);
	                    dataVO.setClient(clientName);
	                    dataVO.setCreatedBy(createdBy);
	                    dataVO.setUpdatedBy(createdBy);
	                    dataToSave.add(dataVO);
	                    System.out.println("closing balance"+clBalance);
	                    result.setSuccessfulUploads(result.getSuccessfulUploads() + 1); // Increment successful uploads
	                } catch (Exception e) {
	                    result.setUnsuccessfulUploads(result.getUnsuccessfulUploads() + 1);
	                    String error = String.format("Row %d: %s", row.getRowNum() + 1, e.getMessage());
	                   
	                    result.addFailureReason(error); // Capture failure reason
	                }
	            }
	        } catch (IOException | EncryptedDocumentException e) {
	            throw new ApplicationException(
	                "Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
	        }
	    }

	    // Save all valid rows
	    if (!dataToSave.isEmpty()) {
	        trialBalanceRepo.saveAll(dataToSave);
	    }

	    return result; // Return the result summary
	}
	
	
	private BigDecimal parseBigDecimal(String value) throws ApplicationException {
	    if (value == null || value.trim().isEmpty()) {
	        return BigDecimal.ZERO; // Return 0.00 for empty or null cells
	    }
	    try {
	        BigDecimal parsedValue = new BigDecimal(value.trim());
	        return parsedValue.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : parsedValue; // Ensure zero is set as 0.00
	    } catch (NumberFormatException e) {
	        throw new ApplicationException("Invalid number format: " + value, e);
	    }
	}


	private String getStringCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue());
			} else {
				double numericValue = cell.getNumericCellValue();
				if (numericValue == (int) numericValue) {
					return String.valueOf((int) numericValue);
				} else {
					return BigDecimal.valueOf(numericValue).toPlainString();
				}
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	private boolean isRowEmpty(Row row) {
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}


	

	@Override
	public Map<String, Object> createUpdateTrailBalance(TbHeaderDTO tbHeaderDTO) throws ApplicationException {

		String screenCode="TB";
		TbHeaderVO tbHeaderVO = new TbHeaderVO();

		String message = null;

		if (ObjectUtils.isEmpty(tbHeaderDTO.getId())) {
			
		
			
			String docId = tbHeaderRepo.getTBDocId(tbHeaderDTO.getOrgId(), tbHeaderDTO.getFinYear(),screenCode,tbHeaderDTO.getClientCode());
			tbHeaderVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCodeAndClientCode(tbHeaderDTO.getOrgId(), tbHeaderDTO.getFinYear(),screenCode,tbHeaderDTO.getClientCode());
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			

			tbHeaderVO.setCreatedBy(tbHeaderDTO.getCreatedBy());
			tbHeaderVO.setUpdatedBy(tbHeaderDTO.getCreatedBy());

			message = "TrailBalance Creation SuccessFully";

		} else {

			tbHeaderVO = tbHeaderRepo.findById(tbHeaderDTO.getId())
					.orElseThrow(() -> new ApplicationException("HeaderDTO not found with id: " + tbHeaderDTO.getId()));
			tbHeaderVO.setUpdatedBy(tbHeaderDTO.getCreatedBy());
			
			message = "TrailBalance Updation SuccessFully";
		}
		
		tbHeaderVO=getTbHeaderVOromTbHeaderDTO(tbHeaderVO,tbHeaderDTO);
		tbHeaderRepo.save(tbHeaderVO);
		
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("tbHeaderVO", tbHeaderVO); // Return the list of saved records
		return response;
	}

	private TbHeaderVO getTbHeaderVOromTbHeaderDTO(TbHeaderVO tbHeaderVO, TbHeaderDTO tbHeaderDTO) {

		tbHeaderVO.setClientCode(tbHeaderDTO.getClientCode());
		tbHeaderVO.setClient(tbHeaderDTO.getClient());
		tbHeaderVO.setOrgId(tbHeaderDTO.getOrgId());
		tbHeaderVO.setClientCode(tbHeaderDTO.getClientCode());
	    tbHeaderVO.setTbMonth(tbHeaderDTO.getTbMonth());
	    tbHeaderVO.setFinYear(tbHeaderDTO.getFinYear());
	    
	    List<TbDetailsVO> tbDetailsVO= new ArrayList<>();
	    List<TbDetailsDTO>tbDetailsDTO= tbHeaderDTO.getTbDetailsDTO();
	    for(TbDetailsDTO detailsDTO:tbDetailsDTO)
	    {
	    	TbDetailsVO tbDetailsVOs=new TbDetailsVO();
	    	tbDetailsVOs.setOrgId(tbHeaderDTO.getOrgId());
	    	tbDetailsVOs.setClient(tbHeaderDTO.getClient());
	    	tbDetailsVOs.setClientCode(tbHeaderDTO.getClientCode());
	    	tbDetailsVOs.setTbMonth(tbHeaderDTO.getTbMonth());
	    	tbDetailsVOs.setFinYear(tbHeaderDTO.getFinYear());
	    	tbDetailsVOs.setCoa(detailsDTO.getCoa());
	    	tbDetailsVOs.setCoaCode(detailsDTO.getCoaCode());
	    	tbDetailsVOs.setClientAccountName(detailsDTO.getClientAccountName());
	    	tbDetailsVOs.setClientAccountCode(detailsDTO.getClientAccountCode());
	    	tbDetailsVOs.setOpeningBalance(detailsDTO.getOpeningBalance());
	    	tbDetailsVOs.setClosingBalance(detailsDTO.getClosingBalance());
	    	tbDetailsVOs.setDebit(detailsDTO.getDebit());
	    	tbDetailsVOs.setCredit(detailsDTO.getCredit());
	    	tbDetailsVOs.setRemarks(detailsDTO.getRemarks());
	    	tbDetailsVOs.setTbHeaderVO(tbHeaderVO);
	    	tbDetailsVO.add(tbDetailsVOs);
	    }
	    tbHeaderVO.setTbDetailsVO(tbDetailsVO);
	    return tbHeaderVO;
	}
	
	@Override
	public String getTBDocId(Long orgId, String finYear,String clientCode) {
		String ScreenCode = "TB";
		String result = tbHeaderRepo.getTBDocId(orgId, finYear, ScreenCode,clientCode);
		return result;
	}
	
	
	@Override
	public List<Map<String, Object>> getFillGridForTB(Long orgId, String finYear,String tbMonth, String client,String clientCode) {

		Set<Object[]> getDetails = trialBalanceRepo.getFillGridForTbExcelUpload(orgId, finYear, tbMonth, client, clientCode);
		return getFillGrid(getDetails);
	}

	private List<Map<String, Object>> getFillGrid(Set<Object[]> getDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("clientAccountCode", ch[0] != null ? ch[0].toString() : "");
			map.put("clientAccountName", ch[1] != null ? ch[1].toString() : "");
			map.put("coaCode", ch[2] != null ? ch[2].toString() : "");
			map.put("coa", ch[3] != null ? ch[3].toString() : "");
			map.put("openingBalance", ch[4] != null ? new BigDecimal(ch[4].toString()) : BigDecimal.ZERO);
			map.put("debit", ch[5] != null ? new BigDecimal(ch[5].toString()) : BigDecimal.ZERO);
			map.put("credit", ch[6] != null ? new BigDecimal(ch[6].toString()) : BigDecimal.ZERO);
			map.put("closingBalance", ch[7] != null ? new BigDecimal(ch[7].toString()) : BigDecimal.ZERO);
			map.put("id",Integer.parseInt(ch[8].toString()));

			List1.add(map);
		}
		return List1;
	}	
	
}
