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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebooks.elevate.common.CommonConstant;
import com.ebooks.elevate.common.UserConstants;
import com.ebooks.elevate.dto.BudgetDTO;
import com.ebooks.elevate.dto.GroupMappingDTO;
import com.ebooks.elevate.dto.PreviousYearDTO;
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.service.BudgetService;

@RestController
@RequestMapping("/api/Budget")
public class BudgetController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(BudgetController.class);
	
	@Autowired
	BudgetService budgetService;
	
	
	@GetMapping("/getSubGroup")
	public ResponseEntity<ResponseDTO> getSubGroup(@RequestParam Long orgId,@RequestParam String mainGroup) {
		String methodName = "getSubGroup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String,Object>> subGroup = new ArrayList<>();
		try {
			subGroup = budgetService.getSubGroupDetails(orgId, mainGroup);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sub Group information get successfully");
			responseObjectsMap.put("subGroup", subGroup);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Sub Group information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getGroupLedgers")
	public ResponseEntity<ResponseDTO> getGroupLedgers(@RequestParam Long orgId,@RequestParam String year,@RequestParam String clientCode,@RequestParam String mainGroup,@RequestParam String subGroupCode) {
		String methodName = "getGroupLedgers()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String,Object>> groupLedgers = new ArrayList<>();
		try {
			groupLedgers = budgetService.getGroupLedgersDetails(orgId, year, clientCode, mainGroup, subGroupCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group Ledgers information get successfully");
			responseObjectsMap.put("groupLedgers", groupLedgers);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Group Ledgers information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/createUpdateBudget")
	public ResponseEntity<ResponseDTO> createUpdateBudget(@RequestBody List<BudgetDTO> budgetDTO) {
		String methodName = "createUpdateBudget()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> BudgetDetails = budgetService.createUpdateBudget(budgetDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, BudgetDetails.get("message"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getPreviousYearGroupLedgers")
	public ResponseEntity<ResponseDTO> getPreviousYearGroupLedgers(@RequestParam Long orgId,@RequestParam String year,@RequestParam String clientCode,@RequestParam String mainGroup,@RequestParam String subGroupCode) {
		String methodName = "getPreviousYearGroupLedgers()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String,Object>> groupLedgers = new ArrayList<>();
		try {
			groupLedgers = budgetService.getPreviousYearGroupLedgersDetails(orgId, year, clientCode, mainGroup, subGroupCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group Ledgers information get successfully");
			responseObjectsMap.put("groupLedgers", groupLedgers);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Group Ledgers information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getActualGroupLedgers")
	public ResponseEntity<ResponseDTO> getActualGroupLedgers(@RequestParam Long orgId,@RequestParam String year,@RequestParam String clientCode,@RequestParam String mainGroup,@RequestParam String subGroupCode) {
		String methodName = "getActualGroupLedgers()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String,Object>> groupLedgers = new ArrayList<>();
		try {
			groupLedgers = budgetService.getActualGroupLedgersDetails(orgId, year, clientCode, mainGroup, subGroupCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group Ledgers information get successfully");
			responseObjectsMap.put("groupLedgers", groupLedgers);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Group Ledgers information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/createUpdatePreviousYear")
	public ResponseEntity<ResponseDTO> createUpdatePreviousYear(@RequestBody List<PreviousYearDTO> previousYearDTO) {
		String methodName = "createUpdatePreviousYear()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> BudgetDetails = budgetService.createUpdatePreviousYear(previousYearDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, BudgetDetails.get("message"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
}
