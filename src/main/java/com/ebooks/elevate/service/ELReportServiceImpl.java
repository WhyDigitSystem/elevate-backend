package com.ebooks.elevate.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.dto.ElMfrDTO;
import com.ebooks.elevate.entity.ElMfrVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.ElMfrRepo;

import io.jsonwebtoken.io.IOException;
@Service
public class ELReportServiceImpl implements ELReportService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ELReportServiceImpl.class);

	@Autowired
	ElMfrRepo elMfrRepo;

	@Override
	public Map<String, Object> createUpdateElMfr(ElMfrDTO elMfrDTO) throws ApplicationException {

		ElMfrVO elMfrVO = null;
		String message = null;

		if (ObjectUtils.isEmpty(elMfrDTO.getId())) {

			if (elMfrRepo.existsByDescriptionAndOrgId(elMfrDTO.getDescription(), elMfrDTO.getOrgId())) {

				String errorMessage = String.format("This Description: %s Already Exists", elMfrDTO.getDescription());
				throw new ApplicationException(errorMessage);

			}

			if (elMfrRepo.existsByElCodeAndOrgId(elMfrDTO.getElCode(), elMfrDTO.getOrgId())) {

				String errorMessage = String.format("This ElCode: %s Already Exists", elMfrDTO.getElCode());
				throw new ApplicationException(errorMessage);

			}

			elMfrVO = new ElMfrVO();
			elMfrVO.setCreatedBy(elMfrDTO.getCreatedBy());
			elMfrVO.setUpdatedBy(elMfrDTO.getCreatedBy());

			message = "El MFR Report Creation Successfully";

		} else {

			elMfrVO = elMfrRepo.findById(elMfrDTO.getId())
					.orElseThrow(() -> new ApplicationException("El MFR not found with id: " + elMfrDTO.getId()));
			elMfrVO.setUpdatedBy(elMfrDTO.getCreatedBy());

			if (!elMfrVO.getDescription().equalsIgnoreCase(elMfrDTO.getDescription())) {

				if (elMfrRepo.existsByDescriptionAndOrgId(elMfrDTO.getDescription(), elMfrDTO.getOrgId())) {

					String errorMessage = String.format("This Description: %s Already Exists",
							elMfrDTO.getDescription());
					throw new ApplicationException(errorMessage);

				}
				elMfrVO.setDescription(elMfrDTO.getDescription());
			}

			if (!elMfrVO.getElCode().equalsIgnoreCase(elMfrDTO.getElCode())) {

				if (elMfrRepo.existsByElCodeAndOrgId(elMfrDTO.getElCode(), elMfrDTO.getOrgId())) {

					String errorMessage = String.format("This ElCode: %s Already Exists", elMfrDTO.getElCode());
					throw new ApplicationException(errorMessage);

				}
				elMfrVO.setElCode(elMfrDTO.getElCode());
			}

			message="El MFR Report Updation Successfully";
		}
		
		elMfrVO=getElMfrVOFromElMfrDTO(elMfrVO,elMfrDTO);
		elMfrRepo.save(elMfrVO);
		
		Map<String, Object> response=new HashMap<String, Object>();
		response.put("message", message);
		response.put("elMfrVO", elMfrVO);
		return response;
	}

	private ElMfrVO getElMfrVOFromElMfrDTO(ElMfrVO elMfrVO, ElMfrDTO elMfrDTO) {
		
		elMfrVO.setDescription(elMfrDTO.getDescription());
		elMfrVO.setElCode(elMfrDTO.getElCode());
		elMfrVO.setOrgId(elMfrDTO.getOrgId());
		
		return elMfrVO;
	}

	@Override
	public List<ElMfrVO> getAllElMfr(Long orgId) {
		return elMfrRepo.getAllElMfr(orgId);
	}

	
	 private int totalRows=0;
	    private int successfulUploads=0;

	    @Override
	    public int getTotalRows() {
	        return totalRows;
	    }

	    @Override
	    public int getSuccessfulUploads() {
	        return successfulUploads;
	    }

	    @Transactional
	    @Override
	    public void excelUploadForElMfr(MultipartFile[] files, String createdBy, Long orgId) throws ApplicationException, java.io.IOException {
	        List<ElMfrVO> dataToSave = new ArrayList<>();

	        // Reset counters at the start of each upload
	        totalRows = 0;
	        successfulUploads = 0;

	        for (MultipartFile file : files) {
	            try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	                Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
	                List<String> errorMessages = new ArrayList<>();
	                System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement

	                Row headerRow = sheet.getRow(0);
	                if (!isHeaderValid(headerRow)) {
	                    throw new ApplicationException("Invalid Excel format. Please refer to the sample file.");
	                }

	                // Check all rows for validity first
	                for (Row row : sheet) {
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue; // Skip header row and empty rows
	                    }

	                    totalRows++; // Increment totalRows
	                    System.out.println("Validating row: " + (row.getRowNum() + 1)); // Debug statement

	                    try {
	                        // Retrieve cell values based on the provided order
	                        String description = getStringCellValue(row.getCell(0));
	                        String elCode = getStringCellValue(row.getCell(1));

	                        // Check if the description already exists in the database
	                        if (elMfrRepo.existsByDescriptionAndOrgId(description, orgId)) {
	                            String errorMessage = String.format("This Description: %s Already Exists", description);
	                            errorMessages.add(errorMessage);
	                            continue; // Skip saving this row if it already exists
	                        }

	                        // Check if the elCode already exists in the database
	                        if (elMfrRepo.existsByElCodeAndOrgId(elCode, orgId)) {
	                            String errorMessage = String.format("This ElCode: %s Already Exists", elCode);
	                            errorMessages.add(errorMessage);
	                            continue; // Skip saving this row if it already exists
	                        }

	                        // Create ElMfrVO and add to list for batch saving
	                        ElMfrVO dataVO = new ElMfrVO();
	                        dataVO.setDescription(description);
	                        dataVO.setElCode(elCode);
	                        dataVO.setCreatedBy(createdBy);
	                        dataVO.setOrgId(orgId);
	                        dataVO.setUpdatedBy(createdBy);

	                        dataToSave.add(dataVO);
	                        successfulUploads++; // Increment successfulUploads

	                    } catch (Exception e) {
	                        errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
	                    }
	                }

	                // Save all valid rows
	                if (!dataToSave.isEmpty()) {
	                    elMfrRepo.saveAll(dataToSave);
	                }

	                if (!errorMessages.isEmpty()) {
	                    throw new ApplicationException("Excel upload validation failed. Errors: " + String.join(", ", errorMessages));
	                }

	            } catch (IOException | EncryptedDocumentException e) {
	                throw new ApplicationException("Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
	            }
	        }
	    }

	    private boolean isRowEmpty(Row row) {
			// TODO Auto-generated method stub
			return false;
		}

		private boolean isHeaderValid(Row headerRow) {
	        if (headerRow == null) {
	            return false;
	        }
	        // Validate header columns for expected field names
	        return "Description".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0)))
	                && "ElCode".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1)));
	    }

	    private String getStringCellValue(Cell cell) {
	        if (cell == null) {
	            return ""; // Return empty string if cell is null
	        }

	        switch (cell.getCellType()) {
	        case STRING:
	            return cell.getStringCellValue().trim(); // Remove leading/trailing whitespaces
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	                return new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue()); // Format date cells
	            } else {
	                double numericValue = cell.getNumericCellValue();
	                if (numericValue == (int) numericValue) {
	                    return String.valueOf((int) numericValue); // Return as integer if the number has no decimal part
	                } else {
	                    return BigDecimal.valueOf(numericValue).toPlainString(); // Return the full numeric value
	                }
	            }
	        case BOOLEAN:
	            return String.valueOf(cell.getBooleanCellValue());
	        case FORMULA:
	            return cell.getCellFormula(); // Return the formula if it's a formula cell
	        default:
	            return ""; // Return empty string for other cell types
	        }
	    }

	    private LocalDate parseDate(String stringCellValue) {
	        try {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	            return LocalDate.parse(stringCellValue, formatter); // Convert date from string format
	        } catch (Exception e) {
	            System.err.println("Error parsing date: " + stringCellValue);
	            return null; // Return null if parsing fails
	        }
	    }

	    private Long parseLong(String stringCellValue) {
	        try {
	            return Long.parseLong(stringCellValue); // Parse string to long
	        } catch (NumberFormatException e) {
	            System.err.println("Error parsing long: " + stringCellValue);
	            return null; // Return null if parsing fails
	        }
	    }

	    private BigDecimal parseBigDecimal(String stringCellValue) {
	        try {
	            return new BigDecimal(stringCellValue); // Parse string to BigDecimal
	        } catch (NumberFormatException e) {
	            System.err.println("Error parsing BigDecimal: " + stringCellValue);
	            return null; // Return null if parsing fails
	        }
	    }

	
	
	

}
