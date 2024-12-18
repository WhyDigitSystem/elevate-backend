package com.ebooks.elevate.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ebooks.elevate.repo.CCoaRepo;
import com.ebooks.elevate.repo.CoaRepo;
import com.ebooks.elevate.repo.LedgerMappingRepo;

import io.jsonwebtoken.io.IOException;

@Service
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	CoaRepo coaRepo;

	@Autowired
	CCoaRepo cCoaRepo;

	@Autowired
	LedgerMappingRepo ledgerMappingRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(BusinessServiceImpl.class);

	@Override
	public Map<String, Object> createUpdateCoa(CoaDTO coaDTO) throws ApplicationException {

		CoaVO coaVO = null;
		String message;

		// Determine if it's a create or update operation
		if (ObjectUtils.isEmpty(coaDTO.getId())) {
			// Create operation

			if (coaRepo.existsByAccountCode(coaDTO.getAccountCode())) {

				String errorMessage = String.format("This AccountCode: %s Already Exists", coaDTO.getAccountCode());
				throw new ApplicationException(errorMessage);
			}

			if (coaRepo.existsByAccountGroupName(coaDTO.getAccountGroupName())) {

				String errorMessage = String.format("This AccountGroupName: %s Already Exists",
						coaDTO.getAccountGroupName());
				throw new ApplicationException(errorMessage);
			}

			coaVO = new CoaVO();

			coaVO.setCreatedBy(coaDTO.getCreatedBy());
			coaVO.setUpdatedBy(coaDTO.getCreatedBy());
			message = "Chart Of Account Creation Successful";
		} else {
			// Update operation
			coaVO = coaRepo.findById(coaDTO.getId()).orElseThrow(
					() -> new ApplicationException("Chart Of Account not found with id: " + coaDTO.getId()));
			coaVO.setUpdatedBy(coaDTO.getCreatedBy());

			if (!coaVO.getAccountCode().equalsIgnoreCase(coaDTO.getAccountCode())) {

				if (coaRepo.existsByAccountCode(coaDTO.getAccountCode())) {

					String errorMessage = String.format("This AccountCode: %s Already Exists", coaDTO.getAccountCode());
					throw new ApplicationException(errorMessage);
				}

				coaVO.setAccountCode(coaDTO.getAccountCode());
			}

			if (!coaVO.getAccountGroupName().equalsIgnoreCase(coaDTO.getAccountGroupName())) {

				if (coaRepo.existsByAccountGroupName(coaDTO.getAccountGroupName())) {

					String errorMessage = String.format("This AccountGroupName: %s Already Exists",
							coaDTO.getAccountGroupName());
					throw new ApplicationException(errorMessage);
				}

				coaVO.setAccountGroupName(coaDTO.getAccountGroupName());
			}

			message = "Chart Of Account Updation Successful";
		}

		// Map fields from DTO to VO
		coaVO = getcoaVOFromcoaDTO(coaVO, coaDTO);

		// Save the entity to the repository
		coaRepo.save(coaVO);

		// Prepare the response
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("coaVO", coaVO);

		return response;
	}

	private CoaVO getcoaVOFromcoaDTO(CoaVO coaVO, CoaDTO coaDTO) throws ApplicationException {

		// Basic field mapping
		coaVO.setType(coaDTO.getType());
		coaVO.setGroupName(coaDTO.getGroupName());
		coaVO.setAccountGroupName(coaDTO.getAccountGroupName());
		coaVO.setNatureOfAccount(coaDTO.getNatureOfAccount());
		coaVO.setAccountCode(coaDTO.getAccountCode());
		coaVO.setCreatedBy(coaDTO.getCreatedBy());
		coaVO.setInterBranchAc(coaDTO.isInterBranchAc());
		coaVO.setControllAc(coaDTO.isControllAc());
		coaVO.setCurrency(coaDTO.getCurrency());
		coaVO.setActive(coaDTO.isActive());

		// Handle type "group" with no groupName
		if ("group".equalsIgnoreCase(coaDTO.getType()) && coaDTO.getGroupName() == null) {
			coaVO.setParentId(null);
			coaVO.setParentCode("0");
		}

		else {
			// Fetch the parent record
			CoaVO coaVO2 = coaRepo.findByAccountGroupName(coaDTO.getGroupName());

			if (coaVO2 == null) {
				// Handle the case where the parent record is not found
				throw new ApplicationException("Parent record not found for groupName: " + coaDTO.getGroupName());
			}

			// Set parentId and parentCode
			coaVO.setParentId(coaVO2.getId().toString());
			coaVO.setParentCode(coaVO2.getAccountCode());
		}

		return coaVO;
	}

	@Override
	public List<CoaVO> getAllCao() {
		return coaRepo.findAll();
	}

	@Override
	public Optional<CoaVO> getCaoById(Long id) {
		return coaRepo.findById(id);
	}

	@Override
	public List<Map<String, Object>> getGroupName() {
		Set<Object[]> getActiveGroup = coaRepo.findGroups();
		return getGroup(getActiveGroup);
	}

	private List<Map<String, Object>> getGroup(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("group", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public Map<String, Object> createUpdateCCoa(CCoaDTO cCoaDTO) throws ApplicationException {
		CCoaVO cCoaVO = null;
		String message;

		// Determine if it's a create or update operation
		if (ObjectUtils.isEmpty(cCoaDTO.getId())) {
			// Create operation

			if (cCoaRepo.existsByAccountCodeAndClientCode(cCoaDTO.getAccountCode(), cCoaDTO.getClientCode())) {

				String errorMessage = String.format("This AccountCode: %s Already Exists", cCoaDTO.getAccountCode());
				throw new ApplicationException(errorMessage);
			}

			if (cCoaRepo.existsByAccountNameAndClientCode(cCoaDTO.getAccountName(), cCoaDTO.getClientCode())) {

				String errorMessage = String.format("This AccountGroupName: %s Already Exists",
						cCoaDTO.getAccountName());
				throw new ApplicationException(errorMessage);
			}

			cCoaVO = new CCoaVO();

			cCoaVO.setCreatedBy(cCoaDTO.getCreatedBy());
			cCoaVO.setUpdatedBy(cCoaDTO.getCreatedBy());
			message = "Chart Of Account Creation Successful";
		} else {
			// Update operation
			cCoaVO = cCoaRepo.findById(cCoaDTO.getId()).orElseThrow(
					() -> new ApplicationException("Chart Of Account not found with id: " + cCoaDTO.getId()));
			cCoaVO.setUpdatedBy(cCoaDTO.getCreatedBy());

			if (!cCoaVO.getAccountCode().equalsIgnoreCase(cCoaDTO.getAccountCode())) {

				if (cCoaRepo.existsByAccountCodeAndClientCode(cCoaDTO.getAccountCode(),cCoaDTO.getClientCode())) {

					String errorMessage = String.format("This AccountCode: %s Already Exists",
							cCoaDTO.getAccountCode());
					throw new ApplicationException(errorMessage);
				}

				cCoaVO.setAccountCode(cCoaDTO.getAccountCode());
			}

			if (!cCoaVO.getAccountName().equalsIgnoreCase(cCoaDTO.getAccountName())) {

				if (cCoaRepo.existsByAccountNameAndClientCode(cCoaDTO.getAccountName(), cCoaDTO.getClientCode())) {

					String errorMessage = String.format("This AccountGroupName: %s Already Exists",
							cCoaDTO.getAccountName());
					throw new ApplicationException(errorMessage);
				}

				cCoaVO.setAccountName(cCoaDTO.getAccountName());
			}

			message = "Chart Of Account Updation Successful";
		}

		// Map fields from DTO to VO
		cCoaVO = getCCoaVOFromCCoaDTO(cCoaVO, cCoaDTO);
 
		// Save the entity to the repository
		cCoaRepo.save(cCoaVO);

		// Prepare the response
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("cCoaVO", cCoaVO);

		return response;
	}

	private CCoaVO getCCoaVOFromCCoaDTO(CCoaVO cCoaVO, CCoaDTO cCoaDTO) throws ApplicationException {
		// Basic field mapping
		cCoaVO.setAccountCode(cCoaDTO.getAccountCode());
		cCoaVO.setAccountName(cCoaDTO.getAccountName());
		cCoaVO.setCreatedBy(cCoaDTO.getCreatedBy());
		cCoaVO.setCurrency(cCoaDTO.getCurrency());
		cCoaVO.setActive(cCoaDTO.isActive());
		cCoaVO.setClientId(cCoaDTO.getClientId());
		cCoaVO.setClientCode(cCoaDTO.getClientCode());

		return cCoaVO;
	}

	@Override
	public List<CCoaVO> getAllCCao() {
		return cCoaRepo.findAll();
	}

	@Override
	public Optional<CCoaVO> getCCaoById(Long id) {
		return cCoaRepo.findById(id);
	}

	@Override
	public List<Map<String, Object>> getGroupNameForCCoa() {
		Set<Object[]> getGroupNameCCoa = cCoaRepo.findGroups();
		return getGroupForCCa(getGroupNameCCoa);
	}

	private List<Map<String, Object>> getGroupForCCa(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("group", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	private int totalRows = 0;
	private int successfulUploads = 0;

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
	public void excelUploadForCoa(MultipartFile[] files, String createdBy)
			throws ApplicationException, EncryptedDocumentException, java.io.IOException {
		List<CoaVO> mainGroupList = new ArrayList<>(); // List to store main group records
		List<CoaVO> subGroupList = new ArrayList<>(); // List to store subgroup records
		List<CoaVO> accountList = new ArrayList<>(); // List to store account records

		// Reset counters at the start of each upload
		totalRows = 0;
		successfulUploads = 0;

		for (MultipartFile file : files) {
			try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
				Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
				List<String> errorMessages = new ArrayList<>();

				System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement

				Row headerRow = sheet.getRow(0);
				if (!isHeaderValid1(headerRow)) {
					throw new ApplicationException("Invalid Excel format. Please refer to the sample file.");
				}

				// Check all rows for validity first
				for (Row row : sheet) {
					if (row.getRowNum() == 0 || isRowEmpty1(row)) {
						continue; // Skip header row and empty rows
					}

					totalRows++; // Increment totalRows

					try {
						// Retrieve cell values based on the provided order
						String type = getStringCellValue1(row.getCell(0));
						String groupName = getStringCellValue1(row.getCell(1));
						String accountCode = getStringCellValue1(row.getCell(2));
						String accountName = getStringCellValue1(row.getCell(3));
						String natureOfAccount = getStringCellValue1(row.getCell(4));
						String activeString = getStringCellValue1(row.getCell(5)); // Get value from the cell

						// Convert activeString to integer and handle the conditions
						boolean active;
						if ("1".equals(activeString)) {
							active = true; // If the value is '1', set active to true
						} else if ("0".equals(activeString)) {
							active = false; // If the value is '0', set active to false
						} else {
							throw new ApplicationException(
									"Invalid value for 'active' field. Expected '1' or '0', but got: " + activeString);
						}

						// Create CoaVO and add to appropriate list
						CoaVO coaVO = new CoaVO();
						coaVO.setType(type);
						coaVO.setGroupName(groupName);
						coaVO.setAccountGroupName(accountName);
						coaVO.setNatureOfAccount(natureOfAccount);
						coaVO.setAccountCode(accountCode);
						coaVO.setCreatedBy(createdBy);
						coaVO.setInterBranchAc(false);
						coaVO.setControllAc(false);
						coaVO.setCurrency("INR");
						coaVO.setActive(active);
						coaVO.setUpdatedBy(createdBy);

						// Logic for adding to specific lists based on conditions
						if ("Group".equalsIgnoreCase(type)) {
							if (groupName == null || groupName.isEmpty()) {
								coaVO.setParentCode("0");
								coaVO.setParentId(null);
								// Main group (groupName is null)
								mainGroupList.add(coaVO);
								coaRepo.saveAll(mainGroupList);
							} else {
								CoaVO vo = coaRepo.findByAccountGroupName(groupName);
								coaVO.setParentCode(vo.getAccountCode());
								coaVO.setParentId(vo.getId().toString());
								// Subgroup (groupName is not null)
								subGroupList.add(coaVO);
								coaRepo.saveAll(subGroupList);
							}
						} else if ("Account".equalsIgnoreCase(type) && groupName != null && !groupName.isEmpty()) {
							// Account (groupName is not null)
							CoaVO vo = coaRepo.findByAccountGroupName(groupName);
							coaVO.setParentCode(vo.getAccountCode());
							coaVO.setParentId(vo.getId().toString());
							accountList.add(coaVO);
							coaRepo.saveAll(accountList);
						}

						successfulUploads++; // Increment successfulUploads

					} catch (Exception e) {
						errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
					}
				}

				if (!errorMessages.isEmpty()) {
					throw new ApplicationException(
							"Excel upload validation failed. Errors: " + String.join(", ", errorMessages));
				}

			} catch (IOException e) {
				throw new ApplicationException(
						"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
			}
		}
	}

	private boolean isHeaderValid1(Row headerRow) {
		if (headerRow == null) {
			return false;
		}

		// Adjust based on the actual header names in your Excel
		return "Type".equalsIgnoreCase(getStringCellValue1(headerRow.getCell(0)))
				&& "Group Name".equalsIgnoreCase(getStringCellValue1(headerRow.getCell(1)))
				&& "Account Code".equalsIgnoreCase(getStringCellValue1(headerRow.getCell(2)))
				&& "AccountName".equalsIgnoreCase(getStringCellValue1(headerRow.getCell(3)))
				&& "Nature of Account".equalsIgnoreCase(getStringCellValue1(headerRow.getCell(4)))
				&& "Active".equalsIgnoreCase(getStringCellValue1(headerRow.getCell(5)));
	}

	private String getStringCellValue1(Cell cell) {
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

	private boolean isRowEmpty1(Row row) {
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}

	// EXCEL UPLOAD FOR CLIENT COA
//	
//	 private int totalRows1=0;
//	    private int successfulUploads1=0;
//
//	    @Override
//	    public int getTotalRows1() {
//	        return totalRows;
//	    }
//
//	    @Override
//	    public int getSuccessfulUploads1() {
//	        return successfulUploads;
//	    }

//	@Transactional
//	@Override
//	public void excelUploadForCCoa(MultipartFile[] files, String createdBy, String clientCode)
//			throws EncryptedDocumentException, java.io.IOException, ApplicationException {
//		List<CCoaVO> mainGroupList = new ArrayList<>(); // List to store main group records
//
//		// Reset counters at the start of each upload
//		totalRows = 0;
//		successfulUploads = 0;
//
//		for (MultipartFile file : files) {
//			try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//				Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
//				List<String> errorMessages = new ArrayList<>();
//
//				System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement
//
//				Row headerRow = sheet.getRow(0);
//				if (!isHeaderValid1(headerRow)) {
//					throw new ApplicationException("Invalid Excel format. Please refer to the sample file.");
//				}
//
//				// Check all rows for validity first
//				for (Row row : sheet) {
//					if (row.getRowNum() == 0 || isRowEmpty1(row)) {
//						continue; // Skip header row and empty rows
//					}
//
//					totalRows++; // Increment totalRows
//
//					try {
//						// Retrieve cell values based on the provided order
////						String type = getStringCellValue1(row.getCell(0));
////						String groupName = getStringCellValue1(row.getCell(1));
//						String accountCode = getStringCellValue1(row.getCell(0));
//						String accountName = getStringCellValue1(row.getCell(1));
////						String natureOfAccount = getStringCellValue1(row.getCell(4));
//						String activeString = getStringCellValue1(row.getCell(2)); // Get value from the cell
//
//						// Convert activeString to integer and handle the conditions
//						boolean active;
//						if ("1".equals(activeString)) {
//							active = true; // If the value is '1', set active to true
//						} else if ("0".equals(activeString)) {
//							active = false; // If the value is '0', set active to false
//						} else {
//							throw new ApplicationException(
//									"Invalid value for 'active' field. Expected '1' or '0', but got: " + activeString);
//						}
//
//						// Create CoaVO and add to appropriate list
//						CCoaVO cCoaVO = new CCoaVO();
//						
//						if (cCoaRepo.existsByAccountCodeAndClientCode(accountCode, clientCode)) {
//
//							String errorMessage = String.format("This AccountCode: %s Already Exists", accountCode);
//							throw new ApplicationException(errorMessage);
//						}
//
//						if (cCoaRepo.existsByAccountNameAndClientCode(accountName, clientCode)) {
//
//							String errorMessage = String.format("This AccountGroupName: %s Already Exists",
//									accountName);
//							throw new ApplicationException(errorMessage);
//						}
//
//						cCoaVO.setAccountName(accountName);
//						cCoaVO.setAccountCode(accountCode);
//						cCoaVO.setCreatedBy(createdBy);
//						cCoaVO.setCurrency("INR");
//						cCoaVO.setActive(active);
//						cCoaVO.setUpdatedBy(createdBy);
//						cCoaVO.setClientId(clientCode);
//
//						mainGroupList.add(cCoaVO);
//
//						successfulUploads++; // Increment successfulUploads
//
//					} catch (Exception e) {
//						errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
//					}
//				}
//
//				if (!errorMessages.isEmpty()) {
//					throw new ApplicationException(
//							"Excel upload validation failed. Errors: " + String.join(", ", errorMessages));
//				}
//
//			} catch (IOException e) {
//				throw new ApplicationException(
//						"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
//			}
//		}
//	}

	

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
	
	@Transactional
	@Override
	public ExcelUploadResultDTO excelUploadForCCoa(MultipartFile[] files, String createdBy, String clientCode)
	        throws EncryptedDocumentException, IOException, ApplicationException, java.io.IOException {

		ExcelUploadResultDTO result = new ExcelUploadResultDTO(); // Result Object

	    List<CCoaVO> mainGroupList = new ArrayList<>();
	    result.setTotalExcelRows(0);
	    result.setSuccessfulUploads(0);
	    result.setUnsuccessfulUploads(0);

	    for (MultipartFile file : files) {
	        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	            Sheet sheet = workbook.getSheetAt(0);
	            Row headerRow = sheet.getRow(0);

	            // Validate header
	           

	            for (Row row : sheet) {
	                if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                    continue; // Skip header and empty rows
	                }

	                result.setTotalExcelRows(result.getTotalExcelRows() + 1); // Increment totalRows
	                try {
	                    // Parse cell values
	                    String accountCode = getStringCellValue(row.getCell(0));
	                    String accountName = getStringCellValue(row.getCell(1));
	                    String activeString = getStringCellValue(row.getCell(2));

	                    boolean active = "1".equals(activeString); // Convert "1"/"0" to boolean

	                    // Business Validations
	                    if (cCoaRepo.existsByAccountCodeAndClientCode(accountCode, clientCode)) {
	                        throw new ApplicationException(String.format("Account Code '%s' already exists for this Client", accountCode));
	                    }

	                    if (cCoaRepo.existsByAccountNameAndClientCode(accountName, clientCode)) {
	                        throw new ApplicationException(String.format("Account Name '%s' already exists for this Client", accountName));
	                    }

	                    // Create and add CCoaVO
	                    CCoaVO cCoaVO = new CCoaVO();
	                    cCoaVO.setAccountName(accountName);
	                    cCoaVO.setAccountCode(accountCode);
	                    cCoaVO.setCreatedBy(createdBy);
	                    cCoaVO.setCurrency("INR");
	                    cCoaVO.setActive(active);
	                    cCoaVO.setUpdatedBy(createdBy);
	                    cCoaVO.setClientCode(clientCode);

	                    mainGroupList.add(cCoaVO);
	                    result.setSuccessfulUploads(result.getSuccessfulUploads() + 1);

	                } catch (Exception e) {
	                	result.setUnsuccessfulUploads(result.getUnsuccessfulUploads() + 1);
	                    String error = String.format("Row %d: %s", row.getRowNum() + 1, e.getMessage());
	                    result.addFailureReason(error); // Capture failure reason
	                }
	            }
	        }
	    }

	    // Save the successfully processed records
	    if (!mainGroupList.isEmpty()) {
	        cCoaRepo.saveAll(mainGroupList);
	    }

	    return result; // Return the result summary
	}


	@Override
	public Map<String, Object> createUpdateLedgerMapping(LedgerMappingDTO ledgerMappingDTO)
			throws ApplicationException {

		List<LedgerMappingVO> ledgerMappingVOList = new ArrayList<LedgerMappingVO>();
		String message = "LedgerMapping processed successfully";

		// Iterate over the list of LedgerMappingDTO objects

			LedgerMappingVO ledgerMappingVO = new LedgerMappingVO();

			if (ObjectUtils.isEmpty(ledgerMappingDTO.getId())) {

				// New record
				ledgerMappingVO.setCreatedBy(ledgerMappingDTO.getCreatedBy());
				ledgerMappingVO.setUpdatedBy(ledgerMappingDTO.getCreatedBy());
				ledgerMappingVO.setClientCoa(ledgerMappingDTO.getClientCoa());
				ledgerMappingVO.setClientCoaCode(ledgerMappingDTO.getClientCoaCode());
				ledgerMappingVO.setCoa(ledgerMappingDTO.getCoa());
				ledgerMappingVO.setOrgId(ledgerMappingDTO.getOrgId());
				ledgerMappingVO.setCoaCode(ledgerMappingDTO.getCoaCode());
				ledgerMappingVO.setCreatedBy(ledgerMappingDTO.getCreatedBy());
				ledgerMappingVO.setActive(ledgerMappingDTO.isActive());
				ledgerMappingVO.setClientCode(ledgerMappingDTO.getClientCode());
				
				if (ledgerMappingRepo.existsByClientCoaCodeAndClientCode(ledgerMappingDTO.getClientCoaCode(),ledgerMappingDTO.getClientCode())) {

					String errorMessage = String.format("This Client Account Code: %s Already Exists",
							ledgerMappingDTO.getClientCoaCode());
					throw new ApplicationException(errorMessage);
				}
				
				if (ledgerMappingRepo.existsByClientCoaAndClientCode(ledgerMappingDTO.getClientCoa(),ledgerMappingDTO.getClientCode())) {

					String errorMessage = String.format("This Client Account Name: %s Already Exists",
							ledgerMappingDTO.getClientCoa());
					throw new ApplicationException(errorMessage);
				}
				

				ledgerMappingRepo.save(ledgerMappingVO);
				// Set the message for creation
				message = "LedgerMapping Creation Successful";
				ledgerMappingVOList.add(ledgerMappingVO);
			}

			else {
				// Existing record, update it
				ledgerMappingVO = ledgerMappingRepo.findById(ledgerMappingDTO.getId()).orElseThrow(
						() -> new ApplicationException("LedgerMapping not found with id: " + ledgerMappingDTO.getId()));
				
				if (!ledgerMappingVO.getClientCoaCode().equalsIgnoreCase(ledgerMappingDTO.getClientCoaCode())) {

					if (ledgerMappingRepo.existsByClientCoaCodeAndClientCode(ledgerMappingDTO.getClientCoaCode(),ledgerMappingDTO.getClientCode())) {

						String errorMessage = String.format("This Client Account Code: %s Already Exists",
								ledgerMappingDTO.getClientCoaCode());
						throw new ApplicationException(errorMessage);
					}

					ledgerMappingVO.setClientCoaCode(ledgerMappingDTO.getClientCoaCode());
				}

				if (!ledgerMappingVO.getClientCoa().equalsIgnoreCase(ledgerMappingDTO.getClientCoa())) {

					if (ledgerMappingRepo.existsByClientCoaAndClientCode(ledgerMappingDTO.getClientCoa(),ledgerMappingDTO.getClientCode())) {

						String errorMessage = String.format("This Client Account Name: %s Already Exists",
								ledgerMappingDTO.getClientCoa());
						throw new ApplicationException(errorMessage);
					}

					ledgerMappingVO.setClientCoa(ledgerMappingDTO.getClientCoa());
				}
				
				ledgerMappingVO.setUpdatedBy(ledgerMappingDTO.getCreatedBy());
				ledgerMappingVO.setCoa(ledgerMappingDTO.getCoa());
				ledgerMappingVO.setOrgId(ledgerMappingDTO.getOrgId());
				ledgerMappingVO.setCoaCode(ledgerMappingDTO.getCoaCode());
				ledgerMappingVO.setActive(ledgerMappingDTO.isActive());
				ledgerMappingRepo.save(ledgerMappingVO);
				message = "LedgerMapping Updation Successful";
				ledgerMappingVOList.add(ledgerMappingVO);
			}

		

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("ledgerMappingVOList", ledgerMappingVOList); // Return the list of saved records
		return response;
	}

	@Override
	public List<Map<String, Object>> getLedgerMap() {
		Set<Object[]> getActiveGroup = coaRepo.findAccountMap();
		return getLedgerGroup(getActiveGroup);
	}

	private List<Map<String, Object>> getLedgerGroup(Set<Object[]> getActiveGroup) {
		// A map to store the hierarchy for efficient processing
		Map<String, Map<String, Object>> mainGroupMap = new LinkedHashMap<>();

		for (Object[] row : getActiveGroup) {
			String mainGroupName = (String) row[0];
			String mainGroupCode = (String) row[1];
			String subGroupName = (String) row[2];
			String subGroupCode = (String) row[3];
			String accountName = (String) row[4];
			String accountCode = (String) row[5];

			// Add or retrieve main group
			Map<String, Object> mainGroup = mainGroupMap.computeIfAbsent(mainGroupCode, k -> {
				Map<String, Object> group = new LinkedHashMap<>();
				group.put("mainGroupName", mainGroupName);
				group.put("mainGroupCode", mainGroupCode);
				group.put("subGroups", new LinkedHashMap<>());
				return group;
			});

			// Add or retrieve sub group within main group
			Map<String, Map<String, Object>> subGroupMap = (Map<String, Map<String, Object>>) mainGroup
					.get("subGroups");
			Map<String, Object> subGroup = subGroupMap.computeIfAbsent(subGroupCode, k -> {
				Map<String, Object> group = new LinkedHashMap<>();
				group.put("subGroupName", subGroupName);
				group.put("subGroupCode", subGroupCode);
				group.put("accounts", new ArrayList<>());
				return group;
			});

			// Add account to sub group
			List<Map<String, String>> accounts = (List<Map<String, String>>) subGroup.get("accounts");
			Map<String, String> account = new LinkedHashMap<>();
			account.put("accountName", accountName);
			account.put("accountCode", accountCode);
			accounts.add(account);
		}

		// Convert the hierarchical map into a list
		List<Map<String, Object>> result = new ArrayList<>();
		for (Map<String, Object> mainGroup : mainGroupMap.values()) {
			Map<String, Map<String, Object>> subGroups = (Map<String, Map<String, Object>>) mainGroup.get("subGroups");
			mainGroup.put("subGroups", new ArrayList<>(subGroups.values()));
			result.add(mainGroup);
		}

		return result;

	}

	@Override
	public List<Map<String, Object>> getFillGridForLedgerMapping(String clientCode) {
		Set<Object[]> getFullGrid = ledgerMappingRepo.getFillGridForLedgerMapping(clientCode);
		return getFillGridForLedger(getFullGrid);
	}

	private List<Map<String, Object>> getFillGridForLedger(Set<Object[]> getFullGrid) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getFullGrid) {
			Map<String, Object> map = new HashMap<>();
			map.put("clientCOA", ch[0] != null ? ch[0].toString() : ""); // Map accountGroupName
			map.put("clientCoaCode", ch[1] != null ? ch[1].toString() : ""); // Map accountCode
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getCOAForLedgerMapping() {
		Set<Object[]> getCoa = ledgerMappingRepo.getCOA();
		return getCOAForLedgerMapping(getCoa);
	}

	private List<Map<String, Object>> getCOAForLedgerMapping(Set<Object[]> getCoa) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getCoa) {
			Map<String, Object> map = new HashMap<>();
			map.put("accountGroupName", ch[0] != null ? ch[0].toString() : "");
			map.put("accountCode", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public Optional<LedgerMappingVO> getLedgerMappingbyId(Long id) {
		return ledgerMappingRepo.findById(id);
	}

	@Override
	public List<LedgerMappingVO> getAllLedgerMapping() {
		return ledgerMappingRepo.findAll();
	}

	@Override
	public ExcelUploadResultDTO excelUploadForLedgerMapping(MultipartFile[] files, String createdBy,
			String clientCode,Long orgId) throws EncryptedDocumentException, java.io.IOException {
		
		ExcelUploadResultDTO result = new ExcelUploadResultDTO(); // Result Object

	    List<LedgerMappingVO> mainGroupList = new ArrayList<>();
	    result.setTotalExcelRows(0);
	    result.setSuccessfulUploads(0);
	    result.setUnsuccessfulUploads(0);

	    for (MultipartFile file : files) {
	        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	            Sheet sheet = workbook.getSheetAt(0);
	            Row headerRow = sheet.getRow(0);

	            // Validate header
	           

	            for (Row row : sheet) {
	                if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                    continue; // Skip header and empty rows
	                }

	                result.setTotalExcelRows(result.getTotalExcelRows() + 1); // Increment totalRows
	                try {
	                    // Parse cell values
	                    String clientAccountName = getStringCellValue(row.getCell(0));
	                    String clientAccountCode = getStringCellValue(row.getCell(1));
	                    String elAccountCode = getStringCellValue(row.getCell(2));
	                    String activeString = getStringCellValue(row.getCell(3));
	                    

	                    boolean active = "1".equals(activeString); // Convert "1"/"0" to boolean

	                    // Business Validations
	                    if (ledgerMappingRepo.existsByClientCoaCodeAndClientCode(clientAccountCode,clientCode)) {

	    					String errorMessage = String.format("This Client Account Code: %s Already Exists",
	    							clientAccountCode);
	    					throw new ApplicationException(errorMessage);
	    				}
	    				
	    				if (ledgerMappingRepo.existsByClientCoaAndClientCode(clientAccountName,clientCode)) {

	    					String errorMessage = String.format("This Client Account Name: %s Already Exists",
	    							clientAccountName);
	    					throw new ApplicationException(errorMessage);
	    				}

	    				CoaVO coa= coaRepo.findByOrgIdAndAccountCode(orgId,elAccountCode);
	    				
	    				LedgerMappingVO ledgerMappingVO = new LedgerMappingVO();

	    					ledgerMappingVO.setCreatedBy(createdBy);
	    					ledgerMappingVO.setUpdatedBy(createdBy);
	    					ledgerMappingVO.setClientCoa(clientAccountName);
	    					ledgerMappingVO.setClientCoaCode(clientAccountCode);
	    					ledgerMappingVO.setCoa(elAccountCode);
	    					ledgerMappingVO.setCoaCode(coa.getAccountGroupName());
	    					ledgerMappingVO.setActive(active);
	    					ledgerMappingVO.setClientCode(clientCode);

	                    mainGroupList.add(ledgerMappingVO);
	                    result.setSuccessfulUploads(result.getSuccessfulUploads() + 1);

	                } catch (Exception e) {
	                	result.setUnsuccessfulUploads(result.getUnsuccessfulUploads() + 1);
	                    String error = String.format("Row %d: %s", row.getRowNum() + 1, e.getMessage());
	                    result.addFailureReason(error); // Capture failure reason
	                }
	            }
	        }
	    }

	    // Save the successfully processed records
	    if (!mainGroupList.isEmpty()) {
	        ledgerMappingRepo.saveAll(mainGroupList);
	    }

	    return result; // Return the result summary
	}

}
