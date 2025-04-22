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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ebooks.elevate.common.CommonConstant;
import com.ebooks.elevate.common.UserConstants;
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.dto.TicketDTO;
import com.ebooks.elevate.entity.TicketVO;
import com.ebooks.elevate.service.TicketService;


@CrossOrigin
@RestController
@RequestMapping("/api/ticketcontroller")
public class TicketController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);
	
	@Autowired
	TicketService ticketService;
	
	@PutMapping("/updateTicket")
	public ResponseEntity<ResponseDTO> updateTicket(@Valid @RequestBody TicketDTO ticketDTO) {
		String methodName = "updateTicket()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
	        Map<String, Object> ticketVO = ticketService.updateTicket(ticketDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, ticketVO.get("message"));
	        responseObjectsMap.put("ticketVO", ticketVO.get("ticketVO")); // Corrected key
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadTicketScreenShotInBloob")
	public ResponseEntity<ResponseDTO> uploadTicketScreenShotInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadTicketScreenShotInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		TicketVO ticketVO = null;
		try {
			ticketVO = ticketService.uploadTicketScreenShotInBloob(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload PartImage", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket ScreenShot Successfully Upload");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Ticket ScreenShot Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getTicketById")
	public ResponseEntity<ResponseDTO> getTicketById(@RequestParam(required = false) Long id) {
		String methodName = "getTicketById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<TicketVO> eltCompanyVO = null;
		try {
			eltCompanyVO = ticketService.getTicketById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();   
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket information get successfully By Id");
			responseObjectsMap.put("eltCompanyVO", eltCompanyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Ticket information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getTicketByUserId")
	public ResponseEntity<ResponseDTO> getTicketByUserId(@RequestParam(required = false) Long userId) {
		String methodName = "getTicketByUserId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TicketVO> eltCompanyVO = new ArrayList<TicketVO>();
		try {
			eltCompanyVO = ticketService.getTicketByUserId(userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();   
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket information get successfully By userId");
			responseObjectsMap.put("eltCompanyVO", eltCompanyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Ticket information receive failed By UserId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getTicketByOrgId")
	public ResponseEntity<ResponseDTO> getTicketByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getTicketByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TicketVO> eltCompanyVO = new ArrayList<TicketVO>();
		try {
			eltCompanyVO = ticketService.getTicketByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();   
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket information get successfully By orgId");
			responseObjectsMap.put("eltCompanyVO", eltCompanyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Ticket information receive failed By orgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
