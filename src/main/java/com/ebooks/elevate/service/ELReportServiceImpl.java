package com.ebooks.elevate.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
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
import com.ebooks.elevate.repo.BudgetRepo;
import com.ebooks.elevate.repo.ElMfrRepo;
import com.ebooks.elevate.repo.PreviousYearActualRepo;

import io.jsonwebtoken.io.IOException;
@Service
public class ELReportServiceImpl implements ELReportService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ELReportServiceImpl.class);

	@Autowired
	ElMfrRepo elMfrRepo;

	@Autowired
	BudgetRepo budgetRepo;
	
	@Autowired
	PreviousYearActualRepo previousYearActualRepo;
	
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
	                System.out.println("Processing file: " + file .getOriginalFilename()); // Debug statement

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

		@Override
		public List<Map<String, Object>> getMisMatchClientTb(Long orgId, String clientCode) {
			// TODO Auto-generated method stub
			Set<Object[]> getTb = elMfrRepo.getMisMatchClientTb(orgId,clientCode);
			return getMisMatch(getTb);
		}

		private List<Map<String, Object>> getMisMatch(Set<Object[]> chCode) {
			List<Map<String, Object>> List1 = new ArrayList<>();
			for (Object[] ch : chCode) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", Integer.parseInt(ch[0].toString()));
				map.put("accountCode", ch[1] != null ? ch[1].toString() : "");
				map.put("accountName", ch[2] != null ? ch[2].toString() : "");
				map.put("action", ch[3] != null ? ch[3].toString() : "");
				map.put("screen", Integer.parseInt(ch[4].toString()));
				List1.add(map);
			}
			return List1;

		}

		@Override
		public List<Map<String, Object>> getClientBudgetDetails(Long orgId, String year, String client,
				String clientCode) {
			Set<Object[]> budget=budgetRepo.getClientBudgetDetails(orgId,year,client,clientCode);
			return budgetDetails(budget);
		}

		private List<Map<String, Object>> budgetDetails(Set<Object[]> budget) {
			List<Map<String, Object>>budgets=new ArrayList<>();
			for(Object[] bud:budget)
			{
				Map<String,Object>b= new HashMap<>();
				b.put("id", Integer.parseInt(bud[0].toString()));
				b.put("accountName", bud[1] != null ? bud[1].toString() : "");
				b.put("accountCode", bud[2] != null ? bud[2].toString() : "");
				b.put("natureOfAccount", bud[3] != null ? bud[3].toString() : "");
				b.put("january", ((Number) bud[4]).intValue());
				b.put("february", ((Number) bud[5]).intValue());
				b.put("march", ((Number) bud[6]).intValue());
				b.put("april", ((Number) bud[7]).intValue());
				b.put("may", ((Number) bud[8]).intValue());
				b.put("june", ((Number) bud[9]).intValue());
				b.put("july", ((Number) bud[10]).intValue());
				b.put("august", ((Number) bud[11]).intValue());
				b.put("september", ((Number) bud[12]).intValue());
				b.put("october", ((Number) bud[13]).intValue());
				b.put("november", ((Number) bud[14]).intValue());
				b.put("december", ((Number) bud[15]).intValue());
				
				budgets.add(b);
			}
			return budgets;
		}

		@Override
		public List<Map<String, Object>> getClientPreviousYearActualDetails(Long orgId, String year, String client,
				String clientCode) {
			Set<Object[]> actual=previousYearActualRepo.getClientPreviousYearActualDetails(orgId,year,client,clientCode);
			return actualDetails(actual);
		}

		private List<Map<String, Object>> actualDetails(Set<Object[]> actual) {
			List<Map<String, Object>>actuals=new ArrayList<>();
			for(Object[] bud:actual)
			{
				Map<String,Object>b= new HashMap<>();
				b.put("id", Integer.parseInt(bud[0].toString()));
				b.put("accountName", bud[1] != null ? bud[1].toString() : "");
				b.put("accountCode", bud[2] != null ? bud[2].toString() : "");
				b.put("natureOfAccount", bud[3] != null ? bud[3].toString() : "");
				b.put("january", ((Number) bud[4]).intValue());
				b.put("february", ((Number) bud[5]).intValue());
				b.put("march", ((Number) bud[6]).intValue());
				b.put("april", ((Number) bud[7]).intValue());
				b.put("may", ((Number) bud[8]).intValue());
				b.put("june", ((Number) bud[9]).intValue());
				b.put("july", ((Number) bud[10]).intValue());
				b.put("august", ((Number) bud[11]).intValue());
				b.put("september", ((Number) bud[12]).intValue());
				b.put("october", ((Number) bud[13]).intValue());
				b.put("november", ((Number) bud[14]).intValue());
				b.put("december", ((Number) bud[15]).intValue());
				
				actuals.add(b);
			}
			return actuals;
		}
		

}
