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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebooks.elevate.common.CommonConstant;
import com.ebooks.elevate.common.UserConstants;
import com.ebooks.elevate.dto.BranchDTO;
import com.ebooks.elevate.dto.CCoaDTO;
import com.ebooks.elevate.dto.EmployeeDTO;
import com.ebooks.elevate.dto.GroupMappingDTO;
import com.ebooks.elevate.dto.ListOfValuesDTO;
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.entity.BranchVO;
import com.ebooks.elevate.entity.CoaVO;
import com.ebooks.elevate.entity.EmployeeVO;
import com.ebooks.elevate.entity.GroupMappingVO;
import com.ebooks.elevate.entity.ListOfValuesVO;
import com.ebooks.elevate.entity.SacCodeVO;
import com.ebooks.elevate.entity.SetTaxRateVO;
import com.ebooks.elevate.entity.SubLedgerAccountVO;
import com.ebooks.elevate.service.MasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/master")
public class MasterController extends BaseController {

	@Autowired
	MasterService masterService;

	public static final Logger LOGGER = LoggerFactory.getLogger(MasterController.class);

	// Branch
	@GetMapping("/branch")
	public ResponseEntity<ResponseDTO> getAllBranch(@RequestParam Long orgid) {
		String methodName = "getAllBranch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BranchVO> branchVO = new ArrayList<>();
		try {
			branchVO = masterService.getAllBranch(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch information get successfully");
			responseObjectsMap.put("branchVO", branchVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Branch information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/branch/{branchid}")
	public ResponseEntity<ResponseDTO> getBranchById(@PathVariable Long branchid) {
		String methodName = "getBranchById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		BranchVO branchVO = null;
		try {
			branchVO = masterService.getBranchById(branchid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch found by ID");
			responseObjectsMap.put("Branch", branchVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Branch not found for ID: " + branchid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Branch not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateBranch")
	public ResponseEntity<ResponseDTO> createUpdateBranch(@RequestBody BranchDTO branchDTO) {
		String methodName = "createBranch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdBranchVO = masterService.createUpdateBranch(branchDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdBranchVO.get("message"));
			responseObjectsMap.put("branchVO", createdBranchVO.get("branchVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Employee

	@GetMapping("/getAllEmployeeByOrgId")
	public ResponseEntity<ResponseDTO> getAllEmployeeByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllEmployeeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmployeeVO> employeeVO = new ArrayList<>();
		try {
			employeeVO = masterService.getAllEmployeeByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee information get successfully");
			responseObjectsMap.put("employeeVO", employeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Employee information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllEmployee")
	public ResponseEntity<ResponseDTO> getAllEmployee() {
		String methodName = "getAllEmployeeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmployeeVO> employeeVO = new ArrayList<>();
		try {
			employeeVO = masterService.getAllEmployee();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee information get successfully");
			responseObjectsMap.put("employeeVO", employeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Employee information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/employee/{employeeid}")
	public ResponseEntity<ResponseDTO> getEmployeeById(@PathVariable Long employeeid) {
		String methodName = "getEmployeeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		EmployeeVO employeeVO = null;
		try {
			employeeVO = masterService.getEmployeeById(employeeid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee found by ID");
			responseObjectsMap.put("Employee", employeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Employee not found for ID: " + employeeid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Employee not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateEmployee")
	public ResponseEntity<ResponseDTO> createUpdateEmployee(@RequestBody EmployeeDTO employeeDTO) {
		String methodName = "createEmployee()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdEmployeeVO = masterService.createEmployee(employeeDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdEmployeeVO.get("message"));
			responseObjectsMap.put("employeeVO", createdEmployeeVO.get("createdEmployeeVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SetTaxRate

	@GetMapping("/getAllSetTaxRateByOrgId")
	public ResponseEntity<ResponseDTO> getAllSetTaxRateByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllSetTaxRateByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SetTaxRateVO> setTaxRateVO = new ArrayList<>();
		try {
			setTaxRateVO = masterService.getAllSetTaxRateByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SetTaxRate information get successfullyByOrgId");
			responseObjectsMap.put("setTaxRateVO", setTaxRateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "SetTaxRate information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllSetTaxRateById")
	public ResponseEntity<ResponseDTO> getAllSetTaxRateById(@RequestParam(required = false) Long id) {
		String methodName = "getAllSetTaxRateById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SetTaxRateVO> setTaxRateVO = new ArrayList<>();
		try {
			setTaxRateVO = masterService.getAllSetTaxRateById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SetTaxRate information get successfullyById");
			responseObjectsMap.put("setTaxRateVO", setTaxRateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "SetTaxRate information receive failedById",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	

	@GetMapping("/getSetTaxRateByActive")
	public ResponseEntity<ResponseDTO> getSetTaxRateByActive() {
		String methodName = "getSetTaxRateByActive()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SetTaxRateVO> setTaxRateVO = new ArrayList<>();
		try {
			setTaxRateVO = masterService.getSetTaxRateByActive();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SetTaxRate information get successfully By Active");
			responseObjectsMap.put("setTaxRateVO", setTaxRateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SetTaxRate information receive failed By Active", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// SacCode
	@GetMapping("/getAllSacCodeByOrgId")
	public ResponseEntity<ResponseDTO> getAllSapCodeByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllSacCodeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SacCodeVO> sacCodeVO = new ArrayList<>();
		try {
			sacCodeVO = masterService.getAllSacCodeByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SacCode information get successfully ByOrgId");
			responseObjectsMap.put("sacCodeVO", sacCodeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "SacCode information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllActiveSacCodeByOrgId")
	public ResponseEntity<ResponseDTO> getAllActiveSacCodeByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllActiveSacCodeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SacCodeVO> sacCodeVO = new ArrayList<>();
		try {
			sacCodeVO = masterService.getAllActiveSacCodeByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SacCode information get successfully ByOrgId");
			responseObjectsMap.put("sacCodeVO", sacCodeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "SacCode information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllSacCodeById")
	public ResponseEntity<ResponseDTO> getAllSacCodeById(@RequestParam(required = false) Long id) {
		String methodName = "getAllSacCodeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SacCodeVO> sacCodeVO = new ArrayList<>();
		try {
			sacCodeVO = masterService.getAllSacCodeById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SacCode information get successfully By OrgId");
			responseObjectsMap.put("sacCodeVO", sacCodeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "SacCode information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}



	// SubLedgerAccount

	@GetMapping("/getAllSubLedgerAccountByOrgId")
	public ResponseEntity<ResponseDTO> getAllSubLedgerAccountByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllSubLedgerAccountByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubLedgerAccountVO> subLedgerAccountVO = new ArrayList<>();
		try {
			subLedgerAccountVO = masterService.getAllSubLedgerAccountByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubLedgerAccount information get successfully ByOrgId");
			responseObjectsMap.put("subLedgerAccountVO", subLedgerAccountVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubLedgerAccount information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllSubLedgerAccountById")
	public ResponseEntity<ResponseDTO> getAllSubLedgerAccountById(@RequestParam(required = false) Long id) {
		String methodName = "getAllSubLedgerAccountById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubLedgerAccountVO> subLedgerAccountVO = new ArrayList<>();
		try {
			subLedgerAccountVO = masterService.getAllSubLedgerAccountById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubLedgerAccount information get successfully By Id");
			responseObjectsMap.put("subLedgerAccountVO", subLedgerAccountVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubLedgerAccount information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	

	@GetMapping("/getSubLedgerAccountByActive")
	public ResponseEntity<ResponseDTO> getSubLedgerAccountByActive() {
		String methodName = "getSubLedgerAccountByActive()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubLedgerAccountVO> subLedgerAccountVO = new ArrayList<>();
		try {
			subLedgerAccountVO = masterService.getSubLedgerAccountByActive();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubLedgerAccount information get successfully By Active");
			responseObjectsMap.put("subLedgerAccountVO", subLedgerAccountVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubLedgerAccount information receive failed By Active", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	
	// ListOfValues

	@GetMapping("/getListOfValuesById")
	public ResponseEntity<ResponseDTO> getListOfValuesById(@RequestParam(required = false) Long id) {
		String methodName = "getListOfValuesById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ListOfValuesVO> listOfValuesVO = new ArrayList<>();
		try {
			listOfValuesVO = masterService.getListOfValuesById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "listOfValues information get successfully By Id");
			responseObjectsMap.put("listOfValuesVO", listOfValuesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"listOfValues information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getListOfValuesByOrgId")
	public ResponseEntity<ResponseDTO> getListOfValuesByOrgId(@RequestParam(required = false) Long orgid) {
		String methodName = "getListOfValuesByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ListOfValuesVO> listOfValuesVO = new ArrayList<>();
		try {
			listOfValuesVO = masterService.getAllListOfValuesByOrgId(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "listOfValues information get successfully By OrgId");
			responseObjectsMap.put("listOfValuesVO", listOfValuesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"listOfValues information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateListOfValues")
	public ResponseEntity<ResponseDTO> updateCreateListOfValues(@Valid @RequestBody ListOfValuesDTO listOfValuesDTO) {
	    String methodName = "updateCreateListOfValues()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;

	    try {
	        ListOfValuesVO listOfValuesVO = masterService.updateCreateListOfValues(listOfValuesDTO);
	        boolean isUpdate = listOfValuesDTO.getId() != null;
	        if (listOfValuesVO != null) {
	            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, isUpdate ? "ListOfValues updated successfully" : "ListOfValues created successfully");
	            responseObjectsMap.put("listOfValuesVO", listOfValuesVO);
	            responseDTO = createServiceResponse(responseObjectsMap);
	        } else {
	            errorMsg = isUpdate ? "ListOfValues not found for ID: " + listOfValuesDTO.getId() : "ListOfValues creation failed";
	            responseDTO = createServiceResponseError(responseObjectsMap, isUpdate ? "ListOfValues update failed" : "ListOfValues creation failed", errorMsg);
	        }
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        boolean isUpdate = listOfValuesDTO.getId() != null;
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, isUpdate ? "ListOfValues update failed" : "ListOfValues creation failed", errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getListValues")
	public ResponseEntity<ResponseDTO> getListValues(@RequestParam Long orgId,@RequestParam String name) {
		String methodName = "getListValues()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String,Object>> listValues = new ArrayList<>();
		try {
			listValues = masterService.getBudgetGroup(orgId,name);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "List Values information get successfully");
			responseObjectsMap.put("listValues", listValues);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "List Values information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getSubGroups")
	public ResponseEntity<ResponseDTO> getSubGroups(@RequestParam Long orgId) {
		String methodName = "getSubGroups()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CoaVO> coaVO = new ArrayList<>();
		try {
			coaVO = masterService.getSubGroup(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sub Group information get successfully");
			responseObjectsMap.put("coaVO", coaVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Sub Group information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getLedgers")
	public ResponseEntity<ResponseDTO> getLedgers(@RequestParam Long orgId,@RequestParam List<String> accountCode) {
		String methodName = "getLedgers()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CoaVO> coaVO = new ArrayList<>();
		try {
			coaVO = masterService.getLegders(orgId,accountCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ledgers information get successfully");
			responseObjectsMap.put("coaVO", coaVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Ledgers information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/createUpdateGroupMapping")
	public ResponseEntity<ResponseDTO> createUpdateGroupMapping(@RequestBody GroupMappingDTO groupMappingDTO) {
		String methodName = "createUpdateGroupMapping()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> groupMappingVO = masterService.createUpdateGroupMapping(groupMappingDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, groupMappingVO.get("message"));
			responseObjectsMap.put("groupMappingVO", groupMappingVO.get("groupMappingVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getLedgers")
	public ResponseEntity<ResponseDTO> getGroupMappingAll(@RequestParam Long orgId) {
		String methodName = "getGroupMappingAll()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GroupMappingVO> groupMappingVO = new ArrayList<>();
		try {
			groupMappingVO = masterService.getGroupMappingAll(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group Mapping information get successfully");
			responseObjectsMap.put("groupMappingVO", groupMappingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Group Mapping information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getLedgers")
	public ResponseEntity<ResponseDTO> getGroupMappingById(@RequestParam Long id) {
		String methodName = "getGroupMappingById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<GroupMappingVO> groupMappingVO = null;
		try {
			groupMappingVO = masterService.getGroupMappingById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group Mapping information get successfully by id");
			responseObjectsMap.put("groupMappingVO", groupMappingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Group Mapping information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	

}