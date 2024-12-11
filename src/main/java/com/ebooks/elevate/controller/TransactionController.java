package com.ebooks.elevate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.common.CommonConstant;
import com.ebooks.elevate.common.UserConstants;
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.entity.BrsExcelUploadVO;
import com.ebooks.elevate.service.TransactionService;

@CrossOrigin
@RestController
@RequestMapping("/api/transaction")
public class TransactionController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionService transactionService;
	// IrnCredit

	

	@GetMapping("/getBranchForBrsOpening")
	public ResponseEntity<ResponseDTO> getBranchForBrsOpening(@RequestParam Long orgId) {

		String methodName = "getBranchForBrsOpening()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> branch = new ArrayList<>();

		try {
			branch = transactionService.getBranchForBrsOpening(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch retrieved successfully");
			responseObjectsMap.put("branch", branch);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Branch information",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/excelUploadForBrs")
	public ResponseEntity<ResponseDTO> ExcelUploadForBrs(@RequestParam MultipartFile[] files, @RequestParam Long orgId,
			@RequestParam String createdBy, String branch, String branchCode) {
		String methodName = "ExcelUploadForBrs()";
		int totalRows = 0;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		int successfulUploads = 0;
		ResponseDTO responseDTO = null;
		try {
			// Call service method to process Excel upload
			transactionService.ExcelUploadForBrs(files, orgId, createdBy, branch, branchCode);

			// Retrieve the counts after processing
			totalRows = transactionService.getTotalRows(); // Get total rows processed
			successfulUploads = transactionService.getSuccessfulUploads(); // Get successful uploads count
			// Construct success response
			responseObjectsMap.put("statusFlag", "Ok");
			responseObjectsMap.put("status", true);
			responseObjectsMap.put("totalRows", totalRows);
			responseObjectsMap.put("successfulUploads", successfulUploads);
			Map<String, Object> paramObjectsMap = new HashMap<>();
			paramObjectsMap.put("message", "Excel Upload For brs successful");
			responseObjectsMap.put("paramObjectsMap", paramObjectsMap);
			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			String errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.EXCEPTION, methodName, e);
			responseObjectsMap.put("statusFlag", "Error");
			responseObjectsMap.put("status", false);
			responseObjectsMap.put("errorMessage", errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Excel Upload For Brs Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllBrsExcelByOrgId")
	public ResponseEntity<ResponseDTO> getAllBrsExcelByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllBrsExcelByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BrsExcelUploadVO> brsExcelVO = new ArrayList<>();
		try {
			brsExcelVO = transactionService.getAllBrsExcelByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Brs Excel information get successfully ByOrgId");
			responseObjectsMap.put("brsExcelVO", brsExcelVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Brs Excel information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	

}