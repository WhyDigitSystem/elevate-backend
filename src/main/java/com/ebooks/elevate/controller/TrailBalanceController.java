package com.ebooks.elevate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.dto.TbHeaderDTO;
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
	
	@GetMapping("/getFillGridForTbExcelUpload")
    public ResponseEntity<ResponseDTO> getFillGridForTbExcelUpload(@RequestParam String finYear,@RequestParam String clientCode,@RequestParam String month) {
        String methodName = "getFillGridForTbExcelUpload()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<Map<String, Object>> excelUpload = null;
        try {
            // Call the method to get the ledger map (i.e., the required group data)
        	excelUpload = trailBalanceService.getFillGridForTbExcelUpload(finYear,clientCode,month);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }

        // If no errors, create the successful response
        if (errorMsg == null) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "excelUpload Tb information retrieved successfull");
            responseObjectsMap.put("excelUploadForTb", excelUpload);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            // If there was an error, create an error response
            responseDTO = createServiceResponseError(responseObjectsMap, "excelUpload Tb information retrieval failed", errorMsg);
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
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,tbHeaderVO.get("message") );
			responseObjectsMap.put("tbHeaderVO", tbHeaderVO.get("tbHeaderVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
}
