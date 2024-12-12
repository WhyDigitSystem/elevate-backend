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

			if (!cCoaVO.getAccountGroupName().equalsIgnoreCase(cCoaDTO.getAccountGroupName())) {

				if (cCoaRepo.existsByAccountGroupNameAndClientName(cCoaDTO.getAccountGroupName(),
						cCoaDTO.getClientName())) {

					String errorMessage = String.format("This AccountGroupName: %s Already Exists",
							cCoaDTO.getAccountGroupName());
					throw new ApplicationException(errorMessage);
				}

				cCoaVO.setAccountGroupName(cCoaDTO.getAccountGroupName());
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
		}

		else {
			// Fetch the parent record
			CCoaVO cCoaVO2 = cCoaRepo.findByAccountGroupName(cCoaDTO.getGroupName());

			if (cCoaVO2 == null) {
				// Handle the case where the parent record is not found
				throw new ApplicationException("Parent record not found for groupName: " + cCoaDTO.getGroupName());
			}

			// Set parentId and parentCode
			cCoaVO.setParentId(cCoaVO2.getId().toString());
			cCoaVO.setParentCode(cCoaVO2.getAccountCode());
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

	@Transactional
	@Override
	public void excelUploadForCCoa(MultipartFile[] files, String createdBy, String clientCode)
			throws EncryptedDocumentException, java.io.IOException, ApplicationException {
		List<CCoaVO> mainGroupList = new ArrayList<>(); // List to store main group records
		List<CCoaVO> subGroupList = new ArrayList<>(); // List to store subgroup records
		List<CCoaVO> accountList = new ArrayList<>(); // List to store account records

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
						CCoaVO cCoaVO = new CCoaVO();
						cCoaVO.setType(type);
						cCoaVO.setGroupName(groupName);
						cCoaVO.setAccountGroupName(accountName);
						cCoaVO.setNatureOfAccount(natureOfAccount);
						cCoaVO.setAccountCode(accountCode);
						cCoaVO.setCreatedBy(createdBy);
						cCoaVO.setInterBranchAc(false);
						cCoaVO.setControllAc(false);
						cCoaVO.setCurrency("INR");
						cCoaVO.setActive(active);
						cCoaVO.setUpdatedBy(createdBy);
						cCoaVO.setClientId(clientCode);

						// Logic for adding to specific lists based on conditions
						if ("Group".equalsIgnoreCase(type)) {
							if (groupName == null || groupName.isEmpty()) {
								cCoaVO.setParentCode("0");
								cCoaVO.setParentId(null);
								// Main group (groupName is null)
								mainGroupList.add(cCoaVO);
								cCoaRepo.saveAll(mainGroupList);
							} else {
								CCoaVO vo = cCoaRepo.findByAccountGroupName(groupName);
								cCoaVO.setParentCode(vo.getAccountCode());
								cCoaVO.setParentId(vo.getId().toString());
								// Subgroup (groupName is not null)
								subGroupList.add(cCoaVO);
								cCoaRepo.saveAll(subGroupList);
							}
						} else if ("Account".equalsIgnoreCase(type) && groupName != null && !groupName.isEmpty()) {
							// Account (groupName is not null)
							CoaVO vo = coaRepo.findByAccountGroupName(groupName);
							cCoaVO.setParentCode(vo.getAccountCode());
							cCoaVO.setParentId(vo.getId().toString());
							accountList.add(cCoaVO);
							cCoaRepo.saveAll(accountList);
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

	private boolean isHeaderValid(Row headerRow) {
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
	public Map<String, Object> createUpdateLedgerMapping(LedgerMappingDTO ledgerMappingDTO)
			throws ApplicationException {

		LedgerMappingVO ledgerMappingVO = null;
		String message;

		if (ObjectUtils.isEmpty(ledgerMappingDTO.getId())) {

			ledgerMappingVO = new LedgerMappingVO();
			ledgerMappingVO.setCreatedBy(ledgerMappingDTO.getCreatedBy());
			ledgerMappingVO.setUpdatedBy(ledgerMappingDTO.getCreatedBy());

			message = "LedgerMapping Creation Succesfully";

		}

		else {

			ledgerMappingVO = ledgerMappingRepo.findById(ledgerMappingDTO.getId()).orElseThrow(
					() -> new ApplicationException("LedgerMapping not found with id: " + ledgerMappingDTO.getId()));
			ledgerMappingVO.setUpdatedBy(ledgerMappingDTO.getCreatedBy());

			message = "LedgerMapping Updation Succesfully";
		}

		ledgerMappingVO = getLedgerMappingVOFromLedgerMappingDTO(ledgerMappingVO, ledgerMappingDTO);

		ledgerMappingRepo.save(ledgerMappingVO);

		Map<String, Object> reponse = new HashMap<String, Object>();
		reponse.put("message", message);
		reponse.put("ledgerMappingVO", ledgerMappingVO);
		return reponse;

	}

	private LedgerMappingVO getLedgerMappingVOFromLedgerMappingDTO(LedgerMappingVO ledgerMappingVO,
			LedgerMappingDTO ledgerMappingDTO) {

		ledgerMappingVO.setClientCoa(ledgerMappingDTO.getClientCoa());
		ledgerMappingVO.setCoa(ledgerMappingDTO.getCoa());
		ledgerMappingVO.setCreatedBy(ledgerMappingDTO.getCreatedBy());
		ledgerMappingVO.setActive(ledgerMappingDTO.isActive());
		ledgerMappingVO.setClientCode(ledgerMappingDTO.getClientCode());
		return ledgerMappingVO;
	}

	@Override
	public List<Map<String, Object>> getFullGridForLedgerMapping() {
		Set<Object[]> getFullGrid = ledgerMappingRepo.getFullGridForLedgerMapping();
		return getFullGridForLedger(getFullGrid);
	}

	private List<Map<String, Object>> getFullGridForLedger(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("accountGroupName", ch[0] != null ? ch[0].toString() : "");
			map.put("accountCode", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public Map<String, Object> createUpdateLedgerMapping(List<LedgerMappingDTO> ledgerMappingDTOList)
	        throws ApplicationException {

	    List<LedgerMappingVO> ledgerMappingVOList = new ArrayList<>();
	    String message = "LedgerMapping processed successfully";

	    // Iterate over the list of LedgerMappingDTO objects
	    for (LedgerMappingDTO ledgerMappingDTO : ledgerMappingDTOList) {

	        LedgerMappingVO ledgerMappingVO = null;

	        if (ObjectUtils.isEmpty(ledgerMappingDTO.getId())) {  
	        
	        if(!ledgerMappingVO.getCoa().equalsIgnoreCase(ledgerMappingDTO.getCoa())) { 
	        	
	        	// Delete existing records based on clientCode (be cautious of deleting all records before saving)
		        ledgerMappingRepo.deleteByClientCode(ledgerMappingDTO.getClientCode());

	        }
		        // Create a new LedgerMappingVO or update existing one
		       
		            // New record
		            ledgerMappingVO = new LedgerMappingVO();
		            ledgerMappingVO.setCreatedBy(ledgerMappingDTO.getCreatedBy());
		            ledgerMappingVO.setUpdatedBy(ledgerMappingDTO.getCreatedBy());

		            // Set the message for creation
		            message = "LedgerMapping Creation Successful";
		       
		        }
	       
	        else {
	            // Existing record, update it
	            ledgerMappingVO = ledgerMappingRepo.findById(ledgerMappingDTO.getId()).orElseThrow(
	                    () -> new ApplicationException("LedgerMapping not found with id: " + ledgerMappingDTO.getId()));
	            ledgerMappingVO.setUpdatedBy(ledgerMappingDTO.getCreatedBy());

	            // Set the message for update
	            message = "LedgerMapping Updation Successful";
	        }

	        // Convert DTO to VO
	        ledgerMappingVO = getLedgerMappingVOFromLedgerMappingDTO(ledgerMappingVO, ledgerMappingDTO);

	        // Add the LedgerMappingVO to the list for batch save
	        ledgerMappingVOList.add(ledgerMappingVO);
	    }

	    // Batch save all records at once (for performance optimization)
	    ledgerMappingRepo.saveAll(ledgerMappingVOList);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("ledgerMappingVOList", ledgerMappingVOList);  // Return the list of saved records
	    return response;
	}

	private LedgerMappingVO getLedgerMappingVOFromLedgerMappingDTO(LedgerMappingVO ledgerMappingVO,
	        LedgerMappingDTO ledgerMappingDTO) {

	    ledgerMappingVO.setClientCoa(ledgerMappingDTO.getClientCoa());
	    ledgerMappingVO.setCoa(ledgerMappingDTO.getCoa());
	    ledgerMappingVO.setCreatedBy(ledgerMappingDTO.getCreatedBy());
	    ledgerMappingVO.setActive(ledgerMappingDTO.isActive());
	    ledgerMappingVO.setClientCode(ledgerMappingDTO.getClientCode());

	    return ledgerMappingVO;
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
	        map.put("coa", ch[2] != null ? ch[2].toString() : ""); // Map coa
	        map.put("coaCode", ch[3] != null ? ch[3].toString() : ""); // Map coacode
	        List1.add(map);
	    }
	    return List1;
	}

}
