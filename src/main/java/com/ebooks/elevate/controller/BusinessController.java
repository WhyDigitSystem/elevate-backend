package com.ebooks.elevate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.ebooks.elevate.dto.CCoaDTO;
import com.ebooks.elevate.dto.CoaDTO;
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.entity.CCoaVO;
import com.ebooks.elevate.entity.CoaVO;
import com.ebooks.elevate.service.BusinessService;

@CrossOrigin
@RestController
@RequestMapping("/api/businesscontroller")
public class BusinessController extends BaseController{
	
	@Autowired
	BusinessService businessService;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(BusinessController.class);

	//COA
	
	@PutMapping("/createUpdateCoa")
	public ResponseEntity<ResponseDTO> createUpdateCoa(@RequestBody CoaDTO coaDTO) {
		String methodName = "createUpdateCoa()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> coaVO = businessService.createUpdateCoa(coaDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,coaVO.get("message") );
			responseObjectsMap.put("coaVO", coaVO.get("coaVO"));
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
	
	@GetMapping("/getAllCao")
	public ResponseEntity<ResponseDTO> getAllCao() {
		String methodName = "getAllCao()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CoaVO> coaVO = new ArrayList<>();
		try {
			coaVO = businessService.getAllCao();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "coa information get successfully");
			responseObjectsMap.put("coaVO", coaVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "coa information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getCaoById")
	public ResponseEntity<ResponseDTO> getCaoById(@RequestParam Long id) {
		String methodName = "getCaoById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<CoaVO> coaVO = null;
		try {
			coaVO = businessService.getCaoById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "coa information get successfully By Id");
			responseObjectsMap.put("coaVO", coaVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "coa information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getGroupName")
	public ResponseEntity<ResponseDTO> getGroupName() {
		String methodName = "getGroupName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> coaVO=new ArrayList<Map<String,Object>>();
		try {
			coaVO = businessService.getGroupName();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group information get successfully");
			responseObjectsMap.put("coaVO", coaVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Group information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/excelUploadForCoa")
	public ResponseEntity<ResponseDTO> excelUploadForCoa(@RequestParam MultipartFile[] files,@RequestParam(required = false) String createdBy) {
		String methodName = "excelUploadForCoa()";
		int totalRows = 0;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		int successfulUploads = 0;
		ResponseDTO responseDTO = null;
		try {
			// Call service method to process Excel upload
			businessService.excelUploadForCoa(files,createdBy);

			// Retrieve the counts after processing
			totalRows = businessService.getTotalRows(); // Get total rows processed
			successfulUploads = businessService.getSuccessfulUploads(); // Get successful uploads count
			responseObjectsMap.put("statusFlag", "Ok");
	        responseObjectsMap.put("status", true);
	        responseObjectsMap.put("totalRows", totalRows);
	        responseObjectsMap.put("successfulUploads", successfulUploads);
	        responseObjectsMap.put("message", "Excel Upload For Coa successful"); // Directly include the message here
	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        String errorMsg = e.getMessage();
	        LOGGER.error(CommonConstant.EXCEPTION, methodName, e);
	        responseObjectsMap.put("statusFlag", "Error");
	        responseObjectsMap.put("status", false);
	        responseObjectsMap.put("errorMessage", errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, "Excel Upload For Coa Failed", errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}
	
	//CCoaVO
	
	@PutMapping("/createUpdateCCoa")
	public ResponseEntity<ResponseDTO> createUpdateCCoa(@RequestBody CCoaDTO cCoaDTO) {
		String methodName = "createUpdateCCoa()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> cCoaVO = businessService.createUpdateCCoa(cCoaDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,cCoaVO.get("message") );
			responseObjectsMap.put("cCoaVO", cCoaVO.get("cCoaVO"));
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
	
	@GetMapping("/getAllCCao")
	public ResponseEntity<ResponseDTO> getAllCCao() {
		String methodName = "getAllCCao()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CCoaVO> cCoaVO = new ArrayList<>();
		try {
			cCoaVO = businessService.getAllCCao();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CCoa information get successfully");
			responseObjectsMap.put("cCoaVO", cCoaVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "CCoa information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getCCaoById")
	public ResponseEntity<ResponseDTO> getCCaoById(@RequestParam Long id) {
		String methodName = "getCCaoById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<CCoaVO> cCoaVO = null;
		try {
			cCoaVO = businessService.getCCaoById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CCoa information get successfully By Id");
			responseObjectsMap.put("cCoaVO", cCoaVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "CCoa information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGroupNameForCCoa")
	public ResponseEntity<ResponseDTO> getGroupNameForCCoa() {
		String methodName = "getGroupNameForCCoa()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> cCoaVO=new ArrayList<Map<String,Object>>();
		try {
			cCoaVO = businessService.getGroupNameForCCoa();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group information get successfully For CCoa");
			responseObjectsMap.put("cCoaVO", cCoaVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Group information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
}
