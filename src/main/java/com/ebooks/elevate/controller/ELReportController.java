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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.common.CommonConstant;
import com.ebooks.elevate.common.UserConstants;
import com.ebooks.elevate.dto.ElMfrDTO;
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.entity.ElMfrVO;
import com.ebooks.elevate.service.ELReportService;

@CrossOrigin
@RestController
@RequestMapping("/api/eLReportController")
public class ELReportController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(ELReportController.class);

	
	@Autowired
	ELReportService elReportService;
	
	
	@PutMapping("/createUpdateElMfr")
	public ResponseEntity<ResponseDTO> createUpdateElMfr(@RequestBody ElMfrDTO elMfrDTO) {
		String methodName = "createUpdateElMfr()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> elMfrVO = elReportService.createUpdateElMfr(elMfrDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, elMfrVO.get("message"));
			responseObjectsMap.put("elMfrVO", elMfrVO.get("elMfrVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllElMfr")
	public ResponseEntity<ResponseDTO> getAllElMfr(@RequestParam(required =false) Long orgId) {
		String methodName = "getAllCao()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ElMfrVO> elMfrVOs = new ArrayList<>();
		try {
			elMfrVOs = elReportService.getAllElMfr(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ELMFR information get successfully");
			responseObjectsMap.put("elMfrVO", elMfrVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ELMFR information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/excelUploadForElMfr")
	public ResponseEntity<ResponseDTO> excelUploadForElMfr(@RequestParam MultipartFile[] files,@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) Long orgId) {
		String methodName = "excelUploadForElMfr()";
		int totalRows = 0;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		int successfulUploads = 0;
		ResponseDTO responseDTO = null;
		try {
			// Call service method to process Excel upload
			elReportService.excelUploadForElMfr(files,createdBy,orgId);

			// Retrieve the counts after processing
			totalRows = elReportService.getTotalRows(); // Get total rows processed
			successfulUploads = elReportService.getSuccessfulUploads(); // Get successful uploads count
			responseObjectsMap.put("statusFlag", "Ok");
	        responseObjectsMap.put("status", true);
	        responseObjectsMap.put("totalRows", totalRows);
	        responseObjectsMap.put("successfulUploads", successfulUploads);
	        responseObjectsMap.put("message", "Excel Upload For ElMfr successful"); // Directly include the message here
	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        String errorMsg = e.getMessage();
	        LOGGER.error(CommonConstant.EXCEPTION, methodName, e);
	        responseObjectsMap.put("statusFlag", "Error");
	        responseObjectsMap.put("status", false);
	        responseObjectsMap.put("errorMessage", errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, "Excel Upload For ElMfr Failed", errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getMisMatchClientTb")
	public ResponseEntity<ResponseDTO> getMisMatchClientTb(@RequestParam Long orgId,@RequestParam String clientCode,@RequestParam String accountCode) {
		String methodName = "getMisMatchClientTb()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> coaVO = new ArrayList<Map<String, Object>>();
		try {
			coaVO = elReportService.getMisMatchClientTb(orgId,clientCode,accountCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MisMatch ClientTb information get successfully");
			responseObjectsMap.put("coaVO", coaVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "MisMatch ClientTb information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
}
