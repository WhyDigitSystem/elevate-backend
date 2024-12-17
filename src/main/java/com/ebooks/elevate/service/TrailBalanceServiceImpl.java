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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.dto.TbHeaderDTO;
import com.ebooks.elevate.entity.TbHeaderVO;
import com.ebooks.elevate.entity.TrailBalanceVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.TbHeaderRepo;
import com.ebooks.elevate.repo.TrailBalanceRepo;

import io.jsonwebtoken.io.IOException;

@Service
public class TrailBalanceServiceImpl implements TrailBalanceService {

	@Autowired
	TrailBalanceRepo trailBalanceRepo;

	@Autowired
	TbHeaderRepo headerRepo;

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

	@Transactional
	@Override
	public void excelUploadForTb(MultipartFile[] files, String createdBy, String clientCode, String finYear,
			String month) throws ApplicationException {
		List<TrailBalanceVO> dataToSave = new ArrayList<>();
		int totalRows = 0;
		int successfulUploads = 0;

		for (MultipartFile file : files) {
			try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
				Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
				List<String> errorMessages = new ArrayList<>();
				System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement
				Row headerRow = sheet.getRow(0);
				if (!isHeaderValid(headerRow)) {
					throw new ApplicationException("Invalid Excel format. Please refer to the Tb file.");
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
						String accountName = getStringCellValue(row.getCell(1)); // Account Name is in column 1
						String accountCode = getStringCellValue(row.getCell(0)); // Account Code is in column 0
						BigDecimal credit = parseBigDecimal(getStringCellValue(row.getCell(2))); // Credit is in column
																									// 2
						BigDecimal debit = parseBigDecimal(getStringCellValue(row.getCell(3))); // Debit is in column 3

						// Validate credit and debit fields (optional)
						if (credit == null || debit == null) {
							errorMessages.add("Invalid Credit/Debit value at row " + (row.getRowNum() + 1));
							continue; // Skip this row if Credit/Debit is invalid
						}

						// Create TrailBalanceVO and add to list for batch saving
						TrailBalanceVO dataVO = new TrailBalanceVO();
						dataVO.setAccountName(accountName);
						dataVO.setAccountCode(accountCode);
						dataVO.setClientCode(clientCode);
						dataVO.setCredit(credit);
						dataVO.setDebit(debit);
						dataVO.setClientCode(clientCode);
						dataVO.setFinYear(finYear);
						dataVO.setMonth(month);
						dataVO.setCreatedBy(createdBy);
						dataVO.setUpdatedBy(createdBy);

						dataToSave.add(dataVO);
						successfulUploads++; // Increment successfulUploads

					} catch (Exception e) {
						errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
					}
				}

				// Save all valid rows
				trailBalanceRepo.saveAll(dataToSave);

				if (!errorMessages.isEmpty()) {
					throw new ApplicationException(
							"Excel upload validation failed. Errors: " + String.join(", ", errorMessages));
				}

			} catch (IOException e) {
				throw new ApplicationException(
						"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
			} catch (EncryptedDocumentException e1) {
				e1.printStackTrace();
			} catch (java.io.IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private LocalDate parseDate(String stringCellValue) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			return LocalDate.parse(stringCellValue, formatter);
		} catch (Exception e) {
			System.err.println("Error parsing date: " + stringCellValue);
			return null;
		}
	}

	private boolean isRowEmpty(Row row) {
		for (Cell cell : row) {
			if (cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}

	private boolean isHeaderValid(Row headerRow) {
		if (headerRow == null) {
			return false;
		}
		// Adjusted to match the new column names from the sample
		return "Account Code".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0)))
				&& "Account Name".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1)))
				&& "Credit".equalsIgnoreCase(getStringCellValue(headerRow.getCell(2)))
				&& "Debit".equalsIgnoreCase(getStringCellValue(headerRow.getCell(3)));
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

	private Long parseLong(String stringCellValue) {
		try {
			return Long.parseLong(stringCellValue);
		} catch (NumberFormatException e) {
			System.err.println("Error parsing long: " + stringCellValue);
			return null;
		}
	}

	private BigDecimal parseBigDecimal(String stringCellValue) {
		try {
			return new BigDecimal(stringCellValue);
		} catch (NumberFormatException e) {
			System.err.println("Error parsing BigDecimal: " + stringCellValue);
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> getFillGridForTbExcelUpload(String finYear, String clientCode, String month) {

		Set<Object[]> getDetails = trailBalanceRepo.getFillGridForTbExcelUpload(finYear, clientCode, month);
		return getFillGrid(getDetails);
	}

	private List<Map<String, Object>> getFillGrid(Set<Object[]> getCoa) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getCoa) {
			Map<String, Object> map = new HashMap<>();
			map.put("accountCode", ch[0] != null ? ch[0].toString() : "");
			map.put("accountName", ch[1] != null ? ch[1].toString() : "");
			map.put("credit", ch[2] != null ? new BigDecimal(ch[2].toString()) : BigDecimal.ZERO);
			map.put("debit", ch[3] != null ? new BigDecimal(ch[3].toString()) : BigDecimal.ZERO);

			List1.add(map);
		}
		return List1;
	}

	@Override
	public Map<String, Object> createUpdateTrailBalance(TbHeaderDTO tbHeaderDTO) throws ApplicationException {

		TbHeaderVO tbHeaderVO = null;

		String message = null;

		if (ObjectUtils.isEmpty(tbHeaderDTO.getId())) {

			tbHeaderVO = new TbHeaderVO();

			tbHeaderVO.setCreatedBy(tbHeaderDTO.getCreatedBy());
			tbHeaderVO.setUpdatedBy(tbHeaderDTO.getCreatedBy());

			message = "TrailBalance Creation SuccessFully";

		} else {

			tbHeaderVO = headerRepo.findById(tbHeaderDTO.getId())
					.orElseThrow(() -> new ApplicationException("HeaderDTO not found with id: " + tbHeaderDTO.getId()));
			tbHeaderVO.setUpdatedBy(tbHeaderDTO.getCreatedBy());
			
			message = "TrailBalance Updation SuccessFully";
		}
		
		tbHeaderVO=getTbHeaderVOromTbHeaderDTO(tbHeaderVO,tbHeaderDTO);
		headerRepo.save(tbHeaderVO);
		
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("tbHeaderVO", tbHeaderVO); // Return the list of saved records
		return response;
	}

	private TbHeaderVO getTbHeaderVOromTbHeaderDTO(TbHeaderVO tbHeaderVO, TbHeaderDTO tbHeaderDTO) {

		tbHeaderVO.setClientCode(tbHeaderDTO.getClientCode());
	    tbHeaderVO.setMonth(tbHeaderDTO.getMonth());
	    tbHeaderVO.setFinYear(tbHeaderDTO.getFinYear());

	    return tbHeaderVO;
	}
}
