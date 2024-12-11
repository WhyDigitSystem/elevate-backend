package com.ebooks.elevate.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.ebooks.elevate.entity.CCoaVO;
import com.ebooks.elevate.entity.CoaVO;
import com.ebooks.elevate.entity.FirstDataVO;
import com.ebooks.elevate.exception.ApplicationException;
import com.ebooks.elevate.repo.CCoaRepo;
import com.ebooks.elevate.repo.CoaRepo;

import io.jsonwebtoken.io.IOException;

@Service
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	CoaRepo coaRepo;

	@Autowired
	CCoaRepo cCoaRepo;

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

				coaVO.setGroupName(coaDTO.getGroupName());
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

	private CoaVO getcoaVOFromcoaDTO(CoaVO coaVO, CoaDTO coaDTO) {

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
			CoaVO coaVO2 = coaRepo.findByAccountGroupName(coaDTO.getGroupName());
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

			if (cCoaRepo.existsByAccountCode(cCoaDTO.getAccountCode())) {

				String errorMessage = String.format("This AccountCode: %s Already Exists", cCoaDTO.getAccountCode());
				throw new ApplicationException(errorMessage);
			}

			if (cCoaRepo.existsByAccountGroupNameAndClientName(cCoaDTO.getAccountGroupName(),
					cCoaDTO.getClientName())) {

				String errorMessage = String.format("This AccountGroupName: %s Already Exists",
						cCoaDTO.getAccountGroupName());
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

				if (cCoaRepo.existsByAccountCode(cCoaDTO.getAccountCode())) {

					String errorMessage = String.format("This AccountCode: %s Already Exists",
							cCoaDTO.getAccountCode());
					throw new ApplicationException(errorMessage);
				}

				cCoaVO.setAccountCode(cCoaDTO.getAccountCode());
			}

			if (!cCoaVO.getAccountGroupName().equalsIgnoreCase(cCoaDTO.getAccountGroupName())
					&& !cCoaVO.getClientName().equalsIgnoreCase(cCoaDTO.getClientName())) {
				// Your code here
			}
			{

				if (cCoaRepo.existsByAccountGroupNameAndClientName(cCoaDTO.getAccountGroupName(),
						cCoaDTO.getClientName())) {

					String errorMessage = String.format("This AccountGroupName: %s Already Exists",
							cCoaDTO.getAccountGroupName());
					throw new ApplicationException(errorMessage);
				}

				cCoaVO.setGroupName(cCoaDTO.getGroupName());
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

	private CCoaVO getCCoaVOFromCCoaDTO(CCoaVO cCoaVO, CCoaDTO cCoaDTO) {
		// Basic field mapping
		cCoaVO.setType(cCoaDTO.getType());
		cCoaVO.setGroupName(cCoaDTO.getGroupName());
		cCoaVO.setAccountGroupName(cCoaDTO.getAccountGroupName());
		cCoaVO.setNatureOfAccount(cCoaDTO.getNatureOfAccount());
		cCoaVO.setAccountCode(cCoaDTO.getAccountCode());
		cCoaVO.setCreatedBy(cCoaDTO.getCreatedBy());
		cCoaVO.setInterBranchAc(cCoaDTO.isInterBranchAc());
		cCoaVO.setControllAc(cCoaDTO.isControllAc());
		cCoaVO.setCurrency(cCoaDTO.getCurrency());
		cCoaVO.setActive(cCoaDTO.isActive());
		cCoaVO.setClientId(cCoaDTO.getClientId());
		cCoaVO.setClientName(cCoaDTO.getClientName());

		// Handle type "group" with no groupName
		if ("group".equalsIgnoreCase(cCoaDTO.getType()) && cCoaDTO.getGroupName() == null) {
			cCoaVO.setParentId(null);
			cCoaVO.setParentCode("0");
		} else {
			CCoaVO cCoaVO3 = cCoaRepo.findByAccountGroupName(cCoaDTO.getGroupName());
			cCoaVO.setParentId(cCoaVO3.getId().toString());
			cCoaVO.setParentCode(cCoaVO3.getAccountCode());
		}

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
	    public void excelUploadForCoa(MultipartFile[] files, String createdBy) throws ApplicationException, EncryptedDocumentException, java.io.IOException {
	        List<CoaVO> dataToSave = new ArrayList<>();
	        List<CoaVO> groupsToSave = new ArrayList<>(); // List to save group records first
	        List<CoaVO> accountsToSave = new ArrayList<>(); // List to save account records

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
	                    
	                    try {
	                        // Retrieve cell values based on the provided order
	                        String type = getStringCellValue(row.getCell(0));
	                        String groupName = getStringCellValue(row.getCell(1));
	                        String accountCode = getStringCellValue(row.getCell(2));
	                        String accountName = getStringCellValue(row.getCell(3));
	                        String natureOfAccount = getStringCellValue(row.getCell(4));
	                        Boolean active = getBooleanCellValue(row.getCell(5));

	                        // Create CoaVO and add to list for batch saving
	                        CoaVO coaVO = new CoaVO();

	                        // Condition 1: Check if AccountCode already exists in the database
	                        if ("account".equalsIgnoreCase(type) && coaRepo.existsByAccountCode(accountCode)) {
	                            String errorMessage = String.format("This AccountCode: %s Already Exists", accountCode);
	                            throw new ApplicationException(errorMessage);
	                        }

	                        // Set common fields for both groups and accounts
	                        coaVO.setType(type);
	                        coaVO.setGroupName(groupName);
	                        coaVO.setAccountGroupName(accountName);
	                        coaVO.setAccountCode(accountCode);
	                        coaVO.setNatureOfAccount(natureOfAccount);
	                        coaVO.setActive(active);
	                        coaVO.setCreatedBy(createdBy);
	                        coaVO.setUpdatedBy(createdBy);

	                        // Condition 2: Handle ParentId and ParentCode based on type and groupName
	                        if ("group".equalsIgnoreCase(type) && (groupName == null || groupName.isEmpty())) {
	                            // For "group" type with empty groupName, set parentId to null and parentCode to "0"
	                            coaVO.setParentId(null);
	                            coaVO.setParentCode("0");

	                            // Add to groupsToSave
	                            groupsToSave.add(coaVO);
	                        } else if ("account".equalsIgnoreCase(type)) {
	                            // For "account" type, check for parent groupName
	                            if (groupName != null && !groupName.isEmpty()) {
	                                CoaVO parentCoa = coaRepo.findByAccountGroupName(groupName);
	                                if (parentCoa == null) {
	                                    String errorMessage = "Parent record not found for groupName: " + groupName;
	                                    errorMessages.add(errorMessage);
	                                    continue; // Skip this row if parent record is not found
	                                }
	                                coaVO.setParentId(parentCoa.getId().toString());
	                                coaVO.setParentCode(parentCoa.getAccountCode());
	                            } else {
	                                // If no groupName for account, set parentId to null and parentCode to "0"
	                                coaVO.setParentId(null);
	                                coaVO.setParentCode("0");
	                            }

	                            // Add to accountsToSave
	                            accountsToSave.add(coaVO);
	                        }

	                        successfulUploads++; // Increment successfulUploads

	                    } catch (Exception e) {
	                        errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
	                    }
	                }

	                // First save all group records
	                if (!groupsToSave.isEmpty()) {
	                    coaRepo.saveAll(groupsToSave);
	                }

	                // Then save all account records (after group records are saved)
	                if (!accountsToSave.isEmpty()) {
	                    coaRepo.saveAll(accountsToSave);
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

	    private Boolean getBooleanCellValue(Cell cell) {
	        if (cell == null || cell.getCellType() == CellType.BLANK) {
	            return false; // Default to false if the cell is blank
	        }

	        switch (cell.getCellType()) {
	            case NUMERIC:
	                return cell.getNumericCellValue() == 1; // Treat 1 as true, 0 as false
	            case STRING:
	                String value = cell.getStringCellValue().trim().toLowerCase();
	                return "1".equals(value) || "true".equals(value) || "yes".equals(value);
	            default:
	                throw new IllegalArgumentException("Invalid value for boolean field in Active column: " + cell.toString());
	        }
	    }

	    private boolean isHeaderValid(Row headerRow) {
	        if (headerRow == null) {
	            return false;
	        }

	        // Adjust based on the actual header names in your Excel
	        return "Type".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0))) &&
	               "Group Name".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1))) &&
	               "Account Code".equalsIgnoreCase(getStringCellValue(headerRow.getCell(2))) &&
	               "AccountName".equalsIgnoreCase(getStringCellValue(headerRow.getCell(3))) &&
	               "Nature of Account".equalsIgnoreCase(getStringCellValue(headerRow.getCell(4))) &&
	               "Active".equalsIgnoreCase(getStringCellValue(headerRow.getCell(5)));
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


}
