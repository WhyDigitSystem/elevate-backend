package com.ebooks.elevate.controller;

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
import com.ebooks.elevate.dto.CoaDTO;
import com.ebooks.elevate.dto.ExcelUploadResultDTO;
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.dto.TbHeaderDTO;
import com.ebooks.elevate.service.TrailBalanceService;

@CrossOrigin
@RestController
@RequestMapping("/api/trailBalanceController")
public class TrailBalanceController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(TrailBalanceController.class);

	@Autowired
	TrailBalanceService trailBalanceService;

	@PostMapping("/excelUploadForTb")
	public ResponseEntity<ResponseDTO> excelUploadForTb(@RequestParam MultipartFile[] files,
			@RequestParam(required = false) String createdBy, @RequestParam(required = false) String clientCode,
			@RequestParam Long orgId, @RequestParam String clientName, @RequestParam String month,
			@RequestParam String finYear) {

		String methodName = "excelUploadForTb()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		ResponseDTO responseDTO;
		Map<String, Object> responseObjectsMap = new HashMap<>();

		try {
			// Call the service method and get the result
			ExcelUploadResultDTO uploadResult = trailBalanceService.excelUploadForTb(files, createdBy, clientCode,
					finYear, month, clientName, orgId);

			responseObjectsMap.put("status", uploadResult.getFailureReasons().isEmpty());
			// Populate success response
			responseObjectsMap.put("statusFlag", "Ok");
			responseObjectsMap.put("uploadResult", uploadResult);
			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			// Handle any exceptions and populate error response
			LOGGER.error(CommonConstant.EXCEPTION, methodName, e);

			responseObjectsMap.put("statusFlag", "Error");
			responseObjectsMap.put("status", false);
			responseObjectsMap.put("errorMessage", e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Excel Upload For Client COA Failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFillGridForTbFromExcelUpload")
	public ResponseEntity<ResponseDTO> getFillGridForTbFromExcelUpload(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String tbMonth, @RequestParam String client,
			@RequestParam String clientCode) {
		String methodName = "getFillGridForTbFromExcelUpload()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> excelUpload = null;
		try {
			// Call the method to get the ledger map (i.e., the required group data)
			excelUpload = trailBalanceService.getFillGridForTB(orgId, finYear, tbMonth, client, clientCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		// If no errors, create the successful response
		if (errorMsg == null) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TB from Upload information retrieved successfull");
			responseObjectsMap.put("excelUploadForTb", excelUpload);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			// If there was an error, create an error response
			responseDTO = createServiceResponseError(responseObjectsMap, "TB from Upload information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateTrailBalance")
	public ResponseEntity<ResponseDTO> createUpdateTrailBalance(@RequestBody TbHeaderDTO tbHeaderDTO) {
		String methodName = "createUpdateTrailBalance()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> tbHeaderVO = trailBalanceService.createUpdateTrailBalance(tbHeaderDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, tbHeaderVO.get("message"));
			responseObjectsMap.put("tbHeaderVO", tbHeaderVO.get("tbHeaderVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getTBDocId")
	public ResponseEntity<ResponseDTO> getTBDocId(@RequestParam Long orgId, @RequestParam String finYear,@RequestParam String clientCode) {

		String methodName = "getTBDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = trailBalanceService.getTBDocId(orgId, finYear,clientCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TB DocId information retrieved successfully");
			responseObjectsMap.put("tbDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve TB DocId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
