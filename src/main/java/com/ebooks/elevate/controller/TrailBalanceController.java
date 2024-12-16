package com.ebooks.elevate.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.common.CommonConstant;
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.service.TrailBalanceService;

@CrossOrigin
@RestController
@RequestMapping("/api/trailBalanceController")
public class TrailBalanceController extends BaseController{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(TrailBalanceController.class);

	@Autowired
	TrailBalanceService trailBalanceService;
	
	@PostMapping("/excelUploadForTb")
	public ResponseEntity<ResponseDTO> excelUploadForTb(@RequestParam MultipartFile[] files,@RequestParam(required = false) String createdBy,
			@RequestParam(required=false) String clientCode,@RequestParam(required=false) String finYear,@RequestParam(required=false) String month) {
		String methodName = "excelUploadForTb()";
		int totalRows = 0;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		int successfulUploads = 0;
		ResponseDTO responseDTO = null;
		try {
			// Call service method to process Excel upload
			trailBalanceService.excelUploadForTb(files,createdBy,clientCode,finYear,month);

			// Retrieve the counts after processing
			totalRows = trailBalanceService.getTotalRows(); // Get total rows processed
			successfulUploads = trailBalanceService.getSuccessfulUploads(); // Get successful uploads count
			responseObjectsMap.put("statusFlag", "Ok");
	        responseObjectsMap.put("status", true);
	        responseObjectsMap.put("totalRows", totalRows);
	        responseObjectsMap.put("successfulUploads", successfulUploads);
	        responseObjectsMap.put("message", "Excel Upload For  Tb successful"); // Directly include the message here
	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        String errorMsg = e.getMessage();
	        LOGGER.error(CommonConstant.EXCEPTION, methodName, e);
	        responseObjectsMap.put("statusFlag", "Error");
	        responseObjectsMap.put("status", false);
	        responseObjectsMap.put("errorMessage", errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, "Excel Upload For Tb Failed", errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}
	
}
