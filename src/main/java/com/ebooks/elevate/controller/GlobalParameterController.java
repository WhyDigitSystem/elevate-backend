package com.ebooks.elevate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebooks.elevate.common.CommonConstant;
import com.ebooks.elevate.common.UserConstants;
import com.ebooks.elevate.dto.GlobalParameterDTO;
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.entity.GlobalParameterVO;
import com.ebooks.elevate.service.GlobalParameterService;

@RestController
@RequestMapping("/api/GlobalParam")
public class GlobalParameterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(GlobalParameterController.class);

	@Autowired
	GlobalParameterService globalParameterService;


	
	@PutMapping("/updateCreateGlobalparam")
	public ResponseEntity<ResponseDTO> updateCreateGlobalparam(@Valid @RequestBody GlobalParameterDTO globalParameterDTO) {
		String methodName = "updateCreateGlobalparam()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
	        Map<String, Object> globalParameterVO = globalParameterService.updateCreateGlobalparam(globalParameterDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, globalParameterVO.get("message"));
	        responseObjectsMap.put("globalParameterVO", globalParameterVO.get("globalParameterVO")); // Corrected key
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

//	@GetMapping("/globalparamBranchByUserName")
//	public ResponseEntity<ResponseDTO> getGlobalParameterBranchByUserName(@RequestParam Long orgid,
//			@RequestParam String userName) {
//		String methodName = "getAllGlobalParameterByUserName()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		Set<Object[]> globalParameters = new HashSet<>();
//		try {
//			globalParameters = globalParameterService.getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(orgid,
//					userName);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			List<Map<String, String>> formattedParameters = formattParameter(globalParameters);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"Global Parameter Branch information get successfully");
//			responseObjectsMap.put("GlopalParameters", formattedParameters);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"Global Parameter Branch information receive failed", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
//
//	private List<Map<String, String>> formattParameter(Set<Object[]> globalParameters) {
//		List<Map<String, String>> formattedParameters = new ArrayList<>();
//		for (Object[] parameters : globalParameters) {
//			Map<String, String> param = new HashMap<>();
//			param.put("branch", parameters[0].toString());
//			param.put("branchcode", parameters[1].toString());
//			formattedParameters.add(param);
//		}
//		return formattedParameters;
//	}      

	@GetMapping("/getClientCodeForGlopalParam")
	public ResponseEntity<ResponseDTO> getClientCodeForGlopalParam(@RequestParam(required =false) String userName) {
		String methodName = "getClientCodeForGlopalParam()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>>clientDetails =new  ArrayList<Map<String,Object>>();
		try {
			clientDetails = globalParameterService.getClientCodeForGlopalParam(userName);
		} catch (Exception e) {  
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ClientCode ForGlopalParam information get successfully");
			responseObjectsMap.put("clientDetails", clientDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ClientCode ForGlopalParam information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getGlobalparamByUserId")
	public ResponseEntity<ResponseDTO> getGlobalparamByUserId(@RequestParam(required =false) Long userId) {
		String methodName = "getGlobalparamByUserId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional <GlobalParameterVO> globalParameterVO =null;
		try {
			globalParameterVO = globalParameterService.getGlobalparamByUserId(userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GlopalParam information get successfully");
			responseObjectsMap.put("globalParameterVO", globalParameterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "GlopalParam information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
}